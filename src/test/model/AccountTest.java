package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class AccountTest {
    private Account testAccount;
    private Account testAccount2;
    private Conversation convo;
    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;

    @BeforeEach
    public void runBefore() {
        testAccount = new Account("Alice", "1234");
        testAccount2 = new Account("Bob", "2345");
        convo = new Conversation();
        message1 = new Message(testAccount, "hi");
        message2 = new Message(testAccount, "bye");
        message3 = new Message(testAccount, "hello");
        message4 = new Message(testAccount, "goodbye");
    }

    @Test
    public void testConstructor() {
        assertEquals(testAccount.getName(), "Alice");
        assertEquals(testAccount.getPassword(), "1234");
        assertTrue(testAccount.getConversations().isEmpty());
    }

    @Test
    public void testSettingFields() {
        testAccount.setName("Charles");
        testAccount.setPassword("3456");
        testAccount.setConversations(Map.of(testAccount2, convo));
        assertEquals(testAccount.getName(), "Charles");
        assertEquals(testAccount.getPassword(), "3456");
        assertEquals(testAccount.getConversations(), Map.of(testAccount2, convo));
    }

    @Test
    public void testBeginConversation() {
        testAccount.beginConversation(testAccount2);
        assertEquals(testAccount.getConversations().size(), 1);
        assertEquals(testAccount2.getConversations().size(), 1);
        assertNotEquals(testAccount.getConversations().get(testAccount2), null);
        assertNotEquals(testAccount2.getConversations().get(testAccount), null);
    }

    @Test
    public void testBeginMultipleConversations() {
        Account testAccount3 = new Account("Charles", "3456");
        testAccount.beginConversation(testAccount2);
        testAccount2.beginConversation(testAccount3);
        assertEquals(testAccount.getConversations().size(), 1);
        assertEquals(testAccount2.getConversations().size(), 2);
        assertEquals(testAccount3.getConversations().size(), 1);
        assertNotEquals(testAccount.getConversations().get(testAccount2), null);
        assertNotEquals(testAccount2.getConversations().get(testAccount), null);
        assertNotEquals(testAccount2.getConversations().get(testAccount3), null);
        assertNotEquals(testAccount3.getConversations().get(testAccount2), null);
    }

    @Test
    public void testSendMessage() {
        testAccount.beginConversation(testAccount2);
        testAccount.sendMessage(testAccount2, message1);
        assertEquals(testAccount.getConversations().get(testAccount2).getMessages().size(), 1);
        assertEquals(testAccount2.getConversations().get(testAccount).getMessages().size(), 1);
        assertEquals(testAccount.getConversations().get(testAccount2).getMessages().get(0), message1);
        assertEquals(testAccount2.getConversations().get(testAccount).getMessages().get(0), message1);
    }

    @Test
    public void testSendMultipleMessages() {
        testAccount.beginConversation(testAccount2);
        testAccount.sendMessage(testAccount2, message1);
        testAccount2.sendMessage(testAccount, message2);
        testAccount.sendMessage(testAccount2, message3);
        assertEquals(testAccount.getConversations().get(testAccount2).getMessages(),
                List.of(message1, message2, message3));
        assertEquals(testAccount2.getConversations().get(testAccount).getMessages(),
                List.of(message1, message2, message3));
    }

    @Test
    public void testDeleteMessage() {
        testAccount.beginConversation(testAccount2);
        testAccount.sendMessage(testAccount2, message1);
        testAccount.deleteMessage(testAccount2);
        assertEquals(testAccount.getConversations().get(testAccount2).getMessages().size(), 0);
        assertEquals(testAccount2.getConversations().get(testAccount).getMessages().size(), 0);
    }

    @Test
    public void testDeleteMultipleMessages() {
        testAccount.beginConversation(testAccount2);
        testAccount.sendMessage(testAccount2, message1);
        testAccount.sendMessage(testAccount2, message2);
        testAccount.sendMessage(testAccount2, message3);
        testAccount.deleteMessage(testAccount2);
        testAccount.deleteMessage(testAccount2);
        assertEquals(testAccount.getConversations().get(testAccount2).getMessages(), List.of(message1));
        assertEquals(testAccount2.getConversations().get(testAccount).getMessages(), List.of(message1));
    }

    @Test
    public void testDeleteMessagesWrongSender() {
        testAccount.beginConversation(testAccount2);
        testAccount.sendMessage(testAccount2, message1);
        testAccount.sendMessage(testAccount2, message2);
        testAccount.sendMessage(testAccount2, message3);
        testAccount.deleteMessage(testAccount2);
        testAccount2.deleteMessage(testAccount);
        testAccount2.deleteMessage(testAccount);
        assertEquals(testAccount.getConversations().get(testAccount2).getMessages(),
                List.of(message1, message2));
        assertEquals(testAccount2.getConversations().get(testAccount).getMessages(),
                List.of(message1, message2));
    }

    @Test
    public void testDeleteMessagesDeletedAll() {
        testAccount.beginConversation(testAccount2);
        testAccount.sendMessage(testAccount2, message1);
        testAccount.sendMessage(testAccount2, message2);
        testAccount.sendMessage(testAccount2, message3);
        testAccount.deleteMessage(testAccount2);
        testAccount.deleteMessage(testAccount2);
        testAccount.deleteMessage(testAccount2);
        testAccount.deleteMessage(testAccount2);
        testAccount.deleteMessage(testAccount2);
        assertEquals(testAccount.getConversations().get(testAccount2).getMessages(), List.of());
        assertEquals(testAccount2.getConversations().get(testAccount).getMessages(), List.of());
    }


    @Test
    public void testReadConversation() {
        testAccount.beginConversation(testAccount2);
        testAccount.sendMessage(testAccount2, message1);
        testAccount.sendMessage(testAccount2, message2);
        testAccount.sendMessage(testAccount2, message3);
        testAccount.sendMessage(testAccount2, message4);
        assertEquals(testAccount.readConversation(testAccount2, 1, 3), List.of(message4));
        assertEquals(testAccount.readConversation(testAccount2, 2, 2), List.of(message3, message2));
    }

    @Test
    public void testReadConversationAll() {
        testAccount.beginConversation(testAccount2);
        testAccount.sendMessage(testAccount2, message1);
        testAccount.sendMessage(testAccount2, message2);
        testAccount.sendMessage(testAccount2, message3);
        testAccount.sendMessage(testAccount2, message4);
        assertEquals(testAccount.readConversation(testAccount2, 100, 3),
                List.of(message4, message3, message2, message1));
    }

    @Test
    public void testCheckLoginDetailsCorrect() {
        assertTrue(testAccount.checkLoginDetails("Alice", "1234"));
    }

    @Test
    public void testCheckLoginDetailsIncorrect() {
        assertFalse(testAccount2.checkLoginDetails("Bob", "1234"));
        assertFalse(testAccount2.checkLoginDetails("Alice", "2345"));
    }
}
