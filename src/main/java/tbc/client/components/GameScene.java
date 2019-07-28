package tbc.client.components;

import javax.swing.*;

public class GameScene {

    private static JFrame window;

    public GameScene() {
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(800, 800);
    }

    public void show() {
        window.setLayout(null);
        window.setVisible(true);
    }

    public void add(JComponent component) {
        window.add(component);
    }

    public static JFrame getWindow() {
        return window;
    }
}
