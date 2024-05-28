package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public class MemoryGameDAO implements GameDAO{
    @Override
    public ChessGame getGame(GameData game) {
        return null;
    }

    @Override
    public boolean verifyGamePosition() {
        return false;
    }

    @Override
    public Collection<GameData> listGames() {
        return null;
    }

    @Override
    public void updateGames(ChessGame game) {

    }

    @Override
    public void deleteGames() {

    }
}
