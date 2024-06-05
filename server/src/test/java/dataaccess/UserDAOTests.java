package dataaccess;

import dataaccess.mysql.MySQLUserDAO;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import passoff.model.TestUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDAOTests {
    private static UserDAO userDAO = new MySQLUserDAO();
    private static TestUser existingUser;
    private static TestUser newUser;
    private final UserData one = new UserData("jin", "taetae", "jin@gmail.com");
    private final UserData two = new UserData("jj", "arayofj", "jj@gmail.com");

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "mysqlNBZIEN12!");
    }

    @BeforeEach
    public void setUp() throws SQLException, DataAccessException {
        try{
            userDAO.deleteUsers();
        }
        catch(DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }


//    @Test
//    public void checkUser() throws SQLException, DataAccessException {
//        UserData user = new UserData("binny", "binnythepooh", "binny@email.com");
//        String hashPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
//        String sql = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
//        var connection = DatabaseManager.getConnection();
//        var pst = connection.prepareStatement(sql);
//        pst.setString(1,user.username());
//        pst.setString(2, hashPassword);
//        pst.setString(3, user.email());
//        Assertions.assertFalse(userDAO.getUser(user), "user was not created correctly.");
//    }
//
//    @Test
//    public void clearUsers() throws SQLException, DataAccessException {
//        userDAO.createUser(one);
//        userDAO.deleteUsers();
//        Assertions.assertFalse(userDAO.getUser(one), "user was not cleared correctly.");
//    }
    @Test
    public void addUser() throws SQLException, DataAccessException {
        var connection = DatabaseManager.getConnection();
        userDAO.createUser(one);
        connection.setCatalog("chess");

        String getOne = """
                SELECT username FROM user WHERE username =?
                """;
        try{
            var pst = connection.prepareStatement(getOne);
            pst.setString(1,"jin");
            var rs = pst.executeQuery();
            if(rs.next()){
                Assertions.assertNotEquals(rs.getString(1), null);
            }
            else{
                Assertions.fail("was not added correctly");
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
