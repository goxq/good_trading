package client.entity;
public class Commodity implements java.io.Serializable {
    private int id;
    private String userID;
    private double price;
    private String name;
    private int nums;
    private Comment comment;
    private int isAuction;

    public Commodity(int id, String userID, double price, String name, int nums, Comment comment, int isAuction) {
        this.id = id;
        this.userID = userID;
        this.price = price;
        this.name = name;
        this.nums = nums;
        this.comment = comment;
        this.isAuction = isAuction;
    }
    public Commodity(int id, String userID, double price, String name, int nums, int isAuction) {
        this.id = id;
        this.userID = userID;
        this.price = price;
        this.name = name;
        this.nums = nums;
        this.isAuction = isAuction;
    }

    public Commodity() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public int getIsAuction() {
        return isAuction;
    }

    public void setIsAuction(int isAuction) {
        this.isAuction = isAuction;
    }
}
