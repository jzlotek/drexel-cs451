package tbc.client.components.factory;

import javafx.scene.paint.Color;
import tbc.client.components.Drawable;
import tbc.client.components.Piece;
import tbc.client.components.decorator.FillColor;
import tbc.client.components.decorator.StrokeColor;

public class PieceFactory {

    private Color color;

    public PieceFactory(Color color) {
        this.color = color;
    }

    public Drawable CreatePiece(int row, int col, int width, int height) {
        return new FillColor(
                new StrokeColor(
                        new Piece(row, col, width, height),
                        this.color
                ), this.color);
    }
}
