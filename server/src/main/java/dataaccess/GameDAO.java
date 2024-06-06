package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface GameDAO {
    List<GameData> GAMES = new ArrayList<>();
    void addGame(GameData game) throws DataAccessException;
//    GameData getGame(GameData game) throws DataAccessException;
    Collection<GameData> listGames();
    boolean verifyWhitePosition(int gameID);
    boolean verifyBlackPosition(int gameID);
    void deleteGames();
    void insertUsername(int gameID, String newUsername, ChessGame.TeamColor color);
    boolean verifyGame(Integer gameID);
}
