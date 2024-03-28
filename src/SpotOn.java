// Main class for the SpotOn Game
// Authors: Kamran Yaghoubian, Hunter Antal

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpotOn extends Application {
    
    private SpotOnController controller;

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file and get the root node
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SpotOnController.fxml"));
        Parent root = loader.load();
        
        // Retrieve the controller from the loader
        controller = loader.getController();
        
        // Set up the scene and stage
        Scene scene = new Scene(root);
        stage.setTitle("SpotOn Game");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        // Save the high score when the application is closed
        if (controller != null) {
            controller.saveHighScore();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
