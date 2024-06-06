package dataaccess.sql;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.UserDAO;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;


import java.util.ArrayList;
import java.util.Collection;

public class MySQLUserDAO implements UserDAO {
    private static String SALT = BCrypt.gensalt();

    public MySQLUserDAO() throws DataAccessException, SQLException {
    }

    @Override
    public boolean checkUser(String username, String password) throws DataAccessException {
        String sql = "SELECT username FROM user WHERE username= '" + username + "'";
        try (var connection = DatabaseManager.getConnection()){
            var pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
                return false;
            }
        }
        catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        sql = "SELECT password FROM user WHERE username= '" + username + "'";
        String foundPassword = "";
        try (var connection = DatabaseManager.getConnection()){
            var pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            rs.next();
            foundPassword = rs.getString("password");
        }
        catch(SQLException | DataAccessException e){
            throw new DataAccessException(e.getMessage());
        }
        return BCrypt.checkpw(password, foundPassword);
    }

    @Override
    public boolean getUser(UserData user) throws DataAccessException, SQLException {
        String find = user.username();
        String sql = "SELECT username FROM user WHERE username= '" + user.username() + "'";
        try (var connection = DatabaseManager.getConnection()){
            var pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        }
        catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }

    }

    @Override
    public void createUser(UserData user) throws DataAccessException, SQLException {
        String hashPassword = BCrypt.hashpw(user.password(), SALT);
        String sql = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        try (var connection = DatabaseManager.getConnection()) {
            var pst = connection.prepareStatement(sql);
            pst.setString(1, user.username());
            pst.setString(2, hashPassword);
            pst.setString(3, user.email());
            int result = pst.executeUpdate();
        }
    }

    @Override
    public void deleteUsers() throws DataAccessException, SQLException {
        String sql = "TRUNCATE TABLE user";
        try (var connection = DatabaseManager.getConnection()) {
            var pst = connection.prepareStatement(sql);
            int result = pst.executeUpdate();
        }
    }

    @Override
    public Collection<UserData> getAllUsers() throws DataAccessException {
        Collection<UserData> allUsers = new ArrayList<>();
        try(var connection = DatabaseManager.getConnection()) {
            String findUsers = """
                    SELECT username, password, email FROM user""";
            try(var pst = connection.prepareStatement(findUsers)){
                var rs = pst.executeQuery();
                while(rs.next()){
                    UserData user = new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
                    allUsers.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }
}
