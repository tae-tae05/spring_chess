package handlers;

import com.google.gson.Gson;
import model.AuthData;
import results.LogoutResults;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route{
    @Override
    public Object handle(Request request, Response response) throws Exception {
        UserService logoutService = new UserService();
        var serializer = new Gson();
//        LogoutRequest logoutRequest = serializer.fromJson(request.body(), LogoutRequest.class);
        AuthData auth = new AuthData(request.headers("authorization"), null);
        response.type("application/json");
        LogoutResults logoutResult = logoutService.logout(auth, response);
        return serializer.toJson(logoutResult);
    }
}
