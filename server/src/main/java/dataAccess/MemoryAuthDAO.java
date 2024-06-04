package dataAccess;

import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemoryAuthDAO implements AuthDAO{
    @Override
    public void createAuth(AuthData auth) {
        auths.add(auth);
    }

    @Override
    public boolean verifyUserAuth(AuthData auth) {
        for(AuthData current: auths){
            if(current.authToken().equals(auth.authToken())){
                return true;
            }
        }
        return false;
    }
    @Override
    public AuthData deleteAuth(AuthData auth) throws DataAccessException{
        AuthData remove = new AuthData(null, null);
        if(!verifyUserAuth(auth)){
            throw new DataAccessException("not in database");
        }
        for (int i = 0; i < auths.size(); i++) {
            String authToken = auths.get(i).authToken();
            if (authToken.equals(auth.authToken())) {
                remove = auths.get(i);
                auths.remove(i);
                break;
            }
        }
        return remove;
    }

    @Override
    public void clear() {
        auths.clear();
    }

    @Override
    public Collection<AuthData> getAuths() {
        return auths;
    }

    public String getUsername(AuthData auth){
        for(AuthData temp: auths){
            if (temp.authToken().equals(auth.authToken())){
                return temp.Username();
            }
        }
        return null;
    }

    public AuthData getAuth(String username){
        for(AuthData temp: auths){
            if(temp.Username().equals(username)){
                return temp;
            }
        }
        return null;
    }
}
