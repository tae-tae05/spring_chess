package dataaccess;

import model.UserData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface UserDAO {
    boolean getUser(UserData user) throws DataAccessException, SQLException; //does not need to check auth
    void createUser(UserData user) throws DataAccessException, SQLException; //puts it into database
    void deleteUsers() throws DataAccessException, SQLException;
    Collection<UserData> getAllUsers();
}
