package persistence;

import model.PostOffice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class JsonWriterTest extends JsonTest {
    PostOffice po;
    JsonWriter writerTest;
    JsonReader readerTest;

    @BeforeEach
    public void runBefore() {
        po = new PostOffice();
    }

    @Test
    public void testWriterInvalidFile() {
        writerTest = new JsonWriter("./data/<<<.json");
        try {
            writerTest.openWriter();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyPostOffice() {
        writerTest = new JsonWriter("./data/testWriterEmptyPostOffice.json");
        try {
            writerTest.openWriter();
            writerTest.writeToFile(po);
            writerTest.closeWriter();

            readerTest = new JsonReader("./data/testWriterEmptyPostOffice.json");
            po = readerTest.read();
            assertTrue(po.getAccounts().isEmpty());
        } catch (IOException e) {
            fail("IOException thrown");
        }
    }

    @Test
    public void testWriterGeneralPostOffice() {
        writerTest = new JsonWriter("./data/testWriterGeneralPostOffice.json");
        try {
            po = buildPostOffice();
            writerTest.openWriter();
            writerTest.writeToFile(po);
            writerTest.closeWriter();

            readerTest = new JsonReader("./data/testWriterGeneralPostOffice.json");
            po = readerTest.read();

            testPostOffice(po);
        } catch (IOException e) {
            fail("IOException thrown");
        }
    }
}
