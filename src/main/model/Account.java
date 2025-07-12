package model;

import java.util.*;

// Represents an account, containing a name, password, and a map of all conversations
public class Account {

    // EFFECTS: Creates an Account with the given name, password, and no preexisting
    // conversations
    public Account(String name, String password) {

    }

    // MODIFIES: this, otherAccount
    // EFFECTS: Adds a shared empty Conversation to this and acc
    public void beginConversation(Account acc) {

    }

    // REQUIRES: numMessagesToRead > 0, startIndex >= 0
    // EFFECTS: Returns the given number of messages in the conversaion with acc, starting from startIndex.
    //          If more messages are requested than actually exist, returns all messages.
    public List<Message> readConversation(Account acc, int numMessagesToRead, int startIndex) {
        return null;
    }

    // EFFECTS: Adds message to the conversation between this and acc
    public void sendMessage(Account acc, Message message) {
        //stub
    }

    // EFFECTS: Returns true if given name and password matches this.name and this.password
    public boolean checkLoginDetails() {
        return false;
    }

    public String getName() {
        return null;
    }

    public void setName(String name) {
        //stub
    }

    public String getPassword() {
        return null;
    }

    public void setPassword(String password) {
        //stub
    }

    public Map<Account, Conversation> getConversations() {
        return null;
    }

    public void setConversations(Map<Account, Conversation> conversations) {
        //stub
    }
}
