package tbc.client.components;

import tbc.util.UUIDUtil;

import java.awt.*;

public class Piece extends Drawable {

    private int row;
    private int col;
    private int width;
    private int height;
    private Color color;

    public Piece(int row, int col, int width, int height, Color color) {
        this.id = UUIDUtil.getUUID();
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

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw() {
    }
}
