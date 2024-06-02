package service;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import jdk.jshell.spi.ExecutionControlProvider;
import model.AuthData;
import model.UserData;
import request.LoginRequest;
import results.ClearResults;
import results.RegisterResults;
import spark.Response;

import java.util.UUID;

public class UserService {
    MemoryUserDAO USERS_DB = new MemoryUserDAO();
    MemoryAuthDAO AUTH_DB = new MemoryAuthDAO();
    public RegisterResults register(UserData user, Response response) throws DataAccessException {
        RegisterResults regResults = new RegisterResults(null,null,null);
        if(user.username() == null || user.password() == null || user.email() == null){
            regResults.setMessage("Error: bad request");
            response.status(400);
            return regResults;
        }
        if(USERS_DB.getUser(user)){
            regResults.setMessage("Error: already taken");
            response.status(403);
            return regResults;
        }
        try{
            USERS_DB.createUser(user);
            AuthData one = new AuthData(UUID.randomUUID().toString(), user.username());
            AUTH_DB.createAuth(one);
            response.status(200);
        }
        catch(Exception e){
            regResults.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return regResults;
    }


    public RegisterResults login(LoginRequest loginRequest, Response response) throws DataAccessException{
        var serializer = new Gson();
//      
    }
    public void logout(AuthData auth) throws DataAccessException {
        boolean inAuth = AUTH_DB.verifyUserAuth(auth);
        if(!inAuth){
            throw new DataAccessException("not authorized to logout");
        }
        AUTH_DB.deleteAuth(auth);
    }

}
