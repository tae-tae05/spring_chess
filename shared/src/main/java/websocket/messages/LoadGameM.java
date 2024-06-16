package websocket.messages;

import chess.*;

public class LoadGameM extends ServerMessage{

    private final ChessGame game;
    private final String message;

    public LoadGameM(ChessGame game, String message)
    {
        super(ServerMessage.ServerMessageType.LOAD_GAME);
        this.game = game;
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public ChessGame getGame()
    {
        return game;
    }
}
