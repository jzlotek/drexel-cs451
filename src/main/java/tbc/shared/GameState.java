package tbc.shared;

import tbc.client.components.Board;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    public String message;
    public Board board;
    public ArrayList<Move> moves;

    public GameState() {
        this.message = "";
        this.board = null;
        this.moves = new ArrayList<>();
    }

    public GameState(String message) {
        this.message = message;
        this.board = null;
        this.moves = new ArrayList<>();
    }
}
