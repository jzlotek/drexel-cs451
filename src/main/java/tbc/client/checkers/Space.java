package tbc.client.checkers;

import tbc.client.components.SpritePanel;
import tbc.util.ConsoleWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;

public class Space extends Clickable implements Serializable, Renderable {
    private Vector pos;
    // The piece, if any, that occupies this space
    private Piece piece;
    private Color color;

    // The paths to the image files for black and red spaces
    private static final String blackPath = "resources/img/space black.png";
    private static final String redPath = "resources/img/space red.png";

    /*
     * Default empty constructor
     */
    private Space() {
    }

    /*
     * Constructor for a space at a given Vector position with specified color
     */
    public Space(Vector _pos, Color _color) {
        this.pos = _pos;
        this.color = _color;
    }

    /*
     * Constructor for a space at a given x y coordinate position with specified color
     */
    public Space(int _x, int _y, Color _color) {
        this.pos = new Vector(_x, _y);
        this.color = _color;
    }

    /*
     * Get the position of this space
     */
    public Vector getPos() {
        return this.pos;
    }

    /*
     * Get whether there is a Piece on this space
     */
    public boolean isOccupied() {
        return this.piece != null;
    }

    /*
     * Get the Piece that occupies this space (or null if there is none)
     */
    public Piece getPiece() {
        return this.piece;
    }

    /*
     * Set the Piece that occupies this space
     */
    public void setPiece(Piece _piece) {
        this.piece = _piece;
        this.piece.setPos(this.pos);
    }

    /*
     * Get the color of this space
     */
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

    @Override
    public void mouseClicked(MouseEvent e) {
        PlayerUI.getInstance().setSelectedSpace(this);
    }
}
