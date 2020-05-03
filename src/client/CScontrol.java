package client;

import com.mysql.cj.xdevapi.JsonString;
import common.entity.*;

import java.io.*;
import java.net.CookiePolicy;
import java.net.Socket;
import java.util.Date;
import java.util.List;

/*
客户端各个功能与服务器连接、发送数据的类
 */
public class CScontrol {
    public static Socket socket;
    public static InputStream is;
    public static DataInputStream dis;
    public static OutputStream os;
    public static DataOutputStream dos;

    public static void baseConnect() throws IOException {
        //连接服务器
        socket = new Socket("127.0.0.1", 8900);
        is = socket.getInputStream();
        dis = new DataInputStream(is);
        os = socket.getOutputStream();
        dos = new DataOutputStream(os);
    }

    public static void sendCommand(String command) throws IOException {
        //给服务器说明操作
        dos.writeUTF(command);
    }

    public static boolean loginToServer(String username, String password) throws IOException {
        try {
            baseConnect();
            sendCommand("Login");
            dos.writeUTF(username);
            dos.writeUTF(password);

            //接受返回success或wrong
            String result = dis.readUTF(dis);
            System.out.println("登录结果是：" + result);
            if (result.equals("success"))
                return true;
            else
                return false;

        } finally {
//            if (dos != null)
//                dos.close();
//            if (os != null)
//                os.close();
//            if (dis != null)
//                dis.close();
//            if (is != null)
//                is.close();
        }

    }

    public static int registerToServer(String username, String password) throws IOException {
        baseConnect();
        sendCommand("Register");
        User user = new User(username,password);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(user);
        String result = dis.readUTF(dis);
        if (result.equals("isExist"))
            return 0;//用户存在返回0
        else
            return 1;//注册成功返回1
    }

    //    private static void shutStream() throws IOException {
//        if(dos!=null)
//            dos.close();
//        if(os!=null)
//            os.close();
//        if(dis!=null)
//            dis.close();
//        if(is!=null)
//            is.close();
//    }
    public static int getGoodsListLenToServer() throws Exception {
        try {
            //连接服务器获取列列表长度
            sendCommand("GetGoodsListLen");
            int listCount = dis.readInt();//获取到list的长度
            return listCount;
        } finally {
//            shutStream();
        }
    }//好像没什么用。。。

    /*
    获取商品列表
     */
    public static List<Commodity> getGoodsListToServer(String userID) throws Exception {
        try {
            //连接服务器获取列列表<commodity>
            sendCommand("GetGoodsList");
            ObjectInputStream ois = new ObjectInputStream(is);
            List<Commodity> resultList = (List<Commodity>) ois.readObject();
            //接受图片
            for (Commodity commodity : resultList) {

                String fileName = dis.readUTF();
                long fileLength = dis.readLong();
                File directory = new File("D:\\Design\\Client\\" + userID);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
                FileOutputStream fos = new FileOutputStream(file);
                //开始接受文件
                byte[] bytes = new byte[1024];
                int length = 0;

                while ((length = dis.read(bytes)) != -1) {
                    fos.write(bytes, 0, length);
                    fos.flush();
                    if (fileLength == file.length()) break;
                }
                System.out.println("在获取商品列表======== 获取商品图片成功 [File Name：" + fileName + "] ========");
                commodity.setPicPath(file.getAbsolutePath());
                dos.writeInt(1);
            }
            return resultList;
        } finally {
//            shutStream();
        }
    }

    /*
    发布商品
     */
    public static int addGoodsToServer(String userID, Commodity commodity) throws Exception {
        try {

            sendCommand("AddGoods");
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(commodity);


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
            long progress = 0;


            while ((length = fis.read(bytes)) != -1) {
                os.write(bytes, 0, length);
                progress += length;
                System.out.print("| " + (100 * progress / file.length()) + "% |");
            }


            System.out.println("======== 文件传输成功 ========");

            if (dis.readInt() == 1) {
                return 1;
            } else
                return 0;
        } finally {

        }
    }

    /*
    商品购买 添加成功返回1，失败返回0
     */
    //Commodity对象里有sellerID，所以只传来buyer就行
    public static int BuyToServer(Order order) throws Exception {
        try {

            sendCommand("Buy");
            ObjectOutputStream oos = new ObjectOutputStream(os);

            //生成唯一订单号
            oos.writeObject(order);

            int status = dis.readInt();
            if (status == 1) {//添加成功
                return 1;
            } else return 0;
        } finally {
//            shutStream();
        }
    }


