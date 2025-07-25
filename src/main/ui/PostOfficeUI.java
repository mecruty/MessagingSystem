package ui;

import javax.swing.*;
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
        setVisible(true);
    }

    // EFFECTS: Saves the PostOffice to file
    private void savePostOffice() {
        try {
            writer.openWriter();
            writer.writeToFile(po);
            writer.closeWriter();
            //System.out.println("\nPost office successfuly saved");
        } catch (IOException e) {
            //System.out.println("\nFile saving to " + JSON_LOCATION + " has failed");
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads PostOffice from file
    private void loadPostOffice() {
        try {
            po = reader.read();
            //System.out.println("\nPost office successfully loaded");
        } catch (IOException e) {
            //System.out.println("\nFile loading from " + JSON_LOCATION + " has failed");
        }
    }

}