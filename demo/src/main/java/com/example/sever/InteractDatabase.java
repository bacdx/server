package com.example.sever;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Driver;
public class InteractDatabase {



    static final String HOST_NAME = "jdbc:mysql://192.168.9.10/mydatabase";
    static final String S_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private  static Connection connection ;
    public InteractDatabase(){
        
          try {
            Class.forName(S_JDBC_DRIVER);
             connection = DriverManager.getConnection(HOST_NAME, "pma","your_password");
          
            
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }




    public Connection getConnection() {
        return this.connection;
    }

    public String GetData(){
        
        String data="";
        try {
           
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM account");
            while (rs.next()){
                
                   data+= rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+rs.getString(4)+",";
                    
            }
            System.out.println(data);
                
          
            // Buoc 5: Dong doi tuong Connection
            connection.close();
            
        }
        catch(Exception e){

        } 
       
 return data;
    }
    public void PushData(){
        try {
           
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM account");
            while (rs.next())
                
            System.out.println(
                    rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));

            // Buoc 5: Dong doi tuong Connection
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }
    
}
