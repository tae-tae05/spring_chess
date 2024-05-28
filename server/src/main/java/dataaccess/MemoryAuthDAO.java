package dataaccess;

import model.AuthData;
import model.UserData;

public class MemoryAuthDAO implements AuthDAO{
    @Override
    public AuthData createAuth(UserData user) {
        return null;
    }

    @Override
    public boolean verifyUserAuth(AuthData auth) {
        return false;
    }

    @Override
    public void deleteAuth() {
        auths.clear();
    }
}
