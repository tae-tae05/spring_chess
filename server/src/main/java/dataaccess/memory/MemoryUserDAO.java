package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO {
    public Collection<UserData> USERS = new ArrayList<>();
    @Override
    public boolean getUser(UserData user) {
        for(UserData current: USERS){
            if(current.username().equals(user.username())){
                return true;
                //returns true if it exists
            }
        }
        return false;
        //returns false if it does not exist
    }
    public boolean checkUser(String username, String password) {
        for(UserData current: USERS) {
            if (current.username().equals(username) && current.password().equals(password)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void createUser(UserData user) throws DataAccessException{
        if(getUser(user)) {
            throw new DataAccessException("failed");
        }
        USERS.add(user);
    }


    @Override
    public void deleteUsers() throws DataAccessException, SQLException {
        USERS.clear();
    }

    @Override
    public Collection<UserData> getAllUsers() {
        return USERS;
    }
}
