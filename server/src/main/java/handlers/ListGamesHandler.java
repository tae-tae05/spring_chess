package handlers;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import results.ListGameResults;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGamesHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        GameService listingGames = new GameService();
        var serializer = new Gson();
        AuthData auth = new AuthData(request.headers("authorization"), null);
        response.type("application/json");
        ListGameResults listGameResult = listingGames.listGames(auth, response);
        return serializer.toJson(listGameResult);
    }
}
