package handlers;

import com.google.gson.Gson;
import results.ClearResults;
import service.ClearService;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.NoRouteToHostException;

public class ClearHandlers implements Route {
    ClearService clearService = new ClearService();
    @Override
    public Object handle(Request request, Response response) throws Exception {
        var serializer = new Gson();
        response.type("application/json");
        ClearResults clearUsers = clearService.clearAll(response);
        return serializer.toJson(clearUsers);
    }
}
