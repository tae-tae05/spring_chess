package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import results.ClearResults;
import service.ClearService;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.NoRouteToHostException;

public class ClearHandlers implements Route {
    private final DataAccess data;
    ClearService clearService;
    public ClearHandlers(DataAccess data){
        this.data = data;
        clearService = new ClearService(data);
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        var serializer = new Gson();
        response.type("application/json");
        ClearResults clearUsers = clearService.clearAll(response);
        return serializer.toJson(clearUsers);
    }
}
