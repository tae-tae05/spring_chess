package websocket;

import websocket.messages.*;

public interface NotifHandler {
//    public default void notify(String message) {
//    }
    ;
    public void printMessage(ServerMessage message);

    public void updateGame(LoadGameM message);
}
