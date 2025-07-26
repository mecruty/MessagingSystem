package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Account;
import model.PostOffice;

public class AccountUI extends PostOfficeUI {

    Account acc;

    JPanel namePanel;
    JLabel name;

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
}
