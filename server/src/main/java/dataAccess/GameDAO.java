package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public interface GameDAO {
    Collection<GameData> games = new ArrayList<>();
    ChessGame getGame(GameData game);
    void updateGames(ChessGame game);
    Collection<GameData> listGames();
    boolean verifyGamePosition();
    void deleteGames();
}
