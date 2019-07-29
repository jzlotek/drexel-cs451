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

    /*
    Adds the component to the window
     */
    public void add(JComponent component) {
        window.add(component);
    }

    /*
    Adds to the window and registers the component with the key to the global object store
     */
    public void register(JComponent component, String key) {
        this.add(component);
        ComponentStore.getInstance().put(key, component);
    }

    /*
    Returns window instance
     */
    public static JFrame getWindow() {
        return window;
    }
}
