package dataaccess;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;

import java.util.Collection;

public class MemoryAuthDAO implements AuthDAO {
    @Override
    public void createAuth(AuthData auth) {
        AUTHS.add(auth);
    }

    @Override
    public boolean verifyUserAuth(AuthData auth) {
        for(AuthData current: AUTHS){
            if(current.authToken().equals(auth.authToken())){
                return true;
            }
        }
        return false;
    }
    @Override
    public AuthData deleteAuth(AuthData auth) throws DataAccessException {
        AuthData remove = new AuthData(null, null);
        if(!verifyUserAuth(auth)){
            throw new DataAccessException("not in database");
        }
        for (int i = 0; i < AUTHS.size(); i++) {
            String authToken = AUTHS.get(i).authToken();
            if (authToken.equals(auth.authToken())) {
                remove = AUTHS.get(i);
                AUTHS.remove(i);
                break;
            }
        }
        return remove;
    }

    @Override
    public void clear() {
        AUTHS.clear();
    }

    @Override
    public Collection<AuthData> getAuths() {
        return AUTHS;
    }

    public String getUsername(AuthData auth){
        for(AuthData temp: AUTHS){
            if (temp.authToken().equals(auth.authToken())){
                return temp.username();
            }
        }
        return null;
    }

    public AuthData getAuth(String username){
        for(AuthData temp: AUTHS){
            if(temp.username().equals(username)){
                return temp;
            }
        }
        return null;
    }
}
