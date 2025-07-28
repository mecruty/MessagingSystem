package ui;

import java.awt.*;

import javax.swing.*;

import model.PostOffice;

// A JFrame of dimensions 800x600 representing a screen of the UI
public abstract class PostOfficeUI extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    protected PostOffice po;

    // EFFECTS: Creates a JFrame for post office with a title
    public PostOfficeUI(String title) {
        super(title);
    }
    
    // EFFECTS: Initializes graphics
    protected void initializeGraphics() {
        initializeDefaultGraphics();
        addElements();
    }

    // MODIFIES: this
    // EFFECTS: Sets default JFrame initializations
    private void initializeDefaultGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // EFFECTS: Adds major elements to the JFrame
    protected abstract void addElements();
}