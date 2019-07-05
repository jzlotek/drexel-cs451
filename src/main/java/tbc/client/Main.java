package tbc.client;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tbc.client.components.factory.PieceFactory;
import tbc.client.scene.GameScene;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GameScene gs = new GameScene(stage);
        PieceFactory factory = new PieceFactory(Color.BLACK);
        gs.addDrawableObject(factory.CreatePiece(1, 1, 10, 10));
        gs.show();
        gs.render();

    }
}
