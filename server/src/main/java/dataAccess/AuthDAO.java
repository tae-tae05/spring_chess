package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public interface AuthDAO {
    Collection<AuthData> auths = new ArrayList<>();
    void createAuth(AuthData auth);
    boolean verifyUserAuth(AuthData auth);
    void deleteAuth();
    Collection<AuthData> getAuths();
}
