package service;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import request.JoinGameRequest;
import results.*;
import spark.Response;

import java.util.Random;

public class GameService {
    private MemoryGameDAO GAMES_DB = new MemoryGameDAO();
    private MemoryAuthDAO AUTH_DB = new MemoryAuthDAO();

    public CreateGameResults createGame(GameData game, AuthData auth, Response response) throws DataAccessException {
        CreateGameResults results = new CreateGameResults();
        if(!AUTH_DB.verifyUserAuth(auth)){
            results.setMessage("Error: unauthorized");
            response.status(401);
            return results;
        }
        if(game.gameName() == null){
            results.setMessage("Error: bad request");
            response.status(400);
            return results;
        }
        try{
            Random rand = new Random();
            int tempGameID = rand.nextInt((1000) + 1);
            game = game.setGameID(tempGameID);
            GAMES_DB.addGame(game);
            results.setGameID(game.gameID());
        }
        catch(Exception e){
            results.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return results;
    }
    public LogoutAndJoinResults joinGame(JoinGameRequest join, AuthData auth, Response response) throws DataAccessException {
        LogoutAndJoinResults results = new LogoutAndJoinResults(null);
        System.out.println(join.toString());
        if(!AUTH_DB.verifyUserAuth(auth)){
            results = results.setMessage("Error: unauthorized");
            response.status(401);
            return results;
        }
        if(join.getTeamColor() == null){
            results = results.setMessage("Error: bad request");
            response.status(400);
            return results;
        }
//        if(join.getTeamColor() == ChessGame.TeamColor.BLACK && GAMES_DB.verifyBlackPosition(join.getGameID()))
        if(join.getTeamColor().equals("BLACK") && GAMES_DB.verifyBlackPosition(join.getGameID())){
            results = results.setMessage("Error: already taken");
            response.status(403);
            return results;
        }
        if(join.getTeamColor().equals("WHITE") && GAMES_DB.verifyWhitePosition(join.getGameID())){
            results = results.setMessage("Error: already taken");
            response.status(400);
            return results;
        }
        try{
            if(join.getTeamColor().equals("BLACK")){
                GAMES_DB.insertUsername(join.getGameID(), auth.Username(), ChessGame.TeamColor.BLACK);
            }
            else{
                GAMES_DB.insertUsername(join.getGameID(), auth.Username(), ChessGame.TeamColor.WHITE);
            }
            response.status(200);
        }
        catch(Exception e){
            results = results.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return results;
    }

    public ListGameResults listGames(AuthData auth, Response response){
        ListGameResults results = new ListGameResults(null, null);
        if(!AUTH_DB.verifyUserAuth(auth)){
            results = results.setMessage("Error: unauthorized");
            response.status(401);
            return results;
        }
        try{
            results = results.setGames(GAMES_DB.listGames());
            response.status(200);
        }
        catch(Exception e){
            results = results.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return results;
    }
}
