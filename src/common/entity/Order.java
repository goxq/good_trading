package common.entity;

import java.util.Date;

public class Order {
    private Commodity commodity;
    private Date buyDate;
    private String buyerID;
    private String sellerID;


    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
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

    public Order(Commodity commodity, Date buyDate, String buyerID, String sellerID) {
        this.commodity = commodity;
        this.buyDate = buyDate;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
    }
}
