package tbc.client.checkers;

import java.util.*;

public class GameManager
{
	private static GameManager instance;
	
	HashMap<Color, ArrayList<Piece>> playerPieces = new HashMap<Color, ArrayList<Piece>>();
	
	public static GameManager getInstance()
	{
		if(instance == null)
		{
			instance = new GameManager();
		}
		
		return instance;
	}
}
