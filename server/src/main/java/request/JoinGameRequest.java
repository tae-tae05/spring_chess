package request;

import chess.ChessGame;
import chess.ChessPosition;

public class JoinGameRequest{
    private Integer gameID;
    private ChessGame.TeamColor playerColor;

    public JoinGameRequest(){
    }

    public JoinGameRequest(int gameID, ChessGame.TeamColor playerColor){
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public Integer getGameID(){
        return gameID;
    }

    @Override
    public String toString() {
        return "JoinGameRequest{" +
                "gameID=" + gameID +
                ", color=" + playerColor +
                '}';
    }

    public ChessGame.TeamColor getTeamColor(){
        return playerColor;
    }
}
