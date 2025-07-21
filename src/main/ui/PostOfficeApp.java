package ui;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import model.Account;
import model.Conversation;
import model.Message;
import model.PostOffice;
import persistence.JsonReader;
import persistence.JsonWriter;

// An app for running post office
public class PostOfficeApp {

    private static final String JSON_LOCATION = "./data/PostOffice.json";
    private Scanner sc;
    private PostOffice po;
    private JsonReader reader = new JsonReader(JSON_LOCATION);
    private JsonWriter writer = new JsonWriter(JSON_LOCATION);

    // EFFECTS: Runs the post office app.
    public PostOfficeApp() {
        runPostOfficeApp();
    }

    // MODIFIES: this
    // EFFECTS: Processes user input
    private void runPostOfficeApp() {
        sc = new Scanner(System.in);
        System.out.println("\nWould you like to load your past accounts and conversations? (y/n)");
        String next = sc.nextLine();
        if (next.equalsIgnoreCase("y") || next.equalsIgnoreCase("yes")) {
            loadPostOffice();
        } else {
            po = new PostOffice();
        }

        mainLoop(next);

        System.out.println("\nWould you like to save the messages in the post office. (y/n)");
        next = sc.nextLine();
        if (next.equalsIgnoreCase("y") || next.equalsIgnoreCase("yes")) {
            savePostOffice();
        }
        System.out.println("Bye!");
    }

    // MODIFIES: this
    // EFFECT: Runs the main loop for logging into accounts
    private void mainLoop(String next) {
        boolean quit = false;
        while (!quit) {
            System.out.println("\nWelcome to the post office, to quit type \"quit\"");
            System.out.println("To login/create an account, enter a name:");
            next = sc.nextLine();
            if (next.equals("quit")) {
                quit = true;
            } else {
                String name = next;
                System.out.println("\nEnter your password:");
                login(name, sc.nextLine());
            }
        }
    }

    // MODIFIES: this
    // EFFECT: Processes user input for the login processes
    private void login(String name, String password) {
        if (po.contains(name)) {
            Account acc = po.getAccount(name);
            if (acc.checkLoginDetails(name, password)) {
                System.out.println("\nSucessfully logged in!");
                enterAccount(name);
            } else {
                System.out.println("\nWrong password, please try again.");
            }
        } else {
            System.out.println("\nThat account does not exist, would you like to create a new account? (y/n)");
            String next = sc.nextLine();
            if (next.equalsIgnoreCase("y") || next.equalsIgnoreCase("yes")) {
                po.addAccount(name, new Account(name, password));
            }
        }
    }

    // MODIFIES: this
    // EFFECT: Processes user input after entering account
    private void enterAccount(String name) {
        Account acc = po.getAccount(name);
        boolean logout = false;
        String next;

        while (!logout) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("\tType \"1\" to view past conversations.");
            System.out.println("\tType \"2\" to create a new conversation.");
            System.out.println("\tType \"3\" to logout.");
            next = sc.nextLine();
            if (next.equals("1")) {
                loadConversations(acc);
            } else if (next.equals("2")) {
                createNewConversation(acc);
            } else if (next.equals("3")) {
                logout = true;
            }
        }

