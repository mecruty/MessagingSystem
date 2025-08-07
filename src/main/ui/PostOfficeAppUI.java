package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import model.Account;
import model.PostOffice;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.Event;
import model.EventLog;

// Modelled after:
// github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter

// A UI for the post office app menu screen
public class PostOfficeAppUI extends PostOfficeUI {

    private static final String JSON_LOCATION = "./data/PostOffice.json";
    private boolean showJsonPopUp;

    private JsonReader reader;
    private JsonWriter writer;

    private JPanel titlePanel;
    private JLabel welcomeLabel;
    private JLabel loginLabel;

    private LoginForm loginPanel;

    private JPanel quitPanel;
    private JButton quitButton;

    private JLabel image;

    // EFFECTS: Creates a UI for the Post Office App
    public PostOfficeAppUI(boolean showJsonPopUp, PostOffice po) {
        super("Digital Post Office");
        this.showJsonPopUp = showJsonPopUp;
        this.po = po;
        initializeGraphics();
        initializeFields();
    }

    // MODIFIES: this
    // EFFECTS: Creates Json readers, writers, and loads the Post Office if needed
    private void initializeFields() {
        reader = new JsonReader(JSON_LOCATION);
        writer = new JsonWriter(JSON_LOCATION);
        if (showJsonPopUp) {
            String question = "Would you like to load your past accounts and conversations?";
            int answer = JOptionPane.showConfirmDialog(
                    this, question, "Post Office Loader", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.YES_OPTION) {
                loadPostOffice();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds major elements to the JFrame
    @Override
    protected void addElements() {

        addTitlePanel();
        addLoginPanel();
        addQuitPanel();

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

        ImageIcon imageIcon = new ImageIcon("./data/post office.png");
        Image imageTemp = imageIcon.getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(imageTemp);
        image = new JLabel(imageIcon);
        image.setBorder(new EmptyBorder(0, 50, 0, 0));

        // Creating a temp panel to put flowlayout over boxlayout
        JPanel wrapper = new JPanel();
        wrapper.add(loginPanel);
        wrapper.add(image);
        add(wrapper, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Creates the quit panel at bottom with button
    private void addQuitPanel() {
        quitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quitPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                askSavePostOffice();
                printLog();
                System.exit(0);
            }
        });
        quitPanel.add(quitButton);
        add(quitPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: Processes user input for logging into an account
    public void login(String name, String password) {
        if (po.contains(name)) {
            Account acc = po.getAccount(name);
            if (acc.checkLoginDetails(name, password)) {
                enterAccount(acc);
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

    // MODIFIES: this
    // EFFECTS: Changes UI to enter account, disposing this
    private void enterAccount(Account acc) {
        dispose();
        new AccountUI(acc, po);
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
    }

    // EFFECTS: prints the EventLog to console
    public void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString());
        }
    }

    // EFFECTS: Saves the PostOffice to file
    private void savePostOffice() {
        try {
            writer.openWriter();
            writer.writeToFile(po);
            writer.closeWriter();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "File saving to " + JSON_LOCATION + " has failed");
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads PostOffice from file
    private void loadPostOffice() {
        try {
            po = reader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "File loading from " + JSON_LOCATION + " has failed");
        }
    }

}