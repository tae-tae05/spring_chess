package dataaccess.sql;

import dataaccess.*;

import java.sql.SQLException;

public class MySQLDataAccess implements DataAccess {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public MySQLDataAccess() throws SQLException, DataAccessException {
        this.userDAO = new MySQLUserDAO();
        this.authDAO = new MySQLAuthDAO();
        this.gameDAO = new MySQLGameDAO();
    }

    @Override
    public AuthDAO getAuthDAO() {
        return authDAO;
    }

    @Override
    public GameDAO getGameDAO() {
        return gameDAO;
    }

    @Override
    public UserDAO getUserDAO() {
        return userDAO;
    }
}
