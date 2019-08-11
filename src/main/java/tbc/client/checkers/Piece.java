package tbc.client.checkers;

import tbc.client.checkers.events.EventHandler;
import tbc.client.components.SpriteLabel;
import tbc.util.UUIDUtil;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.UUID;

public class Piece extends Clickable implements Serializable, Renderable {
    private Board board;
    private Color color = Color.BLACK;
    private boolean hasCrown = false;
    private UUID uuid;

    // The position of the piece on the board
    private Vector position;

    // The file paths to the images of pieces of different colors, with and without a crown
    private static final String blackPawnPath = "resources/img/checkersPiece black.png";
    private static final String blackKingPath = "resources/img/checkersPiece black crowned.png";
    private static final String redPawnPath = "resources/img/checkersPiece red.png";
    private static final String redKingPath = "resources/img/checkersPiece red crowned.png";

    private transient EventHandler onPieceKilledHandler;
    private transient EventHandler onPieceCrownedHandler;

    /*
     * Default, empty constructor
     */
    private Piece() {
    }

    /*
     * Constructor for a piece on a specified board with a given color and position
     */
    public Piece(Board _board, Color _color, Vector _position) {
        this.uuid = UUIDUtil.getUUID();
        this.board = _board;
        this.color = _color;
        this.position = _position;

        this.onPieceKilledHandler = new EventHandler();
        this.onPieceCrownedHandler = new EventHandler();
    }

    /*
     * Get the board this piece occupies
     */
    public Board getBoard() {
        return this.board;
    }

    /*
     * Get the color of this piece (either Color.BLACK or Color.RED)
     */
    public Color getColor() {
        return this.color;
    }

    /*
     * Get whether this piece has a crown
     */
    public boolean getHasCrown() {
        return this.hasCrown;
    }

    /*
     * Set whether this piece has been crowned
     */
    public void setHasCrown(boolean _hasCrown) {
        this.hasCrown = _hasCrown;

        if (this.hasCrown) {
            this.onPieceCrownedHandler.InvokeEvent(new Object[]{this});
        }
    }

    /*
     * Get the UUID of this piece
     */
    public UUID getUUID() {
        return this.uuid;
    }

    /*
     * Get the position of this piece on the board
     */
    public Vector getPos() { return this.position; }

    /*
     * Set the position of this piece on the board
     */
    public void setPos(Vector _location)
    {
        this.position = _location;
    }

    /*
     * Get the EventHandler that is invoked when this piece is killed
     */
    public EventHandler getOnPieceKilledHandler() {
        return this.onPieceKilledHandler;
    }

    /*
     * Get the EventHandler that is invoked when this piece is crowned
     */
    public EventHandler getOnPieceCrownedHandler() {
        return this.onPieceCrownedHandler;
    }

    @Override
    public boolean equals(Object _other)
    {
        boolean returnValue = false;

        if(_other instanceof Piece)
        {
            Piece other = (Piece)_other;

            returnValue = this.uuid.equals(other.getUUID());
        }

        return returnValue;
    }

    @Override
    public int hashCode()
    {
        return uuid.hashCode();
    }

    @Override
    public JComponent getRenderObject() {
        String path = blackPawnPath;

        if (color == Color.BLACK) {
            if (hasCrown) {
                path = blackKingPath;
            }
        } else {
            if (hasCrown) {
                path = redKingPath;
            } else {
                path = redPawnPath;
            }
        }

        ImageIcon icon = new ImageIcon(path);

        return new SpriteLabel(new ImageIcon(path), JLabel.CENTER);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PlayerUI.getInstance().setSelectedPiece(this);
    }
}
