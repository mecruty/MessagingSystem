package model;

import java.util.*;

// Represents a conversation having a list of messages
public class Conversation {

    private List<Message> messages;

    // EFFECTS: Creates an empty conversation between two people
    public Conversation() {
        messages = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a message to the end of the conversation
    public void addMessage(Message m) {
        messages.add(m);
    }

    // REQUIRES: numToLoad > 0, startIndex >= 0
    // EFFECTS: Returns the given number of messages, starting from the given index
    //          If more messages are requested then have been sent, will return all messages
    public List<Message> loadNumMessages(int numToLoad, int startIndex) {
        List<Message> toReturn = new ArrayList<Message>();
        if (startIndex + numToLoad > messages.size()) {
            numToLoad = messages.size() - startIndex;
        }
        for (int i = 0; i < numToLoad; i++) {
            toReturn.add(messages.get(startIndex + i));
        }
        return toReturn;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
