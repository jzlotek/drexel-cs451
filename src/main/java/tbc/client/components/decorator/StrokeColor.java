package tbc.client.components.decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tbc.client.components.Drawable;

public class StrokeColor extends Drawable {

    private Drawable drawable;
    private Color color;

    public StrokeColor(Drawable d, Color c) {
        this.drawable = d;
        this.color = c;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(this.color);
        this.drawable.draw(gc);
    }
}
