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
    private JFXTreeTableView<PatientModel> treeTableView;

    @FXML
    private TreeTableColumn<PatientModel, String> nameCol;

    @FXML
    private TreeTableColumn<PatientModel, String> birthCol;

    @FXML
    private JFXTextField searchTF;

    @FXML
    private JFXTextField nameTF;

    @FXML
    private JFXDatePicker birthPicker;

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
    private TreeTableColumn<PatientModel, String> idCol;

    @FXML
    private TreeTableColumn<PatientModel, String > addressCol;

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


    ObservableList<PatientModel> list;

    private dbConnection dc;
    private String sql = "SELECT * FROM  patients WHERE DOCID =?";
    private String sqlInsert= "INSERT INTO patients(Name,ID,Birthdate,Address,Gender,DOCID) VALUES (?,?,?,?,?,?)";
    private String sqlDelete="DELETE FROM patients WHERE ID = ?";
    private String sqlUpdate="UPDATE patients SET (Name,ID,Birthdate,Address,Gender,DOCID) = (?,?,?,?,?,?) WHERE ID = ? ";
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        genderCombo.getItems().addAll("Male", "Female");
        opacityPane.setVisible(false);
        list = FXCollections.observableArrayList();
        TreeItem<PatientModel> root = new RecursiveTreeItem<PatientModel>(list, RecursiveTreeObject ::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        loadPatientsData();


        //Adding action to the search textfield
        searchTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableView.setPredicate(new Predicate<TreeItem<PatientModel>>() {
                    @Override
                    public boolean test(TreeItem<PatientModel> patientTreeItem) {
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
    public void addAction(ActionEvent actionEvent) {
        try {
            Connection conn = dbConnection.getConn();
            String docid= DoctorModel.getID();
            PreparedStatement pr = conn.prepareStatement(sqlInsert);
            pr.setString(1,nameTF.getText());
            pr.setString(2,idTF.getText());
            pr.setString(3,birthPicker.getEditor().getText());
            pr.setString(4,addressTF.getText());
            pr.setString(5,genderCombo.getSelectionModel().getSelectedItem());
            pr.setString(6,docid);
            pr.execute();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        list.addAll(new PatientModel(nameTF.getText(), idTF.getText(), birthPicker.getEditor().getText(),addressTF.getText(), genderCombo.getSelectionModel().getSelectedItem()));
    }
    //Delete row from table
    public void deleteAction(ActionEvent event){
        int index = treeTableView.getSelectionModel().getSelectedIndex();
        TreeItem<PatientModel> patientData = treeTableView.getSelectionModel().getSelectedItem();
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
    public void showDetails(TreeItem<PatientModel> treeItem){
        nameTF.setText(treeItem.getValue().getName());
        nameLB.setText(treeItem.getValue().getName());

        idTF.setText(treeItem.getValue().getId());
        idLB.setText(treeItem.getValue().getId());

        genderCombo.getSelectionModel().select(treeItem.getValue().getGender());
        genderLB.setText(treeItem.getValue().getGender());

        addressTF.setText(treeItem.getValue().getAddress());
        addressLB.setText(treeItem.getValue().getAddress());
    }
    //Edite data in table
    public void editAction(){
        TreeItem<PatientModel>treeItem=treeTableView.getSelectionModel().getSelectedItem();
        try{
            Connection conn = dbConnection.getConn();
            PreparedStatement pr = conn.prepareStatement(sqlUpdate);
            pr.setString(1,nameTF.getText());
            pr.setString(2,idTF.getText());
            pr.setString(3,birthPicker.getEditor().getText());
            pr.setString(4,addressTF.getText());
            pr.setString(5,genderCombo.getSelectionModel().getSelectedItem());
            pr.setString(6,DoctorModel.getID());
            pr.setString(7,treeItem.getValue().getId());
            pr.execute();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PatientModel patientData = new PatientModel(nameTF.getText(),idTF.getText(),birthPicker.getEditor().getText(),addressTF.getText(),genderCombo.getSelectionModel().getSelectedItem());
        treeItem.setValue(patientData);
    }
    public void clearFields(){
        nameTF.setText("");
        idTF.setText("");
        addressTF.setText("");
        genderCombo.getSelectionModel().select("");
    }
    public void clearAction(ActionEvent event) {
        clearFields();
    }

    //Patiens data load from table to patients view table
    public void loadPatientsData(){
        try{
            String id= DoctorModel.getID();
            System.out.println(id);
            Connection conn = dbConnection.getConn();
            PreparedStatement pr = null;
            ResultSet rs = null;
            pr = conn.prepareStatement(sql);
            pr.setString(1,id);

            rs= pr.executeQuery();

            while(rs.next()){
                this.list.add(new PatientModel(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PatientModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PatientModel, String> param) {
                return param.getValue().getValue().name;
            }
        });
        addressCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PatientModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PatientModel, String> param) {
                return param.getValue().getValue().address;
            }
        });
        birthCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PatientModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PatientModel, String> param) {
                return param.getValue().getValue().birthdate;
            }
        });

        idCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PatientModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PatientModel, String> param) {
                return param.getValue().getValue().id;
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
