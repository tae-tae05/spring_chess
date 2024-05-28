package service;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collection;

public class AuthTests {
    @Test
    public void newAuth(){
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        authDAO.createAuth(new AuthData("tae", "4rsw2@k"));

        Collection<AuthData> compareAuth = new ArrayList<>();
        compareAuth.add(new AuthData("tae", "4rsw2@k"));

        Assertions.assertEquals(authDAO.getAuths(), compareAuth,
                "Auth was not added.");
    }
    @Test
    public void verification(){
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        authDAO.createAuth(new AuthData("tae", "4rsw2@k"));

        boolean check1 = authDAO.verifyUserAuth(new AuthData("tae", "4rsw2@k"));
        boolean check2 = authDAO.verifyUserAuth(new AuthData("lee", "jil8#4"));
        Assertions.assertTrue(check1, "Returned false when it does exist in Auth.");
        Assertions.assertFalse(check2, "Returned true when it does not exist in Auth.");
    }
    @Test
    public void cleared(){
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        authDAO.createAuth(new AuthData("tae", "efes2"));
        authDAO.createAuth(new AuthData("lee", "ikj4s"));
        authDAO.deleteAuth();

        Assertions.assertEquals(authDAO.getAuths().size(), 0,
                "Auth was not cleared correctly.");
    }
}
