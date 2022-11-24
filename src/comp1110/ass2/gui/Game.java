package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {
    /**
    * Authors : Sam Mason-Cox, Jinhao Lu, Hamish Hunter
     *  Runs the Viewer object start method
     */

    private final Group root = new Group();

    private final Group roll = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    /**
     *  Calls the Viewer method and initialise the game board,
     *  as well as produces the Game window
     */

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        Viewer viewer = new Viewer();
        stage.setScene(scene);

        comp1110.ass2.Game game = new comp1110.ass2.Game();
        viewer.show(stage,game);
        root.getChildren().add(roll);
        stage.show();
    }
}
