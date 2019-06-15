package settings;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SettingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane settingsWindow;

    @FXML
    private CheckBox windows1251CheckBox;

    @FXML
    private CheckBox utf8CheckBox;

    @FXML
    private CheckBox javaCheckBox;

    @FXML
    private Button settingsClose;

    @FXML
    private CheckBox htmlCheckBox;

    @FXML
    private TextField pathField;

    @FXML
    void initialize() {
  
       	}
    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) settingsClose.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    }



