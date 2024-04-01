// Represents a clickable spot in the SpotOn game
// Authors: Kamran Yaghoubian, Hunter Antal

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;

public class Spot extends Circle {
    private SpotOnController controller; // Controller for score updates

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
            }
            
            // Attempt to play the hit sound effect
            try {
                Media sound = new Media(new File("src/Sounds/hit.wav").toURI().toString());
                MediaPlayer player = new MediaPlayer(sound);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            event.consume(); // Prevent event bubbling
        });
    }

}
