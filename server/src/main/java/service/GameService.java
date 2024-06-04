package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import request.JoinGameRequest;
import results.*;
import spark.Response;

import java.util.Random;

public class GameService {
    private MemoryGameDAO gameDAO = new MemoryGameDAO();
    private MemoryAuthDAO authDAO = new MemoryAuthDAO();

    public CreateGameResults createGame(GameData game, AuthData auth, Response response) throws DataAccessException {
        CreateGameResults results = new CreateGameResults();
        if(!authDAO.verifyUserAuth(auth)){
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
            gameDAO.addGame(game);
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
        if(!authDAO.verifyUserAuth(auth)){
            results = results.setMessage("Error: unauthorized");
            response.status(401);
            return results;
        }
        if(join.getTeamColor() == null || join.getGameID() == null || !gameDAO.verifyGame(join.getGameID())){
            results = results.setMessage("Error: bad request");
            response.status(400);
            return results;
        }
        if(join.getTeamColor() == ChessGame.TeamColor.BLACK && !gameDAO.verifyBlackPosition(join.getGameID())){
            results = results.setMessage("Error: already taken");
            response.status(403);
            return results;
        }
        if(join.getTeamColor() == ChessGame.TeamColor.WHITE && !gameDAO.verifyWhitePosition(join.getGameID())){
            results = results.setMessage("Error: already taken");
            response.status(403);
            return results;
        }
        try{
            String username = authDAO.getUsername(auth);
            gameDAO.insertUsername(join.getGameID(), username, join.getTeamColor());
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
        if(!authDAO.verifyUserAuth(auth)){
            results = results.setMessage("Error: unauthorized");
            response.status(401);
            return results;
        }
        try{
            results = results.setGames(gameDAO.listGames());
            response.status(200);
        }
        catch(Exception e){
            results = results.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return results;
    }

}
