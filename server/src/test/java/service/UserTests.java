package service;
import dataAccess.DataAccessException;
import dataAccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collection;

public class UserTests {
    @Test
    public void addUser() throws DataAccessException {
        MemoryUserDAO userDAO = new MemoryUserDAO();
        userDAO.createUser(new UserData("tae", "taetae05", "hjhan@gmail.com"));

        Collection<UserData> compareUser = new ArrayList<>();
        compareUser.add(new UserData("tae", "taetae05", "hjhan@gmail.com"));

        Assertions.assertEquals(compareUser, userDAO.getAllUsers(),
                "User was not added.");
    }
    @Test
    public void addingExistingUser() throws DataAccessException {
        MemoryUserDAO userDAO = new MemoryUserDAO();
        userDAO.createUser(new UserData("tae", "taetae05", "hjhan@gmail.com"));
        boolean check = userDAO.getUser(new UserData("tae", "taetae05", "hjhan@gmail.com"));


        Assertions.assertEquals(true, check,
                "User does not exist.");
    }
    @Test
    public void clearMethod() throws DataAccessException {
        MemoryUserDAO userDAO = new MemoryUserDAO();
        userDAO.createUser(new UserData("tae", "taetae05", "hjhan@gmail.com"));
        userDAO.deleteUsers();

        Assertions.assertEquals(0, userDAO.getAllUsers().size(),
                "UserDAO is not completely empty.");
    }

}
