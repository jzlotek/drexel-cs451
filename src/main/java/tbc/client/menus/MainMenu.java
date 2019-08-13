package tbc.client.menus;

import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;

import tbc.Constants;
import tbc.client.components.*;
import tbc.shared.GameState;
import tbc.util.ConsoleWrapper;
import tbc.util.SerializationUtilJSON;
import tbc.util.SocketUtil;

public class MainMenu extends JFrame {
    static JFrame mainMenu;
    static JButton createButton, joinButton, exitButton;
    static JLabel welcomeMessage;


    public static void init() {
        ComponentStore.getInstance().put("main_menu", mainMenu);
        mainMenu = new JFrame("Main Menu");
        welcomeMessage = new JLabel("<html>Welcome to Big Chungus Checkers</html>");
        createButton = new JButton("Create a Lobby");
        joinButton = new JButton("Join a Lobby");
        exitButton = new JButton("Exit Program");
        JPanel p = new JPanel();
        p.add(createButton);
        p.add(joinButton);
        p.add(exitButton);
        p.add(welcomeMessage);
        p.setBackground(Color.white);
        joinButton.addActionListener(MainMenu::createLobby);
        mainMenu.add(p);
        mainMenu.setSize(300, 300);
        mainMenu.show();
    }

    public static void createLobby(ActionEvent e) {
        Socket serverSocket = connectToServer(e);
        SocketUtil.sendGameState(new GameState("create"), serverSocket);
        ComponentStore.getInstance().put("server_socket", serverSocket);
        String string = null;
        GameState state;
        while (string == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
            try {
                string = SocketUtil.readFromSocket(serverSocket);
                state = (GameState) SerializationUtilJSON.deserialize(string);
                ConsoleWrapper.WriteLn(state.message);
            } catch (Exception ex) {
            }
        }
    }

    private static Socket connectToServer(ActionEvent e) {
        Socket serverSocket = null;
        try {
            serverSocket = new Socket(Constants.HOST, Constants.PORT);
            serverSocket.setKeepAlive(true);
        } catch (Exception ex) {
        }

        return serverSocket;
    }
} 
