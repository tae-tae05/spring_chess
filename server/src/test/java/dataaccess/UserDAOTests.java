package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoff.model.TestUser;

import java.sql.SQLException;

public class UserDAOTests {
    private static UserDAO userDAO;
    private static TestUser existingUser;
    private static TestUser newUser;
//    private final UserData userOne = new UserData("jin", "taetae", "jin@gmail.com");
//    private final UserData userTwo = new UserData("jj", "arayofj", "jj@gmail.com");

    @BeforeAll
    public static void init(){
        existingUser = new TestUser("ExistingUser", "existingUserPassword", "eu@mail.com");

        newUser = new TestUser("NewUser", "newUserPassword", "nu@mail.com");
    }

    @BeforeEach
    public void clear() throws SQLException, DataAccessException {
        userDAO.deleteUsers();
    }

    @Test
    public void addUser(){
        
    }
}
