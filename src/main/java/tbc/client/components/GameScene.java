package tbc.client.components;

import javax.swing.*;

public class GameScene {

    private JFrame window;

    public GameScene() {
        this.window = new JFrame();
        this.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.window.setSize(800, 800);
    }

    public void show() {
        this.window.setLayout(null);
        this.window.setVisible(true);
    }

    /*
    Adds the component to the window
     */
    public void add(JComponent component) {
        this.window.add(component);
        this.window.validate();
        this.window.repaint();
    }

    public void remove(JComponent component)
    {
        this.window.remove(component);
        this.window.validate();
        this.window.repaint();
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
    public JFrame getWindow() {
        return this.window;
    }
}
