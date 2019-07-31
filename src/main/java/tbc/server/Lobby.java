package tbc.server;

import tbc.client.checkers.Board;
import tbc.shared.GameState;
import tbc.util.ConsoleWrapper;
import tbc.util.SocketUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Lobby extends Thread {
    protected static ArrayList<Player> players = new ArrayList<Player>();
    protected boolean lobbyStatus;
    protected final int maxPlayers = 2;

    /*
     * Constructor that takes in two players, automatically starts the game
     */
    public Lobby(Player player1, Player player2) throws Exception {
        players.add(player1);
        players.add(player2);
    }

    @Override
    public void run() {
        Board gameBoard = new Board();
        this.lobbyStatus = true;
        String received = "";
        String response = "";
        Socket p1_socket = players.get(0).getSocket();
        Socket p2_socket = players.get(1).getSocket();

        GameState gs = new GameState("Initial Game State", gameBoard);

        synchronized (this) {
            try {
                SocketUtil.sendGameState(new GameState("test"), p1_socket);
                SocketUtil.sendGameState(gs, p1_socket);
                ConsoleWrapper.WriteLn("Sent p1 board state" + p1_socket.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                SocketUtil.sendGameState(new GameState("test"), p2_socket);
                SocketUtil.sendGameState(gs, p2_socket);
                ConsoleWrapper.WriteLn("Sent p2 board state " + p2_socket.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        while (this.lobbyStatus == true) { // while we have a game going on

            boolean isMoveValid = false;
            while (!isMoveValid) {
                // read the move in from player one
                try {
                    received = SocketUtil.readFromSocket(p1_socket);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // if the move is valid, we must output to player two
                if (this.isLegalMove(received)) {
                    isMoveValid = true;
                } else { // else we throw player one a warning and prompt for another move
                    try {
                        response = "Move Invalid! Please make a valid move.";
                        SocketUtil.sendToSocket(response, p1_socket);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    isMoveValid = false;
                }
            }

            try { // output the move to player two
                response = received;
                SocketUtil.sendToSocket(response, p2_socket);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // check if player one's move caused a winner
            if (this.checkWinner()) this.lobbyStatus = false;

            isMoveValid = false;
            while (!isMoveValid) {
                // read the move in from player two
                try {
                    received = SocketUtil.readFromSocket(p2_socket);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // if the move is valid, we must output to player one
                if (this.isLegalMove(received)) {
                    isMoveValid = true;
                } else { // else throw player two a warning and prompt another move
                    try {
                        response = "Move Invalid! Please make a valid move.";
                        SocketUtil.sendToSocket(response, p2_socket);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    isMoveValid = false;
                }
            }
            try { // output the move to player one
                response = received;
                SocketUtil.sendToSocket(response, p1_socket);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // check if player two's move caused a winner
            if (this.checkWinner()) this.lobbyStatus = false;
        }

        // after the game is over, send a closing message to the players and close the sockets
        try {
            SocketUtil.sendGameState(new GameState("Game Over! Thanks for playing."), p1_socket);
            SocketUtil.sendGameState(new GameState("Game Over! Thanks for playing."), p2_socket);
            players.get(0).closeSocket();
            players.get(1).closeSocket();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * Adds a player to the lobby if there is room, or else throws a message back to the player
     */
    public void addPlayer(Player newPlayer) throws Exception {
        if (players.size() > (maxPlayers - 1)) {
            DataOutputStream error = new DataOutputStream(newPlayer.getSocket().getOutputStream());
            error.writeUTF("Unable to add you to the lobby. Lobby is full!");
            ConsoleWrapper.WriteLn("Unable to add " + newPlayer.getSocket() + " to lobby. Lobby full");
            newPlayer.getSocket().close();
        } else {
            players.add(newPlayer);
        }
    }

    /*
     * Function that validates if a move in the form of a message is valid
     */
    private boolean isLegalMove(String message) {
        return true;
    }

    /*
     * Function that checks if we have a winner after a specific move has been made
     */
    private boolean checkWinner() {
        return false;
    }

}