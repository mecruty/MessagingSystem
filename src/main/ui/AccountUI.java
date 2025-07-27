package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Account;
import model.PostOffice;

public class AccountUI extends PostOfficeUI {

    Account acc;

    JPanel namePanel;
    JLabel name;

    JPanel logoutPanel;
    JButton logout;

    // EFFECTS: TODO
    public AccountUI(Account acc, PostOffice po) {
        super("Digital Post Office");
        this.acc = acc;
        this.po = po;
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: TODO
    @Override
    protected void addElements() {
        addNamePanel();
        addLogoutPanel();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates name panel at top
    private void addNamePanel() {
        namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBorder(new EmptyBorder(5, 20, 0, 0));
        name = new JLabel(acc.getName());
        name.setFont(new Font(Font.SERIF, Font.BOLD, 60));

        namePanel.add(name);
        add(namePanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: Creates logout button at bottom
    private void addLogoutPanel() {
        logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoutPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        logout = new JButton("Logout");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        logoutPanel.add(logout);
        add(logoutPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: Changes UI to main menu, disposing this
    private void logout() {
        dispose();
        new PostOfficeAppUI(false, po);
    }
}