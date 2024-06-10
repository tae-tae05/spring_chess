package results;

public record RegisterResults(String username, String authToken, String message) {
    public RegisterResults setAuthToken(String newAuthToken){
        return new RegisterResults(this.username, newAuthToken, this.message);
    }
    public RegisterResults setUsername(String newString){
        return new RegisterResults(newString, this.authToken, this.message);
    }
    public RegisterResults setMessage(String newString){
        return new RegisterResults(this.username, this.authToken, newString);
    }
}

