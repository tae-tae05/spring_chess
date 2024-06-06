package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.AuthData;
import results.LogoutAndJoinResults;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route{
    private final DataAccess data;
    public LogoutHandler(DataAccess data){
        this.data = data;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        UserService logoutService = new UserService(data);
        var serializer = new Gson();
        AuthData auth = new AuthData(request.headers("authorization"), null);
        response.type("application/json");
        LogoutAndJoinResults logoutResult = logoutService.logout(auth, response);
        return serializer.toJson(logoutResult);
    }
}
