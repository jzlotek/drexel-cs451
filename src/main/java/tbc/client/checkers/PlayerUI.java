package tbc.client.checkers;

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
    private static boolean active = false;

    // The currently selected space on the UI
    private static Space selectedSpace;

    // The currently selected piece on the UI
    private static Piece selectedPiece;

    // The next move to make based on the currently selected space and piece
    private static Move nextMove;

    public static PlayerUI getInstance() {
        if (instance == null) {
            instance = new PlayerUI();
        }

        return instance;
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
    public boolean getActive() {
        return active;
    }

    /*
     * Set whether the UI is listening for input and reset all selected items to null if disabled
     */
    public void setActive(boolean _active)
    {
        if(!_active)
        {
            setSelectedPiece(null);
            setSelectedSpace(null);
        }

        active = _active;
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

        if (active) {
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
                        nextMove = move;
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
        if(active) {
            // Only allow the player to select a piece of their own color
            if(_selectedPiece == null || _selectedPiece.getColor() == this.color) {
                selectedPiece = _selectedPiece;
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
    public Move getNextMove() {
        return nextMove;
    }

    public void resetNextMove()
    {
        nextMove = null;
    }
}
