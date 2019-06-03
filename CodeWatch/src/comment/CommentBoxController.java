package comment;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class CommentBoxController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea textComment;

    @FXML
    private Button declineButton;

    @FXML
    private AnchorPane popUpWindow;

    @FXML
    private Button acceptButton;

    @FXML
    void initialize() {
        

    }
}
