package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;

public interface GameDAO {
    ChessGame getGame(GameData game);
    void updateGames(ChessGame game);
    Collection<GameData> listGames();
    boolean verifyGamePosition();
    void deleteGames();
}
