package dataaccess;

import dataaccess.sql.MySQLAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Server;

import java.sql.SQLException;

public class AuthDAOTests {

    private static AuthDAO authDAO;

    static {
        try {
            authDAO = new MySQLAuthDAO();

            // Create DB if not exists
            DatabaseManager.createDatabase();

        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private final AuthData one = new AuthData("asdfel", "jin");
    private final AuthData two = new AuthData("efsgh", "jj");

    private final AuthData three = new AuthData("sef451", "bin");

    @BeforeEach
    public void setUp() throws SQLException, DataAccessException {
        Server.configureDatabase();
        authDAO.clear();
    }

    @Test
    public void clearPass() throws SQLException, DataAccessException {
        authDAO.createAuth(one);
        authDAO.createAuth(two);
        authDAO.clear();
        Assertions.assertFalse(authDAO.verifyUserAuth(one));
    }

    @Test
    public void createAuthPass() throws SQLException, DataAccessException {
        authDAO.createAuth(one);
        authDAO.createAuth(two);

        Assertions.assertTrue(authDAO.verifyUserAuth(one));
    }

    @Test
    public void createAuthFail() throws SQLException, DataAccessException {
        authDAO.createAuth(one);
        authDAO.createAuth(two);

        Assertions.assertFalse(authDAO.verifyUserAuth(three));
    }
    @Test
    public void getsAuthsPass() throws SQLException, DataAccessException {
    authDAO.createAuth(one);
    authDAO.createAuth(two);
    authDAO.createAuth(three);

    Assertions.assertTrue(authDAO.verifyUserAuth(one));
    Assertions.assertTrue(authDAO.verifyUserAuth(two));
    Assertions.assertTrue(authDAO.verifyUserAuth(three));
    }

    @Test
    public void getsAuthsFail() throws SQLException, DataAccessException {
        authDAO.createAuth(one);
        authDAO.createAuth(two);

        Assertions.assertTrue(authDAO.verifyUserAuth(one));
        Assertions.assertTrue(authDAO.verifyUserAuth(two));
        Assertions.assertFalse(authDAO.verifyUserAuth(three));
    }

    @Test
    public void verifyAuthPass() throws SQLException, DataAccessException{
        authDAO.createAuth(one);
        authDAO.createAuth(two);
        Assertions.assertTrue(authDAO.verifyUserAuth(one));
    }
    @Test
    public void verifyAuthFail() throws SQLException, DataAccessException{
        authDAO.createAuth(one);
        Assertions.assertFalse(authDAO.verifyUserAuth(two));
    }

    @Test
    public void getAuthPass() throws SQLException, DataAccessException{
        authDAO.createAuth(one);
        authDAO.createAuth(two);
        Assertions.assertEquals(authDAO.getAuth(one.username()), one);
    }
    @Test
    public void getAuthFail() throws SQLException, DataAccessException{
        authDAO.createAuth(one);
        authDAO.createAuth(two);
        Assertions.assertNotEquals(authDAO.getAuth(one.username()), three);
    }

    @Test
    public void deleteAuthPass() throws SQLException, DataAccessException{
        authDAO.createAuth(one);
        authDAO.createAuth(two);
        authDAO.deleteAuth(one);
        Assertions.assertFalse(authDAO.verifyUserAuth(one));
    }

    @Test
    public void deleteAuthFail() throws SQLException, DataAccessException{
        authDAO.createAuth(one);
        authDAO.createAuth(two);
        authDAO.deleteAuth(one);
        Assertions.assertTrue(authDAO.verifyUserAuth(two));
    }
}
