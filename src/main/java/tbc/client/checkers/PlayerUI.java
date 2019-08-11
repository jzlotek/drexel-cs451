package tbc.client.checkers;

import tbc.client.components.ComponentStore;
import tbc.shared.Move;

import java.util.ArrayList;

public class PlayerUI
{
    private static PlayerUI instance = null;

    // Whether the PlayerUI should be active right now
    // Ignore input from the player if not
    private boolean active = false;

    // The currently selected space on the UI
    private Space selectedSpace;

    // The currently selected piece on the UI
    private Piece selectedPiece;

    // The next move to make based on the currently selected space and piece
    private Move nextMove;

    public static PlayerUI getInstance()
    {
        if(instance == null)
        {
            instance = new PlayerUI();
        }

        return instance;
    }

    /*
     * Get whether the UI is currently listening for input
     */
    public boolean getActive()
    {
        return active;
    }

    /*
     * Set whether the UI is listening for input and reset all selected items to null if disabled
     */
    public void setActive(boolean _active)
    {
        active = _active;

        if(!active)
        {
            setSelectedPiece(null);
            setSelectedSpace(null);
            nextMove = null;
        }
    }

    /*
     * Get the currently selected space
     */
    public Space getSelectedSpace()
    {
        return selectedSpace;
    }

    /*
     * Set the currently selected space
     */
    public void setSelectedSpace(Space _selectedSpace)
    {
        System.out.println("Clicked on a space at position " + _selectedSpace.getPos());

        if(active) {
            selectedSpace = _selectedSpace;

            if(selectedPiece != null)
            {
                Board board = (Board)ComponentStore.getInstance().get("board");

                ArrayList<Move> validMoves = board.getValidMoves(selectedPiece, selectedPiece.getPos());

                for(Move move : validMoves)
                {
                    if(move != null && move.getNewLocation().equals(selectedSpace.getPos()))
                    {
                        nextMove = move;
                    }
                }
            }
        }
    }

    /*
     * Get the currently selected piece
     */
    public Piece getSelectedPiece()
    {
        return selectedPiece;
    }

    /*
     * Set the currently selected piece
     */
    public void setSelectedPiece(Piece _selectedPiece)
    {
        System.out.println("Clicked on piece at position " + _selectedPiece.getPos());
        if(active) {
            selectedPiece = _selectedPiece;
        }
    }

    /*
     * Get the next move to make
     */
    public Move getNextMove()
    {
        return nextMove;
    }
}
