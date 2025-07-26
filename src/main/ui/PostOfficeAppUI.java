package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.Account;
import model.PostOffice;
import persistence.JsonReader;
import persistence.JsonWriter;

// Modelled after:
// github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter (General)
// docs.oracle.com/javase/tutorial/uiswing/layout (Layouts)
// stackoverflow.com/questions/14625091/create-a-list-of-entries-and-make-each-entry-clickable (JList)

// TODO add class documentation
public class PostOfficeAppUI extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private static final String JSON_LOCATION = "./data/PostOffice.json";

    private PostOffice po;
    private JsonReader reader;
    private JsonWriter writer;

    private JPanel titlePanel;
    private JLabel welcomeLabel;
    private JLabel loginLabel;

    private LoginForm loginPanel;

    private JPanel quitPanel;
    private JButton quitButton;

    // EFFECTS: Creates PostOffice UI
    public PostOfficeAppUI() {
        super("Digital Post Office");
        initializeGraphics();
        initializeFields();
    }

    // MODIFIES: this
    // EFFECTS: TODO
    private void initializeFields() {
        reader = new JsonReader(JSON_LOCATION);
        writer = new JsonWriter(JSON_LOCATION);
        String question = "Would you like to load your past accounts and conversations?";
        int answer = JOptionPane.showConfirmDialog(
                this, question, "Post Office Loader", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            loadPostOffice();
        } else {
            po = new PostOffice();
        }
    }

    // MODIFIES: this
    // EFFECTS: TODO
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false); // TODO
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // TODO
        addTitlePanel();
        addLoginPanel();
        addQuitPanel();
        // TODO
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates title panel at top with messages
    private void addTitlePanel() {
        welcomeLabel = new JLabel("Welcome to the post office!", JLabel.CENTER);
        welcomeLabel.setBorder(new EmptyBorder(20, 0, 0, 0));
        welcomeLabel.setFont(new Font(Font.SERIF, Font.BOLD, 50));

        loginLabel = new JLabel("Please login or create an account.", JLabel.CENTER);
        loginLabel.setBorder(new EmptyBorder(-20, 0, 0, 0));
        loginLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 30));

        titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(0, 1));
        titlePanel.add(welcomeLabel);
        titlePanel.add(loginLabel);

        add(titlePanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: Creates the login panel with fields for name and password
    private void addLoginPanel() {
        loginPanel = new LoginForm(this);
        // Creating a temp panel to put flowlayout in boxlayout
        JPanel wrapper = new JPanel();
        wrapper.add(loginPanel);
        add(wrapper, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Creates the login panel with fields for name and password
    private void addQuitPanel() {
        quitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quitPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                askSavePostOffice();
            }
        });
        quitPanel.add(quitButton);
        add(quitPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: TODO
    // EFFECTS: Processes user input for logging into an account
    public void login(String name, String password) {
        if (po.contains(name)) {
            Account acc = po.getAccount(name);
            if (acc.checkLoginDetails(name, password)) {
                // enterAccount(name);
            } else {
                loginPanel.setStatus("Wrong password, please try again");
                loginPanel.reset();
            }
        } else {
            String question = "That account does not exist, would you like to create it as a new account?";
            int answer = JOptionPane.showConfirmDialog(
                    this, question, "Account Creator", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.YES_OPTION) {
                po.addAccount(name, new Account(name, password));
            } else {
                loginPanel.setStatus("Try again");
                loginPanel.reset();
            }
        }
    }

    // EFFECTS: Creates a pop up that asks if the current post office should be
    // saved
    public void askSavePostOffice() {
        String question = "Would you like to save the messages in the post office?";
        int answer = JOptionPane.showConfirmDialog(
                this, question, "Post Office Saver", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            savePostOffice();
        }
        System.exit(0);
    }

    // EFFECTS: Saves the PostOffice to file
    private void savePostOffice() {
        try {
            writer.openWriter();
            writer.writeToFile(po);
            writer.closeWriter();
            // System.out.println("\nPost office successfuly saved"); TODO
        } catch (IOException e) {
            // System.out.println("\nFile saving to " + JSON_LOCATION + " has failed"); TODO
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads PostOffice from file
    private void loadPostOffice() {
        try {
            po = reader.read();
            // System.out.println("\nPost office successfully loaded"); TODO
        } catch (IOException e) {
            // System.out.println("\nFile loading from " + JSON_LOCATION + " has failed");
            // TODO
        }
    }

}