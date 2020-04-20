package common.entity;

import java.util.Date;

public class Comment implements java.io.Serializable {
    private static final long serialVersionUID = 4781120635394321472L;
    private String commodityID;
    private String content;
    private String userID;
    private Date date;

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Comment(String commodityID, String content, String userID, Date date) {
        this.commodityID = commodityID;
        this.content = content;
        this.userID = userID;
        this.date = date;
    }
}
