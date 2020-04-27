package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    private static final String SCONN="jdbc:sqlite:C:\\Users\\zsofiseres\\IntelliJIDEAProjects\\HealthCare\\HealthCare.db";

    public static Connection getConn() throws SQLException {

        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SCONN);
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return null;

    }
}
