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
        JPanel parent = new JPanel();
        parent.setBounds(0, 0, 400, 400);
        this.board = (Board) ComponentStore.getInstance().get("board");
        window.add(parent);
        parent.setLayout(null);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JPanel comp = (JPanel) board.getSpace(i, j).getRenderObject();
                comp.setBounds(j * comp.getWidth(), i * comp.getHeight(), comp.getWidth(), comp.getHeight());
                parent.add(comp);
                comp.validate();
                comp.repaint();
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
