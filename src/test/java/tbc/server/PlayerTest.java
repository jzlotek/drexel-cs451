package tbc.server;
import org.junit.Test;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tbc.Constants;

import static org.junit.Assert.*;

public class PlayerTest {

    private static void run_setInGame() throws IOException {
        ServerSocket testServer = new ServerSocket(2020);
        testServer.accept();
        Socket p1 = new Socket(Constants.HOST, 2020);
        Player player1 = new Player(p1);
        player1.setInGame(true);
        assertTrue(player1.isInGame());
    }

    private static void run_closeSocket() throws IOException {
        ServerSocket testServer = new ServerSocket(2021);
        testServer.accept();
        Socket p1 = new Socket(Constants.HOST, 2021);
        Player player1 = new Player(p1);
        player1.closeSocket();
        assertTrue(player1.getSocket().isClosed());
    }

    private static void run_init() throws IOException {
        ServerSocket testServer = new ServerSocket(2022);
        testServer.accept();
        Socket p1 = new Socket(Constants.HOST, 2022);
        Player player1 = new Player(p1);
        assertFalse(player1.isInGame());
        assertNotNull(player1.socket);
        assertNotNull(player1.getSocket());
        assertNotNull(player1.getAddress());
    }

    @Test
    public void test_init() throws IOException{
        new Thread(() -> {
            try{
                run_init();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Test
    public void test_closeSocket() throws IOException{
        new Thread(() -> {
            try{
                run_closeSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Test
    public void test_setInGame() throws IOException {
        new Thread(() -> {
            try {
                run_setInGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
