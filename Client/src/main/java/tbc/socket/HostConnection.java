package tbc.socket;

import tbc.Config;

import java.io.IOException;
import java.net.ServerSocket;

public class HostConnection {
    private static ServerSocket socket;

    private HostConnection() {
    }

    public static ServerSocket getInstance() {
        if (socket == null) {
            try {
                socket = new ServerSocket(Config.PORT);
                socket.accept();
                System.out.println("Started: " + socket.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return socket;
    }

    public static void closeInstance() {
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
