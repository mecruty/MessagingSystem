package model;

// Represents a single message, with a value and a sender
public class Message {

    private String value;
    private String sender;

    // EFFECTS: Creates a message with a value and a sender
    public Message(String sender, String value) {
        this.value = value;
        this.sender = sender;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
