// Represents a clickable spot in the SpotOn game
// Authors: Kamran Yaghoubian, Hunter Antal

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.nio.file.Paths;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;

public class Spot extends Circle {
    private SpotOnController controller; // Controller for score updates
    @FXML private Circle circle;
    ParallelTransition parallel;
    

    public Spot(double radius) {
        super(radius);
        setFill(new ImagePattern(new Image("Images/green_spot.png")));
        setCenterX(300);
        setCenterY(300);
        SpotClicked(); // Setup click event handling
    }

    // Set the controller for this spot
    public void setController(SpotOnController controller) {
        this.controller = controller;
    }

    // Setup click event handling for the spot
    public void SpotClicked() {
        setOnMouseClicked(event -> {
            if (controller != null) {
                controller.incrementScore(); // Increment score on click
                controller.incrementSpotCounter(); // Update spot counter
                controller.createAndDisplaySpot(); 
                controller.removeSpot(this); // Request to remove this spot
            }
            
            // Attempt to play the hit sound effect
            try {
                Media sound = new Media(new File("src/Sounds/hit.wav").toURI().toString());
                MediaPlayer player = new MediaPlayer(sound);
                player.play();
                parallel.stop();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
            event.consume(); // Prevent event bubbling
        });
    }
    
 // put this in this class as it needs access to genRand
    public void transition() {
    	double rand = controller.genRand(true);
    	double rand1 = controller.genRand(false);
    	
    	// Initialize path transition
    	Path path = new Path(new MoveTo(controller.genRand(true), controller.genRand(false)), new LineTo(controller.genRand(true), controller.genRand(false)));
    	PathTransition translate = new PathTransition(Duration.seconds(6 - (0.33 * controller.getLevel())), path);

    	// Initialize scale transition
    	ScaleTransition scale = new ScaleTransition(Duration.seconds(6 - (0.33 * controller.getLevel())));
    	scale.setByX(-0.25);
    	scale.setByY(-0.25);
    	
    	
    	// put transitions in a parallel transition and play
    	parallel = new ParallelTransition(this, scale, translate);
    	parallel.play();
    	parallel.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			try {
    				// only play sound if player is still alive
    				if (controller.getLivesRemaining() > 0) {
    					Media missSound = new Media(getClass().getResource("/Sounds/disappear.mp3").toExternalForm());
                        new MediaPlayer(missSound).play(); // Play the miss sound
    				}
    				
    			}catch (Exception e) {
                    e.printStackTrace();
                }
    			setFill(Color.TRANSPARENT);
    			controller.loseLife();
    			// Add new spot when old one dies
    			controller.createAndDisplaySpot(); 
    		}	
    	});
    	
//    	translate.play(); 1.5 - (0.05 * getLevel())
    }

}
