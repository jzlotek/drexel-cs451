package tbc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 4510; // Using CS 451 as the port number
    public static final String HOST = "10.246.251.14";    // Using Tux4 to host this service. Map has us connecting to 10.246.251.14
    protected static ServerSocket serverSocket = null;
    protected static Socket socket = null;


    public static void main(String[] args) throws IOException {
        ConsoleWrapper.WriteLn("Booting Up Server Service...");
        try {
            ConsoleWrapper.Write("Starting service on: ");
            serverSocket = new ServerSocket(PORT);
            ConsoleWrapper.WriteLn(serverSocket.getLocalSocketAddress().toString());
        } catch (IOException e) {
            ConsoleWrapper.WriteLn("Unable to start service on port: " + e);
            throw e;
        }

        while (true) {
            try {
                ConsoleWrapper.WriteLn("Ready to accept new connections...");
                socket = serverSocket.accept();
            } catch (IOException e) {
                ConsoleWrapper.WriteLn("Unable to accept new connections: " + e);
                throw e;
            }
            Player newPlayer = new Player(socket); // create a new player for our system
            ConsoleWrapper.WriteLn("New connection established from IP: " + newPlayer.getSocket().getRemoteSocketAddress().toString());
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
