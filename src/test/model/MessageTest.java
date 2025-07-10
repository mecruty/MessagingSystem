package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    private Message testMessage;
    
    @BeforeEach
    public void runBefore() {
        testMessage = new Message("hello!");
    }

    @Test
    public void testConstructor() {
        assertEquals(testMessage.getValue(), "hello!");
    }
}
