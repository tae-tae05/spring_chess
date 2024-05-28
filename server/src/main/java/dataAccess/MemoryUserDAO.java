package dataAccess;

import model.UserData;

import java.util.Collection;

public class MemoryUserDAO implements UserDAO{
    @Override
    public boolean getUser(UserData user) {
        return users.contains((user));
        //returns true if it is in, false if it is not in the list
    }
    @Override
    public void createUser(UserData user) throws DataAccessException{
        if(getUser(user)) {
            throw new DataAccessException("failed");
        }
        users.add(user);
    }
    @Override
    public void deleteUsers() {
        users.clear();
    }

    @Override
    public Collection<UserData> getAllUsers() {
        return users;
    }
}
