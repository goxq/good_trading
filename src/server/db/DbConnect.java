package server.db;
import common.entity.Commodity;

import java.sql.*;
import java.util.ArrayList;
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
                    "& characterEncoding = utf-8&useSSL = false&serverTimezone = GMT","root","123");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {

        }
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
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public static void addGoods(String userID, double price, String name, int nums,int isAuction) {
        try{
            connectDb();
            ps=ct.prepareStatement("insert into commodity (userID,price,name,nums,isAuction) values (?,?,?,?,?)");
            ps.setString(1,userID);
            ps.setDouble(2,price);
            ps.setString(3,name);
            ps.setInt(4,nums);
            ps.setInt(5,isAuction);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (ct != null)
                    ct.close();
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
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }
    public static List<Commodity> getGoodsList() throws SQLException{
        try{
            connectDb();
            ps = ct.prepareStatement("select id,userID,name,price,nums,isAuction from commodity");
            rs = ps.executeQuery();
            List<Commodity> commodityList = new ArrayList<Commodity>();
            while (rs.next()){
                int id = rs.getInt(1);
                String userID = rs.getString(2);
                String name = rs.getString(3);
                double price = rs.getDouble(4);
                int nums = rs.getInt(5);
                int Auction = rs.getInt(6);
                Commodity commodity = new Commodity(id,userID,price,name,nums,Auction);//每一条商品都是一个对象
                commodityList.add(commodity);
            }
            return commodityList;
        }finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
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
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
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
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

//    public static void main(String[] args) {
//        PreparedStatement ps;
//        Connection ct;
//        ResultSet rs;
//        try {
//            //1.加载驱动
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            //2.得到链接
//            ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode = true " +
//                    "& characterEncoding = utf-8&useSSL = false&serverTimezone = GMT","root","123");
//            //3.创建火箭车
//            ps=ct.prepareStatement("select * from people");
//            //4.执行【如果是增加，删除，修改 executeUpdate(),如果是查询 executeQuery()】
//            rs=ps.executeQuery();
//            //循环取出
//            while (rs.next()){
//                String name = rs.getString(1);
//                int age = rs.getInt(2);
//                String xueli = rs.getString(4);
//                System.out.println(name+" "+age+" "+xueli);
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        } finally {
//
//        }
//    }
}




