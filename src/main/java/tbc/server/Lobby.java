package tbc.server;

import java.io.*;
import java.util.*;

import tbc.util.ConsoleWrapper;

public class Lobby extends Thread{
	protected static ArrayList<Player> players = new ArrayList<Player>();
	protected boolean lobbyStatus;
	protected final int maxPlayers = 2;
	    
    /*
     * Constructor that takes in two players, automatically starts the game
     */
	public Lobby(Player player1, Player player2) throws Exception {
		players.add(player1);
		players.add(player2);
	}
	
	@Override
    public void run()  
    { 
		this.lobbyStatus = true;
		String received = "";
		String response = "";
		DataInputStream p1_input = null;
		DataInputStream p2_input = null;
		DataOutputStream p1_output = null;
		DataOutputStream p2_output = null;
		try {
			p1_input = new DataInputStream(players.get(0).getSocket().getInputStream());
			p2_input = new DataInputStream(players.get(1).getSocket().getInputStream());
			p1_output = new DataOutputStream(players.get(0).getSocket().getOutputStream());
			p2_output = new DataOutputStream(players.get(1).getSocket().getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		while(this.lobbyStatus == true) { // while we have a game going on
			boolean isMoveValid = false;
			while(!isMoveValid) {
				// read the move in from player one
				try { 
					received = p1_input.readUTF();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				// if the move is valid, we must output to player two
				if(validate(received)) { 
					isMoveValid = true;
				} else { // else we throw player one a warning and prompt for another move
					try {
						response = "Move Invalid! Please make a valid move.";
						p1_output.writeUTF(response);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					isMoveValid = false;
				}
			}			
			
			try { // output the move to player two
				response = received;
				p2_output.writeUTF(response);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			// check if player one's move caused a winner
			if(checkWinner())this.lobbyStatus = false;
			
			isMoveValid = false;
			while(!isMoveValid) {
				// read the move in from player two
				try { 
					received = p2_input.readUTF();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				// if the move is valid, we must output to player one
				if(validate(received)) { 
					isMoveValid = true;
				} else { // else throw player two a warning and prompt another move
					try {
						response = "Move Invalid! Please make a valid move.";
						p2_output.writeUTF(response);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					isMoveValid = false;
				}
			}		
			try { // output the move to player one
				response = received;
				p1_output.writeUTF(response);
			} catch (IOException ex) {
				ex.printStackTrace();
			}	
			
			// check if player two's move caused a winner
			if(checkWinner())this.lobbyStatus = false;
		}
		
		// after the game is over, send a closing message to the players and close the sockets
		try {
			p1_output.writeUTF("Game Over! Thanks for playing.");
			p2_output.writeUTF("Game Over! Thanks for playing.");
			players.get(0).getSocket().close();
			players.get(1).getSocket().close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }
	
	/*
	 * Adds a player to the lobby if there is room, or else throws a message back to the player
	 */
	public void addPlayer(Player newPlayer) throws Exception {
		if(players.size() > (maxPlayers - 1)) {
			DataOutputStream error = new DataOutputStream(newPlayer.getSocket().getOutputStream());
			error.writeUTF("Unable to add you to the lobby. Lobby is full!");
			ConsoleWrapper.WriteLn("Unable to add " + newPlayer.getSocket() + " to lobby. Lobby full");
			newPlayer.getSocket().close();
		} else {
			players.add(newPlayer);
		}
	}
	
	/*
	 * Function that validates if a move in the form of a message is valid
	 */
	private boolean validate(String message) {
		return true;
	}
	
	/*
	 * Function that checks if we have a winner after a specific move has been made
	 */
	private boolean checkWinner() {
		return false;
	}
		
}