package model;

import java.util.*;

// Represents a conversation having a list of messages and the accounts of the two parties
public class Conversation {

    // REQUIRES: parties.length == 2
    // EFFECTS: Creates an empty conversation between two people
    public Conversation(Account[] parties) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: Adds a message to the end of the conversation
    public void addMessage(Message message) {
        //stub
    }

    // EFFECTS: Returns the given number of messages, starting from the newest
    //          If more messages are requested then have been sent, will return all messages
    public List<Message> loadNumMessages(int numToLoad) {
        return null;
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

    public Account[] getParties() {
        return null;
    }

    // REQUIRES: parties.length == 2
    public void setParties(Account[] parties) {
        //stub
    }

}
