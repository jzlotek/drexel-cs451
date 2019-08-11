package tbc.server;

import tbc.client.checkers.Board;
import tbc.client.components.ComponentStore;
import tbc.shared.GameState;
import tbc.util.ConsoleWrapper;
import tbc.util.SerializationUtilJSON;
import tbc.util.SocketUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Lobby extends Thread {
    protected ArrayList<Player> players = new ArrayList<Player>();
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
        ConsoleWrapper.WriteLn(gameBoard.getPiece(0,0).getColor());

        GameState gs = new GameState("Initial Game State", gameBoard);

        new Thread(() -> SocketUtil.sendGameState(new GameState("test"), p1_socket)).run();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> SocketUtil.sendGameState(gs, p1_socket)).run();
        ConsoleWrapper.WriteLn("Sent p1 board state" + p1_socket.toString());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> SocketUtil.sendGameState(new GameState("test"), p2_socket)).run();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> SocketUtil.sendGameState(gs, p2_socket)).run();
        ConsoleWrapper.WriteLn("Sent p2 board state " + p2_socket.toString());

        GameState userGameState;
        while (this.lobbyStatus == true) { // while we have a game going on
            for (Player p : this.players) {
                if (p.getSocket().isClosed()) {
                    this.lobbyStatus = false;
                }
            }
            userGameState = null;

            boolean isMoveValid = false;
            while (!isMoveValid) {
                // read the move in from player one
                try {
                    received = SocketUtil.readFromSocket(p1_socket);
                    userGameState = (GameState) SerializationUtilJSON.deserialize(received);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // if the move is valid, we must output to player two
                if (this.isLegalMove(userGameState)) {
                    isMoveValid = true;
                } else { // else we throw player one a warning and prompt for another move
                    response = "Move Invalid! Please make a valid move.";
                    SocketUtil.sendGameState(new GameState(response), p1_socket);
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
                    userGameState = (GameState) SerializationUtilJSON.deserialize(received);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // if the move is valid, we must output to player one
                if (this.isLegalMove(userGameState)) {
                    isMoveValid = true;
                } else { // else throw player two a warning and prompt another move
                    response = "Move Invalid! Please make a valid move.";
                    SocketUtil.sendGameState(new GameState(response), p2_socket);
                    isMoveValid = false;
                }
            }
            response = received;
            SocketUtil.sendGameState(new GameState(response), p1_socket);

            // check if player two's move caused a winner
            if (this.checkWinner()) this.lobbyStatus = false;
        }

        // after the game is over, send a closing message to the players and close the sockets
        SocketUtil.sendGameState(new GameState("Game Over! Thanks for playing."), p1_socket);
        SocketUtil.sendGameState(new GameState("Game Over! Thanks for playing."), p2_socket);
        players.get(0).closeSocket();
        players.get(1).closeSocket();
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
    private boolean isLegalMove(GameState state) {
        if (state == null) {
            return false;
        }
        return true;
    }

    // TODO: jzlotek need to ensure gameBoard is kept up to date with each valid move so this function works
    /*
     * Function that checks if we have a winner after a specific move has been made
     */
    private boolean checkWinner() {
        Board board = (Board) ComponentStore.getInstance().get("board");

        if(board != null)
        {
            return board.hasWinner();
        }

        return false;
    }

}