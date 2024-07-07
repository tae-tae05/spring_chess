package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface GameDAO {
    List<GameData> GAMES = new ArrayList<>();
    void addGame(GameData game) throws DataAccessException, SQLException;

    void makeUsernameNull(ChessGame.TeamColor color, int gameID);

    //    GameData getGame(GameData game) throws DataAccessException;
    Collection<GameData> listGames();
    boolean verifyWhitePosition(int gameID);
    boolean verifyBlackPosition(int gameID);
    void deleteGames() throws DataAccessException, SQLException;
    void insertUsername(int gameID, String newUsername, ChessGame.TeamColor color) throws DataAccessException, SQLException;
    boolean verifyGame(Integer gameID);
    void updateGame(ChessGame newGame, int gameID) throws DataAccessException;
}
