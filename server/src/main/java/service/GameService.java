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
    private MemoryGameDAO GAMES_DAO = new MemoryGameDAO();
    private MemoryAuthDAO AUTH_DAO = new MemoryAuthDAO();

    public CreateGameResults createGame(GameData game, AuthData auth, Response response) throws DataAccessException {
        CreateGameResults results = new CreateGameResults();
        if(!AUTH_DAO.verifyUserAuth(auth)){
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
            GAMES_DAO.addGame(game);
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
        if(!AUTH_DAO.verifyUserAuth(auth)){
            results = results.setMessage("Error: unauthorized");
            response.status(401);
            return results;
        }
        if(join.getTeamColor() == null || join.getGameID() == null || !GAMES_DAO.verifyGame(join.getGameID())){
            results = results.setMessage("Error: bad request");
            response.status(400);
            return results;
        }
        if(join.getTeamColor() == ChessGame.TeamColor.BLACK && !GAMES_DAO.verifyBlackPosition(join.getGameID())){
            results = results.setMessage("Error: already taken");
            response.status(403);
            return results;
        }
        if(join.getTeamColor() == ChessGame.TeamColor.WHITE && !GAMES_DAO.verifyWhitePosition(join.getGameID())){
            results = results.setMessage("Error: already taken");
            response.status(403);
            return results;
        }
        try{
            String username = AUTH_DAO.getUsername(auth);
            GAMES_DAO.insertUsername(join.getGameID(), username, join.getTeamColor());
            response.status(200);
        }
        catch(Exception e){
            results = results.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return results;
    }

    public void printGames(){
        for(GameData game : GAMES_DAO.listGames()){
            System.out.println(game.toString());
        }
    }

    public ListGameResults listGames(AuthData auth, Response response){
        ListGameResults results = new ListGameResults(null, null);
        if(!AUTH_DAO.verifyUserAuth(auth)){
            results = results.setMessage("Error: unauthorized");
            response.status(401);
            return results;
        }
        try{
            results = results.setGames(GAMES_DAO.listGames());
            response.status(200);
        }
        catch(Exception e){
            results = results.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return results;
    }

}