        logout();
    }

    // EFFECT: Loads names of past conversations
    private void loadConversations(Account acc) {
        System.out.println("\nYou have exisiting conversations with:");
        Set<Entry<Account, Conversation>> entries = acc.getConversations().entrySet();
        for (Entry<Account, Conversation> entry : entries) {
            System.out.println("\t" + entry.getKey().getName());
        }
        System.out.println("Enter who you would like to talk to:");
        String next = sc.nextLine();
        if (!po.contains(next)) {
            System.out.println("\nThat person does not exist, returning to menu");
        } else {
            Account otherAcc = po.getAccount(next);
            if (acc.getConversations().containsKey(otherAcc)) {
                System.out.println("\nEntering conversation:");
                selectConversation(acc, otherAcc);
            } else {
                System.out.println("\nYou do not have a conversation with that person, returning to menu");
            }
        }
    }

    // EFFECT: Selects a conversation to read, processing user input
    private void selectConversation(Account acc, Account otherAcc) {
        System.out.println("\tType \"leave\" to leave the conversation");
        System.out.println("\tType \"load n\" to load n more messages (do this before sending any messages)");
        System.out.println("\tType \"delete\" to delete the last message if it yours");
        System.out.println("\tType and enter to send a message");

        System.out.print(acc.getName() + ": ");
        String next = sc.nextLine();
        if (!next.equals("leave")) {
            next = loadMessagesLoop(acc, otherAcc, next);
            sendMessageLoop(acc, otherAcc, next);
        }

        System.out.println("\nLeaving conversation, returning to menu");
    }

    // EFFECT: Processes user input to load messages
    private String loadMessagesLoop(Account acc, Account otherAcc, String next) {
        List<Message> loadedMessages = new ArrayList<>();
        int numMessages = acc.getConversations().get(otherAcc).getMessages().size();
        int numLoaded = 0;
        while (next.matches("load [0-9]+")) {
            int num = Integer.parseInt(next.substring(next.indexOf(" ") + 1));
            loadMessages(acc, otherAcc, loadedMessages, num, numMessages - 1 - numLoaded);
            numLoaded += num;
            System.out.print(acc.getName() + ": ");
            next = sc.nextLine();
        }

        return next;
    }

    // MODIFIES: this
    // EFFECT: Processes user input to send/delete messages
    private void sendMessageLoop(Account acc, Account otherAcc, String next) {
        boolean leaveConversation = false;
        while (!leaveConversation) {
            if (next.matches("\\s*")) {
                // doNothing
            } else if (next.equals("leave")) {
                leaveConversation = true;
                continue;
            } else if (next.equals("delete")) {
                acc.deleteMessage(otherAcc);
                System.out.println("Delete command activated");
            } else {
                acc.sendMessage(otherAcc, new Message(acc, next));
            }
            System.out.print(acc.getName() + ": ");
            next = sc.nextLine();
        }
    }

    // EFFECT: Loads and outputs more messages
    private void loadMessages(Account acc, Account otherAcc, List<Message> loaded, int numToLoad, int startIndex) {
        List<Message> messages = acc.readConversation(otherAcc, numToLoad, startIndex);
        loaded.addAll(messages);
        System.out.println("\n_________________________");
        for (int i = loaded.size() - 1; i >= 0; i--) {
            String sender = loaded.get(i).getSender().getName();
            String value = loaded.get(i).getValue();
            System.out.println(sender + ": " + value);
        }
    }

    // MODIFIES: this
    // EFFECT: Creates a new conversation
    private void createNewConversation(Account acc) {
        System.out.println("Enter who you would like to create a conversation with?");
        String next = sc.nextLine();
        if (!po.contains(next)) {
            System.out.println("\nThat person does not exist, returning to menu");
        } else {
            Account otherAcc = po.getAccount(next);
            if (acc.getConversations().containsKey(otherAcc)) {
                System.out.println("\nYou already have a conversation with that person, returning to menu");
            } else {
                acc.beginConversation(otherAcc);
                System.out.println("\nSuccessfully created conversation with " + otherAcc.getName() + ".");
                System.out.println("Returning to menu");
            }
        }
    }

    // EFFECTS: Saves the PostOffice to file
    private void savePostOffice() {
        try {
            writer.openWriter();
            writer.writeToFile(po);
            writer.closeWriter();
            System.out.println("\nPost office successfuly saved");
        } catch (IOException e) {
            System.out.println("\nFile saving to " + JSON_LOCATION + " has failed");
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads PostOffice from file
    private void loadPostOffice() {
        try {
            po = reader.read();
            System.out.println("\nPost office successfully loaded");
        } catch (IOException e) {
            System.out.println("\nFile loading from " + JSON_LOCATION + " has failed");
        }
    }

    // EFFECT: Logs out of account
    private void logout() {
        System.out.println("\nLogged out");
        System.out.println("See you next time!");
    }
}
