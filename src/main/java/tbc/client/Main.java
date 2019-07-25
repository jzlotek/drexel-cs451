package tbc.client;

import tbc.Constants;
import tbc.client.components.Board;
import tbc.util.ConsoleWrapper;
import tbc.util.SerializationUtilJSON;
import tbc.util.SocketUtil;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;


public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        JButton b = new JButton("click");
        b.setBounds(130, 100, 100, 40);

        window.add(b);
        window.setSize(600, 600);
        window.setLayout(null);
        window.setVisible(true);
    }

    public void gameLoop() {
        Socket s = null;
        SerializationUtilJSON serializer = new SerializationUtilJSON();
        try {
            s = new Socket("localhost", Constants.PORT);
        } catch (Exception e) {
            ConsoleWrapper.Write(e);
        }

        if (s != null) {
            ConsoleWrapper.Write(s.toString());
        }
        // Game Loop
        Board boardState = null;
        BufferedReader socketReader = null;
        try {
            socketReader = SocketUtil.getReaderFromSocket(s);
        } catch (Exception e) {
            // throw exception and notify main javafx
            ConsoleWrapper.WriteLn("Failed to get reader from socket");
        }
        while (true) {
            String serverBoard = null;
            while (serverBoard == null) {
                try {
                    serverBoard = socketReader.readLine();
                } catch (IOException e) {
                    serverBoard = null;
                    continue;
                }
                ConsoleWrapper.WriteLn(serverBoard);
            }
//            boardState = serializer.deserialize(serverBoard, Board.class);
        }
}
