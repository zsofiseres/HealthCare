package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    LoginModel loginModel = new LoginModel();


    @FXML
    private AnchorPane rootPane;

    @FXML
    private Pane pane4;

    @FXML
    private Pane pane3;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane1;

    @FXML
    private JFXButton GetStartedBtn;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;
    @FXML
    private Label loginStatus;

    Main main;
    Stage stage;

    public void setMain(Stage stage, Main main){
        this.main=main;
        this.stage=stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pane2.setStyle("-fx-background-image: url(\"/controllers/1.jpg\")");
        pane3.setStyle("-fx-background-image: url(\"/controllers/3.jpg\")");
        pane4.setStyle("-fx-background-image: url(\"/controllers/2.jpg\")");
        loginStatus.setVisible(false);
        backgroundAnimation();

        if(loginModel.isDbConnected()){
            System.out.printf("Connected to Database");
        }else System.out.printf("Not connected to Db");



       /* GetStartedBtn.setOnAction(event -> {  //Másik megoldás a gombnyomás kezelésére
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
                AnchorPane pane= loader.load();
                rootPane.getChildren().addAll(pane);
                FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),pane);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                fadeTransition.play();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });*/

    }
    public void changeScreenCreateAccount(ActionEvent event) throws IOException {

        Parent loader = FXMLLoader.load(getClass().getResource("../fxml/CreateAccount.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),loader);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    public void GetStartedBtn(ActionEvent event) throws IOException {
        try {
            //ha a felhasználónév és email is helyes akkor a MainScreenre váltunk
            if(loginModel.isLogin(this.username.getText(),this.password.getText())) {
                //doctorModel=new DoctorModel(this.username.getText()); //sikeres belépéskor beállítja a doki usernamet
                //doctorModel.SetDocID(); //lekérdezi username alapján a docid-t
                Parent loader = FXMLLoader.load(getClass().getResource("../fxml/MainScreen.fxml"));
                Scene scene = new Scene(loader);
                scene.getStylesheets().addAll(getClass().getResource("styleMain.css").toExternalForm());
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), loader);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                fadeTransition.play();
            }else{
                loginStatus.setVisible(true);
                loginStatus.setText("Wrong username or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void backgroundAnimation() {

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(3),pane2);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> {

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(3),pane3);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                FadeTransition fadeTransition2=new FadeTransition(Duration.seconds(3),pane4);
                fadeTransition2.setFromValue(1);
                fadeTransition2.setToValue(0);
                fadeTransition2.play();

                fadeTransition2.setOnFinished(event2 -> {

                    FadeTransition fadeTransition0 =new FadeTransition(Duration.seconds(3),pane3);
                    fadeTransition0.setToValue(1);
                    fadeTransition0.setFromValue(0);
                    fadeTransition0.play();

                    fadeTransition0.setOnFinished(event3 -> {

                        FadeTransition fadeTransition11 =new FadeTransition(Duration.seconds(3),pane2);
                        fadeTransition11.setToValue(1);
                        fadeTransition11.setFromValue(0);
                        fadeTransition11.play();

                        fadeTransition11.setOnFinished(event4 -> {

                            FadeTransition fadeTransition22 =new FadeTransition(Duration.seconds(3),pane3  );
                            fadeTransition22.setToValue(1);
                            fadeTransition22.setFromValue(0);
                            fadeTransition22.play();

                            fadeTransition22.setOnFinished(event5 -> {
                                backgroundAnimation();
                            });

                        });

                    });

                });
            });

        });


    }
}
