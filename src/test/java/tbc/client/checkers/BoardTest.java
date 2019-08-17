package tbc.client.checkers;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void test_Boardsize(){
        Board b = new Board();
        assertEquals(b.getBoardSize().getX(),8 );
        assertEquals(b.getBoardSize().getY(),8 );
    }

}
