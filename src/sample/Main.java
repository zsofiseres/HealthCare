package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    Stage stage,createSignInStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        mainWindow();
    }
    public void mainWindow() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        AnchorPane pane= loader.load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().addAll(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Health Care");
        stage.setScene(scene);
        stage.show();
    }

    public void createSignInWindow() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("signIn.fxml"));
        AnchorPane pane= loader.load();
        Scene scene1 = new Scene(pane);
        CreateAccount controller=loader.getController();
        controller.setMain(stage,this);
        stage.setScene(scene1);
        //stage.show();
        /*createSignInStage=new Stage();
        SignInController controller=loader.getController();
        controller.setMain(createSignInStage,this);
        //scene.getStylesheets().addAll(getClass().getResource("style.css").toExternalForm());
        createSignInStage.setTitle("Health Care");
        createSignInStage.setScene(scene);
        createSignInStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
