import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
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
        this.setCenterX(100);
        this.setCenterY(100);

        // Add more initialization code here (e.g., setting start and end points)
    }

    public void setupClickHandler() {
        this.setOnMouseClicked((MouseEvent event) -> {
            // Handle spot click here (update score, play sound, etc.)
        });
    }

    public ParallelTransition createAnimation() {
        // Create and return a ParallelTransition combining ScaleTransition and PathTransition
        return new ParallelTransition(this /*, transitions here */);
    }
    
    // Additional methods (e.g., for setting start/end points) go here
}
