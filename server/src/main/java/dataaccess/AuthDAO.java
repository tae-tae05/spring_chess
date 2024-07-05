package dataaccess;

import model.AuthData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface AuthDAO {
    List<AuthData> AUTHS = new ArrayList<>();
    void createAuth(AuthData auth) throws DataAccessException, SQLException;
    boolean verifyUserAuth(AuthData auth) throws DataAccessException, SQLException;
    void deleteAuth(AuthData auth) throws DataAccessException, SQLException;
    void clear() throws DataAccessException, SQLException;
    Collection<AuthData> getAuths();

    AuthData getAuth(String username);

    String getUsername(String auth);
}
