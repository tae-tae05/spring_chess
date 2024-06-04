package results;

public record ClearResults(String message) {
    public ClearResults setMessage(String newMessage){
        return new ClearResults(newMessage);
    }
}
