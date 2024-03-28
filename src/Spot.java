// Kamran Yaghoubian, Hunter Antal
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import java.io.File;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

public class Spot extends Circle {
    public Spot(double radius) {
        super(radius);
        Image image = new Image("Images/green_spot.png");
        ImagePattern pattern = new ImagePattern(image);
        this.setFill(pattern);
        // added for debugging
        this.setCenterX(300);
        this.setCenterY(300);
        
     // Call SpotClicked to setup the click handler
        SpotClicked();

        // Add more initialization code here (e.g., setting start and end points)
    }

 // Member used to comunicate score changes
    private SpotOnController controller;

    // Constructor modification or add a new method to set the controller
    public void setController(SpotOnController controller) {
        this.controller = controller;
    }
    
    public void SpotClicked() {
        this.setOnMouseClicked((MouseEvent event) -> {
        	System.out.println("Spot clicked!"); // error checking
        	
        	// Call the incrementHighScore method on the controller
            if (controller != null) {
                controller.incrementScore();
            }
            // Prevent event from bubbling up
            event.consume();
            
            // Call the incrementSpotCounter method on the controller
            if (controller != null) {
                controller.incrementSpotCounter();
            }
            // Prevent event from bubbling up
            event.consume();
        	
        try {
        	Media sound = new Media(getClass().getResource("/Sounds/hit.mp3").toExternalForm());
            //Media sound = new Media(new File(soundPath).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        });
    }

    public ParallelTransition createAnimation() {
        // Create and return a ParallelTransition combining ScaleTransition and PathTransition
        return new ParallelTransition(this /*, transitions here */);
    }
    
    // Additional methods (e.g., for setting start/end points) go here
}
