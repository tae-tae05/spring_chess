package service;

import dataAccess.GameDAO;
import dataAccess.MemoryGameDAO;
import model.GameData;

import java.util.Collection;
import java.util.List;

public class GameService {
    private MemoryGameDAO GAMES_DB = new MemoryGameDAO();

    public GameData createGame(GameData game){
        return null;
    }
    public GameData joinGame(){
        return null;
    }
    public void clearGames(){
        GAMES_DB.deleteGames();
    }
    public Collection<GameData> listGames(){
        return GAMES_DB.listGames();
    }
}
