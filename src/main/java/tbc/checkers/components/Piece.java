package tbc.checkers.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Piece implements Drawable {

    private int row;
    private int col;
    private Color color;

    public Piece(int row, int col, Color color) {
        this.move(row, col);
        this.color = color;
    }

    public void move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}
