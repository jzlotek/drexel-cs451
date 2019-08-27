package tbc.client.checkers;

import junit.framework.TestCase;

public class PieceTest extends TestCase {

    public void testGetBoard() {
        Board b = new Board();
        Piece p1 = new Piece(b,Color.WHITE, new Vector(0,0));
        assertEquals(p1.getBoard(), b);
        Piece p2 = new Piece(b,Color.RED, new Vector(7,7));
        assertEquals(p2.getBoard(), b);
    }

    public void testGetColor() {
        Board b = new Board();
        Piece p1 = new Piece(b,Color.WHITE, new Vector(0,0));
        assertEquals(p1.getColor(), Color.WHITE);
        Piece p2 = new Piece(b,Color.RED, new Vector(7,7));
        assertEquals(p2.getColor(), Color.RED);
    }

    public void testGetHasCrown() {
        Board b = new Board();
        Piece p1 = new Piece(b,Color.WHITE, new Vector(0,0));
        assertEquals(p1.getHasCrown(), false);

        Piece p2 = new Piece(b,Color.RED, new Vector(7,7));
        assertEquals(p2.getHasCrown(), false);
    }

    public void testSetHasCrown() {
        Board b = new Board();
        Piece p1 = new Piece(b,Color.WHITE, new Vector(0,0));
        assertEquals(p1.getHasCrown(), false);
        p1.setHasCrown(true);
        assertEquals(p1.getHasCrown(), true);

        Piece p2 = new Piece(b,Color.RED, new Vector(7,7));
        assertEquals(p2.getHasCrown(), false);
        p2.setHasCrown(true);
        assertEquals(p2.getHasCrown(), true);

    }

    public void testGetUUID() {
        Board b = new Board();
        Piece p1 = new Piece(b,Color.WHITE, new Vector(0,0));

        assertEquals(p1.getUUID().getClass(), b.getPiece(0,0).getUUID().getClass());

    }

    public void testGetPos() {
        Board b = new Board();
        Piece p1 = new Piece(b,Color.WHITE, new Vector(0,0));
        assertEquals(p1.getPos().getX(), 0);
    }

    public void testSetPos() {
        Board b = new Board();
        Piece p1 = new Piece(b,Color.WHITE, new Vector(0,0));
        assertEquals(p1.getPos().getX(), 0);

        p1.setPos(new Vector(2,0));
        assertEquals(p1.getPos().getX(), 2);

    }


    public void testGetOnPieceKilledHandler() {
        Board b = new Board();
        Piece p1 = new Piece(b,Color.WHITE, new Vector(0,0));
        assertNotNull(p1.getOnPieceKilledHandler());

    }

    public void testGetOnPieceCrownedHandler() {
        Board b = new Board();
        Piece p1 = new Piece(b,Color.WHITE, new Vector(0,0));
        assertNotNull(p1.getOnPieceCrownedHandler());
    }

}