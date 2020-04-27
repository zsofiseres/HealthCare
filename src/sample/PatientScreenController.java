package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class PatientScreenController implements Initializable {

    @FXML
    private JFXTreeTableView<PatientData> treeTableView;

    @FXML
    private TreeTableColumn<PatientData, String> nameCol;

    @FXML
    private TreeTableColumn<PatientData, String> birthCol;

    @FXML
    private TreeTableColumn<PatientData, String> diagnosisCol;

    @FXML
    private TreeTableColumn<PatientData, String> medsCol;

    @FXML
    private TreeTableColumn<PatientData, String> lastVisitCol;

    @FXML
    private JFXTextField searchTF;

    @FXML
    private JFXTextField nameTF;

    @FXML
    private JFXTextField idTF;

    @FXML
    private JFXTextField addressTF;

    @FXML
    private JFXComboBox<String > genderCombo;

    @FXML
    private Label nameLB;

    @FXML
    private Label idLB;

    @FXML
    private Label addressLB;

    @FXML
    private Label genderLB;

    @FXML
    private TreeTableColumn<PatientData, String> idCol;

    @FXML
    private TreeTableColumn<PatientData, String > addressCol;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton clearBtn;
    @FXML
    private JFXButton homeBtn;

    @FXML
    private AnchorPane opacityPane;
    @FXML
    private ImageView menuImg;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private JFXButton LogoutBtn;


    ObservableList<PatientData> list;
    private dbConnection dc;
    private String sql = "SELECT * FROM  patients";
    private String sqlInsert= "INSERT INTO patients(Name,ID,Birthdate,Address,Gender,Diagnosis,Medicines,Lastvisit) VALUES (?,?,?,?,?,?,?,?)";
    private String sqlDelete="DELETE FROM patients WHERE ID = ?";
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        genderCombo.getItems().addAll("Male", "Female");
        opacityPane.setVisible(false);
        list = FXCollections.observableArrayList();
        TreeItem<PatientData> root = new RecursiveTreeItem<PatientData>(list, RecursiveTreeObject ::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        loadPatientsData();


        //Adding action to the search textfield
        searchTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableView.setPredicate(new Predicate<TreeItem<PatientData>>() {
                    @Override
                    public boolean test(TreeItem<PatientData> patientTreeItem) {
                        return patientTreeItem.getValue().name.getValue().contains(newValue) | patientTreeItem.getValue().id.getValue().contains(newValue);
                    }
                });
            }
        });

        treeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        showDetails(newValue);
                    }
                );
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5),opacityPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),menuPane);
        translateTransition.setByX(-600);
        translateTransition.play();
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

    //Add new row to the table
    public void addAction(javafx.event.ActionEvent actionEvent) {
        try {
            Connection conn = dbConnection.getConn();
            PreparedStatement pr = conn.prepareStatement(sqlInsert);
            pr.setString(1,nameTF.getText());
            pr.setString(2,idTF.getText());
            pr.setString(3,"NULL");
            pr.setString(4,addressTF.getText());
            pr.setString(5,genderCombo.getSelectionModel().getSelectedItem());
            pr.setString(6,"NULL");
            pr.setString(7,"NULL");
            pr.setString(8,"NULL");
            pr.execute();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        list.addAll(new PatientData(nameTF.getText(), idTF.getText(), addressTF.getText(), genderCombo.getSelectionModel().getSelectedItem()));
    }
    public void deleteAction(javafx.event.ActionEvent event){
        int index = treeTableView.getSelectionModel().getSelectedIndex();
        TreeItem<PatientData> patientData = treeTableView.getSelectionModel().getSelectedItem();
        System.out.println(patientData.getValue().getId());
        try{
            Connection conn = dbConnection.getConn();
            PreparedStatement pr = conn.prepareStatement(sqlDelete);
            pr.setString(1,patientData.getValue().getId());
            pr.execute();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        list.remove(index);
        clearFields();

    }
    public void showDetails(TreeItem<PatientData> treeItem){
        nameTF.setText(treeItem.getValue().getName());
        nameLB.setText(treeItem.getValue().getName());

        idTF.setText(treeItem.getValue().getId());
        idLB.setText(treeItem.getValue().getId());

        genderCombo.getSelectionModel().select(treeItem.getValue().getGender());
        genderLB.setText(treeItem.getValue().getGender());

        addressTF.setText(treeItem.getValue().getAddress());
        addressLB.setText(treeItem.getValue().getAddress());
    }
    public void editAction(){
        TreeItem<PatientData>treeItem=treeTableView.getSelectionModel().getSelectedItem();
        PatientData patientData = new PatientData (nameTF.getText(),idTF.getText(),addressTF.getText(),genderCombo.getSelectionModel().getSelectedItem());
        treeItem.setValue(patientData);
    }
    public void clearFields(){
        nameTF.setText("");
        idTF.setText("");
        addressTF.setText("");
        genderCombo.getSelectionModel().select("");
    }
    public void clearAction(javafx.event.ActionEvent event) {
        clearFields();
    }
    public void loadPatientsData(){
        try{
            Connection conn = dbConnection.getConn();

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                this.list.add(new PatientData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PatientData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PatientData, String> param) {
                return param.getValue().getValue().name;
            }
        });
        addressCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PatientData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PatientData, String> param) {
                return param.getValue().getValue().address;
            }
        });
        birthCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PatientData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PatientData, String> param) {
                return param.getValue().getValue().birthdate;
            }
        });
        diagnosisCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PatientData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PatientData, String> param) {
                return param.getValue().getValue().diagnosis;
            }
        });
        medsCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PatientData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PatientData, String> param) {
                return param.getValue().getValue().medicines;
            }
        });
        idCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PatientData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PatientData, String> param) {
                return param.getValue().getValue().id;
            }
        });
        lastVisitCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PatientData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PatientData, String> param) {
                return param.getValue().getValue().lastvisit;
            }
        });
    }
    public void changeScreenHome(ActionEvent event) throws IOException {

        Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),loader);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    public void GotoLogin(ActionEvent event) throws IOException {

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
