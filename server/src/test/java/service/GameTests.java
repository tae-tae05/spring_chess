package service;
import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collection;

public class GameTests {
    @Test
    public void gamer(){
        GameData game = new GameData(1345, "Jin", "Unnie", "beat unnie", new ChessGame());
    }

    @Test
    public void addGames() throws DataAccessException{
        MemoryGameDAO testGames = new MemoryGameDAO();
        ChessGame game = new ChessGame();
        testGames.addGame(new GameData(1345, "Jin", "Unnie",
                "beat unnie", game));

        Assertions.assertEquals(1, testGames.listGames().size(),
                "game was not added correctly");
    }
    @Test
    public void clearGames() throws DataAccessException{
        MemoryGameDAO testGames = new MemoryGameDAO();
        ChessGame game = new ChessGame();
        testGames.addGame(new GameData(1345, "Jin", "Unnie",
                "beat unnie", game));
        testGames.deleteGames();

        Assertions.assertEquals(0, testGames.listGames().size(),
                "game was not deleted correctly");
    }
}
