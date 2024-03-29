package models;

public class ResponseMessage {

    boolean successful;
    String message;

    public ResponseMessage() {

    }

    public ResponseMessage(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}