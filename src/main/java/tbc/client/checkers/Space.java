package tbc.client.checkers;

import tbc.client.components.SpritePanel;
import tbc.util.ConsoleWrapper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.Serializable;

public class Space implements Serializable, Renderable {
    private Vector pos;
    private Piece piece;
    private Color color;

    private static final String blackPath = "resources/img/space black.png";
    private static final String redPath = "resources/img/space red.png";

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
    public JComponent getRenderObject()
    {
        String path = blackPath;

        if(color == Color.RED)
        {
            path = redPath;
        }

        ImageIcon icon = new ImageIcon(path);

        SpritePanel panel = new SpritePanel(icon);

        panel.setLayout(new BorderLayout(2, 2));

        if(isOccupied())
        {
            panel.add(piece.getRenderObject());
        }

        return panel;
    }
}
