package websocket.messages;

public class NotificationM extends ServerMessage{

    private final String message;

    public NotificationM(String message)
    {
        super(ServerMessage.ServerMessageType.NOTIFICATION);
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
