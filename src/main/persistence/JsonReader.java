package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;


import org.json.JSONArray;
import org.json.JSONObject;

import model.Account;
import model.Message;
import model.PostOffice;

// Represents a reader that reads post office information from JSON in file
// Modelled after JsonSerialization Demo
public class JsonReader {
    private String source;

    // EFFECTS: Creates a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads PostOffice from file and returns it.
    //          Throws IOException if error occurs while reading file
    public PostOffice read() throws IOException {
        String jsonData = readFile(source);
        JSONObject json = new JSONObject(jsonData);
        return parsePostOffice(json);
    }

    // EFFECTS: Reads source files as string and returns it.
    //          Throws IOException if error occurs while reading file
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Parses PostOffice from JSON object and returns it
    private PostOffice parsePostOffice(JSONObject jsonObj) {
        PostOffice po = new PostOffice();
        addAccounts(po, jsonObj);
        return po;
    }

    // MODIFIES: po
    // EFFECTS: Parses accounts from JSON object and adds them to PostOffice
    private void addAccounts(PostOffice po, JSONObject jsonObj) {
        Set<String> accNames = jsonObj.keySet();
        for (String accName : accNames) {
            JSONObject accJson = jsonObj.getJSONObject(accName);
            String name = accJson.getString("name");
            String password = accJson.getString("password");
            Account acc = new Account(name, password);
            po.addAccount(name, acc);
        }
        // needed to add all accounts first, then add conversations
        for (String accName : accNames) {
            JSONObject accJson = jsonObj.getJSONObject(accName);
            Account acc = po.getAccount(accJson.getString("name"));
            addConversations(po, acc, accJson);
        }
    }

    // MODIFIES: acc
    // EFFECTS: Parses conversations from JSON object and adds them to PostOffice
    private void addConversations(PostOffice po, Account acc, JSONObject jsonObj) {
        JSONObject conversations = jsonObj.getJSONObject("conversations");
        Set<String> convoNames = conversations.keySet();
        for (String convoName : convoNames) {
            JSONArray convoJson = conversations.getJSONArray(convoName);
            Account otherAcc = po.getAccount(convoName);
            acc.beginConversation(otherAcc);
            addMessages(acc, otherAcc, convoJson);
        }
    }

    // MODIFIES: acc, otherAcc
    // EFFECTS: Parses messages from JSON array and adds them to PostOffice
    private void addMessages(Account acc, Account otherAcc, JSONArray messages) {
        for (Object message : messages) {
            JSONObject nextMessage = (JSONObject) message;
            String sender = nextMessage.getString("sender");
            String value = nextMessage.getString("value");
            if (sender.equals(acc.getName())) {
                acc.sendMessage(otherAcc, new Message(acc, value));
            } else {
                otherAcc.sendMessage(acc, new Message(otherAcc, value));
            }
        }
    }
}
