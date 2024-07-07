package server.websocket;

import chess.*;
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
        switch (action.getCommandType()) {
            case CONNECT -> {
                join(new ConnectCommand(action.getAuthString(), action.getGameID(), action.getPlayerColor()), session);
//                if(action.getPlayerColor() == null){
//                    joinAsSpectator(new ConnectCommand(action.getAuthString(), action.getGameID(), action.getPlayerColor()), session);
//                }
//                else {
//                    join(new ConnectCommand(action.getAuthString(), action.getGameID(), action.getPlayerColor()), session);
//                }
            }
            case MAKE_MOVE ->  makeMove(new MakeMoveCommand(action.getAuthString(), action.getGameID(), action.getMove()), session);
            case LEAVE -> leave(new LeaveCommand(action.getAuthString(), action.getGameID()), session);
            case RESIGN -> resign(new ResignCommand(action.getAuthString(), action.getGameID(), action.getPlayerColor()), session);
        }
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
        try {
            //get the team color
            ChessGame.TeamColor myColor = null;
            ChessGame.TeamColor opponentColor = null;
            if(auth.username().equals(game.blackUsername())){
                myColor = ChessGame.TeamColor.BLACK;
                opponentColor = ChessGame.TeamColor.WHITE;
            }
            else if(auth.username().equals(game.whiteUsername())){
                myColor = ChessGame.TeamColor.WHITE;
                opponentColor = ChessGame.TeamColor.BLACK;
            }
            else{
                manager.sendMessage(session, "ERROR: You are not authorized to make a move");
                manager.sendError(session, "ERROR: You are not authorized to make a move");
            }
            //check game status and other illegal stuffs
            if (game.game().getGameOverStatus()) {
                manager.sendMessage(session, "ERROR: game over. There are no moves left to make");
                manager.sendError(session, "ERROR: game over. There are no moves left to make");
                return;
            }
            if(game.game().getBoard().getPiece(makeMove.getMove().getStartPosition()).getTeamColor() != myColor){
                manager.sendMessage(session, "ERROR: stop trying to move pieces that aren't yours");
                manager.sendError(session, "ERROR: stop trying to move pieces that aren't yours");
                return;
            }
            //make move
            ChessGame newGame = game.game();
            ChessBoard board = newGame.getBoard();
            ChessPiece piece = board.getPiece(makeMove.getMove().getStartPosition());
            newGame.makeMove(makeMove.getMove());
            String message = auth.username() + " has made the move " + makeMove.getMove().getStartPosition() + " to " +  makeMove.getMove().getEndPosition();
            NotificationM notify = new NotificationM(message);
            String notifyJson = json.toJson(notify);
            manager.broadcast(notifyJson, game.gameID(), session);
            LoadGameM load = new LoadGameM(game.game(), "");
            notifyJson = json.toJson(load);
            manager.sendMessage(session, notifyJson);
            manager.broadcast(notifyJson, game.gameID(), session);
            //check if making move has ended the game
            if(newGame.isInStalemate(myColor)){
                gameStatusUpdate("You are now in stalemate. The game is over!", game.gameID(), session, opponentColor, myColor);
//                notify = new NotificationM("You are now in stalemate. The game is over!");
//                notifyJson = json.toJson(notify);
//                manager.sendMessage(session, notifyJson);
//                manager.broadcast(notifyJson, game.gameID(), session);
            }
            if(newGame.isInCheckmate(opponentColor)){
                String m = opponentColor + " is in checkmate. " +  myColor + " has won the game!";
                gameStatusUpdate(m, game.gameID(), session, opponentColor, myColor);
//                notify = new NotificationM(m);
//                notifyJson = json.toJson(notify);
//                manager.sendMessage(session, notifyJson);
//                manager.broadcast(notifyJson, game.gameID(), session);
                newGame.setGameOver();
            }
            else if(newGame.isInCheckmate(opponentColor)){
                String m = opponentColor + " is in check.";
                gameStatusUpdate(m, game.gameID(), session, opponentColor, myColor);
//                notify = new NotificationM(m);
//                notifyJson = json.toJson(notify);
//                manager.sendMessage(session, notifyJson);
//                manager.broadcast(notifyJson, game.gameID(), session);
            }
            newGame.setTeamTurn(opponentColor);
            game = game.setGame(newGame);
            data.getGameDAO().updateGame(game.game(), game.gameID());
        } catch (IOException | DataAccessException | InvalidMoveException e) {
            throw new RuntimeException(e);
        }

    }

    public void gameStatusUpdate(String m, int gameID, Session session, ChessGame.TeamColor opponentColor, ChessGame.TeamColor myColor) throws IOException {
        NotificationM notify = new NotificationM(m);
        String notifyJson = json.toJson(notify);
        manager.sendMessage(session, notifyJson);
        manager.broadcast(notifyJson, gameID, session);
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
            if(game != null) {
                if(game.whiteUsername() != null) {
                    if (game.whiteUsername().equals(username)) {
                        updateGame(ChessGame.TeamColor.WHITE, leave.getGameID());
                    }
                }
                if (game.blackUsername() != null) {
                    if(game.blackUsername().equals(username)) {
                        updateGame(ChessGame.TeamColor.BLACK, leave.getGameID());
                    }
                }
                String message = username + " has left the game";
                String notifyJson = json.toJson(message);
                manager.broadcast(notifyJson, game.gameID(), session);
                manager.removeSession(game.gameID(), session);
            }
        }
        catch (Exception e){
            System.out.println("leave request could not be granted - " + e.getMessage());
        }
    }

    public void updateGame(ChessGame.TeamColor color, int gameID){
        if(color.equals(ChessGame.TeamColor.WHITE)){
            data.getGameDAO().makeUsernameNull(ChessGame.TeamColor.WHITE, gameID);
        }
        else{
            data.getGameDAO().makeUsernameNull(ChessGame.TeamColor.BLACK, gameID);
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
//        if(command.getPlayerColor() == null){
//        }
//        else if(command.getPlayerColor() == ChessGame.TeamColor.WHITE && game.whiteUsername() != null){
//            manager.sendError(session, "ERROR: white spot is taken already");
//            manager.sendMessage(session, "ERROR: white spot is taken already");
//            return;
//        }
//        else if(command.getPlayerColor() == ChessGame.TeamColor.BLACK && game.blackUsername() != null){
//            manager.sendError(session, "ERROR: black spot is taken already");
//            manager.sendMessage(session, "ERROR: black spot is taken already");
//            return;
//        }
        manager.addSession(command.getGameID(), session);
        LoadGameM loaded = new LoadGameM(game.game(),"Welcome to " + game.gameName() + "!");
        String loadGameJson = json.toJson(loaded);
        manager.sendMessage(session, loadGameJson);
        String temp = auth.username();
        if(command.getPlayerColor() == null){
            temp += " has joined as an observer.";
        }
        else if(command.getPlayerColor().equals(ChessGame.TeamColor.WHITE)){
            temp += " has joined as white.";
        }
        else if (command.getPlayerColor().equals(ChessGame.TeamColor.BLACK)){
            temp += " has joined as black.";
        }
        NotificationM notify = new NotificationM(temp);
        String notifyJson = json.toJson(notify);
        manager.broadcast(notifyJson, game.gameID(), session);
    }

    public AuthData checkCredentials(String auth){
        AuthData authData = null;
        AuthDAO authDAO = data.getAuthDAO();
        String userName = authDAO.getUsername(auth);
        authData = authDAO.getAuth(userName);
        return authData;
    }

}
