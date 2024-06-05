package dataaccess;

import model.UserData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface UserDAO {
    Collection<UserData> USERS = new ArrayList<>();
    boolean getUser(UserData user); //does not need to check auth
    void createUser(UserData user) throws DataAccessException, SQLException; //puts it into database
    void deleteUsers() throws DataAccessException, SQLException;
    Collection<UserData> getAllUsers();
}
