package tbc.util;

import tbc.shared.GameState;

import java.io.*;
import java.net.Socket;

public class SocketUtil {

    public static String readFromSocket(Socket s) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        return reader.readLine();
    }

    public static void sendToSocket(String string, Socket socket) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write(string, 0, string.length());
        writer.newLine();
        writer.flush();
    }

    public static boolean sendGameState(GameState gs, Socket socket) {
        try {
            String string = SerializationUtilJSON.serialize(gs);
            sendToSocket(string, socket);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}