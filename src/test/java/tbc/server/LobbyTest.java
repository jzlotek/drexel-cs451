package tbc.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tbc.Constants;
import tbc.shared.*;
import static org.junit.Assert.*;


public class LobbyTest {
    public static Socket client;
    public static ServerSocket serverListen;
    public static Socket server;

//    @Before
//    public void setup() throws IOException {
//        serverListen = new ServerSocket(5050);
//        server = serverListen.accept();
//    }
//
//    @After
//    public void clean() throws IOException {
//        try {
//            server.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void test_LobbyInit() {
//        Lobby newLobby = new Lobby();
//        assertNotNull(newLobby.getUUID());
//    }
//
//    @Test
//    public void test_LobbyPlayers(){
//        Socket p1 = null;
//        try {
//            p1 = new Socket("localhost", 5050);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Socket p2 = null;
//        try {
//            p2 = new Socket("localhost", 5050);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Player player1 = new Player(p1);
//        Player player2 = new Player(p2);
//        try {
//            Lobby newLobby = new Lobby(player1, player2);
//            assertNotNull(newLobby.getUUID());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            server.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void test_invalidMove(){
//        Lobby newLobby = new Lobby();
//        Move newMove = null;
//        assertFalse(newLobby.isLegalMove(newMove));
//    }
//
//    @Test
//    public void test_isValidGame(){
//        Lobby newLobby = new Lobby();
//        newLobby.gameRunning = true;
//        assertTrue(newLobby.isGameRunning());
//    }
//
//    @Test
//    public void test_isInvalidGame(){
//        Lobby newLobby = new Lobby();
//        newLobby.gameRunning = false;
//        assertFalse(newLobby.isGameRunning());
//    }
//
//    @Test
//    public void test_addPlayer(){
//        Lobby newLobby = new Lobby();
//        Socket p1 = null;
//        try {
//            p1 = new Socket(Constants.HOST, Constants.PORT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Player player1 = new Player(p1);
//        newLobby.addPlayer(player1);
//        assertFalse(newLobby.players.isEmpty());
//    }
//
//    @Test
//    public void test_addMorePlayers(){
//        Lobby newLobby = new Lobby();
//        Socket p1 = null;
//        try {
//            p1 = new Socket(Constants.HOST, Constants.PORT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Player player1 = new Player(p1);
//        newLobby.addPlayer(player1);
//        Socket p2 = null;
//        try {
//            p2 = new Socket(Constants.HOST, Constants.PORT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Player player2 = new Player(p2);
//        newLobby.addPlayer(player2);
//        Socket p3 = null;
//        try {
//            p3 = new Socket(Constants.HOST, Constants.PORT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Player player3 = new Player(p3);
//        newLobby.addPlayer(player3);
//        assertTrue(newLobby.players.size() == 2);
//    }
//
//    @Test
//    public void test_hasNoWinner(){
//        Lobby newLobby = new Lobby();
//        assertFalse(newLobby.hasWinner());
//        try {
//            server.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void test_checkSockets(){
//        Lobby newLobby = new Lobby();
//        Socket p1 = null;
//        try{
//            p1 = new Socket(Constants.HOST, Constants.PORT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Player player1 = new Player(p1);
//        newLobby.addPlayer(player1);
//        newLobby.checkSockets();
//        try {
//            player1.socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assertFalse(newLobby.gameRunning == false);
//    }
}
