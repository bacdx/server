package com.example.sever;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;

public class ServerThread implements Runnable {

    private Socket serverSocket;
    private BufferedReader is;
    private BufferedWriter os;
    private ConnectSever database;
    private Dao dao;
    private String idAccount=null;
    public ServerThread(Socket serverSocket,ConnectSever  database) {
        this.serverSocket = serverSocket;
        this.database = database;
        dao = new Dao(this.database);
    }

    @Override
    public void run() {
        String massage=null;
        try {
            
            is = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
        } catch (IOException e) {

            e.printStackTrace();
        }
        while (true) {  
            try {
            
                    massage = is.readLine();
                   
                  if(massage!=null){
                     System.out.print(massage);
                      write(read(massage));
                  }
              
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      
    }

    public void write(String message)   {
        try {
            os.write(message);
            
            System.out.println("Send to client:"+message);
            os.newLine();
            os.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }

    public String read( String message)   {
        String data=null;
        String[] messageSplit=null;
           if(message!=null){
            String title=message.split("#")[0];
            String body=message.split("#")[1];
                 messageSplit = body.split(",");
            if (title.equals(Dao.ACTION_CHECK_USER)) {
                 try {
                    data = dao.getAccount(messageSplit[0], messageSplit[1]);
                    idAccount=dao.getidAccount(messageSplit[0], messageSplit[1]);
                    
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            else if(title.equals(Dao.ACTION_POST_MESSAGE))
            {
            //  data=dao.getMessage(idAccount);
            //  System.out.println(data);
            }
            else if(title.equals(Dao.ACTION_GET_BLOCKCHAT))
            {
             data=dao.getListBlockChat(idAccount);
             System.out.println(data);
            }
            else if(title.equals(Dao.ACTION_GET_MESSAGE))
            {   
             data=dao.getMessage(messageSplit);
             System.out.println(data);
            }


             }
            return data;
        }
    }