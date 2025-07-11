package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    private Message testMessage;
    private Account Alice = new Account("Alice");
    
    @BeforeEach
    public void runBefore() {
        testMessage = new Message(Alice, "hello!");
    }

    @Test
    public void testConstructor() {
        assertEquals(testMessage.getValue(), "hello!");
        assertEquals(testMessage.getSender(), Alice);
    }
}
