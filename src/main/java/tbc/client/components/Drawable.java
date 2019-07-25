package tbc.client.components;

import java.util.UUID;

public abstract class Drawable {

    protected UUID id;

    public abstract void draw();

    public String getID() {
        return this.id.toString();
    }
}
