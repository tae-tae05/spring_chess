package dataAccess;

import chess.ChessGame;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MemoryGameDAO implements GameDAO{

    @Override
    public void addGame(GameData game) throws DataAccessException {
        if(games.contains(game)){
            throw new DataAccessException("game already exists");
        }
        games.add(game);
    }

    @Override
    public boolean verifyBlackPosition(int gameID) {
        for (GameData game : games) {
            if (gameID == game.gameID()) {
                if (game.blackUsername() == null)  {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verifyWhitePosition(int gameID){
        for (GameData game : games) {
            if (gameID == game.gameID()) {
                if (game.whiteUsername() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verifyGame(int gameID){
        for (GameData game : games) {
            if (gameID == game.gameID()) {
                return true;
            }
        }
        return false;
    }

    public GameData getGame(int gameID){
        for(GameData game: games){
            if (game.gameID() == gameID){
                return game;
            }
        }
        return null;
    }


    public void insertUsername(int gameID, String newUsername, ChessGame.TeamColor color){
        for(int i = 0; i < games.size(); i++){
            if(gameID == games.get(i).gameID()){
                if(color == ChessGame.TeamColor.WHITE){
                    GameData tempGame = games.get(i);
                    tempGame = tempGame.addWhiteUsername(newUsername);
                    games.set(i, tempGame);
                }
                if(color == ChessGame.TeamColor.BLACK){
                    GameData tempGame = games.get(i);
                    tempGame = tempGame.addBlackUsername(newUsername);
                    games.set(i, tempGame);
                }
            }
        }
    }

    @Override
    public List<GameData> listGames() {
        return games;
    }

    @Override
    public void updateGames(GameData newGame) throws DataAccessException{
        int counter = 0;
        boolean exists = false;
        for(int i = 0; i < games.size(); i++) {
            if(games.get(i).gameID() == newGame.gameID()){
                exists = true;
                counter = i;
                break;
            }
        }
        if(!exists){
            throw new DataAccessException("game does not exist");
        }
        games.set(counter, newGame);
    }

    @Override
    public void deleteGames() {
        games.clear();
    }
}
