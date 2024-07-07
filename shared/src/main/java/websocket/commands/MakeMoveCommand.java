package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    public MakeMoveCommand(String authToken, Integer gameID, ChessMove move){
        super(authToken);
        this.gameID = gameID;
        this.move = move;
        this.commandType = CommandType.MAKE_MOVE;
    }
}
