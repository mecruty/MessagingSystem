package persistence;

import java.io.FileNotFoundException;

import model.PostOffice;

// Represents a writer that writes JSON representation of PostOffice to file
// Modelled after json demo program
public class JsonWriter {

    // Creates a writer to write to destination file
    public JsonWriter(String destination) {

    }

    // MODIFIES: this
    // EFFECTS: Creates writer
    //          Throws FileNotFoundException if destination file cannot found or opened for writing.
    public void openWriter() throws FileNotFoundException {

    }

    // MODIFIES: this
    // EFFECTS: Writes JSON representation of PostOffice to file
    public void writeToFile(PostOffice po) {

    }

    // MODIFIES: this
    // EFFECTS: Closes writer
    public void closeWriter() {

    }
}