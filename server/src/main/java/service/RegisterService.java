package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import results.RegisterResults;
import spark.Response;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.UUID;

public class RegisterService {
    private final DataAccess data;
    public RegisterService(DataAccess data){
        this.data = data;
    }
    public RegisterResults register(DataAccess data, UserData user, Response response) throws DataAccessException, SQLException {

        RegisterResults regResults = new RegisterResults(null,null,null);
        if(user.username() == null || user.password() == null || user.email() == null){
            regResults = regResults.setMessage("Error: bad request");
            response.status(400);
            return regResults;
        }
        if(data.getUserDAO().getUser(user)){
            regResults = regResults.setMessage("Error: already taken");
            response.status(403);
            return regResults;
        }
        try{
            data.getUserDAO().createUser(user);
            AuthData one = new AuthData(UUID.randomUUID().toString(), user.username());
            data.getAuthDAO().createAuth(one);
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
