package tbc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tbc.Constants;
import tbc.util.ConsoleWrapper;

public class Player extends Thread { // each player will be it's own thread

    protected static Socket socket;
    public static final int PORT = Constants.PORT; // Using CS 451 as the port number
    public static final String HOST = Constants.HOST;
    
    /*
     * Default Constructor
     */
    public Player(Socket clientSocket) {
        this.socket = clientSocket;
    }

    /*
     * Getter for Socket Information
     */
    public Socket getSocket() {
        return this.socket;
    }

    /*
     * Setter to close the socket
     */
    public void closeSocket() {
       try {
    	   	socket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }
    
    public static void init() throws IOException {
        try {
            socket = new Socket(HOST, PORT);
            System.out.println("Connected: " + socket.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}