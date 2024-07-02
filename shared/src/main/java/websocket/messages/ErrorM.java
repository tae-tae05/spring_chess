package websocket.messages;

public class ErrorM extends ServerMessage{
    private final String errorM;

    public ErrorM(String message) {
        super(ServerMessageType.ERROR);
        this.errorM = message;
    }

    public String getErrorMessage()
    {
        return errorM;
    }
}
