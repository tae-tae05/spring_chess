package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.*;
import model.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import websocket.commands.*;
import websocket.messages.*;

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
//        switch (action.getCommandType()) {
//            case CONNECT -> {
//                if(action.getPlayerColor() == null){
//                    joinAsSpectator(new ConnectCommand(action.getAuthString(), action.getGameID(), action.getPlayerColor()), session);
//                }
//                else {
//                    join(new ConnectCommand(action.getAuthString(), action.getGameID(), action.getPlayerColor()), session);
//                }
//            }
//            case MAKE_MOVE ->  makeMove(new MakeMoveCommand(action.getAuthString(), action.getGameID(), action.getMove()), session);
//            case LEAVE -> leave(new LeaveCommand(action.getAuthString(), action.getGameID()), session);
//            case RESIGN -> resign(new ResignCommand(action.getAuthString(), action.getGameID(), action.getPlayerColor()), session);
//        }
    }

    @OnWebSocketError
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }
    public void makeMove(MakeMoveCommand makeMove, Session session) throws IOException{
        AuthData auth = checkCredentials(makeMove.getAuthString());
        if(auth == null){
            manager.sendError(session, "ERROR: not authorized");
            manager.sendMessage(session, "ERROR: not authorized");
            return;
        }
        GameData game = null;
        for (GameData currentGame : data.getGameDAO().listGames()) {
            if(currentGame.gameID() ==  makeMove.getGameID()) {
                game = currentGame;
            }
        }
        if(game.game().getGameOverStatus()){
            manager.sendMessage(session, "ERROR: game over. There are no moves left to make");
            return;
        }

    }

    public void leave(LeaveCommand leave, Session session) throws IOException{
        GameData game = null;
        for (GameData currentGame : data.getGameDAO().listGames()) {
            if(currentGame.gameID() ==  leave.getGameID()) {
                game = currentGame;
            }
        }
        String username = data.getAuthDAO().getUsername(leave.getAuthString());
        try {
            if (game.whiteUsername().equals(username)) {
                //TODO - update game
                return;
            }
            if (game.blackUsername().equals(username)) {
                //TODO - update game
                return;
            }
            String message = username + " has left the game";
            manager.broadcast(message, game.gameID(), session);
            manager.removeSession(game.gameID(), session);
        }
        catch (Exception e){
            System.out.println("leave request could not be granted - " + e.getMessage());
        }
    }

    public void resign(ResignCommand resign, Session session) throws IOException{

    }

    public void join(ConnectCommand command, Session session) throws IOException {
        AuthData auth = checkCredentials(command.getAuthString());
        if(auth == null){
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
        AuthData auth = checkCredentials(command.getAuthString());
        if(auth == null){
            manager.sendError(session, "ERROR: not authorized");
            manager.sendMessage(session, "ERROR: not authorized");
            return;
        }
        try{
            manager.addSession(command.getGameID(), session);
            GameDAO gameDAO = data.getGameDAO();
            AuthDAO authDAO = data.getAuthDAO();
            GameData gameData= null;
            Collection<GameData> games = gameDAO.listGames();
            for(GameData tempGame: games){
                if(tempGame.gameID() == command.getGameID()){
                    gameData = tempGame;
                    break;
                }
            }
            if(gameData != null) {
                String message = "Enjoy watching " + gameData.gameName() + "!";
                manager.sendMessage(session, message);
                NotificationM notify = new NotificationM("An observer has joined");
                String notifyJson = json.toJson(notify);
                manager.broadcast(notifyJson, gameData.gameID(), session);
            }
            else{
                manager.sendError(session, "ERROR: Could not join as a spectator");
                manager.sendMessage(session, "ERROR: Could not join as a spectator");
            }
        } catch (Exception e) {
            System.out.println("could not join as a spectator - " + e.getMessage());
        }
    }

    public AuthData checkCredentials(String auth){
        AuthData authData = null;
        AuthDAO authDAO = data.getAuthDAO();
        String userName = authDAO.getUsername(auth);
        authData = authDAO.getAuth(userName);
        return authData;
    }
}
