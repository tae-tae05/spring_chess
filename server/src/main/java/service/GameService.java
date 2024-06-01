package service;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.MemoryGameDAO;
import model.GameData;

import java.util.Collection;
import java.util.List;

public class GameService {
    private MemoryGameDAO GAMES_DB = new MemoryGameDAO();

    public void createGame(GameData game) throws DataAccessException {
        GAMES_DB.addGame(game);
    }
    public GameData joinGame(int gameID, ChessGame.TeamColor teamColor, String username) throws DataAccessException {
        return GAMES_DB.verifyGamePosition(gameID,teamColor, username);
    }
    public void clearGames(){
        GAMES_DB.deleteGames();
    }
    public Collection<GameData> listGames(){
        return GAMES_DB.listGames();
    }
}
