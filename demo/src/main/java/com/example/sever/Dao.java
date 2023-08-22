package com.example.sever;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class Dao {
    public static final String ACTION_CHECK_USER = "0";
    public static final String ACTION_POST_MESSAGE = "1";
    public static final String ACTION_GET_IDACCOUNT = "2";
    public static final String ACTION_GET_BLOCKCHAT = "3";
    public static final String ACTION_GET_MESSAGE = "4";

    private ConnectSever database;
    private Statement stt = null;

    public Dao(ConnectSever database) {
        this.database = database;
        try {

            stt = this.database.getConnection().createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getListBlockChat(String idacount) {
        PreparedStatement pstt = null;
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("req",ACTION_GET_BLOCKCHAT);
        JsonArray jsonArray=new JsonArray();
        String sql = "select * from blockchat join memberblock  on blockchat.id=memberblock.idblock  where memberblock.idaccount= ? ";
        try {
            pstt = this.database.getConnection().prepareStatement(sql);
            pstt.setString(1, idacount);
            ResultSet resultSet = pstt.executeQuery();
           
            while (resultSet.next()) {
                
                JsonObject json = new JsonObject();
                int i = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String img = resultSet.getString("img");
                int loai = resultSet.getInt("loai");
                json.addProperty("id",i);
                json.addProperty("name",name);
                json.addProperty("name",img); 
                json.addProperty("loai",loai);
                jsonArray.add(json);
                System.out.print(jsonArray.toString());
                JsonReader jsonReader=new JsonReader(null);
                
              
                
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        jsonObject.add("body", jsonArray);
        System.out.println(jsonObject.toString());
        return jsonObject.toString();

    }

    public String getMessage(String... id) {

        PreparedStatement pstt = null;
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("req",ACTION_GET_MESSAGE);
        JsonArray jsonArray=new JsonArray();
        String sql = "select  message.id, message.idaccount, message.content, message.time,message.idblock  from message inner join  blockchat  on blockchat.id=message.idblock where blockchat.id =? order by time DESC LIMIT 100;";
        try {
            System.out.println(id);
            pstt = this.database.getConnection().prepareStatement(sql);
            for (int i = 0; i < id.length; i++) {
                pstt.setString(1, id[i]);
                ResultSet resultSet = pstt.executeQuery();
                while (resultSet.next()) {
                    int ma = resultSet.getInt("id");
                    String idaccount = resultSet.getString("idaccount");
                    String content = resultSet.getString("content");
                    String time = resultSet.getDate("time").toString();
                    String idblock = resultSet.getString("idblock");
                   
                }
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }

    public String getAccount(String username, String pass) throws SQLException {
        String id,age;
        String name,email,sdt,img,userName,pass1;
        JsonObject jsonObject=new JsonObject();
        JsonObject infoAcount=new JsonObject();
        jsonObject.addProperty("res",ACTION_GET_IDACCOUNT);
    
        JsonArray jsonArray=new JsonArray();
        PreparedStatement pstt = null;
        String sql = "select * from account where username = ? and password =?";
        
        pstt = this.database.getConnection().prepareStatement(sql);
        pstt.setString(1, username);
        pstt.setString(2, pass);
      
        ResultSet resultSet = pstt.executeQuery();
        while (resultSet.next()) {

             id = resultSet.getString("id");
             name = resultSet.getString("name");
             email = resultSet.getString("email");
             sdt = resultSet.getString("sdt");
             age = resultSet.getString("age");
             img = resultSet.getString("img");
             userName = resultSet.getString("username");
             pass1 = resultSet.getString("password");


             infoAcount.addProperty("username", userName);
            infoAcount.addProperty("password", pass);
            infoAcount.addProperty("id",id);
            infoAcount.addProperty("email",email);
            infoAcount.addProperty("sdt",sdt);
            infoAcount.addProperty("age",age);
            infoAcount.addProperty("img",img);
            infoAcount.addProperty("name",name);
            jsonArray.add(infoAcount);

        }
           
            jsonObject.add("body",jsonArray);
     

        return jsonObject.toString();

    }


    public String getidAccount(String username, String pass) throws SQLException {

        PreparedStatement pstt = null;
        String id = null;
        String sql = "select id from account where username = ? and password =?";
        pstt = this.database.getConnection().prepareStatement(sql);
        pstt.setString(1, username);
        pstt.setString(2, pass);
        ResultSet resultSet = pstt.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getString("id");
        }

        return id;

    }

}
