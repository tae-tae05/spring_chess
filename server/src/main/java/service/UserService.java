package service;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import request.LoginRequest;
import results.LogoutAndJoinResults;
import results.RegisterResults;
import spark.Response;

import java.util.UUID;

public class UserService {
    MemoryUserDAO userDAO = new MemoryUserDAO();
    MemoryAuthDAO authDAO = new MemoryAuthDAO();


    public RegisterResults login(LoginRequest loginRequest, Response response) throws DataAccessException{
        var serializer = new Gson();
        RegisterResults regResults = new RegisterResults(null, null, null);

        if(!userDAO.checkUser(loginRequest.username(), loginRequest.password())){
          regResults = regResults.setMessage("Error: unauthorized");
          response.status(401);
          return regResults;
        }
        try{
            userDAO.checkUser(loginRequest.username(), loginRequest.password());
          AuthData auth = new AuthData(UUID.randomUUID().toString(), loginRequest.username());
            authDAO.createAuth(auth);
          response.status(200);
          regResults = regResults.setUsername(loginRequest.username());
          regResults = regResults.setAuthToken(auth.authToken());
        }
        catch(DataAccessException e){
          regResults = regResults.setMessage("Error: " + e.getMessage());
          response.status(500);
        }

        return regResults;
    }
    public LogoutAndJoinResults logout(AuthData auth, Response response) throws DataAccessException {
        var serializer = new Gson();
        LogoutAndJoinResults logoutResult = new LogoutAndJoinResults(null);
        if(!authDAO.verifyUserAuth(auth)){
            logoutResult = logoutResult.setMessage("Error: unauthorized");
            response.status(401);
            return logoutResult;
        }
        try{
            AuthData temp = authDAO.deleteAuth(auth);
            response.status(200);
        }
        catch(Exception e){
            logoutResult = logoutResult.setMessage("Error: " + e.getMessage());
        }
        return logoutResult;
    }

}
