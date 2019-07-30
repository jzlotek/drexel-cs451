package tbc.util;

import org.junit.Before;
import org.junit.Test;
import tbc.client.checkers.Board;
import tbc.client.checkers.Color;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SerializationUtilJSONTest {

    public SerializationUtilJSON serializer;

    @Before
    public void setup() {
        serializer = new SerializationUtilJSON();
    }

    @Test
    public void test_serializeSuccess() throws Exception {
        Board b = new Board();
        String s = serializer.serialize(b);
        assertNotNull(s);
        Board c = (Board) serializer.deserialize(s, Board.class);
        assertEquals(c.getPiece(0, 1), null);
        assertEquals(c.getPiece(0, 0).getColor(), Color.BLACK);
    }

    @Test(expected = IOException.class)
    public void test_serializeFail() throws Exception {
        Board b = new Board();
        String s = serializer.serialize(b);
        Board c = (Board) serializer.deserialize(s.substring(0, s.length() - 2), Board.class);
    }
}