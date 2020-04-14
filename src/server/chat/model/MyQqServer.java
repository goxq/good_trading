package server.chat.model;

import common.chat.Message;
import common.entity.User;


import javax.management.MalformedObjectNameException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyQqServer {
    public static void main(String[] args) {

        new MyQqServer();
    }
    public MyQqServer(){
        try{
                ServerSocket ss = new ServerSocket(9999);
                System.out.println("服务器启动");
                while (true){
                //阻塞
                Socket s = ss.accept();
                //接受客户端发来的信息
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User u = (User) ois.readObject();
                Message m = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                if (true) {                    //默认登陆成功
                    //返回一个成功登陆的信息包
                    m.setMessageType("1");
                    oos.writeObject(m);
                    //这里就单开一个线程，让该线程与该客户端保持通讯
                    ServerConClientThread scct = new ServerConClientThread(s);
                    ManageClientThread.addClientThread(u.getUserID(),scct);
                    //启动与该客户端通讯的线程
                    scct.start();

                    //通知其他在线用户
                    scct.notifyOther(u.getUserID());
                } else {
                    m.setMessageType("2");
                    oos.writeObject(m);
                    s.close();
                }
            }
        }catch (Exception e){

        }
    }
}
