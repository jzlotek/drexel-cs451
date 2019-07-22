package tbc.util;
import org.junit.Before;
import org.junit.Test;
import tbc.client.components.Board;

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
        Board b = new Board(10, 10, 10);
        String s = serializer.serialize(b);
        System.out.println(s);

        assertNotNull(s);

        Board c = (Board) serializer.deserialize(s, Board.class);
        assertEquals(b.getID(), c.getID());
    }
}