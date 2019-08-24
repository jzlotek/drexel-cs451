package tbc.client.checkers;

import tbc.client.components.BoardDisplayComponent;
import tbc.client.components.ComponentStore;
import tbc.shared.Move;
import tbc.util.ConsoleWrapper;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerUI {
    private static PlayerUI instance = null;

    // The color assigned to this player
    private Color color = Color.WHITE;

    // Whether the PlayerUI should be active right now
    // Ignore input from the player if not
    private static boolean enabled = false;

    // Whether the Player is actively taking a turn
    // If enabled and not active, the player has finished making all their moves
    private static boolean active = false;

    // The currently selected space on the UI
    private static Space selectedSpace;

    // The currently selected piece on the UI
    private static Piece selectedPiece;

    // The next move to make based on the currently selected space and piece
    private static ArrayList<Move> nextMoves;

    public static PlayerUI getInstance() {
        if (instance == null) {
            instance = new PlayerUI();
        }

        return instance;
    }

    public PlayerUI()
    {
        nextMoves = new ArrayList<Move>();
    }

    /*
     * Get the color assigned to this player
     */
    public Color getColor()
    {
        return color;
    }

    /*
     * Set the color of this player
     */
    public void setColor(Color _color)
    {
        color = _color;
    }

    /*
     * Get whether the UI is currently listening for input
     */
    public boolean getEnabled() {
        return enabled;
    }

    /*
     * Set whether the UI is listening for input and reset all selected items to null if disabled
     */
    public void setEnabled(boolean _enabled)
    {
        if(!_enabled)
        {
            setSelectedPiece(null);
            setSelectedSpace(null);
        }

        enabled = _enabled;
        active = enabled;
    }

    /*
     * Get whether the player is currently making moves (true during the player's turn if still clicking on pieces
     * and spaces)
     */
    public boolean getActive()
    {
        return active;
    }

    /*
     * Get the currently selected space
     */
    public Space getSelectedSpace() {
        return selectedSpace;
    }

    /*
     * Set the currently selected space
     */
    public void setSelectedSpace(Space _selectedSpace)
    {
        if(_selectedSpace != null) {
            ConsoleWrapper.WriteLn("Clicked on a space at position " + _selectedSpace.getPos() + "\nActive UI: " + this.active);
        }

        if (enabled && active) {
            selectedSpace = _selectedSpace;

            if(selectedPiece != null && selectedSpace != null)
            {
                Board board = (Board)ComponentStore.getInstance().get("board");

                ArrayList<Move> validMoves = board.getValidMoves(selectedPiece);

                for(Move move : validMoves)
                {
                    if(move != null && move.getNewLocation().equals(selectedSpace.getPos()))
                    {
                        ConsoleWrapper.WriteLn("Creating new move " + move);
                        nextMoves.add(move);

                        board.movePiece(selectedPiece, selectedPiece.getPos(), selectedSpace.getPos());
                        ComponentStore.getInstance().update("board", board);
                        // Update the board display
                        ((BoardDisplayComponent)ComponentStore.getInstance().get("boardDisplayComponent")).renderBoard();

                        // If the last move was a jump, check if more jumps can be made with the same piece
                        // If not, then end the player's turn
                        if(move.getRemoved().size() == 0 || !board.getPiecesThatCanJump(selectedPiece.getColor()).contains(selectedPiece))
                        {
                            active = false;
                        }
                    }
                }
            }
        }
    }

    /*
     * Get the currently selected piece
     */
    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    /*
     * Set the currently selected piece
     */
    public void setSelectedPiece(Piece _selectedPiece)
    {
        if(_selectedPiece != null) {
            ConsoleWrapper.WriteLn("Clicked on piece at position " + _selectedPiece.getPos() + "\nActive UI: " + this.active);
            ConsoleWrapper.WriteLn("Valid Moves: " + Arrays.asList(_selectedPiece.getBoard().getValidMoves(_selectedPiece)).toString());
        }
        // Only allow the player to change the selected piece if it is the player's turn, they have not finished
        // making moves, and they have not already made a move
        // This is to make sure that they do not change the active piece after already moving a piece
        if(enabled && active && nextMoves.size() == 0) {
            // Only allow the player to select a piece of their own color
            if(_selectedPiece == null || _selectedPiece.getColor() == this.color) {
                // Also make sure this piece can jump or no pieces can jump
                Board board = (Board)ComponentStore.getInstance().get("board");

                ArrayList<Piece> jumpPieces = board.getPiecesThatCanJump(this.color);

                if(jumpPieces.size() == 0 || jumpPieces.contains(_selectedPiece))
                {
                    selectedPiece = _selectedPiece;
                }
            }
            else
            {
                selectedPiece = null;
            }
        }
    }

    /*
     * Get the next move to make
     */
    public ArrayList<Move> getNextMoves() {
        return nextMoves;
    }

    public void resetNextMoves()
    {
        nextMoves.clear();
    }
}
