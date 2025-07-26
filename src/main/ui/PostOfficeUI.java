package ui;

import java.awt.*;

import javax.swing.*;

import model.PostOffice;

// TODO
public abstract class PostOfficeUI extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    protected PostOffice po;

    // EFFECTS: Creates a JFrame for post office with a title
    public PostOfficeUI(String title) {
        super(title);
        initializeGraphics();
        initializeFields();
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
        setResizable(false); //TODO
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // EFFECTS: Adds major elements to the JFrame
    protected abstract void addElements();

    // EFFECTS: Initializes fields
    protected abstract void initializeFields();
}