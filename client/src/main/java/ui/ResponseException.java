package ui;

public class ResponseException extends Throwable {
    public ResponseException(String error) {
        super(error);
    }
    public ResponseException(String error, Throwable reason) {
        super(error, reason);
    }
}
