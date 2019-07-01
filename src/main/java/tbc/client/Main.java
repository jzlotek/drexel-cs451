package tbc.client;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tbc.client.components.Piece;
import tbc.client.scene.GameScene;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GameScene gs = new GameScene(stage);
        gs.addDrawableObject(new Piece(1,1, 10, 10, Color.BLACK));
        gs.show();
        gs.render();

    }
}
