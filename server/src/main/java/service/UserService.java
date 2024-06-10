package service;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryUserDAO;
import model.AuthData;
import request.LoginRequest;
import results.LogoutAndJoinResults;
import results.RegisterResults;
import spark.Response;

import java.sql.SQLException;
import java.util.UUID;

public class UserService {
    private final DataAccess data;
    public UserService(DataAccess data){
        this.data = data;
    }

    public RegisterResults login(LoginRequest loginRequest, Response response) throws DataAccessException{
        var serializer = new Gson();
        RegisterResults regResults = new RegisterResults(null, null, null);
//        MemoryUserDAO userDAO = (MemoryUserDAO) data.getUserDAO();
//        MemoryAuthDAO authDAO = (MemoryAuthDAO) data.getAuthDAO();
        if(!data.getUserDAO().checkUser(loginRequest.username(), loginRequest.password())){
          regResults = regResults.setMessage("Error: unauthorized");
          response.status(401);
          return regResults;
        }
        try{
            data.getUserDAO().checkUser(loginRequest.username(), loginRequest.password());
            AuthData auth = new AuthData(UUID.randomUUID().toString(), loginRequest.username());
            data.getAuthDAO().createAuth(auth);
            response.status(200);
            regResults = regResults.setUsername(loginRequest.username());
            regResults = regResults.setAuthToken(auth.authToken());
        }
        catch(DataAccessException e){
          regResults = regResults.setMessage("Error: " + e.getMessage());
          response.status(500);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return regResults;
    }
    public LogoutAndJoinResults logout(AuthData auth, Response response) throws DataAccessException, SQLException {
        var serializer = new Gson();
        LogoutAndJoinResults logoutResult = new LogoutAndJoinResults(null);
        if(!data.getAuthDAO().verifyUserAuth(auth)){
            logoutResult = logoutResult.setMessage("Error: unauthorized");
            response.status(401);
            return logoutResult;
        }
        try{
            data.getAuthDAO().deleteAuth(auth);
            response.status(200);
        }
        catch(Exception e){
            logoutResult = logoutResult.setMessage("Error: " + e.getMessage());
        }
        return logoutResult;
    }

}
