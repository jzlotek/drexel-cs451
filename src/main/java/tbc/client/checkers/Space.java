package tbc.client.checkers;

import tbc.util.ConsoleWrapper;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Space implements Serializable, Renderable {
    private Vector pos;
    private Piece piece;
    private Color color;

    private Space() {
    }

    public Space(Vector _pos, Color _color) {
        this.pos = _pos;
        this.color = _color;
    }

    public Space(int _x, int _y, Color _color) {
        this.pos = new Vector(_x, _y);
        this.color = _color;
    }

    public Vector getPos() {
        return this.pos;
    }

    public boolean isOccupied() {
        return this.piece != null;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece _piece) {
        this.piece = _piece;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public JComponent getRenderObject() {
        JPanel box = new JPanel();
        java.awt.Color c = java.awt.Color.RED;
        if (this.color == Color.BLACK) {
            c = java.awt.Color.black;
        }
        box.setBackground(c);
        box.setBounds(this.getPos().getX() * 50, this.getPos().getY() * 50, 50, 50);
        return box;
    }
}
