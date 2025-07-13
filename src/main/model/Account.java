package model;

import java.util.*;

// Represents an account, containing a name, password, and a map of all conversations
public class Account {

    private String name;
    private String password;
    private Map<Account, Conversation> conversations;

    // EFFECTS: Creates an Account with the given name, password, and no preexisting
    //          conversations
    public Account(String name, String password) {
        this.name = name;
        this.password = password;
        conversations = new HashMap<>();
    }

    // MODIFIES: this, otherAccount
    // EFFECTS: Adds a shared empty Conversation to this and acc
    public void beginConversation(Account acc) {
        Conversation convo = new Conversation();
        conversations.put(acc, convo);
        acc.getConversations().put(this, convo);
    }

    // REQUIRES: numMessagesToRead > 0, startIndex >= 0
    // EFFECTS: Returns the given number of messages in the conversaion with acc,
    //          starting from startIndex.
    // If more messages are requested than actually exist, returns all messages.
    public List<Message> readConversation(Account acc, int numMessagesToRead, int startIndex) {
        Conversation convo = conversations.get(acc);
        return convo.loadNumMessages(numMessagesToRead, startIndex);
    }

    // EFFECTS: Adds message to the conversation between this and acc
    public void sendMessage(Account acc, Message m) {
        Conversation convo = conversations.get(acc);
        convo.addMessage(m);
    }

    // EFFECTS: Returns true if given name and password matches this.name and
    //          this.password
    public boolean checkLoginDetails(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Account, Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Map<Account, Conversation> conversations) {
        this.conversations = conversations;
    }
}
