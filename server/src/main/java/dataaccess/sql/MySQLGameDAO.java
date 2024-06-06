package dataaccess.sql;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;

import java.sql.SQLException;
import java.util.Collection;

public class MySQLGameDAO extends MySQLDAO implements GameDAO {

    public MySQLGameDAO() throws DataAccessException, SQLException {
    }

    @Override
    public Collection<GameData> listGames() {
        return null;
    }

    @Override
    public void addGame(GameData game) throws DataAccessException {

    }

    @Override
    public boolean verifyBlackPosition(int gameID) {
        return false;
    }

    @Override
    public boolean verifyWhitePosition(int gameID) {
        return false;
    }

    @Override
    public void deleteGames() {

    }

    @Override
    public void insertUsername(int gameID, String newUsername, ChessGame.TeamColor color) {

    }

    @Override
    public boolean verifyGame(Integer gameID) {
        return false;
    }
}
