package tbc.client.checkers;

import tbc.client.checkers.events.EventHandler;
import tbc.shared.Move;
import tbc.util.UUIDUtil;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Piece implements Serializable, Renderable {
    private Board board;
    private Color color = Color.BLACK;
    private boolean isAlive = true;
    private boolean hasCrown = false;
    private UUID uuid;

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
        JPanel box = new JPanel();
        java.awt.Color c = java.awt.Color.RED;
        if (this.color == Color.BLACK) {
            c = java.awt.Color.black;
        }
        box.setBackground(c);
        // TODO: get position
//        box.setBounds(this.getPos().getX() * 50, this.getPos().getY() * 50, 50, 50);
        return box;
    }
}
