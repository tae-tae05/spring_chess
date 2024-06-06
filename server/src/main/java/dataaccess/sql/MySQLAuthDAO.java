package dataaccess.sql;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MySQLAuthDAO implements AuthDAO {
    public MySQLAuthDAO() throws DataAccessException, SQLException {
    }

    @Override
    public Collection<AuthData> getAuths() {
        Collection<AuthData> allAuths= new ArrayList<>();
        try(var connection = DatabaseManager.getConnection()) {
            String findUsers = """
                    SELECT authToken, username FROM auth""";
            try(var pst = connection.prepareStatement(findUsers)){
                var rs = pst.executeQuery();
                while(rs.next()){
                    AuthData auth = new AuthData(rs.getString("authToken"), rs.getString("username"));
                    allAuths.add(auth);
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        return allAuths;
    }

    @Override
    public void clear() throws DataAccessException, SQLException {
        String sql = "TRUNCATE TABLE auth";
        var connection = DatabaseManager.getConnection();
        var pst = connection.prepareStatement(sql);
        int result = pst.executeUpdate();
    }

    @Override
    public boolean verifyUserAuth(AuthData auth) throws DataAccessException{
        String find = auth.username();
        String sql = "SELECT authToken FROM auth WHERE authToken= '" + auth.authToken() + "'";
        try{
            var connection = DatabaseManager.getConnection();
            var pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAuth(AuthData auth) throws DataAccessException, SQLException {
        String sql = "DELETE FROM auth WHERE authToken = ?";
        var connection = DatabaseManager.getConnection();
        var pst = connection.prepareStatement(sql);
        pst.setString(1, auth.authToken());
        var rs = pst.executeUpdate();
    }

    @Override
    public void createAuth(AuthData auth) throws DataAccessException, SQLException {
        String sql = "INSERT INTO auth (authToken, username) VALUES (?, ?)";
        var connection = DatabaseManager.getConnection();
        var pst = connection.prepareStatement(sql);
        pst.setString(1, auth.authToken());
        pst.setString(2, auth.username());
        int result = pst.executeUpdate();
    }

    @Override
    public AuthData getAuth(String username) {
        String sql = "SELECT authToken, username FROM auth WHERE username= '" + username + "'";
        try{
            var connection = DatabaseManager.getConnection();
            var pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return new AuthData(rs.getString("authToken"), rs.getString("username"));
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUsername(AuthData auth) {
        String sql = "SELECT username FROM auth WHERE authToken= '" + auth.authToken() + "'";
        try{
            var connection = DatabaseManager.getConnection();
            var pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getString("username");
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
