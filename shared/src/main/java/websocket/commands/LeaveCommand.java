package websocket.commands;

public class LeaveCommand extends UserGameCommand{

    public LeaveCommand(String authToken, Integer gameID){
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.LEAVE;
    }
}
