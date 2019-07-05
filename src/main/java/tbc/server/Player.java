package tbc.server;

import tbc.util.Constants;

import java.io.IOException;
import java.net.Socket;

public class Player extends Thread { // each player will be it's own thread

    protected static Socket socket;


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
            socket = new Socket(Constants.HOST, Constants.PORT);
            System.out.println("Connected: " + socket.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}