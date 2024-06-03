package service;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import results.ClearResults;
import results.ListGameResults;
import spark.Response;

import java.util.Collection;
import java.util.List;

public class GameService {
    private MemoryGameDAO GAMES_DB = new MemoryGameDAO();
    private MemoryAuthDAO AUTH_DB = new MemoryAuthDAO();

    public void createGame(GameData game) throws DataAccessException {
        GAMES_DB.addGame(game);
    }
    public GameData joinGame(int gameID, ChessGame.TeamColor teamColor, String username) throws DataAccessException {
        return GAMES_DB.verifyGamePosition(gameID,teamColor, username);
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
