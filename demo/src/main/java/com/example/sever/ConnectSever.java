package com.example.sever;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.jdbc.Driver;
public class ConnectSever {

    String hostName = "jdbc:mysql://192.168.9.11/mydatabase";
    Connection connection;
    Statement stmt;
    ResultSet rs;

    public ConnectSever() {
        try {
            
           
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(hostName, "root","YES");
            stmt = connection.createStatement();
            

        } catch (SQLException ex) {
            System.out.println(ex);
            System.exit(1);
        } 
       
    }

    public Connection getConnection() {
        return this.connection;

    }

    public void closeDatabase() {
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}