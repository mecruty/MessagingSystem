package model;

// Represents a single message, with a value and a sender
public class Message {

    private String value;

    // EFFECTS: Creates a message with a value and a sender
    public Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSender() {
        return "";
    }

    public void setSender(String value) {
        //stub
    }

}
