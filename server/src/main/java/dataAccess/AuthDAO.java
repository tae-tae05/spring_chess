package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public interface AuthDAO {
    Collection<AuthData> auths = new ArrayList<>();
    AuthData createAuth(UserData user);
    boolean verifyUserAuth(AuthData auth);
    void deleteAuth();
}
