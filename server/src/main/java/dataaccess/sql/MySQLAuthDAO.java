package dataaccess.sql;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;

import java.sql.SQLException;
import java.util.Collection;

public class MySQLAuthDAO extends MySQLDAO implements AuthDAO {
    public MySQLAuthDAO() throws DataAccessException, SQLException {
    }

    @Override
    public Collection<AuthData> getAuths() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean verifyUserAuth(AuthData auth) {
        return false;
    }

    @Override
    public AuthData deleteAuth(AuthData auth) throws DataAccessException {
        return null;
    }

    @Override
    public void createAuth(AuthData auth) {

    }

    @Override
    public AuthData getAuth(String username) {
        return null;
    }
}
