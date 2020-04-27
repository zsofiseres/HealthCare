package sample;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PatientData extends RecursiveTreeObject<PatientData> {

    public  StringProperty name;
    public StringProperty id;
    public  StringProperty address;
    public  StringProperty gender;
    public  StringProperty diagnosis;
    public  StringProperty medicines;
    public  StringProperty lastvisit;
    public  StringProperty birthdate;

    public PatientData(String name, String id,String birthdate, String address,String gender,String diagnosis, String medicine,  String lastvisit) {
        this.name = new SimpleStringProperty(name);
        this.id= new SimpleStringProperty(id);
        this.address= new SimpleStringProperty(address);
        this.gender=new SimpleStringProperty(gender);
        this.medicines=new SimpleStringProperty(medicine);
        this.diagnosis=new SimpleStringProperty(diagnosis);
        this.lastvisit=new SimpleStringProperty(lastvisit);
        this.birthdate = new SimpleStringProperty(birthdate);
    }
    public PatientData(String name, String id, String address,String gender){
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

    public String getDiagnosis() {
        return diagnosis.get();
    }

    public StringProperty diagnosisProperty() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis.set(diagnosis);
    }

    public String getMedicines() {
        return medicines.get();
    }

    public StringProperty medicinesProperty() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines.set(medicines);
    }

    public String getLastvisit() {
        return lastvisit.get();
    }

    public StringProperty lastvisitProperty() {
        return lastvisit;
    }

    public void setLastvisit(String lastvisit) {
        this.lastvisit.set(lastvisit);
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
