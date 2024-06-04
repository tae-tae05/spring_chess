package results;

public record LogoutAndJoinResults(String message) {
    public LogoutAndJoinResults setMessage(String newMessage){
        return new LogoutAndJoinResults(newMessage);
    }
}
