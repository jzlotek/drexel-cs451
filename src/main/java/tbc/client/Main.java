package tbc.client;

import tbc.Constants;
import tbc.client.components.Board;
import tbc.client.components.GameScene;
import tbc.util.ConsoleWrapper;
import tbc.util.SerializationUtilJSON;
import tbc.util.SocketUtil;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;


public class Main {
    static Socket s = null;

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
        GameScene scene = new GameScene();
        JTextArea debug = new JTextArea();
        debug.setBounds(400, 0, 200, 100);
        JButton b = new JButton("click");
        JButton joinButton = new JButton("Join a Game");
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("click")) {
                    if (s != null) {
                        try {
                            s.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    try {
                        s = new Socket("localhost", Constants.PORT);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    if (s == null) {
                        debug.setText("Failed to Join");
                    } else {
                        debug.setText(debug.getText()+"\nConnected to: " + s.getInetAddress().getHostAddress());
                    }
                }
                ConsoleWrapper.WriteLn(e.toString());
            }
        });
        joinButton.setBounds(0, 0, 100, 100);
        b.setBounds(130, 100, 100, 40);
        scene.add(b);
        scene.add(joinButton);
        scene.add(debug);
        scene.show();
    }

    public void gameLoop() throws InterruptedException {
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
        while (true) {
            String serverBoard = null;
            while (serverBoard == null) {
                try {
                    serverBoard = SocketUtil.readFromSocket(s);
                } catch (IOException e) {
                    serverBoard = null;
                    Thread.sleep(500);
                    ConsoleWrapper.WriteLn("Waiting 500 ms");
                    continue;
                }
                ConsoleWrapper.WriteLn(serverBoard);
            }
//            boardState = serializer.deserialize(serverBoard, Board.class);
        }
    }
}
