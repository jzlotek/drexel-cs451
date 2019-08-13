package tbc.client.menus;

import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*;
import tbc.client.components.*;

public class MainMenu extends JFrame {
	static JFrame mainMenu;
	static JButton createButton, joinButton, exitButton; 
	static JLabel welcomeMessage; 


	public static void init() {
		ComponentStore.getInstance().put("main_menu", mainMenu);
        mainMenu = new JFrame("Main Menu"); 
        welcomeMessage = new JLabel("<html>Welcome to Big Chungus Checkers</html>"); 
        createButton = new JButton("Create a Lobby"); 
        joinButton = new JButton("Join a Lobby"); 
        exitButton = new JButton("Exit Program"); 
        JPanel p = new JPanel(); 
        p.add(createButton); 
        p.add(joinButton); 
        p.add(exitButton); 
        p.add(welcomeMessage); 
        p.setBackground(Color.white); 
        mainMenu.add(p); 
        mainMenu.setSize(300, 300); 
        mainMenu.show(); 
    } 
} 
