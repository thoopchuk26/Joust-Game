import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class main {

    public static class FightingGame extends Application {
        @Override
        public void start(Stage primaryStage) {
            try {
                FXMLLoader loader = new FXMLLoader();
                 BorderPane root = (BorderPane) loader.load(getClass().getResource("FightingGame.fxml").openStream());
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public static void main(String[] argv) {
            launch(argv);
        }
    }
}
