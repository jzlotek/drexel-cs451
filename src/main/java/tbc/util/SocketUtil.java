package tbc.util;

import java.io.*;
import java.net.Socket;

public class SocketUtil {

    public static BufferedReader getReaderFromSocket(Socket s) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        return reader;
    }
    public static void sendToSocket(String string, Socket socket) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write(string, 0, string.length());
        writer.newLine();
        writer.flush();
    }
}