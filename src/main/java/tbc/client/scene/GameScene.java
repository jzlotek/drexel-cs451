package tbc.client.scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tbc.client.components.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameScene {

    private Stage stage;
    private GraphicsContext gc;
    private List<Drawable> objects;

    public GameScene(Stage stage) {
        this.init(stage, new Canvas(480, 360));
    }

    public GameScene(Stage stage, int width, int height) {
        this.init(stage, new Canvas(width, height));
    }

    private void init(Stage stage, Canvas c) {
        this.stage = stage;
        this.stage.setScene(new javafx.scene.Scene(new Pane(c)));
        this.gc = c.getGraphicsContext2D();
        this.objects = new ArrayList<>();
    }

    public void addDrawableObject(Drawable obj) {
        this.objects.add(obj);
    }

    public void removeDrawableObj(String uuid) {
        if (uuid != null) {
            this.objects.removeIf(drawable -> drawable.getID().equals(uuid));
        }
    }

    public void removeDrawableObj(UUID uuid) {
        if (uuid != null) {
            this.objects.removeIf(drawable -> drawable.getID().equals(uuid.toString()));
        }
    }

    public void render() {
        for (Drawable d : this.objects) {
            d.draw(this.gc);
        }
    }

    public GraphicsContext getContext() {
        return this.gc;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void show() {
        this.stage.show();
    }
}
