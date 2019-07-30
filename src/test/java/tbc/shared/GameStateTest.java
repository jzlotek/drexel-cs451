package tbc.shared;

import org.junit.Test;
import org.junit.runners.JUnit4;
import tbc.client.checkers.Board;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameStateTest {

    @Test
    public void test_createGameState() {
        GameState gs = new GameState();

        assertEquals(gs.message, "");
        assertEquals(gs.board, null);
        assertEquals(gs.moves, new ArrayList<>());
    }

    @Test
    public void test_createGameState2() {
        GameState gs = new GameState("test");

        assertEquals(gs.message, "test");
        assertEquals(gs.board, null);
        assertEquals(gs.moves, new ArrayList<>());
    }

    @Test
    public void test_createGameState3() {
        Board b = new Board();
        GameState gs = new GameState("test", b);

        assertEquals(gs.message, "test");
        assertEquals(gs.board, b);
        assertEquals(gs.moves, new ArrayList<>());
    }
}