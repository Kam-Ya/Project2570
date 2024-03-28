// Kamran Yaghoubian, Hunter Antal
// 118151, 
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class SpotOnController {
	@FXML
    private Pane gamePane; // Now we can reference the Pane directly

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text highScoreField;

    @FXML
    private Text levelField;

    @FXML
    private Text scoreField;

    @FXML
    void initialize() {
    	displayTestSpot();
    	paneClicked();
        assert highScoreField != null : "fx:id=\"highScoreField\" was not injected: check your FXML file 'SpotOnController.fxml'.";
        assert levelField != null : "fx:id=\"levelField\" was not injected: check your FXML file 'SpotOnController.fxml'.";
        assert scoreField != null : "fx:id=\"scoreField\" was not injected: check your FXML file 'SpotOnController.fxml'.";

    }
    
    private void displayTestSpot() {
        //Circle testSpot = new Circle(50, Color.RED); // Just using a Circle for demonstration
    	Spot testSpot = new Spot(50);
    	System.out.println("Spot Made");
        gamePane.getChildren().add(testSpot); // This will add the test spot to the pane
    }
    
    private void paneClicked() {
        // Play miss sound when pane is clicked but not a spot
        gamePane.setOnMouseClicked(event -> {
            // Check if the event target is the pane itself and not a spot or other node
        	System.out.println("Pane clicked!"); // error checking
        	
            if (event.getTarget().equals(gamePane)) {
                Media missSound = new Media(getClass().getResource("/Sounds/miss.mp3").toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(missSound);
                mediaPlayer.play();
            }
        });
    }
    

    
    
    
    
    
    
}
