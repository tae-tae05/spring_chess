package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import results.RegisterResults;
import spark.Response;

import java.util.UUID;

public class RegisterService {
    MemoryUserDAO USERS_DAO = new MemoryUserDAO();
    MemoryAuthDAO AUTH_DAO = new MemoryAuthDAO();
    public RegisterResults register(UserData user, Response response) throws DataAccessException {

        RegisterResults regResults = new RegisterResults(null,null,null);
        if(user.username() == null || user.password() == null || user.email() == null){
            regResults = regResults.setMessage("Error: bad request");
            response.status(400);
            return regResults;
        }
        if(USERS_DAO.getUser(user)){
            regResults = regResults.setMessage("Error: already taken");
            response.status(403);
            return regResults;
        }
        try{
            USERS_DAO.createUser(user);
            AuthData one = new AuthData(UUID.randomUUID().toString(), user.username());
            AUTH_DAO.createAuth(one);
            regResults = regResults.setUsername(user.username());
            regResults = regResults.setAuthToken(one.authToken());
            response.status(200);
        }
        catch(Exception e){
            regResults = regResults.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return regResults;
    }
}
