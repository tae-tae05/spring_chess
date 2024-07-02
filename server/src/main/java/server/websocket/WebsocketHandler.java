package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.*;
import model.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import websocket.commands.*;
import websocket.messages.ErrorM;
import websocket.messages.NotificationM;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@WebSocket
public class WebsocketHandler {
    private final GameService gameService;
    private DataAccess data;
    private SessionsManager manager;
    private Gson json = new Gson();
    public WebsocketHandler(GameService gameService, DataAccess data){
        this.gameService = gameService;
        manager = new SessionsManager();
        this.data = data;
    }
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case CONNECT -> {
                if(action.getPlayerColor() == null){
                    joinAsSpectator(new ConnectCommand(action.getAuthString(), action.getGameID(), action.getPlayerColor()), session);
                }
                else {
                    join(new ConnectCommand(action.getAuthString(), action.getGameID(), action.getPlayerColor()), session);
                }
            }
//            case MAKE_MOVE ->  makeMove(new MakeMoveCommand( action.getAuthString(), action.getGameID(), action.getMove()), session);
//            case LEAVE -> leave(new LeaveCommand(action.getAuthString(), action.getGameID()), session);
//            case RESIGN -> resign(new ResignCommand(action.getAuthString(), action.getGameID(), action.getPlayerColor()), session));
        }
    }

    public void join(ConnectCommand command, Session session) throws IOException {
        AuthData auth = new AuthData(null, null);
        try{
            AuthDAO authDAO = data.getAuthDAO();
            String username = authDAO.getUsername(new AuthData(null, command.getAuthString()));
            auth = authDAO.getAuth(username);
        }
        catch(Exception e){
            manager.sendError(session, "ERROR: not authorized");
            manager.sendMessage(session, "ERROR: not authorized");
            return;
        }
        GameDAO gameDAO = data.getGameDAO();
        GameData game = new GameData(null, null, null, null, new ChessGame());
        //does the game exist?
        boolean gameReal = gameDAO.verifyGame(command.getGameID());
        if(!gameReal){
            manager.sendError(session, "ERROR: game does not exist");
            manager.sendMessage(session, "ERROR: game does not exist");
            return;
        }
        //find the game because i didn't make a separate function...
        Collection<GameData> games = gameDAO.listGames();
        for(GameData tempGame: games){
            if(tempGame.gameID() == command.getGameID()){
                game = tempGame;
                break;
            }
        }
        //if spot is not open?
        if(command.getTeamColor() == ChessGame.TeamColor.WHITE && game.whiteUsername() != null){
            manager.sendError(session, "ERROR: white spot is taken already");
            manager.sendMessage(session, "ERROR: white spot is taken already");
            return;
        }
        else if(command.getTeamColor() == ChessGame.TeamColor.BLACK && game.blackUsername() != null){
            manager.sendError(session, "ERROR: black spot is taken already");
            manager.sendMessage(session, "ERROR: black spot is taken already");
            return;
        }
        //add observer here later
        manager.addSession(command.getGameID(), session);
        ServerMessage loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        String loadGameJson = json.toJson(loadGame);
        manager.sendMessage(session, loadGameJson);
        String temp = auth.username() + " has joined as ";
        if(command.getTeamColor().equals(ChessGame.TeamColor.WHITE)){
            temp += " white.";
        }
        else{
            temp += "black.";
        }
        NotificationM notify = new NotificationM(temp);
        String notifyJson = json.toJson(notify);
        manager.broadcast(notifyJson, game.gameID(), session);

    }

    public void joinAsSpectator(ConnectCommand command, Session session) throws IOException{

    }
}
