package server.db;

import common.entity.Comment;
import common.entity.Commodity;
import common.entity.Order;
import common.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbHandle {
    Connection ct;
    PreparedStatement ps;
    ResultSet rs;
    public DbHandle(Connection ct){
        this.ct = ct;
    }
    public boolean login_select(String username, String password) throws SQLException {
        try{
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
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
        return false;
    }
    public  void addGoods(String goodId, String userID, double price, String name, int nums, int isAuction, Date postDate, String picPath) throws SQLException{
        try{
            
            System.out.println("在数据库");
            ps=ct.prepareStatement("insert into commodity (id,userID,price,name,nums,isAuction,postDate,picPath) values (?,?,?,?,?,?,?,?)");
            ps.setString(1,goodId);//生成商品号
            ps.setString(2,userID);
            ps.setDouble(3,price);
            ps.setString(4,name);
            ps.setInt(5,nums);
            ps.setInt(6,isAuction);
            java.sql.Timestamp date = new java.sql.Timestamp(postDate.getTime());
            ps.setTimestamp(7,date);
            ps.setString(8,picPath);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }
    /*
   将商品写入指定用户already_post_commodity表
    */
    public  void addToAlreadyPost(String commodityID, String userID, double price, String name, int nums, int isAuction, Date postDate,String picPath) throws SQLException {
        try{
            
            ps = ct.prepareStatement("insert into already_post_commodity(id,userID,price,name,nums,isAuction,postDate,picPath)values (?,?,?,?,?,?,?,?)");
            ps.setString(1,commodityID);
            ps.setString(2,userID);
            ps.setDouble(3,price);
            ps.setString(4,name);
            ps.setInt(5,nums);
            ps.setInt(6,isAuction);
            java.sql.Timestamp date = new java.sql.Timestamp(postDate.getTime());
            ps.setTimestamp(7,date);
            ps.setString(8,picPath);
            ps.executeUpdate();
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }
    public  int getGoodsListLength() throws SQLException {
        try{
            
            ps = ct.prepareStatement("select count(id) from commodity");
            rs = ps.executeQuery();
            rs.next();
            int count = 0;
            count = rs.getInt(1);
            return count;
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }
    public List<Commodity> getGoodsList() throws SQLException{
        try{
            
            ps = ct.prepareStatement("select id,userID,name,price,nums,isAuction,postDate,picPath from commodity");
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
                String picPath = rs.getString(8);
                List<Comment> commentList = new DbHandle(new DbConnectTest().getConnection()).getCommentList(id);
                Commodity commodity = new Commodity(id,userID,price,name,nums,Auction,date,picPath,commentList);//每一条商品都是一个对象
                //commodity.setPicPath(picPath);
                commodityList.add(commodity);
            }
            return commodityList;
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }
    public  boolean register_select_isexist(String username) throws SQLException{
        try{
            
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
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }

    public  void register(String username,String password) throws SQLException{
        try{
            
            ps = ct.prepareStatement("insert into user(userID,password) values (?,?)");
            ps.setString(1,username);
            ps.setString(2,password);
            ps.executeUpdate();

        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }

    /*
    点击购买商品后，添加到order表
     */
    public  void addToOrder(String orderID,String commodityID, String buyerID, String sellerID,double price,String name,int nums, int isAuction,Date date,String picPath) throws SQLException {
        try{
            
            ps = ct.prepareStatement("insert into orders(orderID,commodityID,buyerID,sellerID,price,name,nums,isAuction,date,picPath)values (?,?,?,?,?,?,?,?,?,?)");
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
            ps.setString(10,picPath);
            ps.executeUpdate();
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }

    /*
    给出商品id，从commodity表中删除指定商品
     */
    public  void deleteGoods(String commodityID) throws SQLException {
        try{
            
            ps = ct.prepareStatement("delete from commodity where id = ?");
            ps.setString(1,commodityID);
            ps.executeUpdate();
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }
    /*
    给出商品id，从commodity表删除指定数量商品
     */
    public  void deleteGoods(String commodityID,int deleteNums) throws SQLException {
        try{
            int nowNums;
            
            ps=ct.prepareStatement("select nums from commodity where id = ?");
            ps.setString(1,commodityID);
            rs=ps.executeQuery();
            rs.next();
            nowNums = rs.getInt(1);
            System.out.println("数据库中修改之前当前商品的数量："+nowNums);
            if(nowNums-deleteNums>0){
                System.out.println(deleteNums);
                ps=ct.prepareStatement("update commodity set nums = ? where id = ?");
                ps.setInt(1,nowNums-deleteNums);
                ps.setString(2,commodityID);
                ps.executeUpdate();
                System.out.println("数据库中商品数量修改已完成");
            }else {
                deleteGoods(commodityID);
            }
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }




    /*
    查询数据库获取指定用户已发布商品列表
     */
    public  List<Commodity> getAlreadyPost(String userID)throws SQLException{
        try{
            List<Commodity> commodities = new ArrayList<Commodity>();
            
            ps=ct.prepareStatement("select * from already_post_commodity where userID = ?");
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
                String picPath = rs.getString(8);
                List<Comment> commentList = new DbHandle(new DbConnectTest().getConnection()).getCommentList(userID);
                Commodity commodity = new Commodity(id,userID,price,name,nums,isAuction,date,picPath,commentList);
                commodity.setPicPath(picPath);
                commodities.add(commodity);
            }
            return commodities;

        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }

    /*
    给userID查询与其有关买家的订单，作为买家的已购买
     */
    public  List<Order> getBuyerOrderList(String userID) throws SQLException {
        try {
            
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
            String picPath;
            //ps = ct.prepareStatement("select * from orders where buyerID = ? or sellerID = ?");
            ps = ct.prepareStatement("select * from orders where buyerID = ?");
            ps.setString(1,userID);
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
                picPath = rs.getString(10);
                Order order = new Order(orderID,commodityID,buyerID,sellerID,price,name,nums,isAuction,date,picPath);
                orderList.add(order);
            }
            return orderList;
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }

    /*
    给商品号userID，添加评论（存到comment表）
     */
    public  void addComment(String commodityID,String userID,String content,Date date) throws SQLException {
        try{
            
            ps=ct.prepareStatement("insert into comment (commodityID,content,userID,date) values (?,?,?,?)");
            ps.setString(1,commodityID);
            ps.setString(2,content);
            ps.setString(3,userID);
            java.sql.Timestamp timestamp = new Timestamp(date.getTime());
            ps.setTimestamp(4, timestamp);
            ps.executeUpdate();
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }
    /*
    给出指定商品的评论列表
     */
    public  List<Comment> getCommentList(String commodityID) throws SQLException {
        try{
            
            String content;
            String userID;
            Date date;
            List<Comment> commentList = new ArrayList<Comment>();
            ps=ct.prepareStatement("select * from comment where commodityID = ?");
            ps.setString(1,commodityID);
            rs=ps.executeQuery();
            while (rs.next()){
                commodityID = rs.getString(1);
                content = rs.getString(2);
                userID = rs.getString(3);
                date = new Date(rs.getTimestamp(4).getTime());
                Comment comment = new Comment(commodityID,content,userID,date);
                commentList.add(comment);
            }
            return commentList;
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }
    /*
    获取已经注册的用户列表
     */
    public  List<User> getAllUsers() throws SQLException{
        List<User> userList = new ArrayList<>();
        try{
            ps=ct.prepareStatement("select userID from user;");
            rs=ps.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setUserID(rs.getString(1));
                userList.add(user);
            }
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
        return userList;
    }
    /*
    在商品列表中查询指定商品图片在服务器的路径
     */
    public String getPicPathInServer(String goodId) throws SQLException{
        try{
            ps= ct.prepareStatement("select picPath from commodity where id = ?");
            ps.setString(1,goodId);
            rs = ps.executeQuery();
            rs.next();
            return rs.getString(1);
        }finally {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(ct!=null)
                ct.close();
        }
    }
}
