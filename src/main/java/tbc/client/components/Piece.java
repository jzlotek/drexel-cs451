package tbc.client.components;

import javafx.scene.canvas.GraphicsContext;
import tbc.util.UUIDUtil;

public class Piece extends Drawable {

    private int row;
    private int col;
    private int width;
    private int height;

    public Piece(int row, int col, int width, int height) {
        this.id = UUIDUtil.getUUID();
        this.move(row, col);
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

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.fillRect(0, 0, 100, 100);
    }
}
