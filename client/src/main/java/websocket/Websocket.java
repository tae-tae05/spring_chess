package websocket;

import com.google.gson.Gson;
import websocket.messages.*;

import javax.websocket.*;
import javax.websocket.MessageHandler;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.ServerException;
import javax.websocket.Session;


public class Websocket extends Endpoint {
    private final Session session;

    public Websocket(String url) throws ServerException{
        try {
            url = url.replace("http", "ws");
            URI uri = new URI(url + "/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
//                    switch (serverMessage.getServerMessageType()) {
//                        case NOTIFICATION ->
//                                gameHand.printMessage(new Gson().fromJson(message, NotificationM.class));
//                        case ERROR -> gameHand.printMessage(new Gson().fromJson(message, ErrorM.class));
//                        case LOAD_GAME -> gameHand.updateGame(new Gson().fromJson(message, LoadGameM.class));
//                    }
                }
            });
        }
        catch(URISyntaxException | DeploymentException | IOException e) {
            throw new ServerException(e.getMessage());
        }
    }
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
