package tbc.client.menus;

import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*;
import tbc.client.components.*;

public class ServerDownMenu extends JFrame {
	static JFrame serverDownMenu;
	static JButton exitButton; 
	static JLabel errorMessage; 

	public static void init() {
		ComponentStore.getInstance().put("server_down_menu", serverDownMenu);
		serverDownMenu = new JFrame("Server is Down!"); 
        errorMessage = new JLabel("<html>Unable to connect to the server.<br/>Please exit the program.</html>"); 
        exitButton = new JButton("OK"); 
        JPanel p = new JPanel(); 
        p.add(errorMessage); 
        p.add(exitButton); 
        p.setBackground(Color.white); 
        serverDownMenu.add(p); 
        serverDownMenu.setSize(350, 250); 
        serverDownMenu.show(); 
	}

} 
