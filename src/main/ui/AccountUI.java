package ui;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

import model.Account;
import model.Message;
import model.PostOffice;

// Clickable list modelled after:
// stackoverflow.com/questions/14625091/create-a-list-of-entries-and-make-each-entry-clickable
// TODO class documentation
public class AccountUI extends PostOfficeUI {

    private Account acc;
    private boolean sortByNewest;

    private JPanel namePanel;
    private JLabel name;

    private JPanel accountPanel;
    private JList<String> accounts;

    private JPanel conversationPanel;
    private JTextArea conversation;
    private JTextField textBox;

    private JPanel featurePanel;
    private JButton delete;
    private JButton sort;

    private JPanel logoutPanel;
    private JButton logout;

    // EFFECTS: TODO
    public AccountUI(Account acc, PostOffice po) {
        super("Digital Post Office");
        this.acc = acc;
        this.po = po;
        sortByNewest = true;
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: TODO
    @Override
    protected void addElements() {
        addNamePanel();
        addCenterPanels();
        addFeaturePanel();
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
    // EFFECTS: Creates panel with accounts and corresponding text area
    private void addCenterPanels() {
        accountPanel = new JPanel(new GridLayout(0, 1));
        accountPanel.setBorder(new CompoundBorder(new EmptyBorder(10, 50, 0, 0), new LineBorder(Color.RED)));
        conversationPanel = new JPanel();
        conversationPanel.setLayout(new BoxLayout(conversationPanel, BoxLayout.Y_AXIS));
        conversationPanel.setBorder(new CompoundBorder(new EmptyBorder(0, 30, 0, 30), new LineBorder(Color.ORANGE)));

        initializeAccounts();
        initializeConversation();
        initializeTextBox();

        accountPanel.add(accounts);
        add(accountPanel, BorderLayout.WEST);

        conversationPanel.add(conversation);
        conversationPanel.add(textBox);
        add(conversationPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Sets values for list on left of UI
    private void initializeAccounts() {
        accounts = new JList<>();
        List<String> accsWithoutSelf = new ArrayList<>(Arrays.asList(po.getAccounts().keySet().toArray(new String[0])));
        accsWithoutSelf.remove(acc.getName());

        // Copied
        accounts.setModel(new AbstractListModel<String>() {
            String[] strings = accsWithoutSelf.toArray(new String[0]);

            @Override
            public int getSize() {
                return strings.length;
            }

            @Override
            public String getElementAt(int i) {
                return strings[i];
            }
        });

        accounts.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                loadAccountMessages();
            }
        });
        accounts.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
    }

    // MODIFIES: this
    // EFFECTS: Initalizes text area in center of UI
    private void initializeConversation() {
        conversation = new JTextArea();
        // TODO
        // conversation.setColumns(35);
        conversation.setRows(40);
        conversation.setEditable(false);
        conversation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    }

    // MODIFIES: this
    // EFFECTS: Initalizes text field for typing new messages
    private void initializeTextBox() {
        textBox = new JTextField();
        textBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accounts.getSelectedValue() != null && !textBox.getText().isBlank()) {
                    Message m = new Message(acc, textBox.getText());
                    Account otherAcc = po.getAccount(accounts.getSelectedValue());
                    acc.sendMessage(otherAcc, m);
                    loadAccountMessages();
                    textBox.setText("");
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Loads all messages in the selected conversation
    private void loadAccountMessages() {
        String selectedAcc = accounts.getSelectedValue();
        Account otherAcc = po.getAccount(selectedAcc);
        int numMessages = acc.getConversations().get(otherAcc).getMessages().size();
        List<Message> messages = acc.readConversation(otherAcc, 100, numMessages - 1);
        String text = "";
        if (sortByNewest) {
            for (int i = messages.size() - 1; i >= 0; i--) {
                String sender = messages.get(i).getSender().getName();
                String value = messages.get(i).getValue();
                text += sender + ": " + value + "\n";
            }
        } else {
            for (int i = 0; i < messages.size(); i++) {
                String sender = messages.get(i).getSender().getName();
                String value = messages.get(i).getValue();
                text += sender + ": " + value + "\n";
            }
        }
        conversation.setText(text);
    }

    // MODIFIES: this
    // EFFECTS: Creates a panel with a delete and sort button
    private void addFeaturePanel() {
        JPanel wrapper = new JPanel();
        wrapper.setBorder(new EmptyBorder(0, 0, 0, 20));

        featurePanel = new JPanel(new GridLayout(0,1));
        delete = new JButton("Delete");
        sort = new JButton("Sort: Newest");

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accounts.getSelectedValue() != null) {
                    Account otherAcc = po.getAccount(accounts.getSelectedValue());
                    acc.deleteMessage(otherAcc);
                    loadAccountMessages();
                }
            }
        });

        sort.addActionListener(addSubmitActionListener());

        featurePanel.add(sort);
        featurePanel.add(delete);
        wrapper.add(featurePanel);
        add(wrapper, BorderLayout.EAST);
    }

    // EFFECTS: Adds an action listener for the sort button, swapping between sorting oldest and newest
    private ActionListener addSubmitActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accounts.getSelectedValue() != null) {
                    if (sort.getText().equals("Sort: Newest")) {
                        sort.setText("Sort: Oldest");
                        sortByNewest = false;
                    } else {
                        sort.setText("Sort: Newest");
                        sortByNewest = true;
                    }
                    loadAccountMessages();
                }
            }
        };
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