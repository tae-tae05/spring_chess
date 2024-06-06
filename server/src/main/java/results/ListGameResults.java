package results;

import model.GameData;

import java.util.Collection;
import java.util.List;

public record ListGameResults(Collection<GameData> games, String message) {
    public ListGameResults setMessage(String newMessage){
        return new ListGameResults(games, newMessage);
    }
    public ListGameResults setGames(Collection<GameData> newGames){
        return new ListGameResults(newGames, message);
    }
}
