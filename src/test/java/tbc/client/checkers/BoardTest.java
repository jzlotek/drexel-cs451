package tbc.client.checkers;

import org.junit.Test;
import java.util.ArrayList;
import org.junit.runner.RunWith;

import javax.validation.constraints.Null;
import java.awt.*;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void getBoardSize() {
        Board b = new Board();
        assertEquals(b.getBoardSize().getX(),8 );
        assertEquals(b.getBoardSize().getY(),8 );

    }

    @Test
    public void getSpace() {
        Board b = new Board();
        assertEquals(b.getSpace(0,0).getPos().getX(),0);
        assertEquals(b.getSpace(0,0).getPos().getY(),0);
        assertEquals(b.getSpace(7,7).getPos().getX(),7);
        assertEquals(b.getSpace(7,7).getPos().getY(),7);

        assertEquals(b.getSpace(new Vector(7,7)).getPos(),b.getSpace(7,7).getPos());

        assertEquals(b.getSpace(8,7),null);

    }

    @Test
    public void hasPiece() {
        Board b = new Board();

        for(int y = 0; y < 3; y+=2){
            for(int x = 0; x < 7; x+=2){
                assertTrue(b.hasPiece(x,y));
            }
        }

        for(int y = 0; y < 3; y+=2){
            for(int x = 1; x < 8; x+=2){
                assertFalse(b.hasPiece(x,y));
            }
        }

        for(int y = 5; y < 8; y+=2){
            for(int x = 1; x < 7; x+=2){
                assertTrue(b.hasPiece(x,y));
            }
        }

        for(int y = 5; y < 8; y+=2){
            for(int x = 0; x < 8; x+=2){
                assertFalse(b.hasPiece(x,y));
            }
        }

    }

    @Test
    public void getPiece() {

        Board b = new Board();
        for(int y = 0; y < 3; y+=2){
            for(int x = 0; x < 7; x+=2){
                assertEquals(b.getPiece(x,y).getColor(), Color.RED);
            }
        }

        for(int y = 5; y < 8; y+=2){
            for(int x = 1; x < 7; x+=2){
                assertEquals(b.getPiece(x,y).getColor(), Color.WHITE);
            }
        }


        assertEquals(b.getPiece(9,9), null);

    }


    @Test
    public void movePiece() {
        Board b = new Board();
        assertTrue(b.hasPiece(0,2));
        assertFalse(b.hasPiece(1,3));
        assertTrue(b.isValidSpace(1,3));

    }

    @Test
    public void getValidMoves() {
    }


    @Test
    public void isValidSpace() {

        Board b = new Board();
        for(int y = 0; y < 7; y+=2){
            for(int x = 0; x < 7; x+=2){
                assertTrue(b.isValidSpace(x,y));
            }
        }

        for(int y = 1; y < 8; y+=2){
            for(int x = 1; x < 8; x+=2){
                assertTrue(b.isValidSpace(x,y));
            }
        }

    }

    @Test
    public void GetPiecesForColor() {

        Board b = new Board();
        ArrayList InitialPiecesRed = b.getPiecesForColor(Color.RED);
        ArrayList InitialPiecesWhite = b.getPiecesForColor(Color.WHITE);
        assertEquals(InitialPiecesRed.size(),12);
        assertEquals(InitialPiecesWhite.size(),12);

    }

    @Test
    public void HasWinner() {
        Board b = new Board();
        assertEquals(b.hasWinner(), false);
    }

    @Test
    public void GetValidMoves() {
        Board b = new Board();
        Piece validPossibleMoves = b.getPiece(0,3);
        assertEquals(validPossibleMoves,null);
    }

    @Test
    public void GetPiecesThatCanJump() {
        Board b = new Board();
        ArrayList allEligible = b.getPiecesThatCanJump(Color.RED);
        assertEquals(allEligible.size(),0);
    }

}


