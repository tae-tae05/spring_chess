package dataAccess;

import chess.ChessGame;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface GameDAO {
    List<GameData> games = new ArrayList<>();
    void addGame(GameData game) throws DataAccessException;
//    GameData getGame(GameData game) throws DataAccessException;
    void updateGames(GameData newGame) throws DataAccessException;
    Collection<GameData> listGames();

    boolean verifyWhitePosition(int gameID);
    boolean verifyBlackPosition(int gameID);
    void deleteGames();
    void insertUsername(int gameID, String newUsername, ChessGame.TeamColor color);
}
