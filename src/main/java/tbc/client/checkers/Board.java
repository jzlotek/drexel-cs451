package tbc.client.checkers;

import java.io.Serializable;
import java.util.ArrayList;
import tbc.client.checkers.Vector;

public class Board implements Serializable {
    private Vector boardSize;
    private Space[][] board;

    public Board() {
        this.boardSize = new Vector(8, 8);
        this.createBoard();
    }

    public Board(Vector _size) {
        this.boardSize = _size;
        this.createBoard();
    }

    public Board(int _width, int _height) {
        this.boardSize = new Vector(_width, _height);
        this.createBoard();
    }

    private void createBoard() {
        this.board = new Space[boardSize.getY()][boardSize.getX()];

        Color color = Color.BLACK;

        for (int row = 0; row < this.boardSize.getY(); ++row) {
            for (int col = 0; col < this.boardSize.getX(); ++col) {
                this.board[col][row] = new Space(col, row, color);
                // create a piece on that space
                if (color == Color.BLACK) {
                    if (row < 3) {
                        Piece p = new Piece(this, Color.BLACK);
                        this.board[col][row].setPiece(p);
                    } else if (row >= this.boardSize.getY() - 3) {
                        Piece p = new Piece(this, Color.RED);
                        this.board[col][row].setPiece(p);
                    }
                }

                // Alternate colors for spaces
                if (col < this.boardSize.getX() - 1) {
                    if (color == Color.BLACK) {
                        color = Color.RED;
                    } else {
                        color = Color.BLACK;
                    }
                }
            }
        }
    }

    public Vector getBoardSize() {
        return this.boardSize;
    }

    public Space getSpace(Vector _pos) {
        return this.getSpace(_pos.getX(), _pos.getY());
    }

    public Space getSpace(int _x, int _y) {
        if (!this.isValidSpace(_x, _y)) {
            return null;
        }

        return this.board[_y][_x];
    }

    public boolean hasPiece(Vector _pos) {
        return this.hasPiece(_pos.getX(), _pos.getY());
    }

    public boolean hasPiece(int _x, int _y) {
        return this.board[_y][_x].isOccupied();
    }

    public Piece getPiece(Vector _pos) {
        return this.getPiece(_pos.getX(), _pos.getY());
    }

    public Piece getPiece(int _x, int _y) {
        Space space = this.getSpace(_x, _y);

        if (space == null) {
            return null;
        }

        return space.getPiece();
    }

    public boolean movePiece(Piece _piece, Vector _oldPos, Vector _newPos) {
        return this.movePiece(_piece, _oldPos.getX(), _oldPos.getY(), _newPos.getX(), _newPos.getY());
    }

    public boolean movePiece(Piece _piece, int _xOld, int _yOld, int _xNew, int _yNew) {
        // First check if the wrong origin point or next space is occupied
        if (this.getPiece(_xOld, _yOld) != _piece || this.hasPiece(_xNew, _yNew)) {
            return false;
        }

        // Now check to make sure the destination is a valid move for this piece
        ArrayList<Vector> validMoves = this.getValidMoves(_piece, _xOld, _yOld);

        Vector attemptedMove = new Vector(_xNew, _yNew);

        boolean isFound = false;

        // Compare the attempted move location to all the valid moves
        for (Vector validMove : validMoves) {
            if (validMove.equals(attemptedMove)) {
                isFound = true;
            }
        }

        // If the new location was found, the move is valid, so move the piece
        if (isFound) {
            this.getSpace(_xOld, _yOld).setPiece(null);

            this.getSpace(attemptedMove).setPiece(_piece);
        }

        return isFound;
    }

    public ArrayList<Vector> getValidMoves(Piece _piece, Vector _pos) {
        return this.getValidMoves(_piece, _pos.getX(), _pos.getY());
    }

    public ArrayList<Vector> getValidMoves(Piece _piece, int _x, int _y) {
        ArrayList<Vector> peacefulMoves = new ArrayList<Vector>();
        ArrayList<Vector> jumpMoves = new ArrayList<Vector>();

        if (!this.isValidSpace(_x, _y)) {
            return peacefulMoves;
        }

        if (_piece.getHasCrown()) {
            for (int row = _y - 1; row <= _y + 1; ++row) {
                for (int col = _x - 1; col <= _x + 1; ++col) {
                    // First check that the space exists on the board and it is not the starting space
                    // Also is only valid if the space is black
                    if (this.isValidSpace(col, row) && (_x != col || _y != row) && this.getSpace(col, row).getColor() == Color.BLACK) {
                        // Next check that the space is unoccupied
                        if (!this.hasPiece(col, row)) {
                            peacefulMoves.add(new Vector(col, row));
                        }
                        // There is a piece, so check if that piece is jumpable
                        // Piece is jumpable if not the same color as this piece and the space past it is not occupied
                        else {
                            Piece otherPiece = this.getPiece(col, row);

                            // The two pieces are different color, so check if the next space past is valid
                            if (otherPiece.getColor() != _piece.getColor()) {
                                Vector diff = Vector.subtract(new Vector(_x, _y), new Vector(col, row));
                                diff = Vector.multiply(diff, 2);

                                Vector newPos = Vector.add(new Vector(_x, _y), diff);

                                if (this.isValidSpace(newPos) && !this.hasPiece(newPos)) {
                                    jumpMoves.add(newPos);
                                }
                            }
                        }
                    }
                }
            }
        }
        // The piece is not crowned, so it can only move up or down
        else {
            // Black starts at the bottom of the board (highest y values) while red starts at the top (lowest red values)
            // So, black pawns can only move - 1 on y while red pawns can only move + 1 on y
            int row = _y;

            if (_piece.getColor() == Color.BLACK) {
                --row;
            } else {
                ++row;
            }

            for (int col = _x - 1; col <= _x + 1; ++col) {
                // First check that the space exists on the board and it is not the starting space
                // Also only valid if the space is black
                if (this.isValidSpace(col, row) && (_x != col || _y != row) && this.getSpace(col, row).getColor() == Color.BLACK) {
                    // Next check that the space is unoccupied
                    if (!this.hasPiece(col, row)) {
                        peacefulMoves.add(new Vector(col, row));
                    }
                    // There is a piece, so check if that piece is jumpable
                    // Piece is jumpable if not the same color as this piece and the space past it is not occupied
                    else {
                        Piece otherPiece = this.getPiece(col, row);

                        // The two pieces are different color, so check if the next space past is valid
                        if (otherPiece.getColor() != _piece.getColor()) {
                            Vector diff = Vector.subtract(new Vector(_x, _y), new Vector(col, row));
                            diff = Vector.multiply(diff, 2);

                            Vector newPos = Vector.add(new Vector(_x, _y), diff);

                            if (this.isValidSpace(newPos) && !this.hasPiece(newPos)) {
                                jumpMoves.add(newPos);
                            }
                        }
                    }
                }
            }
        }

        if (jumpMoves.size() > 0) {
            return jumpMoves;
        } else {
            return peacefulMoves;
        }
    }

    public boolean isValidSpace(Vector _pos) {
        return this.isValidSpace(_pos.getX(), _pos.getY());
    }

    public boolean isValidSpace(int _x, int _y) {
        return _x > -1 && _x < this.boardSize.getX()
                && _y > -1 && _y < this.boardSize.getY();
    }
}