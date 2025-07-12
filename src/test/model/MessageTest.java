package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    private Message testMessage;
    private Account alice = new Account("Alice", "1234");
    private Account bob = new Account("Bob", "2345");
    
    @BeforeEach
    public void runBefore() {
        testMessage = new Message(alice, "hello!");
    }

    @Test
    public void testConstructor() {
        assertEquals(testMessage.getValue(), "hello!");
        assertEquals(testMessage.getSender(), alice);
    }

    @Test
    public void testSettingFields() {
        testMessage.setSender(bob);
        testMessage.setValue("bye!");
        assertEquals(testMessage.getValue(), "bye!");
        assertEquals(testMessage.getSender(), bob);
    }
}
