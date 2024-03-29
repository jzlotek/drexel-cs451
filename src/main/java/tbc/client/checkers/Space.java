package tbc.client.checkers;

import tbc.client.components.ComponentStore;
import tbc.client.components.SpritePanel;
import tbc.shared.Move;
import tbc.util.ConsoleWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Space extends Clickable implements Serializable, Renderable {
    private Vector pos;
    // The piece, if any, that occupies this space
    private Piece piece;
    private Color color;

    // The paths to the image files for black and red spaces
    private static final String blackPath = "/img/space black.png";
    private static final String redPath = "/img/space red.png";
    private static final String highlightedPath = "/img/space black highlighted.png";

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

        if (this.piece != null) {
            this.piece.setPos(this.pos);
        }
    }

    /*
     * Get the color of this space
     */
    public Color getColor() {
        return this.color;
    }

    @Override
    public JComponent getRenderObject() {
        String path = blackPath;

        if (color == Color.RED) {
            path = redPath;
        }

        if(PlayerUI.getInstance().getEnabled() && PlayerUI.getInstance().getActive())
        {
            Board board = (Board) ComponentStore.getInstance().get("board");

            ArrayList<Move> validMoves = board.getValidMoves(PlayerUI.getInstance().getSelectedPiece());

            for(Move move : validMoves)
            {
                if(move.getNewLocation().equals(pos))
                {
                    path = highlightedPath;
                }
            }
        }

        SpritePanel panel = new SpritePanel(path);

        panel.setLayout(new BorderLayout(2, 2));

        panel.addMouseListener(this);

        if (isOccupied()) {
            panel.add(piece.getRenderObject());
        }

        return panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PlayerUI.getInstance().setSelectedSpace(this);
    }
}
