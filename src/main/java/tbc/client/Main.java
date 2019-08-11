package tbc.client;

import tbc.Constants;
import tbc.client.checkers.Board;
import tbc.client.checkers.PlayerUI;
import tbc.client.components.BoardDisplayComponent;
import tbc.client.components.ComponentStore;
import tbc.client.components.GameScene;
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


public class Main {
    static Socket s = null;
    static GameScene window = null;

    public static void main(String[] args) throws Exception {
        // set initial environment up including window and board state
        window = new GameScene();
        init(window);
        window.show();
        Board currentBoard = null;
        Board lastBoard = null;
        JTextArea debug = (JTextArea) ComponentStore.getInstance().get("debug");
        boolean gameRunning = false;
        String json;
        GameState gs;

        ComponentStore.getInstance().put("board", new Board());
        BoardDisplayComponent boardDisplayComponent = new BoardDisplayComponent(window);

        // begin game loop
        while (true) {
            boardDisplayComponent.renderBoard();

            // get serialized string from the server
//            boardDisplayComponent.renderBoard();
            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            }
            try {
                json = SocketUtil.readFromSocket(s);
                gs = (GameState) SerializationUtilJSON.deserialize(json);
            } catch (Exception e) {
                gs = null;
                e.printStackTrace();
                continue;
            }

            if (gs != null) {
                debug.append("\n" + gs.message);
                // if we do not have a board, set the board with UUID's to the current and last board state
                if (gs.board != null && currentBoard == null) {
                    currentBoard = gs.board;
                    lastBoard = gs.board;
                    ComponentStore.getInstance().put("board", currentBoard);
                    debug.append("\n" + currentBoard);
                    gameRunning = true;
                } else if (gameRunning) {
//                    BoardDisplayComponent boardDisplayComponent = new BoardDisplayComponent(window);
                    debug.append("\nGame is Running");
                    if (gs.yourTurn) {

                        // wait for board to update locally before continuing
                        while (lastBoard.equals(currentBoard)) {
                            currentBoard = (Board) ComponentStore.getInstance().get("board");
                            Thread.sleep(500);
                        }

                        PlayerUI.getInstance().setActive(true);

                        // TODO: jcarfagno - calculate new move
                        while(PlayerUI.getInstance().getNextMove() == null)
                        {
                            Thread.sleep(500);
                        }

                        gs = new GameState("New Move");
                        gs.moves.add(PlayerUI.getInstance().getNextMove());

                        PlayerUI.getInstance().setActive(false);

                        SocketUtil.sendGameState(gs, s);
                        gs = null;
                        while (gs == null) {
                            try {
                                json = SocketUtil.readFromSocket(s);
                                gs = (GameState) SerializationUtilJSON.deserialize(json);
                            } catch (Exception e) {
                                gs = null;
                            }
                        }

                        if (gs.message.equals("success")) {
                            ConsoleWrapper.WriteLn("move accepted");
                            lastBoard = currentBoard;
                            ComponentStore.getInstance().put("board", currentBoard);
                        } else {
                            ConsoleWrapper.WriteLn("move denied");
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
        debug.setBounds(400, 0, 400, 100);
        JButton joinButton = new JButton("Join a Game");
        ComponentStore.getInstance().put("join_button", joinButton);
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer(e);
            }
        });
        joinButton.setBounds(400, 400, 400, 100);
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
