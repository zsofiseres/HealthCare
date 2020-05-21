package models;

import controllers.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorModel {

    Connection connection;
    String username;
    static String  ID;

    public static String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    DoctorModel(String username){
        this.username=username;
    }

    public boolean SetDocID() throws Exception {

        this.connection = dbConnection.getConn();
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM login where username == ?";
        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, this.username);

            rs = pr.executeQuery();

            if (rs.next()) {
                this.setID(rs.getString(6));
                System.out.println(this.getID());
                return true;
            }
            return false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            pr.close();
            rs.close();
        }
    }
}

