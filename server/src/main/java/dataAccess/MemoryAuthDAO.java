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
        return auths.contains(auth);
    }
    @Override
    public void deleteAuth(AuthData auth) throws DataAccessException{
        if(!verifyUserAuth(auth)){
            throw new DataAccessException("not in database");
        }
        for (int i = 0; i < auths.size(); i++) {
            if (auths.get(i).equals(auth)) {
                auths.remove(i);
                break;
            }
        }
    }

    @Override
    public void clear() {
        auths.clear();
    }

    @Override
    public Collection<AuthData> getAuths() {
        return auths;
    }
}
