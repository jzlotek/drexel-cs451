package tbc.server;

import java.io.IOException;
import java.net.Socket;

public class Player extends Thread { // each player will be it's own thread

    protected static Socket socket;
    public static final int PORT = 451; // Using CS 451 as the port number
    public static final String HOST = "10.246.251.14";    // Using Tux4 to host this service. DNS Map has us connecting to 10.246.251.14

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
    public void setSocket(boolean isActive) {
        if (isActive) {
            // if we need to active the socket
        } else {
            // else close the socket
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