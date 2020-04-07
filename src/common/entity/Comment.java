package common.entity;

import java.util.Date;

public class Comment {
    private String content;
    private String userID;
    private Date date;

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

    public Comment(String content, String userID, Date date) {
        this.content = content;
        this.userID = userID;
        this.date = date;
    }
}
