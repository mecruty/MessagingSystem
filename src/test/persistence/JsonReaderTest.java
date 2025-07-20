package persistence;

import model.PostOffice;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class JsonReaderTest extends JsonTest {
    JsonReader readerTest;

    @Test
    public void testReaderInvalidFile() {
        readerTest = new JsonReader("./data/invalidFile.json");
        try {
            PostOffice po = readerTest.read();
            fail("IOExcepion expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyPostOfice() {
        readerTest = new JsonReader("./data/testReaderEmptyPostOffice.json");
        try {
            PostOffice po = readerTest.read();
            assertTrue(po.getAccounts().isEmpty());
        } catch (IOException e) {
            fail("IOException thrown");
        }
    }

    @Test
    public void testGeneralPostOfice() {
        readerTest = new JsonReader("./data/testReaderGeneralPostOffice.json");
        try {
            PostOffice po = readerTest.read();
            assertEquals(po, buildPostOffice());
        } catch (IOException e) {
            fail("IOException thrown");
        }
    }
}
