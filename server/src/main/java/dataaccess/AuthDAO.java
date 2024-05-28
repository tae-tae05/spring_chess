package dataaccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {
    AuthData createAuth(UserData user);
    boolean verifyUserAuth(AuthData auth);
    void deleteAuth();
}
