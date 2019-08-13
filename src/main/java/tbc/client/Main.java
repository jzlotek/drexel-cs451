package tbc.client;

import tbc.Constants;
import tbc.client.checkers.Board;
import tbc.client.checkers.PlayerUI;
import tbc.client.components.BoardDisplayComponent;
import tbc.client.components.ComponentStore;
import tbc.client.components.GameScene;
import tbc.client.components.ServerStatus;
import tbc.shared.GameState;
import tbc.shared.Move;
import tbc.util.ConsoleWrapper;
import tbc.util.SerializationUtilJSON;
import tbc.util.SocketUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import tbc.client.menus.*;

public class Main {
    static Socket serverSocket = null;
    static GameScene window = null;

    public static void main(String[] args) throws Exception {
    	/*
    	 * Need to ping static IP hosted on Drexel's TUX
    	 * If we are able to get network data back, then display the menu
    	 */
    	boolean isServerAlive = ServerStatus.pingServer(Constants.HOST);
    	if(isServerAlive) {
    		// if we can connect to the server, start the main menu
    		MainMenu menu = new MainMenu();
    		menu.init();
    	} else {
    		// else we have to throw our error menu, cancel the program
    		ServerDownMenu menu = new ServerDownMenu();
    		menu.init();
    	}
    	   	
    	
    	
        // set initial environment up including window and board state
        window = new GameScene();
        init(window);
        window.show();
//        Board testBoard = new Board();
        Board currentBoard = null;
        Board lastBoard = null;
        JTextArea debug = (JTextArea) ComponentStore.getInstance().get("debug");
        boolean gameRunning = false;
        String json;
        GameState gs;

        BoardDisplayComponent boardDisplayComponent = new BoardDisplayComponent(window);

//        ComponentStore.getInstance().put("board", testBoard);
//        boardDisplayComponent.renderBoard();

        // begin game loop
        while (true) {
            // get serialized string from the server
            try {
                json = SocketUtil.readFromSocket(serverSocket);
                gs = (GameState) SerializationUtilJSON.deserialize(json);
            } catch (Exception e) {
                gs = null;
                e.printStackTrace();
                continue;
            }

            if (gs != null) {
                debug.append("\n" + gs.message);
                ConsoleWrapper.WriteLn("\nBoard: " + gs.board);
                // if we do not have a board, set the board with UUID's to the current and last board state
                if (gs.board != null && currentBoard == null) {
                    currentBoard = gs.board;
                    lastBoard = gs.board;
                    ComponentStore.getInstance().put("board", currentBoard);
                    debug.append("\n" + currentBoard);
                    gameRunning = true;
                    boardDisplayComponent.renderBoard();
                    debug.append("\nGame is Running");
                }

                if (gameRunning) {
                    boardDisplayComponent.renderBoard();
                    if (gs.yourTurn) {
                        debug.append("\nYour Turn");
                        PlayerUI.getInstance().setActive(true);
                        // TODO: jcarfagno - calculate new move
                        while(PlayerUI.getInstance().getNextMove() == null)
                        {
                            Thread.sleep(500);
                        }

                        gs = new GameState("New Move");
                        gs.moves.add(PlayerUI.getInstance().getNextMove());

                        PlayerUI.getInstance().setActive(false);

                        SocketUtil.sendGameState(gs, serverSocket);
                        gs = null;
                        while (gs == null) {
                            try {
                                json = SocketUtil.readFromSocket(serverSocket);
                                gs = (GameState) SerializationUtilJSON.deserialize(json);
                            } catch (Exception e) {
                                gs = null;
                            }
                        }

                        if (gs.message.equals("success")) {
                            debug.append("\nMove was accepted");
                            lastBoard = currentBoard;
                            ComponentStore.getInstance().put("board", currentBoard);
                            boardDisplayComponent.renderBoard();
                        } else {
                            debug.append("\nMove was denied");
                            currentBoard = lastBoard;
                        }
                    }
                    else
                    {
                        PlayerUI.getInstance().setActive(false);
                    }
                }
            }
        }
    }

    public static void init(GameScene scene) {
        JTextArea debug = new JTextArea();
        ComponentStore.getInstance().put("debug", debug);
        debug.setBounds(400, 0, 400, 400);
        JButton joinButton = new JButton("Join a Game");
        ComponentStore.getInstance().put("join_button", joinButton);
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer(e);
            }
        });
        scene.add(joinButton);
        joinButton.setBounds(400, 400, 400, 100);
        scene.add(debug);
    }

    private static void connectToServer(ActionEvent e) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            serverSocket = new Socket("localhost", Constants.PORT);
            serverSocket.setKeepAlive(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JTextArea debug = (JTextArea) ComponentStore.getInstance().get("debug");

        if (serverSocket == null || !serverSocket.isConnected()) {
            debug.setText("Failed to Join");
        } else {
            debug.setText(debug.getText() + "\nConnected to: " + serverSocket.getInetAddress().getHostAddress());
        }
        ConsoleWrapper.WriteLn(e.toString());
    }

}
