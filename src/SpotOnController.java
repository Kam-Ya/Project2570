// Kamran Yaghoubian, Hunter Antal
// 118151, 

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SpotOnController {

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
        assert highScoreField != null : "fx:id=\"highScoreField\" was not injected: check your FXML file 'SpotOnController.fxml'.";
        assert levelField != null : "fx:id=\"levelField\" was not injected: check your FXML file 'SpotOnController.fxml'.";
        assert scoreField != null : "fx:id=\"scoreField\" was not injected: check your FXML file 'SpotOnController.fxml'.";

    }

}
