package tbc.server;

import tbc.client.checkers.Board;
import tbc.client.checkers.Color;
import tbc.client.checkers.Piece;
import tbc.client.components.ComponentStore;
import tbc.shared.GameState;
import tbc.shared.Move;
import tbc.util.ConsoleWrapper;
import tbc.util.SerializationUtilJSON;
import tbc.util.SocketUtil;
import tbc.util.UUIDUtil;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class Lobby extends Thread {
    protected ArrayList<Player> players = new ArrayList<Player>();
    protected boolean gameRunning;
    protected final int maxPlayers = 2;
    protected Board gameBoard;
    private UUID uuid;

    // generic constructor
    public Lobby() {
        this.uuid = UUIDUtil.getUUID();
    }

    /*
     * Constructor that takes in two players, automatically starts the game
     */
    public Lobby(Player player1, Player player2) throws Exception {
        this();
        this.addPlayer(player1);
        this.addPlayer(player2);
    }

    @Override
    public void run() {
        while (this.players.size() < 2) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        this.gameBoard = new Board();
        String received;
        String response;
        Socket p1_socket = players.get(0).getSocket();
        Socket p2_socket = players.get(1).getSocket();
        Color[] randomize = new Color[]{Color.WHITE, Color.RED};
        Collections.shuffle(Arrays.asList(randomize));

        GameState gs = new GameState("Initial Game State", this.gameBoard);
        gs.yourTurn = false;

        Socket finalP1_socket = p1_socket;
        gs.yourColor = randomize[0];
        if (randomize[0] == Color.WHITE) {
            gs.yourTurn = true;
        }
        new Thread(() -> SocketUtil.sendGameState(gs, finalP1_socket)).run();
        ConsoleWrapper.WriteLn("Sent p1 board state" + p1_socket.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Socket finalP2_socket = p2_socket;
        gs.yourColor = randomize[1];
        gs.yourTurn = false;
        if (randomize[1] == Color.WHITE) {
            gs.yourTurn = true;
        }
        new Thread(() -> SocketUtil.sendGameState(gs, finalP2_socket)).run();
        ConsoleWrapper.WriteLn("Sent p2 board state " + p2_socket.toString());

        GameState userGameState;
        // make sure that player[0] is the first player
        if (randomize[0] == Color.RED) {
            Player tmp = this.players.get(0);
            this.players.set(0, this.players.get(1));
            this.players.set(1, tmp);
            Socket tmpSocket = p1_socket;
            p1_socket = p2_socket;
            p2_socket = tmpSocket;
        }

        // start gameRunning loop
        this.gameRunning = true;
        while (this.gameRunning) {
            this.checkSockets();
            userGameState = null;

            boolean isMoveValid = false;
            while (!isMoveValid) {
                // read the move in from current player's turn
                try {
                    received = SocketUtil.readFromSocket(p1_socket);
                    userGameState = (GameState) SerializationUtilJSON.deserialize(received);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // if the move is valid, we must output to player two
                // and we update the locally stored board in the stor
                if (this.isLegalMove(userGameState)) {
                    isMoveValid = true;
                    Move move = userGameState.moves.get(0);
                    Piece p = this.gameBoard.getPiece(move.getOldLocation());
                    this.gameBoard.movePiece(p, move.getOldLocation(), move.getNewLocation());
                } else { // else we throw player one a warning and prompt for another move
                    response = "Move Invalid! Please make a valid move.";
                    SocketUtil.sendGameState(new GameState(response), p1_socket);
                    isMoveValid = false;
                }
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }

            // accept game state and send confirmation to p1_socket
            GameState newState = new GameState("success");
            newState.yourTurn = false;
            newState.board = this.gameBoard;
            SocketUtil.sendGameState(newState, p1_socket);

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }

            // send updated move to p2_socket
            newState = new GameState("New Move");
            newState.yourTurn = true;
            newState.moves = userGameState.moves;
            newState.board = this.gameBoard;
            SocketUtil.sendGameState(newState, p2_socket);

            // check for winner
            if (this.hasWinner()) {
                break;
            }

            // swap players so we do not have to repeat code
            Socket tmp = p1_socket;
            p1_socket = p2_socket;
            p2_socket = tmp;
        }

        // after the game is over, send a closing message to the players and close the sockets
        SocketUtil.sendGameState(new GameState("Game Over! Thanks for playing."), p1_socket);
        SocketUtil.sendGameState(new GameState("Game Over! Thanks for playing."), p2_socket);
        players.get(0).setInGame(false);
        players.get(1).setInGame(false);
    }

    /*
     * Adds a player to the lobby if there is room, or else throws a message back to the player
     */
    public void addPlayer(Player newPlayer) {
        ConsoleWrapper.WriteLn("Attempting to add player: " + newPlayer.getSocket() + " to lobby: " + this.getUUID().toString());

        if (this.players.size() < this.maxPlayers) {
            synchronized (Server.class) {
                if (this.players.size() < this.maxPlayers) {
                    ConsoleWrapper.WriteLn("Added player: " + newPlayer.getSocket() + " to lobby: " + this.getUUID().toString());
                    this.players.add(newPlayer);
                } else {
                    SocketUtil.sendGameState(new GameState("Unable to add " + newPlayer.getSocket() + " to lobby. Lobby full"), newPlayer.getSocket());
                }
            }
        }
    }

    /*
     * Function that validates if a move in the form of a message is valid
     */
    private boolean isLegalMove(GameState state) {
        if (state == null) {
            return false;
        }
        Move move = state.moves.get(0);

        ArrayList<Move> validMoves = gameBoard.getValidMoves((this.gameBoard.getPiece(move.getOldLocation())));

        for(Move validMove : validMoves)
        {
            if(validMove.equals(move))
            {
                return true;
            }
        }

        return false;
    }

    /*
     * Function that checks if we have a winner after a specific move has been made
     */
    private boolean hasWinner() {
        Board board = (Board) ComponentStore.getInstance().get("board");

        if (board != null) {
            return board.hasWinner();
        }

        return false;
    }

    public boolean isGameRunning() {
        return this.gameRunning;
    }

    private void checkSockets() {
        for (Player p : this.players) {
            if (p.getSocket().isClosed()) {
                this.gameRunning = false;
            }
        }
        if (this.players.get(0).getSocket().isClosed() || this.players.get(1).getSocket().isClosed()) {
            try {
                this.players.get(0).closeSocket();
            } catch (Exception e) {
                ConsoleWrapper.WriteLn("Player 1 Has Disconnected");
            }
            try {
                this.players.get(1).closeSocket();
            } catch (Exception e) {
                ConsoleWrapper.WriteLn("Player 2 Has Disconnected");
            }
        }
    }

    public UUID getUUID() {
        return this.uuid;
    }

}