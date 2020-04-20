package server.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnectTest {
    Connection ct = null;
    public DbConnectTest(){

    }
    public Connection getConnection(){
        try{
            //1.加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.得到链接
            ct= DriverManager.getConnection("jdbc:mysql://localhost:3306/mydesign?useUnicode = true " +
                    "& characterEncoding = utf-8&useSSL = false&serverTimezone = Asia/Shanghai","root","123");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ct;
    }
}
