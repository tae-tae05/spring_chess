package websocket.messages;

public class ErrorM extends ServerMessage{
    private final String errorM;

    public ErrorM(String type) {
        super(ServerMessageType.ERROR);
        this.errorM = type;
    }

    public String getErrorMessage()
    {
        return errorM;
    }
}
