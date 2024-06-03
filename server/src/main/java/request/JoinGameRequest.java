package request;

import chess.ChessGame;

public class JoinGameRequest{
    private Integer gameID;
    private String color;

    public JoinGameRequest(){
    }

    public JoinGameRequest(int gameID, String color){
        this.gameID = gameID;
        this.color = color;
    }

    public int getGameID(){
        return gameID;
    }

    @Override
    public String toString() {
        return "JoinGameRequest{" +
                "gameID=" + gameID +
                ", color=" + color +
                '}';
    }

    public String getTeamColor(){
        return color;
    }
}
