package dataAccess;

import chess.ChessGame;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.Optional;

public class MemoryGameDAO implements GameDAO{
//    @Override
//    public GameData getGame(GameData game) throws DataAccessException{
//        for(GameData spot:games) {
//            if (spot.equals(game)){
//                return spot;
//            }
//        }
//        throw new DataAccessException("game does not exist");
//    }

    @Override
    public void addGame(GameData game) throws DataAccessException {
        if(games.contains(game)){
            throw new DataAccessException("game already exists");
        }
        games.add(game);
    }

    @Override
    //adds a player after confirming game exists
    public GameData verifyGamePosition(int gameID, ChessGame.TeamColor teamcolor, String playerName) throws DataAccessException{
        int counter = 0;
        for(int i = 0; i < games.size(); i++) {
            int gameid = games.get(i).gameID();
            if(gameID == gameid){
                if(games.get(i).blackUsername() != null && games.get(i).whiteUsername() != null){
                    throw new DataAccessException("all player spots are taken");
                }
                else if(games.get(i).blackUsername() == null && teamcolor == ChessGame.TeamColor.BLACK){
                    GameData newName = games.get(i).addBlackUsername(playerName);
                    games.set(i, newName);
                    return newName;
                }
                else if(games.get(i).whiteUsername() == null && teamcolor == ChessGame.TeamColor.WHITE){
                    GameData newName = games.get(i).addWhiteUsername(playerName);
                    games.set(i, newName);
                    return newName;
                }
            }
        }
        throw new DataAccessException("game does not exist");
    }

    @Override
    public Collection<GameData> listGames() {
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
