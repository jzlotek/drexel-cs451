package tbc.client;

import tbc.Constants;
import tbc.client.checkers.Board;
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

    public static void main(String[] args) {
        window = new GameScene();
        init(window);
        window.show();
        Board board = null;
        JTextArea debug = (JTextArea) ComponentStore.getInstance().get("debug");
        boolean gameRunning = false;
        String json;
        GameState gs = null;
        while (true) {
            try {
                json = SocketUtil.readFromSocket(s);
            } catch (Exception e) {
                gs = null;
                json = "";
                e.printStackTrace();
                continue;
            }

            if (json != null && !json.equals("")) {

                ConsoleWrapper.WriteLn(json);
                try {
                    gs = (GameState) SerializationUtilJSON.deserialize(json, GameState.class);
                } catch (Exception e) {
                    gs = null;
                    e.printStackTrace();
                    continue;
                }
                json = "";
                if (gs != null) {
                    debug.append("\n" + gs.message);
                    if (gs.board != null && board == null) {
                        board = gs.board;
                        debug.append("\n" + board);
                        gameRunning = true;
                    } else if (gameRunning) {
                        debug.append("\nGame is Running");
                    }
                }
            }
        }
    }

    public static void init(GameScene scene) {
        JTextArea debug = new JTextArea();
        ComponentStore.getInstance().put("debug", debug);
        debug.setBounds(400, 0, 400, 100);
        JButton joinButton = new JButton("Join a Game");
        ComponentStore.getInstance().put("join_button", joinButton);
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer(e);
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
            s.setKeepAlive(true);
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
