package handlers;

import com.google.gson.Gson;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import results.RegisterResults;
import service.RegisterService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        RegisterService REG_SERVICE  = new RegisterService();
        //convert request to UserData from http
        var serializer = new Gson();
        UserData holdingUser = serializer.fromJson(request.body(), UserData.class);
        //create response type - json
        response.type("application/json");
        //make a register result object using service class
        RegisterResults regResults = REG_SERVICE.register(holdingUser, response);

        return serializer.toJson(regResults);
    }
}
