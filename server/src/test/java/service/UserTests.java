package service;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Collection;

public class UserTests {

    public void addUser(){}
    @Test
    public void clearMethod() {
        MemoryUserDAO userDAO = new MemoryUserDAO();
        userDAO.createUser(new UserData("tae", "taetae05", "hjhan@gmail.com"));
        userDAO.deleteUsers();

        Assertions.assertEquals(0, userDAO.getAllUsers().size(),
                "UserDAO is not completely empty.");
    }

}
