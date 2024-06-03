package handlers;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import results.CreateGameResults;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        GameService createGame = new GameService();
        var serializer = new Gson();
        AuthData auth = new AuthData(request.headers("authorization"), null);
        GameData game = serializer.fromJson(request.body(), GameData.class);
        response.type("application/json");
        CreateGameResults results = createGame.createGame(game, auth, response);
        return serializer.toJson(results);
    }
}
