package server;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    static int port = 8900;


    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("服务器启动 等待连接");
            int count = 0;
            while (true){
                Socket socket = ss.accept();
                count++;
                InetAddress inetAddress = socket.getInetAddress();
                ServeOne s = new ServeOne(socket,inetAddress.getHostAddress());
                Thread thread = new Thread(s);


                System.out.println("请求操作的客户端已有"+count+"个");
                System.out.println("客户端的IP地址是："+inetAddress.getHostAddress()+"  name是："+inetAddress.getHostName());

                thread.start();
            }
        } catch (Exception e) {
            System.out.println("异常");
        }
    }
}
