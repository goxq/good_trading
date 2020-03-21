import javax.swing.*;
import java.sql.*;


public class Dbstest {
    private Connection ct;
    public Dbstest(){
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
    public Connection getConnection(){
        return this.ct;
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




