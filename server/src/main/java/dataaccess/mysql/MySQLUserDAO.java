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
    public void createUser(UserData user) throws DataAccessException, SQLException {
        String hashPassword = BCrypt.hashpw(user.password(), SALT);
        String sql = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1,user.username());
        pst.setString(2, hashPassword);
        pst.setString(3, user.email());
        int result = pst.executeUpdate();
    }

    @Override
    public void deleteUsers() throws DataAccessException, SQLException {
        String sql = "TRUNCATE TABLE user";
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        int result = pst.executeUpdate();
    }

    protected String createStatements(){
        String create = """
                        CREATE TABLE IF NOT EXISTS `user`
                            (`username` VARCHAR(64) NOT NULL,
                            `password` VARCHAR(64) NOT NULL,
                            `email` VARCHAR(64) NOT NULL,
                            PRIMARY KEY (`username`))
                """;
        return create;
    }
}
