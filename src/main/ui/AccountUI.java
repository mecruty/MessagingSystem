package ui;

import java.awt.BorderLayout;

import javax.swing.*;

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
        namePanel = new JPanel();
        name = new JLabel(acc.getName());

        namePanel.add(name);
        add(namePanel, BorderLayout.NORTH);
    }
}
