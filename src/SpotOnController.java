// Controller for the SpotOn game interface
// Authors: Kamran Yaghoubian, Hunter Antal

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.geometry.Bounds;
import javafx.event.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.paint.Color;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SpotOnController {
    // High score, current score, spots clicked, and current level tracking
    private int highScore = 0;
    private int score = 0;
    private int spotsClicked = 0;
    private int currentLevel = 1;
    int livesRemaining = 3;

    @FXML private Pane gamePane;
    @FXML private Text highScoreField;
    @FXML private Text levelField;
    @FXML private Text scoreField;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Circle Life1;
    @FXML private Circle Life2;
    @FXML private Circle Life3;
    @FXML private Circle Life4;
    @FXML private Circle Life5;
    @FXML private Circle Life6;
    @FXML private Circle Life7;
    
    
    Circle[] lives = new Circle[7];
    
    // Initialize the game setup
    @FXML
    void initialize() {
    	initilizeLives();
        loadHighScore(); // Load the high score from a file
        initializeSpots(); // Initialize the spots at game start
        paneClicked(); // Set up a listener for missed clicks on the pane
    }
    
    // Handle clicks on the game pane that miss any spots
    private void paneClicked() {
        gamePane.setOnMouseClicked(event -> {
            if (event.getTarget().equals(gamePane)) {
                decrementScore(); // Penalize the score for misses
                Media missSound = new Media(getClass().getResource("/Sounds/miss.mp3").toExternalForm());
                new MediaPlayer(missSound).play(); // Play the miss sound
                
                // lose a life
                loseLife();
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
        // Add 1 life for every level increased S
        livesRemaining++;
        // Add 1 life for leveling up (max 7 lives)
        addLife();
        
    }
    
    int getLevel() {
    	return currentLevel;
    }
    
    int getLivesRemaining()
    {
    	return livesRemaining;
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
    
    // function to generate random numbers because its easier on my head
    double genRand(boolean choice) {
    	// random and bounds made to generate a random number in range of bounds
    	Random rand = new Random();
    	Bounds bound = gamePane.getBoundsInLocal();
    	// calculated random this way because it allows for negative numbers easier
    	
    	if (choice == true) {
    		return (rand.nextDouble() * bound.getMaxX());
    	} else {
    		return (rand.nextDouble() * bound.getMaxY());
    	}
    }
    
    // Remove a life
    void loseLife() {
    	livesRemaining--;
    	
    	if (livesRemaining >=0) {
    		// removes life from screen
    	lives[livesRemaining].setFill(Color.TRANSPARENT);
    	}
    	
    	
    	// ends game
    	if (livesRemaining == 0) {
    		showGameOverDialog();
    		return;
    	}
    	    	
    }
    
    // Show game over alert box
    private void showGameOverDialog() {
        // Ensure dialog is created and shown on the JavaFX Application Thread
        Platform.runLater(() -> {
            Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
            gameOverAlert.setTitle("Game Over");
            gameOverAlert.setHeaderText("Game Over!");
            gameOverAlert.setContentText("Your score: " + score + "\nHigh score: " + highScore);

            gameOverAlert.setOnHidden(dialogEvent -> Platform.exit()); // Exit application when dialog is closed
            gameOverAlert.showAndWait();
        });
    }
    
    // Initialize a spot
    private void initializeSpots() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> createAndDisplaySpot()));
        timeline.setCycleCount(5); // Create 5 spots at the start
        timeline.play();
    }
    // Add the initialized spot to the pane
    public void createAndDisplaySpot() {
        Spot spot = new Spot(50); // Adjust the size as needed
        spot.setController(this);
        spot.SpotClicked(); // Setup click event
        spot.transition(); // Start spot animation
        gamePane.getChildren().add(spot); // Add to the game pane
    }
    // Remove a spot from the pane
    public void removeSpot(Spot spot) {
        Platform.runLater(() -> gamePane.getChildren().remove(spot));
    }
    // Initialize the lives 
    private void initilizeLives() {
    	lives[0] = Life1;
    	lives[1] = Life2;
    	lives[2] = Life3;
    	lives[3] = Life4;
    	lives[4] = Life5;
    	lives[5] = Life6;
    	lives[6] = Life7;
    	
    	// Start the game with 3 lives
    	for (int i = 0; i < livesRemaining; i++) {
    		lives[i].setFill(new ImagePattern(new Image("Images/green_spot.png")));
    	}
    	// Rest of lives are transparent 
    	for (int i = 3; i < 7; i++) {
    		lives[i].setFill(Color.TRANSPARENT);
    	}
    	
    }
    // Add a life for every new level
    public void addLife() {
    	// Max lives allowed is 7
    	if (livesRemaining < lives.length) {
    		for (int i = 0; i < livesRemaining; i++) {
    			lives[i].setFill(new ImagePattern(new Image("Images/green_spot.png")));
    		}	
    	}
    	
    }
    
    
}
