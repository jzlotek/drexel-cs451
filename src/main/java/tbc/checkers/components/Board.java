package tbc.checkers.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board implements Drawable {
    private Piece[][] board;

    public Board(int size) {
        this.board = new Piece[size][size];

        for (int r = 0; r < size; r++) {
            if (r < 3 || r > size - 4) {
                for (int c = 0; c < size; c++) {

                    if ((c + r) % 2 == 0) {
                        Piece p = new Piece(r, c, new Color(255, 0, 0, 1));
                        this.board[r][c] = p;
                    } else {
                        this.board[r][c] = null;
                    }
                }
            }
        }

    }

    @Override
    public void draw(GraphicsContext gc) {
    }
}
