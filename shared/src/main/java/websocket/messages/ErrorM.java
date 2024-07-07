package websocket.messages;

public class ErrorM extends ServerMessage{
    private final String errorMessage;

    public ErrorM(String message) {
        super(ServerMessageType.ERROR);
        this.errorMessage = message;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
