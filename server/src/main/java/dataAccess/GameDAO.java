package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public interface GameDAO {
    Collection<GameData> games = new ArrayList<>();
    GameData getGame(GameData game) throws DataAccessException;
    void updateGames(ChessGame game);
    Collection<GameData> listGames();
    GameData verifyGamePosition() throws DataAccessException;
    void deleteGames();
}
