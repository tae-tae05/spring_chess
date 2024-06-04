package dataAccess;

import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface AuthDAO {
    List<AuthData> auths = new ArrayList<>();
    void createAuth(AuthData auth);
    boolean verifyUserAuth(AuthData auth);
    AuthData deleteAuth(AuthData auth) throws DataAccessException;
    void clear();
    Collection<AuthData> getAuths();
}
