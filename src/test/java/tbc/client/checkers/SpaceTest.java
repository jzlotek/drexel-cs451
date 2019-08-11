package tbc.client.checkers;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest {

    @Test
    public void test_spaceInit() {
        Space s = new Space(0, 0, Color.BLACK);
        assertEquals(s.getColor(), Color.BLACK);
        Piece p = new Piece(null, Color.BLACK, new Vector(0,0));
        assertTrue(s.getPos().equals(new Vector(0, 0)));
        s.setPiece(p);
        assertSame(p, s.getPiece());
        assertTrue(s.isOccupied());
        Space s1 = new Space(new Vector(1, 1), Color.RED);
        assertTrue(s1.getPos().equals(new Vector(1, 1)));
    }

    @Test
    public void test_spaceUnoccupied() {
        Space s = new Space(0, 0, Color.BLACK);
        assertEquals(s.getColor(), Color.BLACK);
        assertFalse(s.isOccupied());
    }

}