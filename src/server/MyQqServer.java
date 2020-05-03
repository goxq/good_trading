package server;

import common.chat.Message;
import common.entity.User;
import server.chat.model.ManageClientThread;
import server.chat.model.ServerConClientThread;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MyQqServer {
    public static void main(String[] args) {

        new MyQqServer();
    }

    public MyQqServer() {
        try {
            ServerSocket ss = new ServerSocket(9999);
            System.out.println("服务器启动");
            while (true) {
                //阻塞
                Socket s = ss.accept();
                InetAddress inetAddress = s.getInetAddress();
                System.out.println("新连接！客户端的IP地址是："+inetAddress.getHostAddress()+"  name是："+inetAddress.getHostName());
                //接受客户端发来的信息
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User u = (User) ois.readObject();
                Message m = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                //默认登陆成功
                //返回一个成功登陆的信息包
                m.setMessageType("1");
                oos.writeObject(m);
                //这里就单开一个线程，让该线程与该客户端保持通讯
                ServerConClientThread scct = new ServerConClientThread(s,u);
                ManageClientThread.addClientThread(u.getUserID(), scct);
                //启动与该客户端通讯的线程
                scct.start();
                //通知其他在线用户
                scct.notifyOther(u.getUserID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
