package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.AuthData;
import results.ListGameResults;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGamesHandler implements Route {
    private final DataAccess data;
    public ListGamesHandler(DataAccess data){
        this.data = data;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        GameService listingGames = new GameService(data);
        var serializer = new Gson();
        AuthData auth = new AuthData(request.headers("authorization"), null);
        response.type("application/json");
        ListGameResults listGameResult = listingGames.listGames(auth, response);
        return serializer.toJson(listGameResult);
    }
}
