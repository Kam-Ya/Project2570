// Kamran Yaghoubian, Hunter Antal
// 11881581

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpotOn extends Application {
	
	private SpotOnController controller;

	@Override
	public void start(Stage stage) throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("SpotOnController.fxml"));
	    Parent root = loader.load();
	    
	    controller = loader.getController();
	    
	    Scene scene = new Scene(root);
	    stage.setTitle("SpotOn Game");
	    stage.setScene(scene);
	    stage.show();
	}

	@Override
	public void stop() throws Exception {
	    if (controller != null) {
	        controller.saveHighScore(); // Call the non-static saveHighScore method
	    }
	    super.stop();
	}


	public static void main(String[] args) {
		launch(args);
	}

}