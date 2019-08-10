package tbc.client.checkers;

import tbc.client.checkers.events.EventHandler;
import tbc.client.components.SpriteLabel;
import tbc.util.UUIDUtil;

import javax.swing.*;
import java.io.Serializable;
import java.util.UUID;

public class Piece implements Serializable, Renderable {
    private Board board;
    private Color color = Color.BLACK;
    private boolean isAlive = true;
    private boolean hasCrown = false;
    private UUID uuid;

    private static final String blackPawnPath = "resources/img/checkersPiece black.png";
    private static final String blackKingPath = "resources/img/checkersPiece black crowned.png";
    private static final String redPawnPath = "resources/img/checkersPiece red.png";
    private static final String redKingPath = "resources/img/checkersPiece red crowned.png";

    private transient EventHandler onPieceKilledHandler;
    private transient EventHandler onPieceCrownedHandler;

    private Piece() {
    }

    public Piece(Board _board, Color _color) {
        this.uuid = UUIDUtil.getUUID();
        this.board = _board;
        this.color = _color;

        this.onPieceKilledHandler = new EventHandler();
        this.onPieceCrownedHandler = new EventHandler();
    }

    public Board getBoard() {
        return this.board;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }

    public void setIsAlive(boolean _isAlive) {
        this.isAlive = _isAlive;

        if (!isAlive) {
            this.onPieceKilledHandler.InvokeEvent(new Object[]{this});
        }
    }

    public boolean getHasCrown() {
        return this.hasCrown;
    }

    public void setHasCrown(boolean _hasCrown) {
        this.hasCrown = _hasCrown;

        if (this.hasCrown) {
            this.onPieceCrownedHandler.InvokeEvent(new Object[]{this});
        }
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public EventHandler getOnPieceKilledHandler() {
        return this.onPieceKilledHandler;
    }

    public EventHandler getOnPieceCrownedHandler() {
        return this.onPieceCrownedHandler;
    }

    @Override
    public JComponent getRenderObject() {
        String path = blackPawnPath;

        if(color == Color.BLACK)
        {
            if(hasCrown)
            {
                path = blackKingPath;
            }
        }
        else
        {
            if(hasCrown)
            {
                path = redKingPath;
            }
            else
            {
                path = redPawnPath;
            }
        }

        ImageIcon icon = new ImageIcon(path);

        return new SpriteLabel(new ImageIcon(path));
    }
}
