package tbc.client;

import tbc.Constants;
import tbc.client.components.GameScene;
import tbc.util.ConsoleWrapper;
import tbc.util.SerializationUtilJSON;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;


public class Main {
    static Socket s = null;

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
                        debug.setText(debug.getText() + "\nConnected to: " + s.getInetAddress().getHostAddress());
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
}
