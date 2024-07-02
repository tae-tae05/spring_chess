package websocket;

import chess.*;
import com.google.gson.Gson;
import websocket.commands.*;
import websocket.messages.*;

import javax.websocket.*;
import javax.websocket.MessageHandler;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.ServerException;
import javax.websocket.Session;


public class WebsocketFacade extends Endpoint {
    Session session;
    private NotifHandler notifHandler;

    public WebsocketFacade(String url) throws ServerException{
        try {
            url = url.replace("http", "ws");
            URI uri = new URI(url + "/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()) {
                        case NOTIFICATION ->
                                notifHandler.printMessage(new Gson().fromJson(message, NotificationM.class));
                        case ERROR -> notifHandler.printMessage(new Gson().fromJson(message, ErrorM.class));
                        case LOAD_GAME -> notifHandler.updateGame(new Gson().fromJson(message, LoadGameM.class));
                    }
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

    public void connect(String authToken, ChessGame.TeamColor teamColor, int gameID) throws ServerException {
        try{
            var action = new ConnectCommand(authToken, gameID, teamColor);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        }
        catch(IOException e){
            throw new ServerException(e.getMessage());
        }
    }

    public void leave(String authToken, int gameID) throws ServerException {
        try{
            var action = new LeaveCommand(authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        }
        catch(IOException e){
            throw new ServerException(e.getMessage());
        }
    }
    public void makeMove(String authToken, int gameID, ChessMove move) throws ServerException {
        try{
            var action = new MakeMoveCommand(authToken, gameID, move);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        }
        catch(IOException e){
            throw new ServerException(e.getMessage());
        }
    }

    public void resign(String authToken, int gameID) throws ServerException {
        try{
            var action = new ResignCommand(authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        }
        catch(IOException e){
            throw new ServerException(e.getMessage());
        }
    }
}