    /*
    获取已发布商品
     */
    public static List<Commodity> getAlreadyPost(String userID) throws Exception {
        try {
            List<Commodity> commodities;
            sendCommand("getAlreadyPost");
            dos.writeUTF(userID);
            dos.flush();
            ObjectInputStream ois = new ObjectInputStream(is);
            commodities = (List<Commodity>) ois.readObject();
            //接受图片
            for (Commodity commodity : commodities) {
                String fileName = dis.readUTF();
                long fileLength = dis.readLong();
                File directory = new File("D:\\Design\\Client\\" + userID);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
                FileOutputStream fos = new FileOutputStream(file);
                //开始接受文件
                byte[] bytes = new byte[1024];
                int length = 0;

                while ((length = dis.read(bytes)) != -1) {
                    fos.write(bytes, 0, length);
                    fos.flush();
                    if (fileLength == file.length()) break;
                }
                System.out.println("======== 获取商品图片成功 [File Name：" + fileName + "] ========");
                commodity.setPicPath(file.getAbsolutePath());

                dos.writeInt(1);
            }
            return commodities;
        } finally {
//            shutStream();
        }
    }

    /*
    获取订单列表
     */
    public static List<Order> getBuyerOrderList(String userID) throws Exception {
        try {

            sendCommand("getBuyerOrderList");
            dos.writeUTF(userID);
            List<Order> orderList;
            ObjectInputStream ois = new ObjectInputStream(is);
            orderList = (List<Order>) ois.readObject();
            //接受图片
            for (Order order : orderList) {
                String fileName = dis.readUTF();
                long fileLength = dis.readLong();
                File directory = new File("D:\\Design\\Client\\" + userID);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
                FileOutputStream fos = new FileOutputStream(file);
                //开始接受文件
                byte[] bytes = new byte[1024];
                int length = 0;

                while ((length = dis.read(bytes)) != -1) {
                    fos.write(bytes, 0, length);
                    fos.flush();
                    if (fileLength == file.length()) break;
                }
                System.out.println("Client:在获取订单（已购买表）== 获取商品图片成功 [File Name：" + fileName + "] ========");
                order.setPicPath(file.getAbsolutePath());
                dos.writeInt(1);
            }
            return orderList;
        } finally {
//            shutStream();
        }
    }

    /*
    获取指定商品的评论列表
     */
    public static List<Comment> getCommentList(String commodityID) throws Exception {
        try {
            sendCommand("getCommentList");
            dos.writeUTF(commodityID);
            dos.flush();
            List<Comment> commentList;
            ObjectInputStream ois = new ObjectInputStream(is);
            commentList = (List<Comment>) ois.readObject();
            return commentList;
        } finally {
//            shutStream();
        }

    }

    /*
    给指定商品添加评论
     */
    public static void addComment(Comment comment) throws Exception {
        try {

            sendCommand("addComment");
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(comment);
        } finally {
//            shutStream();
        }
    }

    public static List<User> getAllUsers() throws Exception {
        sendCommand("getAllUsers");
        ObjectInputStream ois = new ObjectInputStream(is);
        List<User> userList = (List<User>) ois.readObject();
        return userList;
    }

