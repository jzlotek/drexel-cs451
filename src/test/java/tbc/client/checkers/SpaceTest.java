package tbc.client.checkers;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest {

    @Test
    public void test_spaceInit() {
        Space s = new Space(0, 0, Color.WHITE);
        assertEquals(s.getColor(), Color.WHITE);
        Piece p = new Piece(null, Color.WHITE, new Vector(0,0));
        assertTrue(s.getPos().equals(new Vector(0, 0)));
        s.setPiece(p);
        assertSame(p, s.getPiece());
        assertTrue(s.isOccupied());
        Space s1 = new Space(new Vector(1, 1), Color.RED);
        assertTrue(s1.getPos().equals(new Vector(1, 1)));
    }

    @Test
    public void test_spaceUnoccupied() {
        Space s = new Space(0, 0, Color.WHITE);
        assertEquals(s.getColor(), Color.WHITE);
        assertFalse(s.isOccupied());
    }

}