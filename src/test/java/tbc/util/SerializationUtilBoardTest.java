package tbc.util;

import org.junit.Before;
import org.junit.Test;
import tbc.client.components.Board;

import static org.junit.Assert.*;

public class SerializationUtilBoardTest {

    public SerializationUtilBoard serializer;

    @Before
    public void setUp() throws Exception {
        serializer = new SerializationUtilBoard();
    }

    @Test
    public void test_serializeSuccess() throws Exception {
        Board b = new Board(10, 10, 10);
        String s = serializer.serialize(b);
        System.out.println(s);

        assertNotNull(s);

        Board c = serializer.deserialize(s);
        assertEquals(b.getID(), c.getID());
    }
}