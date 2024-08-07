package websocket.commands;

import chess.ChessGame;
import model.AuthData;

public class ResignCommand extends UserGameCommand{
    private final ChessGame.TeamColor myColor;

    public ResignCommand(String auth, Integer gameID, ChessGame.TeamColor color){
        super(auth);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
        this.myColor = color;
    }

    public ChessGame.TeamColor getPlayerColor(){
        return myColor;
    }
}
