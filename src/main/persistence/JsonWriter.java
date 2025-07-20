package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

import model.PostOffice;

// Represents a writer that writes JSON representation of PostOffice to file
// Modelled after json demo program
public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // Creates a writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: Creates writer
    //          Throws FileNotFoundException if destination file cannot found or opened for writing.
    public void openWriter() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: Writes JSON representation of PostOffice to file
    public void writeToFile(PostOffice po) {
        JSONObject json = po.toJson();
        writer.print(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: Closes writer
    public void closeWriter() {
        writer.close();
    }
}