package websocket.commands;

import chess.ChessGame;

public class ConnectCommand extends UserGameCommand{

    public ConnectCommand(String authToken, Integer gameID, ChessGame.TeamColor color){
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.CONNECT;
        this.playerColor = color;
    }

}
