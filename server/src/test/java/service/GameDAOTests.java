package service;
import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MemoryGameDAO;
import model.GameData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class GameDAOTests {
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
    @Test
    public void verify() throws DataAccessException{
        MemoryGameDAO testGames = new MemoryGameDAO();
        testGames.addGame(new GameData(1345, "Jin", "Unnie",
                "beat unnie", new ChessGame()));
        testGames.addGame(new GameData(2345, null, "Joon",
                "bin beat joon", new ChessGame()));
        testGames.addGame(new GameData(3345, "Unnie", null,
                "STALEMATE", new ChessGame()));
        GameData comparison = testGames.verifyGamePosition(3345, ChessGame.TeamColor.BLACK, "Bin");
        GameData comparison2 = testGames.verifyGamePosition(2345, ChessGame.TeamColor.WHITE, "Bin");


        Assertions.assertEquals(new GameData(3345, "Unnie", "Bin",
                "STALEMATE", new ChessGame()), comparison,
                "black was not updated correctly");
        Assertions.assertEquals(new GameData(2345, "Bin", "Joon",
                        "bin beat joon", new ChessGame()), comparison2,
                "white was not updated correctly");
    }

}
