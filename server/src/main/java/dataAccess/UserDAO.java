package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public interface UserDAO {
    Collection<UserData> users = new ArrayList<>();
    boolean getUser(UserData user); //does not need to check auth
    void createUser(UserData user); //puts it into database
    void deleteUsers();
    Collection<UserData> getAllUsers();
}
