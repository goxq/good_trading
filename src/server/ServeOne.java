package server;

import client.utils.CodecUtil;
import common.entity.*;
import server.db.DbConnect;
import server.db.DbHandle;

import javax.print.attribute.standard.OrientationRequested;
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

    public ServeOne(Socket socket, String clientIP) {
        this.socket = socket;
        this.clientIP = clientIP;
    }

    @Override
    public void run() {
        try {

            while (true) {
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
                else if (command.equals("getBuyerOrderList"))
                    this.getBuyerOrderList();
                else if (command.equals("getCommentList"))
                    this.getCommentList();
                else if (command.equals("addComment"))
                    this.addComment();
                else if (command.equals("getAllUsers"))
                    this.getAllUsers();
                else if (command.equals("getSpecificCommodity"))
                    this.getSpecificCommodity();
                else if (command.equals("getSellerOrderList"))
                    this.getSellerOrderList();
                else if(command.equals("deleteAlreadyGood"))
                    this.deleteAlreadyGood();
                else if(command.equals("getAuctionList"))
                    this.getAuctionList();
                else if(command.equals("addToAuction"))
                    this.addToAuction();
                else if(command.equals("getAuctionListWithSameBuyer"))
                    this.getAuctionListWithSameBuyer();
                else if(command.equals("getNumsWithComIdInAuc"))
                    this.getNumsWithComIdInAuc();
                else if(command.equals("auctionSell"))
                    this.auctionSell();
            }
        } catch (SocketException e) {
            System.out.println("连接 " + clientIP + " 已退出");
        } catch (Exception ex) {
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


    private void getGoodsListLen() throws Exception {
        try {
            int count = new DbHandle(new DbConnect().getConnection()).getGoodsListLength();
            dos.writeInt(count);
        } finally {
//            shutStream();
        }
    }

    private void Login() {
        try {
            //登陆操作
            String username;
            String password;

            username = dis.readUTF(dis);
            System.out.println("获取到username：" + username);

            password = dis.readUTF(dis);
            System.out.println("获取到password：" + password);

            //连接数据库查询得到结果rs
            boolean isSuccess = new DbHandle(new DbConnect().getConnection()).login_select(username, password);
            if (isSuccess)
                dos.writeUTF("success");
            else {
                dos.writeUTF("fail");
                socket.close();
            }

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
        try {
            ObjectInputStream ois = new ObjectInputStream(ins);
            User user = (User) ois.readObject();

            //判断用户是否已经存在
            boolean isExist;
            isExist = new DbHandle(new DbConnect().getConnection()).register_select_isexist(user.getUserID());
            if (isExist) {
                dos.writeUTF("isExist");
                socket.close();
            } else {
                //开始注册
                new DbHandle(new DbConnect().getConnection()).register(user.getUserID(), user.getPassword());
                dos.writeUTF("success");
            }
        } finally {
//            shutStream();
        }


    }

    /*
    获取商品列表
     */
    private void getGoodsList() throws Exception {
        try {
            List<Commodity> list = new DbHandle(new DbConnect().getConnection()).getGoodsList();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(list);
            for (Commodity commodity : list) {
                String picPath = commodity.getPicPath();
                File file = new File(picPath);
                FileInputStream fis = new FileInputStream(file);

                //发送文件名和长度
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();

                //开始传输文件
                byte[] bytes = new byte[1024];
                int length = 0;
//                    long progress = 0;

                while ((length = fis.read(bytes)) != -1) {
                    dos.write(bytes, 0, length);
//                        progress += length;
//                        System.out.print("| " + (100 * progress / file.length()) + "% |");
                    dos.flush();
                }
                System.out.println("商品列表图片===== 文件发送成功 ========");
                dos.flush();

                dis.readInt();
            }

        } finally {
//            shutStream();

        }
    }

    /*
    添加商品
     */
    private void AddGoods() throws Exception {
        try {
            ObjectInputStream ois = new ObjectInputStream(ins);
            Commodity commodity = (Commodity) ois.readObject();
            String goodID = commodity.getId();
            String userID = commodity.getUserID();
            String name = commodity.getName();
            double price = commodity.getPrice();
            int nums = commodity.getNums();
            int isAuction = commodity.getIsAuction();
            Date date = commodity.getPostDate();

            //下面接受图片
            String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            File directory = new File("D:\\Design\\Server\\" + commodity.getUserID());
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            //开始接受文件
            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = ins.read(bytes)) != -1) {
                fos.write(bytes, 0, length);
                if (fileLength == file.length()) break;
            }
            System.out.println("添加商品==== 图片接收成功 [File Name：" + fileName + "] ========");

            new DbHandle(new DbConnect().getConnection()).addGoods(goodID, userID, price, name, nums, isAuction, date, file.getAbsolutePath());//将商品写入commodity表（商品列表）
            new DbHandle(new DbConnect().getConnection()).addToAlreadyPost(goodID, userID, price, name, nums, isAuction, date, file.getAbsolutePath());//将商品写入已发布表
            dos.writeInt(1);
        } finally {
//            shutStream();
        }
    }

    //普通购买
    private void Buy() throws Exception {
        try {
            ObjectInputStream ois = new ObjectInputStream(ins);
            Order order = (Order) ois.readObject();
            int buyNums = order.getNums();
            int realNums = new DbHandle(new DbConnect().getConnection()).getCommodityNums(order.getCommodityID());
            if(realNums<buyNums){
                dos.writeInt(0);//验证一把数量是否正确，防止购买的时候别人已经买了页面数量没更新
            }else {
                order.setPicPath(new DbHandle(new DbConnect().getConnection()).getPicPathInServer(order.getCommodityID()));
                new DbHandle(new DbConnect().getConnection()).addToOrder(order.getOrderID(), order.getCommodityID(), order.getBuyerID(), order.getSellerID()
                        , order.getPrice(), order.getName(), order.getNums(), order.getIsAuction(), order.getBuyDate(), order.getPicPath());
                //把买到的商品从商品表里删除
                new DbHandle(new DbConnect().getConnection()).deleteGoods(order.getCommodityID(), order.getNums());
                dos.writeInt(1);
            }
        } finally {
//            shutStream();
        }
    }

    private void getAlreadyPost() throws Exception {
        try {
            String userID = dis.readUTF(dis);
            List<Commodity> alreadyPostList = new DbHandle(new DbConnect().getConnection()).getAlreadyPost(userID);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(alreadyPostList);
            for (Commodity commodity : alreadyPostList) {
                String picPath = commodity.getPicPath();


                File file = new File(picPath);
                FileInputStream fis = new FileInputStream(file);

                //发送文件名和长度
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();

                //开始传输文件
                byte[] bytes = new byte[1024];
                int length = 0;
//                    long progress = 0;

                while ((length = fis.read(bytes)) != -1) {
                    dos.write(bytes, 0, length);
//                        progress += length;
//                        System.out.print("| " + (100 * progress / file.length()) + "% |");
                    dos.flush();
                }
                System.out.println("已发布商品图片======== 文件发送成功 ========");
                dos.flush();

                dis.readInt();
            }
        } finally {

        }
    }

    /*
    返回指定买家订单列表，作为买家的已购买
     */
    private void getBuyerOrderList() throws Exception {
        try {
            List<Order> orderList;
            String userID;
            userID = dis.readUTF(dis);
            orderList = new DbHandle(new DbConnect().getConnection()).getBuyerOrderList(userID);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(orderList);
            //下面发送图片
            for (Order order : orderList) {
                String picPath = order.getPicPath();
                File file = new File(picPath);
                FileInputStream fis = new FileInputStream(file);

                //发送文件名和长度
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();

                //开始传输文件
                byte[] bytes = new byte[1024];
                int length = 0;

                while ((length = fis.read(bytes)) != -1) {
                    dos.write(bytes, 0, length);
                    dos.flush();
                }
                System.out.println("服务器:在返回订单类图片======== 文件发送成功 ========");
                dos.flush();

                dis.readInt();
            }

        } finally {
//            shutStream();
        }
    }

    /*
    获取指定商品的评论列表
     */
    private void getCommentList() throws Exception {
        String commodityID;
        commodityID = dis.readUTF(dis);
        List<Comment> commentList = new DbHandle(new DbConnect().getConnection()).getCommentList(commodityID);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(commentList);
    }

    /*
    给指定商品添加评论
     */
    private void addComment() throws Exception {

        ObjectInputStream ois = new ObjectInputStream(ins);
        Comment comment = (Comment) ois.readObject();
        new DbHandle(new DbConnect().getConnection()).addComment(comment.getCommodityID(), comment.getUserID(), comment.getContent(), comment.getDate());
    }

    /*
    查询用户列表并返回所有用户
     */
    private void getAllUsers() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(new DbHandle(new DbConnect().getConnection()).getAllUsers());
    }

    /*
    商品搜索
     */
    public void getSpecificCommodity() throws Exception {
        try {
            String name = dis.readUTF(dis);
            List<Commodity> list = new DbHandle(new DbConnect().getConnection()).getSpecificCommodity(name);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(list);
            for (Commodity commodity : list) {
                String picPath = commodity.getPicPath();
                File file = new File(picPath);
                FileInputStream fis = new FileInputStream(file);

                //发送文件名和长度
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();

                //开始传输文件
                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = fis.read(bytes)) != -1) {
                    dos.write(bytes, 0, length);
                    dos.flush();
                }
                System.out.println("商品搜索图片======== 文件发送成功 ========");
                dos.flush();
                dis.readInt();
            }
        } finally {
        }
    }


    /*
    每个被别人已购买的商品的所拥有的订单列表
    */
    public void getSellerOrderList() throws Exception {
        List<Order> orderList;
        ObjectInputStream ois = new ObjectInputStream(ins);
        Commodity commodity = (Commodity)ois.readObject();
        orderList = new DbHandle(new DbConnect().getConnection()).getSellerOrderList(commodity.getUserID(),commodity.getId());
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(orderList);
        //下面发送图片
        for (Order order : orderList) {
            String picPath = order.getPicPath();
            File file = new File(picPath);
            FileInputStream fis = new FileInputStream(file);

            //发送文件名和长度
            dos.writeUTF(file.getName());
            dos.flush();
            dos.writeLong(file.length());
            dos.flush();

            //开始传输文件
            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = fis.read(bytes)) != -1) {
                dos.write(bytes, 0, length);
                dos.flush();
            }
            System.out.println("服务器:在返回订单类图片(卖家已发布)======== 文件发送成功 ========");
            dos.flush();
            dis.readInt();
        }
    }
    /*
    卖家删除已发布但没人买的商品
     */
    public void deleteAlreadyGood() throws Exception {
        String commodityID = dis.readUTF(dis);
        new DbHandle(new DbConnect().getConnection()).deleteAlreadyGoods(commodityID);
        new DbHandle(new DbConnect().getConnection()).deleteGoods(commodityID);
    }

    public void getAuctionList() throws Exception{
        String commodityID = dis.readUTF(dis);
        List<Auction> auctionList = new DbHandle(new DbConnect().getConnection()).getAuctionList(commodityID);;
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(auctionList);
        //下面发送图片
        for (Auction auction : auctionList) {
            String picPath = auction.getPicPath();
            File file = new File(picPath);
            FileInputStream fis = new FileInputStream(file);

            //发送文件名和长度
            dos.writeUTF(file.getName());
            dos.flush();
            dos.writeLong(file.length());
            dos.flush();

            //开始传输文件
            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = fis.read(bytes)) != -1) {
                dos.write(bytes, 0, length);
                dos.flush();
            }
            System.out.println("服务器:在返回拍卖类图片======== 文件发送成功 ========");
            dos.flush();
            dis.readInt();
        }

    }

    public void addToAuction()throws Exception{
        ObjectInputStream ois = new ObjectInputStream(ins);
        Auction auction = (Auction)ois.readObject();
        auction.setPicPath(new DbHandle(new DbConnect().getConnection()).getPicPathInServer(auction.getCommodityID()));
        if(new DbHandle(new DbConnect().getConnection()).getMaxAuctionPrice(auction.getCommodityID())>=auction.getPrice()) {
            dos.writeInt(0);
        }
//        }else if(new DbHandle(new DbConnect().getConnection()).getNumsWithSameBuyerAndComID(auction.getCommodityID(),auction.getBuyer())!=0){
//            dos.writeInt(0);
//        }
        else{
            new DbHandle(new DbConnect().getConnection()).addToAuction(auction);
            dos.writeInt(1);
        }
    }
    public void getAuctionListWithSameBuyer() throws Exception{
        String buyer = dis.readUTF(dis);
        List<Auction> auctionList = new DbHandle(new DbConnect().getConnection()).getAuctionListWithSameBuyer(buyer);;
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(auctionList);
        //下面发送图片
        for (Auction auction : auctionList) {
            String picPath = auction.getPicPath();
            File file = new File(picPath);
            FileInputStream fis = new FileInputStream(file);

            //发送文件名和长度
            dos.writeUTF(file.getName());
            dos.flush();
            dos.writeLong(file.length());
            dos.flush();

            //开始传输文件
            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = fis.read(bytes)) != -1) {
                dos.write(bytes, 0, length);
                dos.flush();
            }
            System.out.println("服务器:在返回拍卖类图片======== 文件发送成功 ========");
            dos.flush();
            dis.readInt();
        }
    }
    public void getNumsWithComIdInAuc() throws Exception{
        String commodityID = dis.readUTF(dis);
        int num = new DbHandle(new DbConnect().getConnection()).getNumsWithComIdInAuc(commodityID);
        dos.writeInt(num);
    }
    public void auctionSell() throws Exception{
        ObjectInputStream ois = new ObjectInputStream(ins);
        Order order = (Order)ois.readObject();//order的orderID,comID,buyDate,name已经设置好
        Auction auction = new DbHandle(new DbConnect().getConnection()).getFinalAuction(order.getCommodityID());
        order.setPicPath(auction.getPicPath());
        order.setCommentList(new DbHandle(new DbConnect().getConnection()).getCommentList(order.getCommodityID()));
        order.setIsAuction(1);
        order.setSellerID(auction.getSeller());
        order.setBuyerID(auction.getBuyer());
        order.setNums(1);
        order.setPrice(auction.getFirstPrice());
        order.setAuctionPrice(auction.getPrice());

        int realNums = new DbHandle(new DbConnect().getConnection()).getCommodityNums(order.getCommodityID());
        if(realNums<1){
            dos.writeInt(0);//验证一把数量是否正确，防止购买的时候别人已经买了页面数量没更新
        }else {
            new DbHandle(new DbConnect().getConnection()).addToOrder(order.getOrderID(), order.getCommodityID(), order.getBuyerID(), order.getSellerID()
                    , order.getPrice(), order.getName(), order.getNums(), order.getIsAuction(), order.getBuyDate(), order.getPicPath(),order.getAuctionPrice());
            //把买到的商品从商品表里删除
            new DbHandle(new DbConnect().getConnection()).deleteGoods(order.getCommodityID(), order.getNums());
            new DbHandle(new DbConnect().getConnection()).setIsSoldInAucTrue(order.getCommodityID());
            dos.writeInt(1);
        }
    }
}

