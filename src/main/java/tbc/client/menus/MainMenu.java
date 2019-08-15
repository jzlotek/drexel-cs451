package tbc.client.menus;

import tbc.Constants;
import tbc.client.components.ComponentStore;
import tbc.shared.GameState;
import tbc.shared.UUIDContainer;
import tbc.util.ConsoleWrapper;
import tbc.util.SerializationUtilJSON;
import tbc.util.SocketUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

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
        createButton.addActionListener(MainMenu::createLobby);
        joinButton.addActionListener(MainMenu::getLobbies);
        p.add(createButton);
        p.add(joinButton);
        p.add(exitButton);
        p.add(welcomeMessage);
        p.setBackground(Color.white);
        mainMenu.add(p);
        mainMenu.setSize(300, 300);
        mainMenu.show();
    }

    public static void getLobbies(ActionEvent e) {
        String string = null;
        UUIDContainer uuids;
        while (string == null) {
            try {
                ConsoleWrapper.WriteLn("Attempting to Connect to the Server and get a lobby");
                Socket serverSocket = connectToServer(e);
                SocketUtil.sendGameState(new GameState("lobbies"), serverSocket);
                while (string == null) {
                    string = SocketUtil.readFromSocket(serverSocket);
                    Thread.sleep(500);
                }
                ConsoleWrapper.WriteLn(string);
                GameState gs = (GameState) SerializationUtilJSON.deserialize(string);
                ConsoleWrapper.WriteLn(gs.message);
                string = null;
                while (string == null) {
                    string = SocketUtil.readFromSocket(serverSocket);
                    Thread.sleep(500);
                }
                uuids = (UUIDContainer) SerializationUtilJSON.deserialize(string);
                gs = new GameState(uuids.getUUIDS().get(0).toString());
                SocketUtil.sendGameState(gs, serverSocket);
                ComponentStore.getInstance().put("server_socket", serverSocket);
            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException exc) {
                }
            }
        }
    }

    public static void createLobby(ActionEvent e) {
        ConsoleWrapper.WriteLn("Attempting to Connect to the Server and create a lobby");
        Socket serverSocket = connectToServer(e);
        SocketUtil.sendGameState(new GameState("create"), serverSocket);
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
                string = null;
                ex.printStackTrace();
            }
        }
        ComponentStore.getInstance().put("server_socket", serverSocket);
        ConsoleWrapper.WriteLn(serverSocket.getInetAddress());
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
