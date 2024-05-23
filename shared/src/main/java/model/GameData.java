package model;

import chess.ChessGame;

public class GameData {
    private final int GAME_ID;
    private final String WHITE_USERNAME;
    private final String BLACK_USERNAME;
    private final String GAME_NAME;
    private ChessGame GAME;

    public GameData(int gameID, String white, String black, String gameName, ChessGame game){
        this.GAME_ID = gameID;
        this.WHITE_USERNAME = white;
        this.BLACK_USERNAME = black;
        this.GAME_NAME = gameName;
        this.GAME = game;
    }

    public int getGameID(){
        return GAME_ID;
    }
    public String getWhiteUsername(){
        return WHITE_USERNAME;
    }
    public String getBlackUsername(){
        return BLACK_USERNAME;
    }
    public String getGameName(){
        return GAME_NAME;
    }
}
