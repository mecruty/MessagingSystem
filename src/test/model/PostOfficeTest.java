package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class PostOfficeTest {

    private PostOffice postOfficeTest;
    private Account acc1;
    private Account acc2;

    @BeforeEach
    public void runBefore() {
        postOfficeTest = new PostOffice();
        acc1 = new Account("Alice", "1234");
        acc2 = new Account("Bob", "2345");
    }

    @Test
    public void testConstructor() {
        assertEquals(postOfficeTest.getAccounts().size(), 0);
    }

    @Test
    public void testAddAccount() {
        postOfficeTest.addAccount("Alice", acc1);
        postOfficeTest.addAccount("Bob", acc2);
        assertEquals(postOfficeTest.getAccounts(), Map.of("Alice", acc1, "Bob", acc2));
    }

    @Test
    public void testGetAccount() {
        postOfficeTest.addAccount("Alice", acc1);
        postOfficeTest.addAccount("Bob", acc2);
        assertEquals(postOfficeTest.getAccount("Alice"), acc1);
        assertEquals(postOfficeTest.getAccount("Bob"), acc2);
    }

    @Test
    public void testContains() {
        postOfficeTest.addAccount("Alice", acc1);
        assertTrue(postOfficeTest.contains("Alice"));
        assertFalse(postOfficeTest.contains("Bob"));
        postOfficeTest.addAccount("Bob", acc1);
        assertTrue(postOfficeTest.contains("Alice"));
        assertTrue(postOfficeTest.contains("Bob"));
    }
}
