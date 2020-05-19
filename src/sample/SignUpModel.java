package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpModel {
    Connection conn;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty userName;
    private StringProperty email;
    private StringProperty password;
    private StringProperty password2;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getPassword2() {
        return password2.get();
    }

    public StringProperty password2Property() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2.set(password2);
    }

    SignUpModel(String firstName, String lastName, String userName, String email, String password, String password2) {
        try {
            this.conn = dbConnection.getConn();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (this.conn == null) {
            System.exit(1);
        }
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.password2 = new SimpleStringProperty(password2);
    }
    SignUpModel(){
        try {
            this.conn = dbConnection.getConn();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (this.conn == null) {
            System.exit(1);
        }
    }

    public boolean isDbConnected() {
        return this.conn != null;
    }

    public boolean isSignUp(String firstName, String lastName, String userName, String email, String password) throws SQLException {
        PreparedStatement pr =null;
        String sql = "INSERT INTO login(email, password, firstname, lastname, username) VALUES (?,?,?,?,?)";
        try {
            pr = this.conn.prepareStatement(sql);
            pr.setString(1, email);
            pr.setString(2, password);
            pr.setString(3, firstName);
            pr.setString(4, lastName);
            pr.setString(5, userName);

            pr.execute();
            conn.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            pr.close();
        }
    }
}
