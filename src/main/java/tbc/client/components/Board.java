package tbc.client.components;

import javafx.scene.canvas.GraphicsContext;
import tbc.util.UUIDUtil;

public class Board extends Drawable {
    private Piece[][] board;

    public Board() {

    }

    public Board(int size, int width, int height) {
        this.id = UUIDUtil.getUUID();
        this.board = new Piece[size][size];

        for (int r = 0; r < size; r++) {
            if (r < 3 || r > size - 4) {
                for (int c = 0; c < size; c++) {

                    if ((c + r) % 2 == 0) {
                        Piece p = new Piece(r, c, 10, 10);
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
