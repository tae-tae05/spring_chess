package dataaccess;

import model.UserData;

public interface UserDAO {
    String getUser(UserData user); //does not need to check auth
    void createUser(UserData user); //puts it into database
    void deleteUsers();
}
