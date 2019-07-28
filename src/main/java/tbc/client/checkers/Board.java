package tbc.client.checkers;

import java.util.ArrayList;

public class Board
{
	private Vector boardSize = new Vector(8, 8);
	private Space[][] board;
	
	public Board()
	{
		createBoard();
	}
	
	public Board(Vector _size)
	{
		boardSize = _size;
		createBoard();
	}
	
	public Board(int _width, int _height)
	{
		boardSize = new Vector(_width, _height);
		createBoard();
	}
	
	private void createBoard()
	{
		board = new Space[boardSize.getY()][boardSize.getX()];
		
		Color color = Color.BLACK;
		
		for(int row = 0; row < boardSize.getY(); ++row)
		{
			for(int col = 0; col < boardSize.getX(); ++col)
			{
				board[row][col] = new Space(col, row, color);
				
				// Alternate colors for spaces
				if(col < boardSize.getX() - 1)
				{
					if(color == Color.BLACK)
					{
						color = Color.RED;
					}
					else
					{
						color = Color.BLACK;
					}
				}
			}
		}
	}
	
	public Vector getBoardSize()
	{
		return boardSize;
	}
	
	public Space getSpace(Vector _pos)
	{
		return getSpace(_pos.getX(), _pos.getY());
	}
	
	public Space getSpace(int _x, int _y)
	{
		if(!isValidSpace(_x, _y))
		{
			return null;
		}
		
		return board[_y][_x];
	}
	
	public boolean hasPiece(Vector _pos)
	{
		return hasPiece(_pos.getX(), _pos.getY());
	}
	
	public boolean hasPiece(int _x, int _y)
	{
		return board[_y][_x].isOccupied();
	}
	
	public Piece getPiece(Vector _pos)
	{
		return getPiece(_pos.getX(), _pos.getY());
	}
	
	public Piece getPiece(int _x, int _y)
	{
		Space space = getSpace(_x, _y);
		
		if(space == null)
		{
			return null;
		}
		
		return space.getPiece();
	}
	
	public boolean movePiece(Piece _piece, Vector _oldPos, Vector _newPos)
	{
		return movePiece(_piece, _oldPos.getX(), _oldPos.getY(), _newPos.getX(), _newPos.getY());
	}
	
	public boolean movePiece(Piece _piece, int _xOld, int _yOld, int _xNew, int _yNew)
	{
		// First check if the wrong origin point or next space is occupied
		if(getPiece(_xOld, _yOld) != _piece || hasPiece(_xNew, _yNew))
		{
			return false;
		}
		
		
		
		return true;
	}
	
	public ArrayList<Vector> getValidMoves(Piece _piece, Vector _pos)
	{
		return getValidMoves(_piece, _pos.getX(), _pos.getY());
	}
	
	public ArrayList<Vector> getValidMoves(Piece _piece, int _x, int _y)
	{
		ArrayList<Vector> peacefulMoves = new ArrayList<Vector>();
		ArrayList<Vector> jumpMoves = new ArrayList<Vector>();
		
		if(!isValidSpace(_x, _y))
		{
			return peacefulMoves;
		}
		
		// TODO: Add logic to check for all valid moves here
		if(_piece.getHasCrown())
		{
			for(int row = _y - 1; row <= _y + 1; ++row)
			{
				for(int col = _x - 1; col <= _x + 1; ++col)
				{
					// First check that the space exists on the board and it is not the starting space
					// Also is only valid if the space is black
					if(isValidSpace(col, row) && (_x != col || _y != row) && getSpace(col, row).getColor() == Color.BLACK)
					{
						// Next check that the space is unoccupied
						if(!hasPiece(col, row))
						{
							peacefulMoves.add(new Vector(col, row));
						}
						// There is a piece, so check if that piece is jumpable
						// Piece is jumpable if not the same color as this piece and the space past it is not occupied
						else
						{
							Piece otherPiece = getPiece(col, row);
							
							// The two pieces are different color, so check if the next space past is valid
							if(otherPiece.getColor() != _piece.getColor())
							{
								Vector diff = Vector.subtract(new Vector(_x, _y), new Vector(col, row));
								diff = Vector.multiply(diff, 2);
								
								Vector newPos = Vector.add(new Vector(_x, _y), diff);
								
								if(isValidSpace(newPos) && !hasPiece(newPos))
								{
									jumpMoves.add(newPos);
								}
							}
						}
					}
				}
			}
		}
		// The piece is not crowned, so it can only move up or down
		else
		{
			// Black starts at the bottom of the board (highest y values) while red starts at the top (lowest red values)
			// So, black pawns can only move - 1 on y while red pawns can only move + 1 on y
			int row = _y;
			
			if(_piece.getColor() == Color.BLACK)
			{
				--row;
			}
			else
			{
				++row;
			}
			
			for(int col = _x - 1; col <= _x + 1; ++col)
			{
				// First check that the space exists on the board and it is not the starting space
				// Also only valid if the space is black
				if(isValidSpace(col, row) && (_x != col || _y != row) && getSpace(col, row).getColor() == Color.BLACK)
				{
					// Next check that the space is unoccupied
					if(!hasPiece(col, row))
					{
						peacefulMoves.add(new Vector(col, row));
					}
					// There is a piece, so check if that piece is jumpable
					// Piece is jumpable if not the same color as this piece and the space past it is not occupied
					else
					{
						Piece otherPiece = getPiece(col, row);
						
						// The two pieces are different color, so check if the next space past is valid
						if(otherPiece.getColor() != _piece.getColor())
						{
							Vector diff = Vector.subtract(new Vector(_x, _y), new Vector(col, row));
							diff = Vector.multiply(diff, 2);
							
							Vector newPos = Vector.add(new Vector(_x, _y), diff);
							
							if(isValidSpace(newPos) && !hasPiece(newPos))
							{
								jumpMoves.add(newPos);
							}
						}
					}
				}
			}
		}
		
		if(jumpMoves.size() > 0)
		{
			return jumpMoves;
		}
		else
		{
			return peacefulMoves;
		}
	}
	
	public boolean isValidSpace(Vector _pos)
	{
		return isValidSpace(_pos.getX(), _pos.getY());
	}
	
	public boolean isValidSpace(int _x, int _y)
	{
		return _x > -1 && _x < boardSize.getX()
				&& _y > -1 && _y < boardSize.getY();
	}
}