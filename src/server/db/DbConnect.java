package server.db;
import client.connect.CodecUtil;
import common.entity.Comment;
import common.entity.Commodity;
import common.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DbConnect {
    private static Connection ct;
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static void connectDb(){
        try{
            //1.加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.得到链接
            ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydesign?useUnicode = true " +
                    "& characterEncoding = utf-8&useSSL = false&serverTimezone = Asia/Shanghai","root","123");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {

        }
    }
    public static void shut() throws SQLException {
        if (rs != null)
            rs.close();
        if (ps != null)
            ps.close();
        if (ct != null)
            ct.close();
    }
    public static boolean login_select(String username, String password) throws SQLException {
        try{
            connectDb();
            ps = ct.prepareStatement("select userID,password from user");
            rs = ps.executeQuery();
            while (rs.next()) {
                String dbUserID = rs.getString(1);
                String dbPassword = rs.getString(2);
                if (dbUserID.equals(username)) {
                    if (dbPassword.equals(password)) {
                        return true;
                    }
                }
            }
            return false;
        }finally {
            shut();
        }
    }

    public static void addGoods(String goodId, String userID, double price, String name, int nums, int isAuction, Date postDate) {
        try{
            connectDb();
            ps=ct.prepareStatement("insert into commodity (id,userID,price,name,nums,isAuction,postDate) values (?,?,?,?,?,?,?)");
            ps.setString(1,goodId);//生成商品号
            ps.setString(2,userID);
            ps.setDouble(3,price);
            ps.setString(4,name);
            ps.setInt(5,nums);
            ps.setInt(6,isAuction);
            java.sql.Timestamp date = new java.sql.Timestamp(postDate.getTime());
            ps.setTimestamp(7,date);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                shut();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }
    public static int getGoodsListLength() throws SQLException {
        try{
            connectDb();
            ps = ct.prepareStatement("select count(id) from commodity");
            rs = ps.executeQuery();
            rs.next();
            int count = 0;
            count = rs.getInt(1);
            return count;

        }finally {
            shut();
        }
    }
    public static List<Commodity> getGoodsList() throws SQLException{
        try{
            connectDb();
            ps = ct.prepareStatement("select id,userID,name,price,nums,isAuction,postDate from commodity");
            rs = ps.executeQuery();
            List<Commodity> commodityList = new ArrayList<Commodity>();
            while (rs.next()){
                String id = rs.getString(1);
                String userID = rs.getString(2);
                String name = rs.getString(3);
                double price = rs.getDouble(4);
                int nums = rs.getInt(5);
                int Auction = rs.getInt(6);
                Date date = new Date(rs.getTimestamp(7).getTime());
                Commodity commodity = new Commodity(id,userID,price,name,nums,Auction,date);//每一条商品都是一个对象
                commodityList.add(commodity);
            }
            return commodityList;
        }finally {
            shut();
        }
    }
    public static boolean register_select_isexist(String username) throws SQLException{
        try{
            connectDb();
            ps = ct.prepareStatement("select userID from user");
            rs = ps.executeQuery();
            while(rs.next()){
                String userID = rs.getString(1);
                if(userID.equals(username)){
                    return true;
                }
            }
            return false;
        }finally {
            shut();
        }
    }

    public static void register(String username,String password) throws SQLException{
        try{
            connectDb();
            ps = ct.prepareStatement("insert into user(userID,password) values (?,?)");
            ps.setString(1,username);
            ps.setString(2,password);
            ps.executeUpdate();

        }finally {
            shut();
        }
    }

    /*
    点击购买商品后，添加到order表
     */
    public static void addToOrder(String orderID,String commodityID, String buyerID, String sellerID,double price,String name,int nums, int isAuction,Date date) throws SQLException {
        try{
            connectDb();
            ps = ct.prepareStatement("insert into orders(orderID,commodityID,buyerID,sellerID,price,name,nums,isAuction,date)values (?,?,?,?,?,?,?,?,?)");
            ps.setString(1,orderID);
            ps.setString(2,commodityID);
            ps.setString(3,buyerID);
            ps.setString(4,sellerID);
            ps.setDouble(5,price);
            ps.setString(6,name);
            ps.setInt(7,nums);
            ps.setInt(8,isAuction);
            java.sql.Timestamp date_sql = new java.sql.Timestamp(date.getTime());
            ps.setTimestamp(9,date_sql);
            ps.executeUpdate();
        }finally {
            shut();
        }
    }

    /*
    给出商品id，从commodity表中删除指定商品
     */
    public static void deleteGoods(String commodityID) throws SQLException {
        try{
            connectDb();
            ps = ct.prepareStatement("delete from commodity where id = ?");
            ps.setString(1,commodityID);
        }finally {
            shut();
        }
    }
    /*
    给出商品id，从commodity表删除指定数量商品
     */
    public static void deleteGoods(String commodityID,int deleteNums) throws SQLException {
        try{
            int nowNums;
           connectDb();
           ps=ct.prepareStatement("select nums from commodity where id = ?");
           ps.setString(1,commodityID);
           rs=ps.executeQuery();
           rs.next();
           nowNums = rs.getInt(1);
           if(nowNums-deleteNums>0){
               ps=ct.prepareStatement("update commodity set nums = ? where id = ?");
               ps.setInt(1,nowNums-deleteNums);
               ps.setString(2,commodityID);
           }else {
               deleteGoods(commodityID);
           }
        }finally {
            shut();
        }
    }


    /*
    将商品写入指定用户already_post_commodity表
     */
    public static void addToAlreadyPost(String commodityID, String userID, double price, String name, int nums, int isAuction, Date postDate) throws SQLException {
        try{
            connectDb();
            ps = ct.prepareStatement("insert into already_post_commodity(id,userID,price,name,nums,isAuction,postDate)values (?,?,?,?,?,?,?)");
            ps.setString(1,commodityID);
            ps.setString(2,userID);
            ps.setDouble(3,price);
            ps.setString(4,name);
            ps.setInt(5,nums);
            ps.setInt(6,isAuction);
            java.sql.Timestamp date = new java.sql.Timestamp(postDate.getTime());
            ps.setTimestamp(7,date);
            ps.executeUpdate();
        }finally {
            shut();
        }
    }

    /*
    查询数据库获取指定用户已发布商品列表
     */
    public static List<Commodity> getAlreadyPost(String userID)throws SQLException{
        try{
            List<Commodity> commodities = new ArrayList<Commodity>();
            connectDb();
            ps=ct.prepareStatement("select * from commodity where userID = ?");
            ps.setString(1,userID);
            rs = ps.executeQuery();
            while (rs.next()){
                String id = rs.getString(1);
                double price = rs.getDouble(3);
                String name = rs.getString(4);
                int nums = rs.getInt(5);
                int isAuction = rs.getInt(6);
                java.sql.Timestamp timestamp = rs.getTimestamp(7);
                Date date = new Date(timestamp.getTime());
                Commodity commodity = new Commodity(id,userID,price,name,nums,isAuction,date);
                commodities.add(commodity);
            }
            return commodities;

        }finally {
           shut();
        }
    }

    /*
    给userID查询与其有关的订单，无论是seller还是buyer（查询order表）
     */
    public static List<Order> getOrderList(String userID) throws SQLException {
        try {
            connectDb();
            List<Order> orderList = new ArrayList<Order>();
            String orderID;
            String commodityID;
            String buyerID;
            String sellerID;
            double price;
            String name;
            int nums;
            int isAuction;
            Date date;
            ps = ct.prepareStatement("select * from orders where buyerID = ? or sellerID = ?");
            ps.setString(1,userID);
            ps.setString(2,userID);
            rs= ps.executeQuery();
            while(rs.next()){
                orderID = rs.getString(1);
                commodityID = rs.getString(2);
                buyerID = rs.getString(3);
                sellerID = rs.getString(4);
                price = rs.getDouble(5);
                name = rs.getString(6);
                nums = rs.getInt(7);
                isAuction = rs.getInt(8);
                java.sql.Timestamp timestamp = rs.getTimestamp(9);
                date = new Date(timestamp.getTime());
                Order order = new Order(orderID,commodityID,buyerID,sellerID,price,name,nums,isAuction,date);
                orderList.add(order);
            }
            return orderList;
        }finally {
            shut();
        }
    }
    
    /*
    给商品号userID，添加评论（存到comment表）
     */
    public static void addComment(String commodityID,String userID,String content) throws SQLException {
        try{
            connectDb();
            ps=ct.prepareStatement("insert into comment (commodityID,content,userID,date) values (?,?,?,?)");
            ps.setString(1,commodityID);
            ps.setString(2,content);
            ps.setString(3,userID);
            Date date = new Date();
            java.sql.Timestamp timestamp = new Timestamp(date.getTime());
            ps.setTimestamp(4, timestamp);
            ps.executeUpdate();
        }finally {
            shut();
        }
    }
    /*
    给出指定商品的评论列表
     */
    public static List<Comment> getCommentList(String commodityID) throws SQLException {
        try{
            connectDb();
            String content;
            String userID;
            Date date;
            List<Comment> commentList = new ArrayList<Comment>();
            ps=ct.prepareStatement("select * from comment where commodityID = ?");
            ps.setString(1,commodityID);
            rs=ps.executeQuery();
            while (rs.next()){
                content = rs.getString(1);
                userID = rs.getString(2);
                date = new Date(rs.getTimestamp(4).getTime());
                Comment comment = new Comment(content,userID,date);
                commentList.add(comment);
            }
            return commentList;
        }finally {
            shut();
        }
    }
}




