package server;

import client.connect.CodecUtil;
import common.entity.*;
import server.db.DbConnect;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.List;


public class ServeOne implements Runnable {
    Socket socket;
    String clientIP;
    OutputStream os;
    DataOutputStream dos;
    InputStream ins;
    DataInputStream dis;

    String command;

    public ServeOne(Socket socket,String clientIP) {
        this.socket = socket;
        this.clientIP = clientIP;
    }

    @Override
    public void run() {
        try {

            while(true) {
                os = socket.getOutputStream();
                dos = new DataOutputStream(os);

                ins = socket.getInputStream();
                dis = new DataInputStream(ins);
                command = dis.readUTF(dis);//从client获取操作

                if (command.equals("Login"))
                    this.Login();
                else if (command.equals("Register"))
                    this.Register();
                else if (command.equals("GetGoodsListLen"))
                    this.getGoodsListLen();
                else if (command.equals("GetGoodsList"))
                    this.getGoodsList();
                else if (command.equals("AddGoods"))
                    this.AddGoods();
                else if (command.equals("Buy"))
                    this.Buy();
                else if (command.equals("getAlreadyPost"))
                    this.getAlreadyPost();
                else if (command.equals("getOrderList"))
                    this.getOrderList();
                else if (command.equals("getCommentList"))
                    this.getCommentList();
                else if (command.equals("addComment"))
                    this.addComment();
                else if(command.equals("getAllUsers"))
                    this.getAllUsers();
            }
        } catch (SocketException e) {
            System.out.println("连接 "+clientIP+" 已退出");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
//    private void shutStream() throws IOException {
//        if (dis != null)
//            dis.close();
//        if (ins != null)
//            ins.close();
//        if (dos != null)
//            dos.close();
//        if (os != null)
//            os.close();
//    }
    private void getGoodsList() throws Exception {
        try{

            List<Commodity> list = DbConnect.getGoodsList();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(list);
        }finally {
//            shutStream();

        }
    }

    private void getGoodsListLen() throws Exception {
        try{
            int count = DbConnect.getGoodsListLength();
            dos.writeInt(count);
        }finally {
//            shutStream();
        }
    }
    private void Login(){
        try {
            //登陆操作
            String username;
            String password;

            username = dis.readUTF(dis);
            System.out.println("获取到username：" + username);

            password = dis.readUTF(dis);
            System.out.println("获取到password：" + password);

            //连接数据库查询得到结果rs
            Boolean isSuccess = DbConnect.login_select(username,password);
            if(isSuccess)
                dos.writeUTF("success");
            else
                dos.writeUTF("fail");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //shutStream();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    private void Register() throws Exception {
        try{
            String username;
            String password;

            username = dis.readUTF(dis);
            password = dis.readUTF(dis);

            //判断用户是否已经存在
            Boolean isExist;
            isExist = DbConnect.register_select_isexist(username);
            if(isExist){
                dos.writeUTF("isExist");
            }

            //开始注册
            DbConnect.register(username,password);
            dos.writeUTF("success");
        }finally {
//            shutStream();
        }


    }

    private void AddGoods() throws Exception{
        try{
            String userID;
            String name;
            double price;
            int nums;
            int isAuction;
            Date postDate;
            String goodID;

            goodID=dis.readUTF(dis);
            userID=dis.readUTF(dis);
            name=dis.readUTF(dis);
            price = dis.readDouble();
            nums= dis.readInt();
            isAuction = dis.readInt();

            ObjectInputStream ois = new ObjectInputStream(ins);
            postDate = (Date)ois.readObject();
            DbConnect.addGoods(goodID,userID,price,name,nums,isAuction,postDate);//将商品写入commodity表（商品列表）
            DbConnect.addToAlreadyPost(goodID,userID,price,name,nums,isAuction,postDate);//将商品写入已发布
            dos.writeInt(1);
        }finally {
//            shutStream();
        }
    }

    //点击购买按钮后。。。
    private void Buy() throws Exception{
        try{
            Commodity commodity;
            User buyer;
            Date date;
            int nums;//购买数量
            String orderID;//订单编号
            ObjectInputStream ois = new ObjectInputStream(ins);
            commodity = (Commodity)ois.readObject();
            buyer = (User)ois.readObject();
            date = (Date)ois.readObject();
            nums = dis.readInt();
            orderID = dis.readUTF();
            DbConnect.addToOrder(orderID,commodity.getId(),buyer.getUserID(),commodity.getUserID(),commodity.getPrice(),
                    commodity.getName(),nums,commodity.getIsAuction(),date);
            //把买到的商品从商品表里删除
            DbConnect.deleteGoods(commodity.getId(),nums);

            dos.writeInt(1);
        }finally {
//            shutStream();
        }
    }

    private void getAlreadyPost() throws Exception {
        try{
            String userID=dis.readUTF(dis);
            List<Commodity> alreadyPostList = DbConnect.getAlreadyPost(userID);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(alreadyPostList);

        }finally {
//            shutStream();
        }
    }
    private void getOrderList() throws Exception{
        try{
            List<Order> orderList;
            String userID;
            userID = dis.readUTF(dis);
            orderList = DbConnect.getOrderList(userID);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(orderList);
        }finally {
//            shutStream();
        }
    }
    private void getCommentList() throws Exception{
        List<Comment> commentList;
        String commodityID;
        commodityID = dis.readUTF(dis);
        commentList = DbConnect.getCommentList(commodityID);

        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(commentList);
    }
    /*
    给指定商品添加评论
     */
    private void addComment() throws Exception{
        String commodityID;
        String userID;
        String content;

        commodityID = dis.readUTF(dis);
        userID=dis.readUTF(dis);
        content=dis.readUTF(dis);

        DbConnect.addComment(commodityID,userID,content);
    }
    /*
    查询用户列表并返回所有用户
     */
    private void getAllUsers() throws Exception{
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(DbConnect.getAllUsers());
    }

}
