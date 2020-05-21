package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.SignUpModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountController implements Initializable {

    @FXML
    private JFXButton SignBtn;
    @FXML
    private AnchorPane AnchorSignIn;

    @FXML
    private ImageView backBtn;

    @FXML
    private Pane pane;

    @FXML
    private JFXTextField firstNameTF;

    @FXML
    private JFXTextField lastNameTF;

    @FXML
    private JFXTextField userNameTF;

    @FXML
    private JFXTextField emailTF;

    @FXML
    private JFXPasswordField passwordTF;

    @FXML
    private JFXPasswordField password2TF;
    @FXML
    private Label ErrLabel;

    private SignUpModel signUpModel;

    Main main;
    Stage stage;

    public void setMain(Stage stage, Main main){
        this.main=main;
        this.stage=stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ErrLabel.setVisible(false);
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
        Parent loader = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),loader);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    public boolean isPasswordSame(){
        if(password2TF.getText().equals(passwordTF.getText())){
            System.out.println("Password helyes");
            return true;
        }else{
            ErrLabel.setText("Passwords don't mach");
            return false;
        }
    }

    /**
     * Validates the email form
     * @return
     */
    public boolean emailValidator(){
        Pattern pattern= Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9.]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher matcher = pattern.matcher(emailTF.getText());
        if(matcher.find()&&matcher.group().equals(emailTF.getText())){
            System.out.println("Email helyes");
            return true;
        }else{
            ErrLabel.setVisible(true);
            ErrLabel.setText("Pleast fill out every field!");
            return false;
        }
    }

    /**
     * Creates the new account
     * @throws SQLException
     */
    public void CreateAccount() throws SQLException {

        if(firstNameTF.getText().isEmpty() || lastNameTF.getText().isEmpty() || emailTF.getText().isEmpty() || passwordTF.getText().isEmpty() || password2TF.getText().isEmpty()){
            ErrLabel.setVisible(true);
            ErrLabel.setText("Please fill out every field!");
        }else{
            if(emailValidator() && isPasswordSame()){
                signUpModel=new SignUpModel();
                System.out.println("Új account készül");
                if(signUpModel.isSignUp(firstNameTF.getText(),lastNameTF.getText(),userNameTF.getText(),emailTF.getText(),passwordTF.getText())){
                    System.out.println("Elkészült az új account");
                }

            }
        }

    }

}
