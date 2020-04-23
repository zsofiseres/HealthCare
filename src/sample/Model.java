package sample;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model extends RecursiveTreeObject<Model> {

    StringProperty name;
    StringProperty id;
    StringProperty address;
    StringProperty gender;
    StringProperty medicines;
    StringProperty lastvisit;
    StringProperty birthdate;

    public String getDiagnosis() {
        return diagnosis.get();
    }

    public StringProperty diagnosisProperty() {
        return diagnosis;
    }

    StringProperty diagnosis;

    public String getMedicines() {
        return medicines.get();
    }

    public StringProperty medicinesProperty() {
        return medicines;
    }

    public String getLastvisit() {
        return lastvisit.get();
    }

    public StringProperty lastvisitProperty() {
        return lastvisit;
    }

    public String getBirthdate() {
        return birthdate.get();
    }

    public StringProperty birthdateProperty() {
        return birthdate;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }



    public Model(String name, String id, String address,String gender, String medicine, String birthdate, String lastvisit, String diagnosis){
        this.name= new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.address = new SimpleStringProperty(address);
        this.gender= new SimpleStringProperty(gender);
        this.birthdate = new SimpleStringProperty(birthdate);
        this.lastvisit= new SimpleStringProperty(lastvisit);
        this.medicines= new SimpleStringProperty(medicine);
        this.diagnosis = new SimpleStringProperty(diagnosis);
    }
    public Model(String name, String id, String address,String gender){
        this.name= new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.address = new SimpleStringProperty(address);
        this.gender= new SimpleStringProperty(gender);

    }
}
