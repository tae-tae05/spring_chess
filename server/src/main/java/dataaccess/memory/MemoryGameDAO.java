package dataaccess.memory;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MemoryGameDAO implements GameDAO {


    @Override
    public void addGame(GameData game) throws DataAccessException {
        if(GAMES.contains(game)){
            throw new DataAccessException("game already exists");
        }
        GAMES.add(game);
    }


    @Override
    public boolean verifyBlackPosition(int gameID) {
        for (GameData game : GAMES) {
            if (gameID == game.gameID()) {
                if (game.blackUsername() == null)  {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verifyWhitePosition(int gameID){
        for (GameData game : GAMES) {
            if (gameID == game.gameID()) {
                if (game.whiteUsername() == null) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public boolean verifyGame(Integer gameID){
        for (GameData game : GAMES) {
            if (Objects.equals(gameID, game.gameID())) {
                return true;
            }
        }
        return false;
    }

    public void insertUsername(int gameID, String newUsername, ChessGame.TeamColor color){
        for(int i = 0; i < GAMES.size(); i++){
            if(gameID == GAMES.get(i).gameID()){
                if(color == ChessGame.TeamColor.WHITE){
                    GameData tempGame = GAMES.get(i);
                    tempGame = tempGame.addWhiteUsername(newUsername);
                    GAMES.set(i, tempGame);
                }
                if(color == ChessGame.TeamColor.BLACK){
                    GameData tempGame = GAMES.get(i);
                    tempGame = tempGame.addBlackUsername(newUsername);
                    GAMES.set(i, tempGame);
                }
            }
        }
    }

    @Override
    public Collection<GameData> listGames() {
        return GAMES;
    }


    @Override
    public void deleteGames() {
        GAMES.clear();
    }
}
