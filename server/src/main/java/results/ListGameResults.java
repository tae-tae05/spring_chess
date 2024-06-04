package results;

import model.GameData;

import java.util.Collection;
import java.util.List;

public record ListGameResults(List<GameData> games, String message) {
    public ListGameResults setMessage(String newMessage){
        return new ListGameResults(games, newMessage);
    }
    public ListGameResults setGames(List<GameData> newGames){
        return new ListGameResults(newGames, message);
    }
}
