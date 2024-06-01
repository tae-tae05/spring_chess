package server;

import com.google.gson.Gson;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import spark.*;

import java.util.ArrayList;
import java.util.Map;

import static spark.Spark.get;

public class Server {
    private ArrayList<UserData> users = new ArrayList<>();
    private ArrayList<UserData> auths = new ArrayList<>();
    private ArrayList<UserData> games = new ArrayList<>();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::userBody);
//        Spark.delete("/db/:users", this::clearUsers);
        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object userBody(Request req, Response res){
        var bodyObj = getBody(req, Map.class);

        res.type("application/json");
        return new Gson().toJson(bodyObj);
    }


    private static <T> T getBody(Request request, Class<T> clazz) {
        var body = new Gson().fromJson(request.body(), clazz);
        if (body == null) {
            throw new RuntimeException("missing required body");
        }
        return body;
    }

    private Object listUser(Request req, Response res){
        res.type("application/json");
        return new Gson().toJson(Map.of("user", users));
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}