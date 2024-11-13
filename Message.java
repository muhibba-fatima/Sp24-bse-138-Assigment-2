import java.time.LocalDateTime;

public class Message {

    private String messageContent;
    private String messageID;
    private boolean status;
    private LocalDateTime timeStamp;

    public Message(String messageContent, String messageID, boolean status) {
        this.messageContent = messageContent;
        this.messageID = messageID;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getMessageID() {
        return messageID;
    }

    public boolean getStatus() {
        return status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String toString(){
        return String.format("Message ID : %s\nMessage Content : %s\nStatus : %s\nTime Stamp : %s", messageID, messageContent, status ? "Seen" : "Unseen", timeStamp);
    }
}