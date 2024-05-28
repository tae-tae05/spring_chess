package dataAccess;

import model.UserData;

import java.util.Collection;

public class MemoryUserDAO implements UserDAO{
    @Override
    public boolean getUser(UserData user) {
        return users.contains((user));
    }
    @Override
    public void createUser(UserData user) {
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
