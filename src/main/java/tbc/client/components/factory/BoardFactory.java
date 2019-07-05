package tbc.client.components.factory;

import tbc.client.components.Board;

public class BoardFactory {

    private int width;
    private int height;

    public BoardFactory(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Board CreateBoard() {
        return new Board(8, this.width, this.height);
    }
}
