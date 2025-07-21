package persistence;

import model.PostOffice;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class JsonReaderTest extends JsonTest {
    PostOffice po;
    JsonReader readerTest;

    @BeforeEach
    public void runBefore() {
        po = new PostOffice();
    }

    @Test
    public void testReaderInvalidFile() {
        readerTest = new JsonReader("./data/invalidFile.json");
        try {
            po = readerTest.read();
            fail("IOExcepion expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyPostOfice() {
        readerTest = new JsonReader("./data/testReaderEmptyPostOffice.json");
        try {
            po = readerTest.read();
            assertTrue(po.getAccounts().isEmpty());
        } catch (IOException e) {
            fail("IOException thrown");
        }
    }

    @Test
    public void testReaderGeneralPostOfice() {

        readerTest = new JsonReader("./data/testReaderGeneralPostOffice.json");
        try {
            po = readerTest.read();
            assertEquals(po, buildPostOffice());
        } catch (IOException e) {
            fail("IOException thrown");
        }
    }
}
