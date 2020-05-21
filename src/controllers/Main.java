package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    Stage stage,createSignInStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
        String appVersion = appProps.getProperty("version");
        System.out.println("Appversion:"+appVersion);
        this.stage = primaryStage;
        mainWindow();
    }
    public void mainWindow() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Login.fxml"));
        AnchorPane pane= loader.load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().addAll(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Health Care");
        stage.setScene(scene);
        stage.show();
    }

    public void createSignInWindow() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/signin.fxml"));
        AnchorPane pane= loader.load();
        Scene scene1 = new Scene(pane);
        CreateAccountController controller=loader.getController();
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
