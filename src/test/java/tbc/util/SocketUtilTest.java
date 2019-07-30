package tbc.util;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class SocketUtilTest {
    public static Socket client;
    public static ServerSocket serverListen;
    public static Socket server;

    @Before
    public void setup() throws IOException {
        serverListen = new ServerSocket(5000);
        client = new Socket("localhost", 5000);
        server = serverListen.accept();
    }

    @Test
    public void readFromSocket() throws IOException {
        SocketUtil.sendToSocket("test", server);
        String s = SocketUtil.readFromSocket(client);
        assertEquals(s, "test");
    }
}