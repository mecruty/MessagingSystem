package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ConversationTest {
    private Conversation conversationTest;
    private Account alice = new Account("Alice", "1234");
    private Account bob = new Account("Bob", "2345");
    private Message message1 = new Message(alice, "hi");
    private Message message2 = new Message(bob, "bye");
    private Message message3 = new Message(alice, "hello");
    private Message message4 = new Message(bob, "goodbye");

    @BeforeEach
    public void runBefore() {
        conversationTest = new Conversation();
    }

    @Test
    public void testConstructor() {
        assertTrue(conversationTest.getMessages().isEmpty());
    }

    @Test
    public void testSetMessages() {
        conversationTest.addMessage(message1);
        conversationTest.setMessages(List.of(message2, message3));
        assertEquals(conversationTest.getMessages(), List.of(message2, message3));
    }

    @Test
    public void testAddSingleMessage() {
        conversationTest.addMessage(message1);
        assertEquals(conversationTest.getMessages().size(), 1);
        assertEquals(conversationTest.getMessages().get(0), message1);
    }

    @Test
    public void testAddMultipleMessages() {
        conversationTest.addMessage(message1);
        conversationTest.addMessage(message2);
        assertEquals(conversationTest.getMessages().size(), 2);
        assertEquals(conversationTest.getMessages().get(0), message1);
        assertEquals(conversationTest.getMessages().get(1), message2);
    }

    @Test
    public void testRemoveSingleMessage() {
        conversationTest.addMessage(message1);
        conversationTest.removeMessage();
        assertEquals(conversationTest.getMessages().size(), 0);
    }

    @Test
    public void testRemoveMultipleMessages() {
        conversationTest.addMessage(message1);
        conversationTest.addMessage(message2);
        conversationTest.addMessage(message3);
        conversationTest.removeMessage();
        conversationTest.removeMessage();
        assertEquals(conversationTest.getMessages().size(), 1);
        assertEquals(conversationTest.getMessages().get(0), message1);
    }

    @Test
    public void testLoadNumMessages() {
        conversationTest.addMessage(message1);
        conversationTest.addMessage(message2);
        conversationTest.addMessage(message3);
        conversationTest.addMessage(message4);
        assertEquals(conversationTest.loadNumMessages(1, 0), List.of(message1));
        assertEquals(conversationTest.loadNumMessages(2, 2), List.of(message3, message4));
    }

    @Test
    public void testLoadAllMessages() {
        conversationTest.addMessage(message1);
        conversationTest.addMessage(message2);
        conversationTest.addMessage(message3);
        conversationTest.addMessage(message4);
        assertEquals(conversationTest.loadNumMessages(100, 0), List.of(message1, message2, message3, message4));
    }
}
