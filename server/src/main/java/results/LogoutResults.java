package results;

import org.eclipse.jetty.util.log.Log;

public record LogoutResults(String message) {
    public LogoutResults setMessage(String newMessage){
        return new LogoutResults(newMessage);
    }
}
