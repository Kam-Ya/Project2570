// Kamran Yaghoubian, Hunter Antal
// 118151, 
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class SpotOnController {
	// HighScore counter
	private int highScore = 0;
	// Score counter
	private int score = 0;
	
	// Number of Spots clicked
	private int spotsClicked = 0;
	
	// Current Level
	private int currentLevel = 1;
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
    	loadHighScore(); // Load the high score at startup
    	displayTestSpot();
    	paneClicked();
        assert highScoreField != null : "fx:id=\"highScoreField\" was not injected: check your FXML file 'SpotOnController.fxml'.";
        assert levelField != null : "fx:id=\"levelField\" was not injected: check your FXML file 'SpotOnController.fxml'.";
        assert scoreField != null : "fx:id=\"scoreField\" was not injected: check your FXML file 'SpotOnController.fxml'.";

    }
    
    private void displayTestSpot() {
        //Circle testSpot = new Circle(50, Color.RED); // Just using a Circle for demonstration
    	Spot testSpot = new Spot(50);
    	
    	testSpot.setController(this); // Pass the controller reference to the Spot
        testSpot.SpotClicked(); // Make sure this is called after setting the controller
        
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
    
    public void incrementScore() {
    	// The score should be 10(currentLevel)
        score += currentLevel*10; 
        scoreField.setText("Score: " + score); // Update the text field
        
     // Update high score if necessary
        if (score > highScore) {
            highScore = score;
            highScoreField.setText("High Score: " + highScore);
            saveHighScore(); // Save the new high score
        }
    }

    public void incrementSpotCounter() {
    	System.out.println(spotsClicked); // error checking
    	spotsClicked += 1;
    	
    	// Every 10 spots counts as a level up
    	if (spotsClicked >= 10) {
    		levelIncrease();
    	}
    }
    
    public void levelIncrease() {
    		// reset spotsClicked
    		spotsClicked = 0;
    		// Increment the level
    		currentLevel += 1;
    		// Update level every 10 spots clicked
    		levelField.setText("Level: "+ currentLevel);
    	
    }
    
    public void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadHighScore() {
        File highScoreFile = new File("highscore.txt");
        if (highScoreFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(highScoreFile))) {
                highScore = Integer.parseInt(reader.readLine());
                highScoreField.setText("High Score: " + highScore);
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
    
    


    
    
    
}
