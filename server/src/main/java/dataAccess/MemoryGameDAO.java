package dataAccess;

import chess.ChessGame;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.Optional;

public class MemoryGameDAO implements GameDAO{
    @Override
    public GameData getGame(GameData game) throws DataAccessException{
        for(GameData spot:games) {
            if (spot.equals(game)){
                return spot;
            }
        }
        throw new DataAccessException("game does not exist");
    }

    @Override
    public GameData verifyGamePosition() throws DataAccessException{
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return games;
    }

    @Override
    public void updateGames(ChessGame game) {

    }

    @Override
    public void deleteGames() {
        games.clear();
    }
}
