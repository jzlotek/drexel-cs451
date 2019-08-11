package tbc.client.checkers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import tbc.shared.Move;

public class Board implements Serializable {
    private Vector boardSize;
    private Space[][] board;

    HashMap<Color, ArrayList<Piece>> playerPieces = new HashMap<Color, ArrayList<Piece>>();

    /*
     * Constructor that creates the board at default size of 8x8
     */
    public Board() {
        this.boardSize = new Vector(8, 8);
        this.createBoard();
    }

    /*
     * Create a board full of spaces of alternating color and populate with 12 checkers pieces
     */
    private void createBoard() {
        this.board = new Space[boardSize.getY()][boardSize.getX()];

        // Start by creating a black space
        Color color = Color.BLACK;

        for (int row = 0; row < this.boardSize.getY(); ++row) {
            for (int col = 0; col < this.boardSize.getX(); ++col) {
                this.board[row][col] = new Space(col, row, color);
                // create a piece on that space if on the first or last 3 rows (where player pieces always start)
                if (color == Color.BLACK) {
                    if (row < 3) {
                        Piece p = new Piece(this, Color.BLACK, new Vector(col, row));
                        this.board[row][col].setPiece(p);
                        registerPiece(p);
                    } else if (row >= this.boardSize.getY() - 3) {
                        Piece p = new Piece(this, Color.RED, new Vector(col, row));
                        this.board[row][col].setPiece(p);
                        registerPiece(p);
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

    /*
     * Get the dimensions of the board
     */
    public Vector getBoardSize() {
        return this.boardSize;
    }

    /*
     * Get the Space object with a given position as a Vector
     */
    public Space getSpace(Vector _pos) {
        return this.getSpace(_pos.getX(), _pos.getY());
    }

    /*
     * Get the Space object with a given position as x and y coordinates
     */
    public Space getSpace(int _x, int _y) {
        if (!this.isValidSpace(_x, _y)) {
            return null;
        }

        return this.board[_y][_x];
    }

    /*
     * Get whether there is a piece at a specific Vector position
     */
    public boolean hasPiece(Vector _pos) {
        return this.hasPiece(_pos.getX(), _pos.getY());
    }

    /*
     * Get whether there is a piece at a specific x and y coordinate position
     */
    public boolean hasPiece(int _x, int _y) {
        return this.board[_y][_x].isOccupied();
    }

    /*
     * Get the Piece at a specific Vector position (null if no piece is present)
     */
    public Piece getPiece(Vector _pos) {
        return this.getPiece(_pos.getX(), _pos.getY());
    }

    /*
     * Get the Piece at a specific x and y coordinate position (null if no piece is present)
     */
    public Piece getPiece(int _x, int _y) {
        Space space = this.getSpace(_x, _y);

        if (space == null) {
            return null;
        }

        return space.getPiece();
    }

    /*
     * Get all the active pieces on the board for a given color
     */
    public ArrayList<Piece> getPiecesForColor(Color _color)
    {
        // First ensure that the playerPieces HashMap contains the _color key
        if(playerPieces.containsKey(_color))
        {
            return playerPieces.get(_color);
        }

        return null;
    }

    /*
     * Check whether there is a winner to the match (occurs when one color no longer has any active pieces)
     */
    public boolean hasWinner()
    {
        for(Color color : playerPieces.keySet())
        {
            if(playerPieces.get(color).size() == 0)
            {
                return true;
            }
        }

        return false;
    }

    /*
     * Get the piece on the board with a given UUID (or null if no piece with the UUID can be found)
     */
    public Piece getPiece(UUID _id)
    {
        for(Color color : playerPieces.keySet())
        {
            for(Piece piece : playerPieces.get(color))
            {
                if(piece.getUUID().equals(_id))
                {
                    return piece;
                }
            }
        }

        return null;
    }

    /*
     * Register a piece within the playerPieces HashMap
     */
    private void registerPiece(Piece _piece)
    {
        if(!playerPieces.containsKey(_piece.getColor()))
        {
            playerPieces.put(_piece.getColor(), new ArrayList<Piece>());
        }

        playerPieces.get(_piece.getColor()).add(_piece);
    }

    /*
     * Remove a piece from the playerPieces HashMap
     */
    private boolean removePiece(UUID _id)
    {
        // First get the piece with the UUID given and make sure it is not null
        Piece targetPiece = getPiece(_id);

        if(targetPiece == null)
        {
            return false;
        }

        // Double-check that the piece is actually at the space where it thinks it is at
        if(this.getSpace(targetPiece.getPos()).getPiece().getUUID() == _id)
        {
            // Remove the piece from the position
            this.getSpace(targetPiece.getPos()).setPiece(null);

            // Should remove the piece, but need to double-check
            if(this.playerPieces.get(targetPiece.getColor()).contains(targetPiece)) {
                this.playerPieces.get(targetPiece.getColor()).remove(targetPiece);
                return true;
            }
        }

        return false;
    }

    /*
     * Move a piece from one Vector position to another Vector position
     */
    public Move movePiece(Piece _piece, Vector _oldPos, Vector _newPos) {
        return this.movePiece(_piece, _oldPos.getX(), _oldPos.getY(), _newPos.getX(), _newPos.getY());
    }

    /*
     * Move a piece from one x y coordinate position to another x y coordinate position
     */
    public Move movePiece(Piece _piece, int _xOld, int _yOld, int _xNew, int _yNew) {
        // First check if the wrong origin point or next space is occupied
        if (this.getPiece(_xOld, _yOld) != _piece || this.hasPiece(_xNew, _yNew)) {
            return null;
        }

        // Now check to make sure the destination is a valid move for this piece
        ArrayList<Move> validMoves = this.getValidMoves(_piece);

        Vector attemptedMove = new Vector(_xNew, _yNew);

        // Compare the attempted move location to all the valid moves
        for (Move validMove : validMoves) {
            // The desired move was valid, so make the move
            if (validMove.getNewLocation().equals(attemptedMove)) {
                this.getSpace(_xOld, _yOld).setPiece(null);

                this.getSpace(attemptedMove).setPiece(_piece);

                _piece.setPos(attemptedMove);

                // TODO: Crown the piece if in the enemy home row

                for(UUID id : validMove.getRemoved())
                {
                    // Remove each piece with matching UUID
                    removePiece(id);
                }

                return validMove;
            }
        }

        return null;
    }

    /*
     * Get an ArrayList of all moves a piece can make from its current position
     */
    public ArrayList<Move> getValidMoves(Piece _piece) {
        int x = _piece.getPos().getX();
        int y = _piece.getPos().getY();

        // Create an ArrayList for all possible peaceful moves and another for all moves that would entail a jump over an opponent piece
        // At the end, will only return the peaceful moves if there are no jump moves available
        ArrayList<Move> peacefulMoves = new ArrayList<Move>();
        ArrayList<Move> jumpMoves = new ArrayList<Move>();

        if (!this.isValidSpace(x, y)) {
            return peacefulMoves;
        }

        // If the piece has a crown, then it can move diagonally in any direction
        if (_piece.getHasCrown()) {
            for (int row = y - 1; row <= y + 1; ++row) {
                for (int col = x - 1; col <= x + 1; ++col) {
                    // First check that the space exists on the board and it is not the starting space
                    // Also is only valid if the space is black
                    if (this.isValidSpace(col, row) && (x != col || y != row) && this.getSpace(col, row).getColor() == Color.BLACK) {
                        // Next check that the space is unoccupied
                        if (!this.hasPiece(col, row)) {
                            Move newMove = new Move(_piece.getUUID(), _piece.getPos(), new Vector(col, row));

                            peacefulMoves.add(newMove);
                        }
                        // There is a piece, so check if that piece is jumpable
                        // Piece is jumpable if not the same color as this piece and the space past it is not occupied
                        else {
                            Piece otherPiece = this.getPiece(col, row);

                            // The two pieces are different color, so check if the next space past is valid
                            if (otherPiece.getColor() != _piece.getColor()) {
                                Vector diff = Vector.subtract(new Vector(x, y), new Vector(col, row));
                                diff = Vector.multiply(diff, 2);

                                Vector newPos = Vector.add(new Vector(x, y), diff);

                                if (this.isValidSpace(newPos) && !this.hasPiece(newPos)) {
                                    Move newMove = new Move(_piece.getUUID(), _piece.getPos(), new Vector(col, row));
                                    newMove.addRemoved(otherPiece.getUUID());
                                    jumpMoves.add(newMove);
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
            int row = y;

            if (_piece.getColor() == Color.BLACK) {
                --row;
            } else {
                ++row;
            }

            for (int col = x - 1; col <= x + 1; ++col) {
                // First check that the space exists on the board and it is not the starting space
                // Also only valid if the space is black
                if (this.isValidSpace(col, row) && (x != col || y != row) && this.getSpace(col, row).getColor() == Color.BLACK) {
                    // Next check that the space is unoccupied
                    if (!this.hasPiece(col, row)) {
                        Move newMove = new Move(_piece.getUUID(), _piece.getPos(), new Vector(col, row));
                        peacefulMoves.add(newMove);
                    }
                    // There is a piece, so check if that piece is jumpable
                    // Piece is jumpable if not the same color as this piece and the space past it is not occupied
                    else {
                        Piece otherPiece = this.getPiece(col, row);

                        // The two pieces are different color, so check if the next space past is valid
                        if (otherPiece.getColor() != _piece.getColor()) {
                            Vector diff = Vector.subtract(new Vector(x, y), new Vector(col, row));
                            diff = Vector.multiply(diff, 2);

                            Vector newPos = Vector.add(new Vector(x, y), diff);

                            if (this.isValidSpace(newPos) && !this.hasPiece(newPos)) {
                                Move newMove = new Move(_piece.getUUID(), _piece.getPos(), new Vector(col, row));
                                newMove.addRemoved(otherPiece.getUUID());
                                jumpMoves.add(newMove);
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

    /*
     * Check that a given Vector position is valid on the board
     */
    public boolean isValidSpace(Vector _pos) {
        return this.isValidSpace(_pos.getX(), _pos.getY());
    }

    /*
     * Check that a given x y coordinate position is valid on the board
     */
    public boolean isValidSpace(int _x, int _y) {
        return _x > -1 && _x < this.boardSize.getX()
                && _y > -1 && _y < this.boardSize.getY();
    }
}