package results;


public class CreateGameResults {
    private Integer gameID;
    private String message;

    public CreateGameResults(){}

    public CreateGameResults(int gameID, String message){
        this.gameID = gameID;
        this.message = message;
    }

    public void setMessage(String newMessage){
        message = newMessage;
    }
    public void setGameID(int newGameID){
        gameID = newGameID;
    }
}
