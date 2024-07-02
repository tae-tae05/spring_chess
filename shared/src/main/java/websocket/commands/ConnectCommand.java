package websocket.commands;

import chess.ChessGame;

public class ConnectCommand extends UserGameCommand{
    private final int gameID;
    private String authToken;
    private ChessGame.TeamColor teamColor;

    public ConnectCommand(String authToken, Integer gameID, ChessGame.TeamColor color){
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.CONNECT;
        this.teamColor = color;
    }

    public int getGameID(){
        return gameID;
    }
    public ChessGame.TeamColor getTeamColor(){
        return teamColor;
    }
}
