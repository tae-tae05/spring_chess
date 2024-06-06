package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface AuthDAO {
    List<AuthData> AUTHS = new ArrayList<>();
    void createAuth(AuthData auth);
    boolean verifyUserAuth(AuthData auth);
    AuthData deleteAuth(AuthData auth) throws DataAccessException;
    void clear();
    Collection<AuthData> getAuths();

    AuthData getAuth(String username);
}
