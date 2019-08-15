package tbc.client.checkers;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class PieceTest {

    @Test
    public void getBoard() {
        Board b = new Board();
        Piece p1 = new Piece(b, Color.BLACK);
        Piece p2 = new Piece(b, Color.RED);

        assertEquals(p1.getBoard(),b);
        assertEquals(p2.getBoard(),b);
    }

    @Test
    public void getColor() {
        Board b = new Board();
        Piece p1 = new Piece(b,Color.BLACK);
        Piece p2= new Piece(b,Color.RED);

        assertEquals(p1.getColor(), Color.BLACK);
        assertEquals(p2.getColor(), Color.RED);
    }

    @Test
    public void getIsAlive() {
        Board b = new Board();
        Piece p1 = new Piece(b, Color.BLACK);
        assertTrue(p1.getIsAlive());
    }

    @Test
    public void setIsAlive() {
        Board b = new Board();
        Piece p1 = new Piece(b, Color.BLACK);
        p1.setIsAlive(false);
        assertFalse(p1.getIsAlive());

    }

    @Test
    public void getHasCrown() {
        Board b = new Board();
        Piece p1 = new Piece(b, Color.BLACK);
        assertFalse(p1.getHasCrown());
    }

    @Test
    public void setHasCrown() {
        Board b = new Board();
        Piece p1 = new Piece(b, Color.BLACK);
        p1.setHasCrown(true);
        assertTrue(p1.getHasCrown());
    }
}
