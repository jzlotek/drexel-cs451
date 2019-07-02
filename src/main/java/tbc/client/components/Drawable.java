package tbc.client.components;

import javafx.scene.canvas.GraphicsContext;

import java.util.UUID;

public abstract class Drawable {

    protected UUID id;

    public abstract void draw(GraphicsContext gc);

    public String getID() {
        return this.id.toString();
    }
}
