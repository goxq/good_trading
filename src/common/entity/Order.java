package common.entity;

import java.util.Date;

public class Order {
    private String orderID;
    private String commodityID;
    private String buyerID;
    private String sellerID;
    private double price;
    private String name;
    private int nums;
    private int isAuction;
    private Date buyDate;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public int getIsAuction() {
        return isAuction;
    }

    public void setIsAuction(int isAuction) {
        this.isAuction = isAuction;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Order(String orderID, String commodityID, String buyerID, String sellerID, double price, String name, int nums, int isAuction, Date buyDate) {
        this.orderID = orderID;
        this.commodityID = commodityID;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.price = price;
        this.name = name;
        this.nums = nums;
        this.isAuction = isAuction;
        this.buyDate = buyDate;
    }
}
