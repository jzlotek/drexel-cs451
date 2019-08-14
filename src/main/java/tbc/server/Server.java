package tbc.server;

import tbc.Constants;
import tbc.shared.GameState;
import tbc.util.ConsoleWrapper;
import tbc.util.SerializationUtilJSON;
import tbc.util.SocketUtil;

import java.io.Console;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class Server {

    public static final int PORT = Constants.PORT; // Using 4510
    public static final String HOST = Constants.HOST;    // Using Tux2 to host this service.
    private static ServerSocket serverSocket = null;
    private static ArrayList<Player> activePlayers = new ArrayList<>();
    private final static int allowedClients = 2;
    private static ArrayList<Lobby> lobbies = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ConsoleWrapper.WriteLn("Booting Up Server Service...");
        try {
            ConsoleWrapper.Write("Starting service on: ");
            serverSocket = new ServerSocket(PORT);
            ConsoleWrapper.WriteLn(serverSocket.getLocalSocketAddress().toString());
        } catch (IOException ex) {
            ConsoleWrapper.WriteLn("Unable to start service on port: " + ex.getMessage());
            throw ex;
        }


        new Thread(() -> {
            while (true) {
                activePlayers.removeIf(player -> player.getSocket().isClosed());
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                lobbies.removeIf(lobby -> {
                    boolean remove = false;
                    if (lobby.players.size() == 0) {
                        remove = true;
                        ConsoleWrapper.WriteLn("Empty Lobby: " + lobby.getUUID().toString());
                    }
                    return remove;
                });
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }
        }).start();
        while (true) {
            // TODO: upon create lobby, add new lobby to list.
            // TODO: upon join lobby, add player to lobby.
            // lobby thread should handle the game from there.
            // Notes: will need to make a list of currently connected player
            // and iterate all of them for requests to join or make lobbies
            connectPlayer();

            for (Player p : activePlayers) {
                ConsoleWrapper.WriteLn(p.getSocket());
                if (!p.isInGame()) {
                    String string = SocketUtil.readFromSocket(p.getSocket());
                    GameState state = (GameState) SerializationUtilJSON.deserialize(string);
                    if (state != null && state.message != null) {
                        ConsoleWrapper.WriteLn(state.message);
                        if (state.message.equals("create")) {
                            Lobby newLobby = new Lobby();
                            newLobby.addPlayer(p);
                            SocketUtil.sendGameState(new GameState(newLobby.getUUID().toString()), p.getSocket());
                        } else if (state.message.equals("lobbies")) { // return lobbies to user
                            // TODO: implement
                            String serializedList = SerializationUtilJSON.serialize(getLobbyUUIDs());
                            SocketUtil.sendToSocket(serializedList, p.getSocket());
                        } else { // assume it is a join
                            ConsoleWrapper.WriteLn(state.message);
                            lobbies.forEach(lobby -> {
                                if (!lobby.isGameRunning() && lobby.getUUID().toString().equals(state.message)) {
                                    lobby.addPlayer(p);
                                }
                            });
                        }
                    }
                }
            }

            // spin up lobby if max players is achieved
            for (Lobby l : lobbies) {
                if (!l.isGameRunning() && (l.players.size() == l.maxPlayers)) {
                    l.start();
                    for (Player p : l.players) {
                        p.setInGame(true);
                    }
                }
            }
//            ConsoleWrapper.WriteLn("Max connections established. Creating Lobby.");

//            Player p1 = activePlayers.get(0);
//            Player p2 = activePlayers.get(1);
//            Lobby newGame = new Lobby(p1, p2);
//            newGame.run(); // start the new game
//            ConsoleWrapper.WriteLn("Lobby created. Starting health check process..");
//            while (newGame.gameRunning) {
//                try {
//                    Thread.sleep(5000);
//                    if (!heartbeat()) {
//                        newGame.gameRunning = false;
//                    }
//                } catch (InterruptedException ex) {
//                    throw ex;
//                }
//            }
//            activePlayers.clear(); // clear the sockets for start of new game
        }
    }

    private static void connectPlayer() {
        try {
            Socket socket = serverSocket.accept(); // take in the new connection
            socket.setKeepAlive(true);
            ConsoleWrapper.WriteLn(socket.toString());
            ConsoleWrapper.WriteLn("Ready to accept new connection...");
            synchronized (Server.class) {
                activePlayers.add(new Player(socket)); // add it to the list of active players
            }
            GameState gs = new GameState("Connected!");
            SocketUtil.sendGameState(gs, socket);
            ConsoleWrapper.WriteLn("New connection established: " + socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Health Check Function that sees if any client disconnects in the middle of the game.
     */
    public static boolean heartbeat() {
        boolean didDisconnect = false;
        ConsoleWrapper.Write("Health Check Sent: ");
        for (Player player : activePlayers) {
            if (player.getSocket().isClosed()) { // if we have a terminated connection
                activePlayers.remove(player);
                didDisconnect = true;
                ConsoleWrapper.WriteLn("Player " + player + " has disconnected.");
            }
        }
        ConsoleWrapper.WriteLn(activePlayers.size() + "/" + allowedClients + " connected");

        // if there was a player that disconnected, there should be only one player left who won the game
        if (didDisconnect) {
            if (activePlayers.size() == 0) {
                ConsoleWrapper.WriteLn("No active players to award a victory to.");
            } else if (activePlayers.size() == 1) {
                try {
                    DataOutputStream winnerDefault = new DataOutputStream(activePlayers.get(0).getSocket().getOutputStream());
                    winnerDefault.writeUTF("You are the only remaining player left, win by default!");
                    winnerDefault.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                ConsoleWrapper.WriteLn("Can't distinguish who won due to multiple players left.");
            }
            return true;
        } else {
            return false;
        }
    }

    public static void closeSocket(Player socket) throws Exception {
        try {
            socket.getSocket().close();
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static void terminate() {
        ConsoleWrapper.WriteLn("Starting shut down procedures");
        ConsoleWrapper.WriteLn("Reason for server closure?");
        String reason = ConsoleWrapper.Read();
        ConsoleWrapper.WriteLn("Sending closing message to sockets");

        ConsoleWrapper.WriteLn("Closing sockets");

    }

    public static ArrayList<UUID> getLobbyUUIDs() {
        ArrayList<UUID> uuids = new ArrayList<>();
        lobbies.forEach(lobby -> {
            if (!lobby.isGameRunning()) {
                uuids.add(lobby.getUUID());
            }
        });
        return uuids;
    }
}
