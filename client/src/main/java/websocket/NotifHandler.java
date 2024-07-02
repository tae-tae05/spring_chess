package websocket;

import websocket.messages.LoadGameM;
import websocket.messages.ServerMessage;

public interface NotifHandler {
    public void updateGame(LoadGameM message);
    public void printMessage(ServerMessage message);
}
