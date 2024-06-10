package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;

import model.AuthData;
import model.GameData;
import request.JoinGameRequest;
import results.*;
import spark.Response;

import java.sql.SQLException;
import java.util.Random;

public class GameService {
    private final DataAccess data;

    public GameService(DataAccess data){
        this.data = data;
    }

    public CreateGameResults createGame(GameData game, AuthData auth, Response response) throws DataAccessException, SQLException {
        CreateGameResults results = new CreateGameResults();
        if(!data.getAuthDAO().verifyUserAuth(auth)){
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
            data.getGameDAO().addGame(game);
            results.setGameID(game.gameID());
        }
        catch(Exception e){
            results.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return results;
    }
    public LogoutAndJoinResults joinGame(JoinGameRequest join, AuthData auth, Response response) throws DataAccessException, SQLException {
        LogoutAndJoinResults results = new LogoutAndJoinResults(null);
        if(!data.getAuthDAO().verifyUserAuth(auth)){
            results = results.setMessage("Error: unauthorized");
            response.status(401);
            return results;
        }
        if(join.getTeamColor() == null || join.getGameID() == null || !data.getGameDAO().verifyGame(join.getGameID())){
            results = results.setMessage("Error: bad request");
            response.status(400);
            return results;
        }
        if(join.getTeamColor() == ChessGame.TeamColor.BLACK && !data.getGameDAO().verifyBlackPosition(join.getGameID())){
            results = results.setMessage("Error: already taken");
            response.status(403);
            return results;
        }
        if(join.getTeamColor() == ChessGame.TeamColor.WHITE && !data.getGameDAO().verifyWhitePosition(join.getGameID())){
            results = results.setMessage("Error: already taken");
            response.status(403);
            return results;
        }
        try{
            String username = data.getAuthDAO().getUsername(auth);
            data.getGameDAO().insertUsername(join.getGameID(), username, join.getTeamColor());
            response.status(200);
        }
        catch(Exception e){
            results = results.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return results;
    }

    public ListGameResults listGames(AuthData auth, Response response) throws SQLException, DataAccessException {
        ListGameResults results = new ListGameResults(null, null);
        if(!data.getAuthDAO().verifyUserAuth(auth)){
            results = results.setMessage("Error: unauthorized");
            response.status(401);
            return results;
        }
        try{
            results = results.setGames(data.getGameDAO().listGames());
//            for(GameData game : results.games()){
//                System.out.println(game.toString());
//            }
            response.status(200);
        }
        catch(Exception e){
            results = results.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return results;
    }

}
