package sample;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccount implements Initializable {

    @FXML
    private JFXButton SignBtn;
    @FXML
    private AnchorPane AnchorSignIn;

    @FXML
    private ImageView backBtn;

    @FXML
    private Pane pane;

    Main main;
    Stage stage;

    public void setMain(Stage stage, Main main){
        this.main=main;
        this.stage=stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pane.setStyle("-fx-background-image: url(\"/sample/5.jpg\")");
        backBtn.setOnMouseClicked(event -> {
            try {
                BacktoMainScene(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void BacktoMainScene(MouseEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),loader);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }


}
