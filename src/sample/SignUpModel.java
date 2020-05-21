package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpModel {
    Connection conn;
    public StringProperty firstName;
    public StringProperty lastName;
    public StringProperty userName;
    public StringProperty email;
    public StringProperty password;
    public StringProperty password2;
    public SecureRandom random;

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
    public SignUpModel(){
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

    public static String SecretPassword(String password) throws NoSuchAlgorithmException {
        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        //Add password bytes to digest
        md.update(password.getBytes());
        //Get the hash's bytes
        byte[] bytes = md.digest();
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        //Get complete hashed password in hex format
       return sb.toString();
    }

    public boolean isSignUp(String firstName, String lastName, String userName, String email, String password) throws SQLException {
        PreparedStatement pr =null;
        String sql = "INSERT INTO login(email, password, firstname, lastname, username) VALUES (?,?,?,?,?)";
        try {

            String pass=SecretPassword(password);
            System.out.println(pass);
            pr = this.conn.prepareStatement(sql);
            pr.setString(1, email);
            pr.setString(2, pass);
            pr.setString(3, firstName);
            pr.setString(4, lastName);
            pr.setString(5, userName);

            pr.execute();
            conn.close();
            return true;

        } catch (SQLException | NoSuchAlgorithmException throwables) {
            throwables.printStackTrace();
            return false;

        } finally {
            pr.close();
        }
    }
}
