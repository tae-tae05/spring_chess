package service;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserService {
    private MemoryUserDAO USERS_DB = new MemoryUserDAO();
    private MemoryAuthDAO AUTH_DB = new MemoryAuthDAO();
    public AuthData register(UserData user) throws DataAccessException {
        //check if username is taken
        boolean inUsers = USERS_DB.getUser(user);
        //add user to db
        if(!inUsers){
            USERS_DB.createUser(user);
        }
        //create and add/return AuthData
        return new AuthData(UUID.randomUUID().toString(), user.username());
    }
    public AuthData login(UserData user) throws DataAccessException{
        boolean inUsers = USERS_DB.getUser(user);
        if(inUsers){
            return new AuthData(UUID.randomUUID().toString(), user.username());
        }
        throw new DataAccessException("not in users");
    }
    public void logout(AuthData auth) throws DataAccessException {
        boolean inAuth = AUTH_DB.verifyUserAuth(auth);
        if(!inAuth){
            throw new DataAccessException("not authorized to logout");
        }
        AUTH_DB.deleteAuth(auth);
    }

    public void deleteUsers(){
        USERS_DB.deleteUsers();
    }
    public void deleteAuth(){
        AUTH_DB.clear();
    }
}