    /*
    商品搜索
     */
    public static List<Commodity> getSpecificCommodity(String name,String userID) throws Exception{
        sendCommand("getSpecificCommodity");
        dos.writeUTF(name);
        ObjectInputStream ois = new ObjectInputStream(is);
        List<Commodity> commodityList = (List<Commodity>) ois.readObject();
        for (Commodity commodity : commodityList) {

            String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            File directory = new File("D:\\Design\\Client\\" + userID);
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            //开始接受文件
            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = dis.read(bytes)) != -1) {
                fos.write(bytes, 0, length);
                fos.flush();
                if (fileLength == file.length()) break;
            }
            System.out.println("在获取商品列表======== 获取商品图片成功 [File Name：" + fileName + "] ========");
            commodity.setPicPath(file.getAbsolutePath());
            dos.writeInt(1);
        }
        return commodityList;
    }

    /*
    每个被别人已购买的商品的所拥有的订单列表
     */
    public static List<Order> getSellerOrderList(Commodity commodity,String userID) throws Exception{
        sendCommand("getSellerOrderList");
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(commodity);
        ObjectInputStream ois = new ObjectInputStream(is);
        List<Order> orders = (List<Order>)ois.readObject();
        for (Order order : orders) {
            String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            File directory = new File("D:\\Design\\Client\\" + userID);
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            //开始接受文件
            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = dis.read(bytes)) != -1) {
                fos.write(bytes, 0, length);
                fos.flush();
                if (fileLength == file.length()) break;
            }
            System.out.println("在获取卖家已卖出订单列表======== 获取商品图片成功 [File Name：" + fileName + "] ========");
            order.setPicPath(file.getAbsolutePath());
            dos.writeInt(1);
        }
        return orders;
    }
    /*
    删除还没人买的已发布商品
     */
    public static void deleteAlreadyGood(String commodityID) throws IOException {
        sendCommand("deleteAlreadyGood");
        dos.writeUTF(commodityID);
    }
    public static List<Auction> getBuyerAuctionList(String commodityID) throws Exception{
        sendCommand("getAuctionList");
        dos.writeUTF(commodityID);
        ObjectInputStream ois = new ObjectInputStream(is);
        List<Auction> auctionList = (List<Auction>)ois.readObject();
        for (Auction auction : auctionList) {
            String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            File directory = new File("D:\\Design\\Client\\" + auction.getBuyer());
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            //开始接受文件
            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = dis.read(bytes)) != -1) {
                fos.write(bytes, 0, length);
                fos.flush();
                if (fileLength == file.length()) break;
            }
            System.out.println("在获取买家拍卖列表（指定商品）======== 获取商品图片成功 [File Name：" + fileName + "] ========");
            auction.setPicPath(file.getAbsolutePath());
            dos.writeInt(1);
        }
        return auctionList;
    }
    public static List<Auction> getSellerAuctionList(String commodityID) throws Exception{
        sendCommand("getAuctionList");
        dos.writeUTF(commodityID);
        ObjectInputStream ois = new ObjectInputStream(is);
        List<Auction> auctionList = (List<Auction>)ois.readObject();
        for (Auction auction : auctionList) {
            String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            File directory = new File("D:\\Design\\Client\\" + auction.getSeller());
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            //开始接受文件
            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = dis.read(bytes)) != -1) {
                fos.write(bytes, 0, length);
                fos.flush();
                if (fileLength == file.length()) break;
            }
            System.out.println("在获取卖家拍卖列表======== 获取商品图片成功 [File Name：" + fileName + "] ========");
            auction.setPicPath(file.getAbsolutePath());
            dos.writeInt(1);
        }
        return auctionList;
    }
    public static int addToAuction(Auction auction) throws IOException {
        sendCommand("addToAuction");
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(auction);
        int result = dis.readInt();
        return result;
    }
    public static List<Auction> getAuctionListWithSameBuyer(String buyer) throws IOException, ClassNotFoundException {
        sendCommand("getAuctionListWithSameBuyer");
        dos.writeUTF(buyer);
        ObjectInputStream ois = new ObjectInputStream(is);
        List<Auction> auctionList = (List<Auction>)ois.readObject();
        for (Auction auction : auctionList) {
            String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            File directory = new File("D:\\Design\\Client\\" + auction.getBuyer());
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            //开始接受文件
            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = dis.read(bytes)) != -1) {
                fos.write(bytes, 0, length);
                fos.flush();
                if (fileLength == file.length()) break;
            }
            System.out.println("在获取买家拍卖列表(指定用户)======== 获取商品图片成功 [File Name：" + fileName + "] ========");
            auction.setPicPath(file.getAbsolutePath());
            dos.writeInt(1);
        }
        return auctionList;
    }
    public static int getNumsWithComIdInAuc(String commodityID) throws Exception{
        sendCommand("getNumsWithComIdInAuc");
        dos.writeUTF(commodityID);
        return dis.readInt();
    }
    public static int auctionSell(Order order) throws Exception{
        sendCommand("auctionSell");
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(order);
        return dis.readInt();
    }
    public static List<Order> getOrdersByDate(String seller, int days) throws Exception{
        sendCommand("getOrdersByDate");
        dos.writeInt(days);
        dos.writeUTF(seller);
        ObjectInputStream ois = new ObjectInputStream(is);
        return (List<Order>)ois.readObject();
    }
}
