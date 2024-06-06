package dataaccess;

import chess.ChessGame;
import dataaccess.sql.MySQLGameDAO;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class GameDAOTests {
    private static GameDAO gameDAO;

    static {
        try {
            gameDAO = new MySQLGameDAO();

            // Create DB if not exists
            DatabaseManager.createDatabase();

        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private final GameData one = new GameData(235, "jin", "jj", "beat jj!", new ChessGame());
    private final GameData two = new GameData(743, "bin", "joon", "who will win?", new ChessGame());

    private final GameData three = new GameData(23, "min", "bin", "fighto", new ChessGame());

    @BeforeEach
    public void setUp() throws SQLException, DataAccessException {
        Server.configureDatabase();
        gameDAO.deleteGames();
    }

    @Test
    public void deleteGamesPass() throws SQLException, DataAccessException {
        gameDAO.addGame(one);
        gameDAO.addGame(two);
        gameDAO.addGame(three);
        gameDAO.deleteGames();
        Assertions.assertFalse(gameDAO.verifyGame(one.gameID()));
        Assertions.assertFalse(gameDAO.verifyGame(two.gameID()));
        Assertions.assertFalse(gameDAO.verifyGame(three.gameID()));
    }
    @Test
    public void addGamesPass() throws SQLException, DataAccessException {
        gameDAO.addGame(one);
        Assertions.assertTrue(gameDAO.verifyGame(one.gameID()));
    }
    @Test
    public void addGamesFail() throws SQLException, DataAccessException {
        gameDAO.addGame(one);
        Assertions.assertFalse(gameDAO.verifyGame(two.gameID()));
    }

    @Test
    public void verifyGamePass() throws DataAccessException, SQLException {
        gameDAO.addGame(two);
        Assertions.assertTrue(gameDAO.verifyGame(two.gameID()));
    }
    @Test
    public void verifyGameFail() throws DataAccessException, SQLException {
        gameDAO.addGame(three);
        Assertions.assertFalse(gameDAO.verifyGame(two.gameID()));
    }

    @Test
    public void checkWhitePass() throws SQLException, DataAccessException {
        GameData temp = new GameData(34, null, null, "testing null", new ChessGame());
        gameDAO.addGame(temp);
        Assertions.assertTrue(gameDAO.verifyWhitePosition(temp.gameID()));
    }
    @Test
    public void checkWhiteFail() throws SQLException, DataAccessException {
        GameData temp = new GameData(34, "jin", null, "testing null", new ChessGame());
        gameDAO.addGame(temp);
        Assertions.assertFalse(gameDAO.verifyWhitePosition(temp.gameID()));
    }

    @Test
    public void checkBlackPass() throws SQLException, DataAccessException {
        GameData temp = new GameData(234, null, null, "testing null", new ChessGame());
        gameDAO.addGame(temp);
        Assertions.assertTrue(gameDAO.verifyBlackPosition(temp.gameID()));
    }
    @Test
    public void checkBlackFail() throws SQLException, DataAccessException {
        GameData temp = new GameData(464, null, "hello", "testing null", new ChessGame());
        gameDAO.addGame(temp);
        Assertions.assertFalse(gameDAO.verifyBlackPosition(temp.gameID()));
    }
    @Test
    public void insertUsernamePass() throws SQLException, DataAccessException {
        GameData temp = new GameData(34, null, null, "testing null", new ChessGame());
        gameDAO.addGame(temp);
        gameDAO.insertUsername(temp.gameID(), "whiteUser", ChessGame.TeamColor.WHITE);
        String sql = "SELECT whiteUsername FROM game WHERE gameID= '" + 34 + "'";
        try (var connection = DatabaseManager.getConnection()) {
            var pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            rs.next();
            System.out.println(rs.getString("whiteUsername"));
        }
        Assertions.assertFalse(gameDAO.verifyWhitePosition(temp.gameID()));
    }
    @Test
    public void insertUsernameFail() throws SQLException, DataAccessException {
        GameData temp = new GameData(34, null, "hey", "testing null", new ChessGame());
        gameDAO.addGame(temp);

        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.insertUsername(temp.gameID(), "blackUser", ChessGame.TeamColor.BLACK));
    }

    @Test
    public void getGamesSuccess() throws SQLException, DataAccessException {
        gameDAO.addGame(one);
        gameDAO.addGame(two);
        gameDAO.addGame(three);
        Collection<GameData> allGames = gameDAO.listGames();
        for(GameData game: allGames){
            System.out.println(game);
        }
        Assertions.assertTrue(gameDAO.verifyGame(one.gameID()));
        Assertions.assertTrue(gameDAO.verifyGame(two.gameID()));
        Assertions.assertTrue(gameDAO.verifyGame(three.gameID()));
    }
    @Test
    public void getGamesFail() throws SQLException, DataAccessException {
        gameDAO.addGame(one);
        gameDAO.addGame(two);
        Assertions.assertTrue(gameDAO.verifyGame(one.gameID()));
        Assertions.assertTrue(gameDAO.verifyGame(two.gameID()));
        Assertions.assertFalse(gameDAO.verifyGame(three.gameID()));
    }

}
