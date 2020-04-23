package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class PatientScreenController implements Initializable {

    @FXML
    private JFXTreeTableView<Model> treeTableView;

    @FXML
    private TreeTableColumn<Model, String> nameCol;

    @FXML
    private TreeTableColumn<Model, String> birthCol;

    @FXML
    private TreeTableColumn<Model, String> diagnosisCol;

    @FXML
    private TreeTableColumn<Model, String> medsCol;

    @FXML
    private TreeTableColumn<Model, String> lastVisitCol;

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
    private TreeTableColumn<Model, String> idCol;

    @FXML
    private TreeTableColumn<Model, String > addressCol;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton clearBtn;

    ObservableList<Model> list;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
            genderCombo.getItems().addAll("Male", "Female");

            nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Model, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Model, String> param) {
                    return param.getValue().getValue().name;
                }
            });
            addressCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Model, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Model, String> param) {
                    return param.getValue().getValue().address;
                }
            });
            birthCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Model, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Model, String> param) {
                    return param.getValue().getValue().birthdate;
                }
            });
            diagnosisCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Model, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Model, String> param) {
                    return param.getValue().getValue().diagnosis;
                }
            });
            medsCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Model, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Model, String> param) {
                    return param.getValue().getValue().medicines;
                }
            });
            idCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Model, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Model, String> param) {
                    return param.getValue().getValue().id;
                }
            });
            lastVisitCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Model, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Model, String> param) {
                    return param.getValue().getValue().lastvisit;
                }
            });

        list = FXCollections.observableArrayList();
        TreeItem<Model> root = new RecursiveTreeItem<Model>(list, RecursiveTreeObject ::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);

        list.addAll(new Model("adel","123","Eger,Kálnoky","Female","Bromicriptin", "1996", "yesterday", "hormonde level increased"));

        //Adding action to the search textfield
        searchTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableView.setPredicate(new Predicate<TreeItem<Model>>() {
                    @Override
                    public boolean test(TreeItem<Model> modelTreeItem) {
                        return modelTreeItem.getValue().name.getValue().contains(newValue) | modelTreeItem.getValue().id.getValue().contains(newValue);
                    }
                });
            }
        });

        treeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        showDetails(newValue);
                    }
                );
    }

    //Add new row to the table
    public void addAction(javafx.event.ActionEvent actionEvent) {
        list.addAll(new Model(nameTF.getText(), idTF.getText(), addressTF.getText(), genderCombo.getSelectionModel().getSelectedItem()));
    }
    public void deleteAction(javafx.event.ActionEvent event){
        int index = treeTableView.getSelectionModel().getSelectedIndex();
        list.remove(index);
        clearFields();
    }
    public void showDetails(TreeItem<Model>treeItem){
        nameTF.setText(treeItem.getValue().getName());
        nameLB.setText(treeItem.getValue().getName());

        idTF.setText(treeItem.getValue().getId());
        idLB.setText(treeItem.getValue().getId());

        genderCombo.getSelectionModel().select(treeItem.getValue().getGender());

        addressTF.setText(treeItem.getValue().getAddress());
        addressLB.setText(treeItem.getValue().getAddress());
    }
    public void editAction(){
        TreeItem<Model>treeItem=treeTableView.getSelectionModel().getSelectedItem();
        Model model = new Model (nameTF.getText(),idTF.getText(),addressTF.getText(),genderCombo.getSelectionModel().getSelectedItem());
        treeItem.setValue(model);
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
}
