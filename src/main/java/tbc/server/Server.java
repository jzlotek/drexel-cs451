package tbc.server;

import java.io.*;
import java.net.*;
import java.util.*;

import tbc.Constants;
import tbc.util.*;

public class Server {

    public static final int PORT = Constants.PORT; // Using 4510
    public static final String HOST = Constants.HOST;    // Using Tux2 to host this service.
    protected static ServerSocket serverSocket = null;
    protected static Socket socket = null;
    protected static ArrayList<Socket> activePlayers = new ArrayList<Socket>();
    protected final static int allowedClients = 2;

    public static void main(String[] args) throws Exception {
        ConsoleWrapper.WriteLn("Booting Up Server Service...");
        try {
            ConsoleWrapper.Write("Starting service on: ");
            serverSocket = new ServerSocket(PORT);
            ConsoleWrapper.WriteLn(serverSocket.getLocalSocketAddress().toString());
        } catch (IOException ex) {
            ConsoleWrapper.WriteLn("Unable to start service on port: " + ex.getMessage());
            throw ex;
        }
        
        while(true) {
	        while (activePlayers.size() < allowedClients) {
	            try {
	                ConsoleWrapper.WriteLn("Ready to accept new connection...");
	                socket = serverSocket.accept(); // take in the new connection
	                activePlayers.add(socket); // add it to the list of active players
	                DataOutputStream initial_message = new DataOutputStream(socket.getOutputStream());
	                initial_message.writeUTF("Connected! Waiting for " + (allowedClients - activePlayers.size()) + " players to join");
	            } catch (IOException ex) {
	                ConsoleWrapper.WriteLn("Unable to accept new connection: " + ex.getMessage());
	                throw ex;
	            }
	            ConsoleWrapper.WriteLn("New connection established: " + socket);
	        }	        
	        ConsoleWrapper.WriteLn("Max connections established. Creating Lobby.");
	        
	        Player p1 = new Player(activePlayers.get(0));
	        Player p2 = new Player(activePlayers.get(1));	        
	        Lobby newGame = new Lobby(p1,p2);
	        newGame.start(); // start the new game
	        ConsoleWrapper.WriteLn("Lobby created. Starting health check process..");
	        while(newGame.lobbyStatus) {
	        	try {
	  				Thread.sleep(5000);
	  				if(!heartbeat()) {
	  					newGame.lobbyStatus = false;
	  				}
	  			} catch (InterruptedException ex) {
	  				throw ex;
	  			}
	        }
	        activePlayers.clear(); // clear the sockets for start of new game
        }
    }
    
    /*
     * Health Check Function that sees if any client disconnects in the middle of the game.
     */
    public static boolean heartbeat() {
    	boolean didDisconnect = false;
    	ConsoleWrapper.Write("Health Check Sent: ");
    	for (Socket player : activePlayers) {
    		if(player.isClosed()) { // if we have a terminated connection
    			activePlayers.remove(player);
    			didDisconnect = true;
    			ConsoleWrapper.WriteLn("Player " + player + " has disconnected.");   		
    		}
    	}
    	ConsoleWrapper.WriteLn(activePlayers.size() + "/" + allowedClients + " connected");
    	
    	// if there was a player that disconnected, there should be only one player left who won the game
    	if(didDisconnect) {
    		if(activePlayers.size() == 0) {
    			ConsoleWrapper.WriteLn("No active players to award a victory to.");
    		} else if (activePlayers.size() == 1) {
    			try {
					DataOutputStream winnerDefault = new DataOutputStream(activePlayers.get(0).getOutputStream());
					winnerDefault.writeUTF("You are the only remaining player left, win by default!");
					winnerDefault.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
    		} else {
    			ConsoleWrapper.WriteLn("Can't distinguish who won due to multiple players left.");
    		}
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public static void closeSocket(Player socket) throws Exception {
    	try {
			socket.getSocket().close();
		} catch (IOException ex) {
			throw ex;
		}
    }

    public static void terminate() {
        ConsoleWrapper.WriteLn("Starting shut down procedures");
        ConsoleWrapper.WriteLn("Reason for server closure?");
        String reason = ConsoleWrapper.Read();
        ConsoleWrapper.WriteLn("Sending closing message to sockets");
        
        ConsoleWrapper.WriteLn("Closing sockets");
        
    } 

}
