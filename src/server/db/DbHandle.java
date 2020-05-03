package server.db;

import com.mysql.cj.result.SqlDateValueFactory;
import common.chat.Message;
import common.chat.MessageType;
import common.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbHandle {
    Connection ct;
    PreparedStatement ps;
    ResultSet rs;

    public DbHandle(Connection ct) {
        this.ct = ct;
    }

    public boolean login_select(String username, String password) throws SQLException {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
        return false;
    }

    public void addGoods(String goodId, String userID, double price, String name, int nums, int isAuction, Date postDate, String picPath) throws SQLException {
        try {

            System.out.println("在数据库");
            ps = ct.prepareStatement("insert into commodity (id,userID,price,name,nums,isAuction,postDate,picPath) values (?,?,?,?,?,?,?,?)");
            ps.setString(1, goodId);//生成商品号
            ps.setString(2, userID);
            ps.setDouble(3, price);
            ps.setString(4, name);
            ps.setInt(5, nums);
            ps.setInt(6, isAuction);
            java.sql.Timestamp date = new java.sql.Timestamp(postDate.getTime());
            ps.setTimestamp(7, date);
            ps.setString(8, picPath);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
   将商品写入指定用户already_post_commodity表
    */
    public void addToAlreadyPost(String commodityID, String userID, double price, String name, int nums, int isAuction, Date postDate, String picPath) throws SQLException {
        try {

            ps = ct.prepareStatement("insert into already_post_commodity(id,userID,price,name,nums,isAuction,postDate,picPath)values (?,?,?,?,?,?,?,?)");
            ps.setString(1, commodityID);
            ps.setString(2, userID);
            ps.setDouble(3, price);
            ps.setString(4, name);
            ps.setInt(5, nums);
            ps.setInt(6, isAuction);
            java.sql.Timestamp date = new java.sql.Timestamp(postDate.getTime());
            ps.setTimestamp(7, date);
            ps.setString(8, picPath);
            ps.executeUpdate();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public int getGoodsListLength() throws SQLException {
        try {

            ps = ct.prepareStatement("select count(id) from commodity");
            rs = ps.executeQuery();
            rs.next();
            int count = 0;
            count = rs.getInt(1);
            return count;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public List<Commodity> getGoodsList() throws SQLException {
        try {
            ps = ct.prepareStatement("select id,userID,name,price,nums,isAuction,postDate,picPath from commodity");
            rs = ps.executeQuery();
            List<Commodity> commodityList = new ArrayList<Commodity>();
            while (rs.next()) {
                String id = rs.getString(1);
                String userID = rs.getString(2);
                String name = rs.getString(3);
                double price = rs.getDouble(4);
                int nums = rs.getInt(5);
                int Auction = rs.getInt(6);
                Date date = new Date(rs.getTimestamp(7).getTime());
                String picPath = rs.getString(8);
                List<Comment> commentList = new DbHandle(new DbConnect().getConnection()).getCommentList(id);
                Commodity commodity = new Commodity(id, userID, price, name, nums, Auction, date, picPath, commentList);//每一条商品都是一个对象
                //commodity.setPicPath(picPath);
                commodityList.add(commodity);
            }
            return commodityList;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public boolean register_select_isexist(String username) throws SQLException {
        try {

            ps = ct.prepareStatement("select userID from user");
            rs = ps.executeQuery();
            while (rs.next()) {
                String userID = rs.getString(1);
                if (userID.equals(username)) {
                    return true;
                }
            }
            return false;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public void register(String username, String password) throws SQLException {
        try {

            ps = ct.prepareStatement("insert into user(userID,password) values (?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    点击购买商品后，添加到order表
     */
    public void addToOrder(String orderID, String commodityID, String buyerID, String sellerID, double price, String name, int nums, int isAuction, Date date, String picPath) throws SQLException {
        try {

            ps = ct.prepareStatement("insert into orders(orderID,commodityID,buyerID,sellerID,price,name,nums,isAuction,date,picPath)values (?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, orderID);
            ps.setString(2, commodityID);
            ps.setString(3, buyerID);
            ps.setString(4, sellerID);
            ps.setDouble(5, price);
            ps.setString(6, name);
            ps.setInt(7, nums);
            ps.setInt(8, isAuction);
            java.sql.Timestamp date_sql = new java.sql.Timestamp(date.getTime());
            ps.setTimestamp(9, date_sql);
            ps.setString(10, picPath);
            ps.executeUpdate();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public void addToOrder(String orderID, String commodityID, String buyerID, String sellerID, double price, String name, int nums, int isAuction, Date date, String picPath, double auctionPrice) throws SQLException {
        try {

            ps = ct.prepareStatement("insert into orders(orderID,commodityID,buyerID,sellerID,price,name,nums,isAuction,date,picPath,auctionPrice)values (?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, orderID);
            ps.setString(2, commodityID);
            ps.setString(3, buyerID);
            ps.setString(4, sellerID);
            ps.setDouble(5, price);
            ps.setString(6, name);
            ps.setInt(7, nums);
            ps.setInt(8, isAuction);
            java.sql.Timestamp date_sql = new java.sql.Timestamp(date.getTime());
            ps.setTimestamp(9, date_sql);
            ps.setString(10, picPath);
            ps.setDouble(11, auctionPrice);
            ps.executeUpdate();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    给出商品id，从commodity表中删除指定商品
     */
    public void deleteGoods(String commodityID) throws SQLException {
        try {
            ps = ct.prepareStatement("delete from commodity where id = ?");
            ps.setString(1, commodityID);
            ps.executeUpdate();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    给出商品id，从commodity表删除指定数量商品
     */
    public void deleteGoods(String commodityID, int deleteNums) throws SQLException {
        try {
            int nowNums;

            ps = ct.prepareStatement("select nums from commodity where id = ?");
            ps.setString(1, commodityID);
            rs = ps.executeQuery();
            rs.next();
            nowNums = rs.getInt(1);
            System.out.println("数据库中修改之前当前商品的数量：" + nowNums);
            if (nowNums - deleteNums > 0) {
                System.out.println(deleteNums);
                ps = ct.prepareStatement("update commodity set nums = ? where id = ?");
                ps.setInt(1, nowNums - deleteNums);
                ps.setString(2, commodityID);
                ps.executeUpdate();
                System.out.println("数据库中商品数量修改已完成");
            } else {
                deleteGoods(commodityID);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    查询数据库获取指定用户已发布商品列表
     */
    public List<Commodity> getAlreadyPost(String userID) throws SQLException {
        try {
            List<Commodity> commodities = new ArrayList<Commodity>();

            ps = ct.prepareStatement("select * from already_post_commodity where userID = ?");
            ps.setString(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                double price = rs.getDouble(3);
                String name = rs.getString(4);
                int nums = rs.getInt(5);
                int isAuction = rs.getInt(6);
                java.sql.Timestamp timestamp = rs.getTimestamp(7);
                Date date = new Date(timestamp.getTime());
                String picPath = rs.getString(8);
                List<Comment> commentList = new DbHandle(new DbConnect().getConnection()).getCommentList(userID);
                Commodity commodity = new Commodity(id, userID, price, name, nums, isAuction, date, picPath, commentList);
                commodity.setIsSold(new DbHandle(new DbConnect().getConnection()).isOrderHas(id));//看看这个商品在订单里有没有
                commodity.setPicPath(picPath);
                commodities.add(commodity);
            }
            return commodities;

        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    给userID查询与其有关买家的订单，作为买家的已购买
     */
    public List<Order> getBuyerOrderList(String userID) throws SQLException {
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
            ps = ct.prepareStatement("select * from orders where buyerID = ?");
            ps.setString(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
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
                List<Comment> commentList = new DbHandle(new DbConnect().getConnection()).getCommentList(commodityID);
                Order order = new Order(orderID, commodityID, buyerID, sellerID, price, name, nums, isAuction, date, picPath, commentList);
                if (isAuction == 1) {
                    order.setAuctionPrice(rs.getDouble(11));
                }
                orderList.add(order);
            }
            return orderList;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    查询订单类，作为卖家的已发布
     */
    public List<Order> getSellerOrderList(String sellerID, String commodityID) throws SQLException {
        try {
            List<Order> orderList = new ArrayList<Order>();
            String orderID;
            String buyerID;
            double price;
            String name;
            int nums;
            int isAuction;
            Date date;
            String picPath;
            ps = ct.prepareStatement("select * from orders where sellerID = ? and commodityID = ?");
            ps.setString(1, sellerID);
            ps.setString(2, commodityID);
            rs = ps.executeQuery();
            while (rs.next()) {
                orderID = rs.getString(1);
                //commodityID = rs.getString(2);
                buyerID = rs.getString(3);
                //sellerID = rs.getString(4);
                price = rs.getDouble(5);
                name = rs.getString(6);
                nums = rs.getInt(7);
                isAuction = rs.getInt(8);
                java.sql.Timestamp timestamp = rs.getTimestamp(9);
                date = new Date(timestamp.getTime());
                picPath = rs.getString(10);
                List<Comment> commentList = new DbHandle(new DbConnect().getConnection()).getCommentList(commodityID);
                Order order = new Order(orderID, commodityID, buyerID, sellerID, price, name, nums, isAuction, date, picPath, commentList);
                if (isAuction == 1) {
                    order.setAuctionPrice(rs.getDouble(11));
                }
                orderList.add(order);
            }
            return orderList;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    给商品号userID，添加评论（存到comment表）
     */
    public void addComment(String commodityID, String userID, String content, Date date) throws SQLException {
        try {

            ps = ct.prepareStatement("insert into comment (commodityID,content,userID,date) values (?,?,?,?)");
            ps.setString(1, commodityID);
            ps.setString(2, content);
            ps.setString(3, userID);
            java.sql.Timestamp timestamp = new Timestamp(date.getTime());
            ps.setTimestamp(4, timestamp);
            ps.executeUpdate();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    给出指定商品的评论列表
     */
    public List<Comment> getCommentList(String commodityID) throws SQLException {
        try {

            String content;
            String userID;
            Date date;
            List<Comment> commentList = new ArrayList<Comment>();
            ps = ct.prepareStatement("select * from comment where commodityID = ?");
            ps.setString(1, commodityID);
            rs = ps.executeQuery();
            while (rs.next()) {
                commodityID = rs.getString(1);
                content = rs.getString(2);
                userID = rs.getString(3);
                date = new Date(rs.getTimestamp(4).getTime());
                Comment comment = new Comment(commodityID, content, userID, date);
                commentList.add(comment);
            }
            return commentList;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    获取已经注册的用户列表
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        try {
            ps = ct.prepareStatement("select userID from user;");
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getString(1));
                userList.add(user);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
        return userList;
    }

    /*
    在商品列表中查询指定商品图片在服务器的路径
     */
    public String getPicPathInServer(String goodId) throws SQLException {
        try {
            ps = ct.prepareStatement("select picPath from commodity where id = ?");
            ps.setString(1, goodId);
            rs = ps.executeQuery();
            rs.next();
            return rs.getString(1);
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    查询含有某一关键字的商品
     */
    public List<Commodity> getSpecificCommodity(String commodityName) throws SQLException {
        try {
            ps = ct.prepareStatement("select id,userID,name,price,nums,isAuction,postDate,picPath from commodity where name like ?");
            ps.setString(1, "%" + commodityName + "%");
            rs = ps.executeQuery();
            List<Commodity> commodityList = new ArrayList<Commodity>();
            while (rs.next()) {
                String id = rs.getString(1);
                String userID = rs.getString(2);
                String name = rs.getString(3);
                double price = rs.getDouble(4);
                int nums = rs.getInt(5);
                int Auction = rs.getInt(6);
                Date date = new Date(rs.getTimestamp(7).getTime());
                String picPath = rs.getString(8);
                List<Comment> commentList = new DbHandle(new DbConnect().getConnection()).getCommentList(id);
                Commodity commodity = new Commodity(id, userID, price, name, nums, Auction, date, picPath, commentList);//每一条商品都是一个对象
                commodityList.add(commodity);
            }
            return commodityList;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    查询指定商品在order类里面是否存在
     */
    public boolean isOrderHas(String commodityID) throws SQLException {
        try {
            ps = ct.prepareStatement("select count(*) from orders where commodityID = ?");
            ps.setString(1, commodityID);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
   查询指定商品的数量
    */
    public int getCommodityNums(String commodityID) throws SQLException {
        try {
            ps = ct.prepareStatement("select nums from commodity where id = ?");
            ps.setString(1, commodityID);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
  给出商品id，从已发布表删除指定数量商品
   */
    public void deleteAlreadyGoods(String commodityID) throws SQLException {
        try {
            ps = ct.prepareStatement("delete from already_post_commodity where id = ?");
            ps.setString(1, commodityID);
            ps.executeUpdate();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    向拍卖表中添加
     */
    public void addToAuction(Auction auction) throws SQLException {
        try {
            String aucID = auction.getAuctionID();
            String commodityID = auction.getCommodityID();
            double firstPrice = auction.getFirstPrice();
            String seller = auction.getSeller();
            String buyer = auction.getBuyer();
            double auctionPrice = auction.getPrice();
            java.sql.Timestamp timestamp = new Timestamp(auction.getDate().getTime());
            String picPath = auction.getPicPath();
            int isSold = auction.getIsSold();
            ps = ct.prepareStatement("insert into auction (commodityID,firstPrice,seller,buyer,auctionPrice,date,picPath,isSold,auctionID) values (?,?,?,?,?,?,?,?,?)");
            ps.setString(1, commodityID);
            ps.setDouble(2, firstPrice);
            ps.setString(3, seller);
            ps.setString(4, buyer);
            ps.setDouble(5, auctionPrice);
            ps.setTimestamp(6, timestamp);
            ps.setString(7, picPath);
            ps.setInt(8, isSold);

            ps.setString(9, aucID);
            ps.executeUpdate();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    /*
    获取拍卖表
     */
    public List<Auction> getAuctionList(String commodityID) throws SQLException {
        try {
            ps = ct.prepareStatement("select * from auction where commodityID = ?");
            ps.setString(1, commodityID);
            rs = ps.executeQuery();
            List<Auction> auctionList = new ArrayList<>();
            while (rs.next()) {
                String aucID = rs.getString(1);
                double firstPrice = rs.getDouble(3);
                String seller = rs.getString(4);
                String buyer = rs.getString(5);
                double auctionPrice = rs.getInt(6);
                Date date = new Date(rs.getTimestamp(7).getTime());
                String picPath = rs.getString(8);
                int isSold = rs.getInt(9);
                Auction auction = new Auction(aucID, commodityID, firstPrice, seller, buyer, auctionPrice, date, picPath, isSold);
                auctionList.add(auction);
            }
            return auctionList;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public double getMaxAuctionPrice(String commodityID) throws SQLException {
        try {
            ps = ct.prepareStatement("select max(auctionPrice) from auction where commodityID = ?");
            ps.setString(1, commodityID);
            rs = ps.executeQuery();
            rs.next();
            Object price = rs.getObject(1);
            if (price == null) {
                price = "0";
            } else
                price = price.toString();
            return Double.parseDouble((String) price);
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public void setIsSoldInAucTrue(String commodityID) throws SQLException {
        try {
            ps = ct.prepareStatement("update auction set isSold = ? where commodityID = ?");
            ps.setInt(1, 1);
            ps.setString(2, commodityID);
            ps.executeUpdate();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public int getNumsWithSameBuyerAndComID(String commodityID, String buyer) throws SQLException {
        try {
            ps = ct.prepareStatement(" SELECT COUNT(buyer) FROM auction where commodityID = ? and buyer = ?");
            ps.setString(1, commodityID);
            ps.setString(2, buyer);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public List<Auction> getAuctionListWithSameBuyer(String buyer) throws SQLException {
        try {
            ps = ct.prepareStatement("select * from auction where buyer = ?");
            ps.setString(1, buyer);
            rs = ps.executeQuery();
            List<Auction> auctionList = new ArrayList<>();
            while (rs.next()) {
                String aucID = rs.getString(1);
                String commodityID = rs.getString(2);
                double firstPrice = rs.getDouble(3);
                String seller = rs.getString(4);
                double auctionPrice = rs.getInt(6);
                Date date = new Date(rs.getTimestamp(7).getTime());
                String picPath = rs.getString(8);
                int isSold = rs.getInt(9);
                Auction auction = new Auction(aucID, commodityID, firstPrice, seller, buyer, auctionPrice, date, picPath, isSold);
                auctionList.add(auction);
            }
            return auctionList;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public int getNumsWithComIdInAuc(String commodityID) throws SQLException {
        try {
            ps = ct.prepareStatement("select count(*) from auction where commodityID = ?");
            ps.setString(1, commodityID);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public Commodity getComById(String commodityID) throws SQLException {
        try {
            ps = ct.prepareStatement("select * from commodity where id = ?");
            ps.setString(1, commodityID);
            rs = ps.executeQuery();
            rs.next();
            Commodity commodity = new Commodity();
            commodity.setId(rs.getString(1));
            commodity.setUserID(rs.getString(2));
            commodity.setPrice(rs.getDouble(3));
            commodity.setName(rs.getString(4));
            commodity.setNums(rs.getInt(5));
            commodity.setIsAuction(rs.getInt(6));
            commodity.setPostDate(new Date(rs.getTimestamp(7).getTime()));
            commodity.setPicPath(rs.getString(8));
            return commodity;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public Auction getFinalAuction(String commodityID) throws SQLException {
        try {
            ps = ct.prepareStatement("select * from auction where auctionPrice = (select max(auctionPrice) from auction where commodityID = ?)");
            ps.setString(1, commodityID);
            rs = ps.executeQuery();
            rs.next();
            return new Auction(rs.getString(1), rs.getString(2), rs.getDouble(3),
                    rs.getString(4), rs.getString(5), rs.getDouble(6), new Date(rs.getTimestamp(7).getTime()), rs.getString(8), rs.getInt(9));
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public List<Order> getOrdersByDate(String sellerID, int days) throws SQLException {
        try {
            ps = ct.prepareStatement("select * from orders where  TO_DAYS(NOW()) - TO_DAYS(date)<=? and sellerID = ?");
            ps.setInt(1, days);
            ps.setString(2, sellerID);
            rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while ((rs.next())) {
                Order order = new Order();
                order.setOrderID(rs.getString(1));
                order.setCommodityID(rs.getString(2));
                order.setBuyerID(rs.getString(3));
                order.setSellerID(rs.getString(4));
                order.setPrice(rs.getDouble(5));
                order.setName(rs.getString(6));
                order.setNums(rs.getInt(7));
                order.setIsAuction(rs.getInt(8));
                order.setBuyDate(new Date(rs.getTimestamp(9).getTime()));
                order.setAuctionPrice(rs.getDouble(11));
                orders.add(order);
            }
            return orders;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }

    }

    public void addToMessage(Message message) throws SQLException {
        try {
            ps = ct.prepareStatement("insert into message(sender,getter,con,date) values (?,?,?,?)");
            ps.setString(1, message.getSender());
            ps.setString(2, message.getGetter());
            ps.setString(3, message.getCon());
            ps.setTimestamp(4, new Timestamp(message.getDate().getTime()));
            ps.executeUpdate();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public List<Message> getAndDeleteMessage(String getter) throws SQLException {
        try {
            ps = ct.prepareStatement("select * from message where getter = ?");
            ps.setString(1, getter);
            rs = ps.executeQuery();
            List<Message> messageList = new ArrayList<>();
            while (rs.next()) {
                Message message = new Message();
                message.setSender(rs.getString(1));
                message.setGetter(rs.getString(2));
                message.setCon(rs.getString(3));
                message.setDate(new Date(rs.getTimestamp(4).getTime()));
                message.setMessageType(MessageType.message_comm_mes);
                messageList.add(message);
            }
            ps = ct.prepareStatement("delete from message where getter = ?");
            ps.setString(1, getter);
            ps.executeUpdate();
            return messageList;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }

    public List<User> getGetterInMessage() throws SQLException {
        try {
            ps = ct.prepareStatement("select * from message");
            rs = ps.executeQuery();
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getString(2));
                userList.add(user);
            }
            return userList;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (ct != null)
                ct.close();
        }
    }
}
