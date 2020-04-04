/*
客户端各个功能与服务器连接、发送数据的类
 */

package client;


import client.entity.Commodity;
import client.utility.SendList;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class CScontrol {
    static Socket socket;
    static InputStream is;
    static DataInputStream dis;
    static OutputStream os;
    static DataOutputStream dos;

    public static void baseConnect() throws IOException {
        //连接服务器
        socket = new Socket("127.0.0.1",8090);
    }
    public static void sendCommand(String command) throws IOException {
        is = socket.getInputStream();
        dis = new DataInputStream(is);
        os = socket.getOutputStream();
        dos = new DataOutputStream(os);

        System.out.println(dis.readUTF(dis));//打印你已连接到服务器

        //给服务器说明操作
        dos.writeUTF(command);
    }
    public static boolean loginToServer(String username,String password) throws IOException {
        try{
            baseConnect();
            sendCommand("Login");
            dos.writeUTF(username);
            dos.writeUTF(password);

            //接受返回success或wrong
            String result = dis.readUTF(dis);
            System.out.println("登录结果是："+result);
            if(result.equals("success"))
                return true;
            else
                return false;

        }finally {
            if (dos != null)
                dos.close();
            if (os != null)
                os.close();
            if (dis != null)
                dis.close();
            if (is != null)
                is.close();
        }

    }

    public static int RegisterToServer(String username, String password) throws IOException {
        baseConnect();
        sendCommand("Register");
        dos.writeUTF(username);
        dos.writeUTF(password);

        String result = dis.readUTF(dis);
        if(result.equals("isExist"))
            return 0;//用户存在返回0
        else
            return 1;//注册成功返回1
    }
    public static int getGoodsListLenToServer() throws Exception{
        try{
            //连接服务器获取列列表长度
            baseConnect();
            sendCommand("GetGoodsListLen");
            int listCount = dis.readInt();//获取到list的长度
            return listCount;
        }finally {
            if (dos != null)
                dos.close();
            if (os != null)
                os.close();
            if (dis != null)
                dis.close();
            if (is != null)
                is.close();
        }
    }
    public static List<Commodity> getGoodsListToServer() throws Exception{
        try{
            //连接服务器获取列列表<commodity>
            baseConnect();
            sendCommand("GetGoodsList");

            ObjectInputStream ois = new ObjectInputStream(is);
            SendList sl = new SendList();
            sl = (SendList)ois.readObject();

            List resultList = new ArrayList();
            resultList = sl.getSendlist();
            return resultList;
        }finally {
            if (dos != null)
                dos.close();
            if (os != null)
                os.close();
            if (dis != null)
                dis.close();
            if (is != null)
                is.close();
        }
    }

    public static int addGoods(String userID,String name,double price,int nums,int isAuction) throws Exception{
        try{
            baseConnect();
            sendCommand("AddGoods");

            dos.writeUTF(userID);
            dos.writeUTF(name);
            dos.writeDouble(price);
            dos.writeInt(nums);
            dos.writeInt(isAuction);
            if(dis.readInt()==1){
                return 1;
            }else
                return 0;
        }finally {
            if (dos != null)
                dos.close();
            if (os != null)
                os.close();
            if (dis != null)
                dis.close();
            if (is != null)
                is.close();
        }
    }

}
