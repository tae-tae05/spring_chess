package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.Collection;

public class MemoryAuthDAO implements AuthDAO{
    @Override
    public void createAuth(AuthData auth) {
        auths.add(auth);
    }

    @Override
    public boolean verifyUserAuth(AuthData auth) {
        return auths.contains(auth);
    }
    @Override
    public void deleteAuth() {
        auths.clear();
    }

    @Override
    public Collection<AuthData> getAuths() {
        return auths;
    }
}
