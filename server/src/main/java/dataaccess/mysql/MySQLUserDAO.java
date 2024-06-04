package dataaccess.mysql;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Collection;

public class MySQLUserDAO implements UserDAO {
    private static String SALT = BCrypt.gensalt();
    @Override
    public boolean getUser(UserData user) {
        return false;
    }

    @Override
    public Collection<UserData> getAllUsers() {
        return null;
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {
        String hashPassword = BCrypt.hashpw(user.password(), SALT);
        String sql = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
    }

    @Override
    public void deleteUsers() {

    }
}
