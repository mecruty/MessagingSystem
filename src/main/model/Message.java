package model;

// Represents a single message, with a value and the account that sent it
public class Message {

    private String value;
    private Account sender;

    // EFFECTS: Creates a message with a value and a sender
    public Message(Account sender, String value) {
        this.value = value;
        this.sender = sender;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }
}
