package dataaccess;

import dataaccess.sql.MySQLUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import server.Server;

import java.sql.SQLException;

public class UserDAOTests {
    private static UserDAO userDAO;

    static {
        try {
            userDAO = new MySQLUserDAO();

            DatabaseManager.createDatabase();

        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private final UserData one = new UserData("jin", "taetae", "jin@email.com");
    private final UserData two = new UserData("jj", "arayofj", "jj@email.com");
    private final UserData three = new UserData("bin", "binnythepooh", "binny@email.com");

    @BeforeEach
    public void setUp() throws SQLException, DataAccessException {
        Server.configureDatabase();
        try{
            userDAO.deleteUsers();
        }
        catch(DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }


    @Test
    public void getUserPass() throws SQLException, DataAccessException {
        String hashPassword = BCrypt.hashpw(three.password(), BCrypt.gensalt());
        String sql = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        var connection = DatabaseManager.getConnection();
        var pst = connection.prepareStatement(sql);
        pst.setString(1,three.username());
        pst.setString(2, hashPassword);
        pst.setString(3, three.email());
        Assertions.assertFalse(userDAO.getUser(three), "user was not accessed correctly.");
    }
    @Test
    public void getUserFail() throws SQLException, DataAccessException {
        userDAO.createUser(one);
        Assertions.assertFalse(userDAO.getUser(two));
    }

    @Test
    public void deleteUsersPass() throws SQLException, DataAccessException {
        userDAO.createUser(one);
        userDAO.deleteUsers();
        Assertions.assertFalse(userDAO.getUser(one), "user was not cleared correctly.");
    }
    @Test
    public void createUserPass() throws SQLException, DataAccessException {
        userDAO.createUser(one);
        Assertions.assertTrue(userDAO.getUser(one));
    }

    @Test
    public void createUserFail() throws SQLException, DataAccessException {
        userDAO.createUser(two);
        Assertions.assertFalse(userDAO.getUser(three));
    }
    @Test
    public void getAllUsersPass() throws SQLException, DataAccessException {
        userDAO.createUser(one);
        userDAO.createUser(two);
        userDAO.createUser(three);
        Assertions.assertTrue(userDAO.getUser(one), "user one does not exist");
        Assertions.assertTrue(userDAO.getUser(two), "user two does not exist");
        Assertions.assertTrue(userDAO.getUser(three), "user three does not exist");
    }

    @Test
    public void getAllUsersFail() throws SQLException, DataAccessException{
        userDAO.createUser(one);
        userDAO.createUser(two);
        Assertions.assertTrue(userDAO.getUser(one), "user one does not exist");
        Assertions.assertTrue(userDAO.getUser(two), "user two does not exist");
        Assertions.assertFalse(userDAO.getUser(three), "user three does exist");
    }
    @Test
    public void checkUserPass() throws SQLException, DataAccessException {
        userDAO.createUser(one);
        Assertions.assertTrue(userDAO.checkUser("jin", "taetae"));
    }
    @Test
    public void checkUserFail() throws SQLException, DataAccessException {
        userDAO.createUser(one);
        Assertions.assertFalse(userDAO.checkUser("jin", "hello"));
    }
}
