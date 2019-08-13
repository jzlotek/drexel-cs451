package tbc.client.components;

import java.io.*;
import java.net.*;

import tbc.Constants;

public class ServerStatus {
	
	public static boolean pingServer(String ipAddress) throws UnknownHostException, IOException, Exception { 
		SocketAddress server = new InetSocketAddress(InetAddress.getByName(ipAddress), Constants.PORT); 
		Socket socket = new Socket();
		boolean canConnect = false;
		try {
			socket.connect(server, 5000);
			canConnect = true;
		} catch (Exception timeout) {
			socket.close();
			canConnect = false;
		} finally {
			try {
				socket.close();
			} catch (Exception ex) {
				canConnect = false;
			}
		}
		return canConnect;
	} 
}