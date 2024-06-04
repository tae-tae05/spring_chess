package dataaccess.mysql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.UserDAO;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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

        Connection connection = DatabaseManager.getConnection
        PreparedStatement stmt = connection.prepareStatement(sql);
        int result = executeUpdate(sql, user.username(), hashPassword, user.email());
    }

    @Override
    public void deleteUsers() {

    }
}
