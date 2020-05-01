package common.entity;

import java.util.Date;

public class Auction implements java.io.Serializable {
    private static final long serialVersionUID = 5582121421832093334L;
    String auctionID;
    String commodityID;
    double firstPrice;
    String seller;
    String buyer;
    double price;
    Date date;
    String picPath;
    int isSold;

    public String getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(String auctionID) {
        this.auctionID = auctionID;
    }

    public Auction(String auctionID, String commodityID, double firstPrice, String seller, String buyer, double price, Date date, int isSold) {
        this.auctionID = auctionID;
        this.commodityID = commodityID;
        this.firstPrice = firstPrice;
        this.seller = seller;
        this.buyer = buyer;
        this.price = price;
        this.date = date;
        this.isSold = isSold;

    }

    public Auction() {
    }

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public double getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(double firstPrice) {
        this.firstPrice = firstPrice;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getIsSold() {
        return isSold;
    }

    public void setIsSold(int isSold) {
        this.isSold = isSold;
    }

    public Auction(String auctionID,String commodityID, double firstPrice, String seller, String buyer, double price, Date date, String picPath, int isSold) {
        this.auctionID = auctionID;
        this.commodityID = commodityID;
        this.firstPrice = firstPrice;
        this.seller = seller;
        this.buyer = buyer;
        this.price = price;
        this.date = date;
        this.picPath = picPath;
        this.isSold= isSold;

    }
}
