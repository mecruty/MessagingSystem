package model;

// Represents a single message, one string
public class Message {

    private String value;

    // EFFECTS: Creates a message with a value.
    public Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
