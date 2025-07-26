package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.io.IOException;

import model.PostOffice;
import persistence.JsonReader;
import persistence.JsonWriter;

// Modelled after:
// github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter (General)
// docs.oracle.com/javase/tutorial/uiswing/layout/card.html (Layout Card)
// stackoverflow.com/questions/14625091/create-a-list-of-entries-and-make-each-entry-clickable (JList)

// TODO add class documentation
public class PostOfficeUI extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private static final String JSON_LOCATION = "./data/PostOffice.json";

    private PostOffice po;
    private JsonReader reader;
    private JsonWriter writer;

    private JPanel titlePanel;
    private JLabel welcomeLabel;
    private JLabel loginLabel;

    // EFFECTS: Creates PostOffice UI
    public PostOfficeUI() {
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
                this, question, "Message Loader", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // TODO
        addTitlePanel();

        // TODO
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates title panel at top with messages
    public void addTitlePanel() {
        welcomeLabel = new JLabel("Welcome to the post office!", JLabel.CENTER);
        welcomeLabel.setBorder(new EmptyBorder(20,0,0,0));
        welcomeLabel.setFont(new Font(Font.SERIF, Font.BOLD, 50));

        loginLabel = new JLabel("Please login or create an account.", JLabel.CENTER);
        loginLabel.setBorder(new EmptyBorder(-20,0,0,0));
        loginLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 30));

        titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(0, 1));
        titlePanel.add(welcomeLabel);
        titlePanel.add(loginLabel);

        add(titlePanel, BorderLayout.NORTH);
    }

    // EFFECTS: Saves the PostOffice to file
    private void savePostOffice() {
        try {
            writer.openWriter();
            writer.writeToFile(po);
            writer.closeWriter();
            //System.out.println("\nPost office successfuly saved"); TODO
        } catch (IOException e) {
            //System.out.println("\nFile saving to " + JSON_LOCATION + " has failed"); TODO
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads PostOffice from file
    private void loadPostOffice() {
        try {
            po = reader.read();
            //System.out.println("\nPost office successfully loaded"); TODO
        } catch (IOException e) {
            //System.out.println("\nFile loading from " + JSON_LOCATION + " has failed"); TODO
        }
    }

}