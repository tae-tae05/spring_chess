package model;

import chess.ChessGame;
public record GameData(Integer gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game){
    public GameData addWhiteUsername(String newWhite){
        return new GameData(gameID, newWhite, blackUsername, gameName, game);
    }
    public GameData addBlackUsername(String newBlack){
        return new GameData(gameID, whiteUsername, newBlack, gameName, game);
    }
    public GameData setGameID(int newGameID){
        return new GameData(newGameID, whiteUsername, blackUsername, gameName, game);
    }
    public GameData setGameName(String newGameName){
        return new GameData(gameID, whiteUsername, blackUsername, newGameName, game);
    }

    public GameData setGame(ChessGame game){
        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
    }
}