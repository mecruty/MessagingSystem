package persistence;

import java.io.IOException;

import org.json.JSONObject;

import model.Account;
import model.Conversation;
import model.PostOffice;

// Represents a reader that reads post office information from JSON in file
// Modelled after json demo program
public class JsonReader {

    // EFFECTS: Creates a reader to read from source file
    public JsonReader(String source) {
        
    }

    // EFFECTS: Reads PostOffice from file and returns it.
    //          Throws IOException if error occurs while reading file
    public PostOffice read() throws IOException {
        return null;
    }

    // EFFECTS: Reads source files as string and returns it.
    //          Throws IOException if error occurs while reading file
    private String readFile(String source) throws IOException {
        return "";
    }

    // EFFECTS: Parses PostOffice from JSON object and returns it
    private PostOffice parsePostOffice(JSONObject jsonObj) {
        return null;
    }

    // MODIFIES: po
    // EFFECTS: Parses accounts from JSON object and adds them to PostOffice
    private void addAccounts(PostOffice po, JSONObject jsonObj) {

    }

    // MODIFIES: acc
    // EFFECTS: Parses conversations from JSON object and adds them to PostOffice
    private void addConversations(Account acc, JSONObject jsonObj) {

    }

    // MODIFIES: convo
    // EFFECTS: Parses messages from JSON object and adds them to PostOffice
    private void addMessages(Conversation convo, JSONObject jsonObj) {

    }
}
