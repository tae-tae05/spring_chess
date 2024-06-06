package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.AuthData;
import request.JoinGameRequest;
import results.LogoutAndJoinResults;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {
    private final DataAccess data;
    public JoinGameHandler(DataAccess data){
        this.data = data;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        GameService joinGame = new GameService(data);
        var serializer = new Gson();
        JoinGameRequest currentGame = serializer.fromJson(request.body(), JoinGameRequest.class);
        AuthData auth = new AuthData(request.headers("authorization"), null);
        response.type("application/json");

        LogoutAndJoinResults results = joinGame.joinGame(currentGame ,auth, response);
        return serializer.toJson(results);
    }
}
