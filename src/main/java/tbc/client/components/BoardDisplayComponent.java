package tbc.client.components;

import tbc.client.checkers.Board;
import tbc.client.checkers.Space;
import tbc.shared.GameState;
import tbc.util.ConsoleWrapper;

import javax.swing.*;
import java.util.ArrayList;

public class BoardDisplayComponent {

    private static GameScene window;
    private Board board;

    public BoardDisplayComponent(GameScene scene) {
        this.board = (Board) ComponentStore.getInstance().get("board");
        window = scene;
    }

    public void renderBoard() {
        this.board = (Board) ComponentStore.getInstance().get("board");
        if (this.board == null) {
            return;
        }
        JPanel parent = new JPanel();
        parent.setBounds(0, 0, 400, 400);
        window.add(parent);
        parent.setLayout(null);

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JPanel comp = (JPanel) board.getSpace(x, y).getRenderObject();
                comp.setBounds(x * comp.getWidth(), y * comp.getHeight(), comp.getWidth(), comp.getHeight());
                parent.add(comp);
            }
        }
        parent.revalidate();
        parent.repaint();
    }

    public void render() {
        Board lastBoard = this.board;
        while (true) {
            this.board = (Board) ComponentStore.getInstance().get("board");
            if (this.board != lastBoard) {
                lastBoard = this.board;

                this.renderBoard();
            }
        }
    }
}
