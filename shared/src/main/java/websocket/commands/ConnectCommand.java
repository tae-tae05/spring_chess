package websocket.commands;

public class ConnectCommand extends UserGameCommand{
    private final int gameID;
    private String authToken;

    public ConnectCommand(String authToken, Integer gameID){
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.CONNECT;
    }

    public int getGameID(){
        return gameID;
    }
}
