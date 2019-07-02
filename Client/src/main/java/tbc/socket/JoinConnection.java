package tbc.socket;

import tbc.Config;

import java.io.IOException;
import java.net.Socket;

public class JoinConnection {
    private static Socket socket;

    private JoinConnection() {
    }

    public static Socket getInstance() {
        if (socket == null) {
            try {
                socket = new Socket(Config.HOST, Config.PORT);
                System.out.println("Connected: " + socket.toString());
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
