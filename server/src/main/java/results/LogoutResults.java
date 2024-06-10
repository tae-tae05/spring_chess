package results;

public record LogoutResults(String message) {
    public LogoutResults setMessage(String newMessage){
        return new LogoutResults(newMessage);
    }
}
