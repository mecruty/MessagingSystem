package ui;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

// A UI for the login form of the Post Office App
public class LoginForm extends JPanel {

    private PostOfficeAppUI parent;

    private JLabel askForName;
    private JLabel askForPassword;
    private JTextField name;
    private JTextField password;
    private JButton submitLogin;
    private JLabel status;

    // EFFECTS: Creates the login form with inputs for name and password
    public LoginForm(PostOfficeAppUI parent) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.parent = parent;

        askForName = new JLabel("Enter your username:");
        askForName.setBorder(new EmptyBorder(50, 0, 20, 0));
        askForPassword = new JLabel("Enter your password:");
        askForPassword.setBorder(new EmptyBorder(20, 0, 20, 0));

        name = new JTextField(10);
        password = new JTextField(10);
        submitLogin = new JButton("Submit");
        submitLogin.addActionListener(addSubmitActionListener());

        status = new JLabel("");
        status.setBorder(new EmptyBorder(20, 0, 0, 0));

        addAll();
    }

    // MODIFIES: this
    // EFFECTS: Adds all labels, buttons, and text fields to this
    private void addAll() {
        add(askForName);
        add(name);
        add(askForPassword);
        add(password);
        add(submitLogin);
        add(status);
    }

    // EFFECTS: Adds an action listener for the submit button
    private ActionListener addSubmitActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typedName = name.getText();
                String typedPassword = password.getText();
                if (typedName.isBlank() || typedPassword.isBlank()) {
                    status.setText("Name or password cannot be blank");
                } else {
                    parent.login(name.getText(), password.getText());
                }
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: Resets all text fields to blank
    public void reset() {
        name.setText("");
        password.setText("");
    }

    // MODIFIES: this
    // EFFECTS: Sets the status text
    public void setStatus(String statusText) {
        status.setText(statusText);
    }
}
