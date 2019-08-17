package tbc.client.components;

import tbc.client.checkers.Board;
import tbc.client.checkers.Color;

import javax.swing.*;

public class BoardDisplayComponent {

    private static GameScene window;
    private Board board;
    private Color color;

    JPanel boardPanel;

    public BoardDisplayComponent(GameScene scene, Color color) {
        this.board = (Board) ComponentStore.getInstance().get("board");
        window = scene;
        this.color = color;
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
                if (this.color == Color.WHITE) {
                    comp.setBounds(x * comp.getWidth(), y * comp.getHeight(), comp.getWidth(), comp.getHeight());
                } else {
                    comp.setBounds((7 - x) * comp.getWidth(), (7 - y) * comp.getHeight(), comp.getWidth(), comp.getHeight());
                }
                parent.add(comp);
            }
        }

        if (boardPanel != null) {
            window.remove(boardPanel);
        }

        boardPanel = parent;

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
