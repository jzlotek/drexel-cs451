package tbc.client.checkers;

import java.io.Serializable;

public class Space implements Serializable {
    private Vector pos;
    private Piece piece;
    private Color color;

    private Space() {
    }

    public Space(Vector _pos, Color _color) {
        pos = _pos;
        color = _color;
    }

    public Space(int _x, int _y, Color _color) {
        pos = new Vector(_x, _y);
        color = _color;
    }

    public Vector getPos() {
        return pos;
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece _piece) {
        piece = _piece;
    }

    public Color getColor() {
        return color;
    }
}
