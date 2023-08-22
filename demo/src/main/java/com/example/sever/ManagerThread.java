
package com.example.sever;
import java.util.ArrayList;

public  class ManagerThread {
    public static ArrayList<ServerThread> list=new ArrayList<>();
public ManagerThread(){

}
public void Add(ServerThread serverThread){
    list.add(serverThread);
}
public void Remove(ServerThread serverThread){
    list.remove(serverThread);
}
}
