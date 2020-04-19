package common.entity;

import java.util.Date;
import java.util.List;

public class Commodity implements java.io.Serializable {
    private static final long serialVersionUID = 6680730582893488539L;
    private String id;
    private String userID;
    private double price;
    private String name;
    private int nums;
    private int isAuction;
    private Date postDate;//商品发布时间
    private String picPath;
    private List<Comment> commentList;//评论列表

    public Commodity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Commodity(String id, String userID, double price, String name, int nums, int isAuction, Date postDate) {
        this.id = id;
        this.userID = userID;
        this.price = price;
        this.name = name;
        this.nums = nums;
        this.isAuction = isAuction;
        this.postDate = postDate;
    }
}
