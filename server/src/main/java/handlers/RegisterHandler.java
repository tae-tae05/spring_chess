package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import results.RegisterResults;
import service.RegisterService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.xml.crypto.Data;

public class RegisterHandler implements Route {
    private final DataAccess data;
    public RegisterHandler(DataAccess data){
        this.data = data;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        RegisterService regService  = new RegisterService(data);
        var serializer = new Gson();
        UserData holdingUser = serializer.fromJson(request.body(), UserData.class);
        response.type("application/json");
        RegisterResults regResults = regService.register(data, holdingUser, response);

        return serializer.toJson(regResults);
    }
}
