package sample;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

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
