package sample;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    private ImageView menuImg;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton patientsBtn;

    @FXML
    private AnchorPane opacityPane;

    @FXML
    private AnchorPane menuPane;
    @FXML
    private JFXButton LogoutBtn;

    public void changeScreenPatientScreen(ActionEvent event) throws IOException {

        Parent loader = FXMLLoader.load(getClass().getResource("PatientsScreen.fxml"));
        Scene scene = new Scene(loader);
        scene.getStylesheets().addAll(getClass().getResource("StyleTable.css").toExternalForm());
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),loader);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void changeScreenLogin(ActionEvent event) throws IOException {

        Parent loader = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(loader);
        scene.getStylesheets().addAll(getClass().getResource("StyleTable.css").toExternalForm());
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),loader);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        opacityPane.setVisible(false);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5),opacityPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),menuPane);
        translateTransition.setByX(-600);
        translateTransition.play();
        pane.setStyle("-fx-background-image: url(\"/sample/6.jpg\")");

        menuImg.setOnMouseClicked(event -> {

            opacityPane.setVisible(true);
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5),opacityPane);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(0.15);
            fadeTransition.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5),menuPane);
            translateTransition1.setByX(600);
            translateTransition1.play();
        });

        opacityPane.setOnMouseClicked(event -> {


            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5),opacityPane);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                opacityPane.setVisible(false);
            });

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5),menuPane);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });

    }
}
