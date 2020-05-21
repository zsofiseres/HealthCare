package models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PatientModel extends RecursiveTreeObject<PatientModel> {

    public  StringProperty name;
    public  StringProperty id;
    public  StringProperty address;
    public  StringProperty gender;
    public  StringProperty birthdate;

    public PatientModel(String name, String id, String birthdate, String address, String gender) {
        this.name = new SimpleStringProperty(name);
        this.id= new SimpleStringProperty(id);
        this.address= new SimpleStringProperty(address);
        this.gender=new SimpleStringProperty(gender);
        this.birthdate = new SimpleStringProperty(birthdate);
    }
    public PatientModel(String name, String id, String address, String gender){
        this.name= new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.address = new SimpleStringProperty(address);
        this.gender= new SimpleStringProperty(gender);

    }
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getBirthdate() {
        return birthdate.get();
    }

    public StringProperty birthdateProperty() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate.set(birthdate);
    }
}
