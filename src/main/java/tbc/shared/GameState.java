package tbc.shared;


import tbc.client.checkers.Board;
import tbc.client.checkers.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    public String message;
    public Board board;
    public ArrayList<Move> moves;
    public boolean yourTurn;
    public Color yourColor;

    public GameState() {
        this.message = "";
        this.board = null;
        this.moves = new ArrayList<>();
        this.yourTurn = false;
    }

    public GameState(String message) {
        this.message = message;
        this.board = null;
        this.moves = new ArrayList<>();
        this.yourTurn = false;
    }

    public GameState(String message, Board board) {
        this.message = message;
        this.board = board;
        this.moves = new ArrayList<>();
        this.yourTurn = false;
    }
}
