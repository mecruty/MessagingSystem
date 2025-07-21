package model;

import java.util.*;

import org.json.JSONObject;

// Represents a post office, with a list of accounts
public class PostOffice {

    private Map<String, Account> accounts;

    // EFFECTS: Creates a post office with no accounts
    public PostOffice() {
        accounts = new HashMap<String, Account>();
    }

    // MODIFIES: this
    // EFFECTS: Adds an empty account to this
    public void addAccount(String name, Account acc) {
        accounts.put(name, acc);
    }

    // EFFECTS: Returns an account with given name in getAccounts()
    public Account getAccount(String name) {
        return accounts.get(name);
    }

    // EFFECTS: Returns true if getAccounts() contains acc
    public boolean contains(String name) {
        return accounts.containsKey(name);
    }

    // EFFECTS: Returns this as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        Set<String> accNames = accounts.keySet();
        for (String accName : accNames) {
            json.put(accName, accounts.get(accName).toJson());
        }
        return json;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }
}