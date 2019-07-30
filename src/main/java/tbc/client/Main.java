package tbc.client;

import tbc.Constants;
import tbc.client.components.ComponentStore;
import tbc.client.components.GameScene;
import tbc.shared.GameState;
import tbc.util.ConsoleWrapper;
import tbc.util.SerializationUtilJSON;
import tbc.util.SocketUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;


public class Main {
    static Socket s = null;
    static GameScene window = null;
    static SerializationUtilJSON serializer = new SerializationUtilJSON();

    public static void main(String[] args) throws InterruptedException {
        window = new GameScene();
        init(window);
        window.show();
        while (true) {
            try {
                String string = SocketUtil.readFromSocket(s);
                GameState gs = (GameState) serializer.deserialize(string, GameState.class);
                JTextArea debug = (JTextArea) ComponentStore.getInstance().get("debug");
                debug.append("\n" + gs.message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(1000);
        }
    }

    public static void init(GameScene scene) {
        SerializationUtilJSON serializer = new SerializationUtilJSON();
        JTextArea debug = new JTextArea();
        ComponentStore.getInstance().put("debug", debug);
        debug.setBounds(400, 0, 200, 100);
        JButton joinButton = new JButton("Join a Game");
        ComponentStore.getInstance().put("join_button", joinButton);
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer(e);
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
                        debug.setText(debug.getText() + "\nConnected to: " + s.getInetAddress().getHostAddress());
                    }
                }
                ConsoleWrapper.WriteLn(e.toString());
            }
        });
        joinButton.setBounds(0, 0, 100, 100);
        scene.add(joinButton);
        scene.add(debug);
    }

    private static void connectToServer(ActionEvent e) {
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

        JTextArea debug = (JTextArea) ComponentStore.getInstance().get("debug");

        if (s == null || !s.isConnected()) {
            debug.setText("Failed to Join");
        } else {
            debug.setText(debug.getText() + "\nConnected to: " + s.getInetAddress().getHostAddress());
        }
        ConsoleWrapper.WriteLn(e.toString());
    }

}
