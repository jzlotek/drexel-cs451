package tbc.client.checkers;

import tbc.client.checkers.events.EventHandler;

public class Piece
{
	private Board board;
    private Color color = Color.BLACK;
    private boolean isAlive = true;
    private boolean hasCrown = false;
    
    private EventHandler onPieceKilledHandler;
    private EventHandler onPieceCrownedHandler;
    
    public Piece(Board _board, Color _color)
    {
    	board = _board;
    	color = _color;
    	
    	onPieceKilledHandler = new EventHandler();
    	onPieceCrownedHandler = new EventHandler();
    }
    
    public Board getBoard()
    {
    	return board;
    }
    
    public Color getColor()
    {
    	return color;
    }
    
    public boolean getIsAlive()
    {
    	return isAlive;
    }
    
    public void setIsAlive(boolean _isAlive)
    {
    	isAlive = _isAlive;
    	
    	if(!isAlive)
    	{
    		onPieceKilledHandler.InvokeEvent(new Object[] {this});
    	}
    }
    
    public boolean getHasCrown()
    {
    	return hasCrown;
    }
    
    public void setHasCrown(boolean _hasCrown)
    {
    	hasCrown = _hasCrown;
    	
    	if(hasCrown)
    	{
    		onPieceCrownedHandler.InvokeEvent(new Object[] {this});
    	}
    }
    
    public EventHandler getOnPieceKilledHandler()
    {
    	return onPieceKilledHandler;
    }
    
    public EventHandler getOnPieceCrownedHandler()
    {
    	return onPieceCrownedHandler;
    }
}
