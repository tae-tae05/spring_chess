package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.AuthData;
import model.GameData;
import results.CreateGameResults;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
    private final DataAccess data;
    public CreateGameHandler(DataAccess data){
        this.data = data;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        GameService createGame = new GameService(data);
        var serializer = new Gson();
        AuthData auth = new AuthData(request.headers("authorization"), null);
        GameData game = serializer.fromJson(request.body(), GameData.class);
        response.type("application/json");
        CreateGameResults results = createGame.createGame(game, auth, response);
        return serializer.toJson(results);
    }
}
