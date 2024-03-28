// Controller for the SpotOn game interface
// Authors: Kamran Yaghoubian, Hunter Antal

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
import javafx.scene.text.Text;

public class SpotOnController {
    // High score, current score, spots clicked, and current level tracking
    private int highScore = 0;
    private int score = 0;
    private int spotsClicked = 0;
    private int currentLevel = 1;

    @FXML private Pane gamePane;
    @FXML private Text highScoreField;
    @FXML private Text levelField;
    @FXML private Text scoreField;
    @FXML private ResourceBundle resources;
    @FXML private URL location;

    // Initialize the game setup
    @FXML
    void initialize() {
        loadHighScore(); // Load the high score from a file
        displayTestSpot(); // Display a test spot for interaction
        paneClicked(); // Set up a listener for missed clicks on the pane
    }

    // Display a test spot within the game pane
    private void displayTestSpot() {
        Spot testSpot = new Spot(50); // Create a new spot instance
        testSpot.setController(this); // Assign this controller to the spot
        testSpot.SpotClicked(); // Activate the spot's click listener
        gamePane.getChildren().add(testSpot); // Add the spot to the pane
    }

    // Handle clicks on the game pane that miss any spots
    private void paneClicked() {
        gamePane.setOnMouseClicked(event -> {
            if (event.getTarget().equals(gamePane)) {
                decrementScore(); // Penalize the score for misses
                Media missSound = new Media(getClass().getResource("/Sounds/miss.mp3").toExternalForm());
                new MediaPlayer(missSound).play(); // Play the miss sound
            }
        });
    }

    // Decrement the score when the pane is clicked but no spots are hit
    private void decrementScore() {
        score -= currentLevel * 15;
        scoreField.setText("Score: " + score);
    }

    // Increment the score when a spot is successfully clicked
    public void incrementScore() {
        score += currentLevel * 10;
        scoreField.setText("Score: " + score);
        if (score > highScore) {
            highScore = score;
            highScoreField.setText("High Score: " + highScore);
            saveHighScore(); // Save new high score if necessary
        }
    }

    // Handle logic for when spots are clicked, including level progression
    public void incrementSpotCounter() {
        spotsClicked += 1;
        if (spotsClicked >= 10) {
            levelIncrease(); // Increase level every 10 successful clicks
        }
    }

    // Increase the game level and update the UI accordingly
    public void levelIncrease() {
        spotsClicked = 0; // Reset spot counter
        currentLevel += 1;
        levelField.setText("Level: " + currentLevel);
    }

    // Save the current high score to a file
    public void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the high score from a file at the start of the game
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
