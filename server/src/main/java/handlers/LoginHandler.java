package handlers;

import com.google.gson.Gson;
import request.LoginRequest;
import results.RegisterResults;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.sound.midi.Receiver;

public class LoginHandler implements Route{
    @Override
    public Object handle(Request request, Response response) throws Exception {
        UserService loginService = new UserService();
        var serializer = new Gson();
        LoginRequest loginRequest = serializer.fromJson(request.body(), LoginRequest.class);
        response.type("application/json");
        RegisterResults loginResults = loginService.login(loginRequest, response);
//        System.out.println(loginResults.toString());
        return serializer.toJson(loginResults);
    }
}
