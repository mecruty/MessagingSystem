package model;

import java.util.*;

// Represents a conversation having a list of messages
public class Conversation {

    // EFFECTS: Creates an empty conversation between two people
    public Conversation() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: Adds a message to the end of the conversation
    public void addMessage(Message m) {
        //stub
    }

    // EFFECTS: Returns the given number of messages, starting from the given index
    //          If more messages are requested then have been sent, will return all messages
    public List<Message> loadNumMessages(int numToLoad, int startIndex) {
        return null;
    }

    public List<Message> getMessages() {
        return null;
    }

    public void setMessages(List<Message> messages) {
       //stub
    }
}
