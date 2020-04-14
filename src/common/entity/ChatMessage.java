package common.entity;

import java.util.Date;

public class ChatMessage implements java.io.Serializable{
    private String sender;
    private String getter;
    private String content;
    private Date date;

    public ChatMessage(String sender, String getter, String content) {
        this.sender = sender;
        this.getter = getter;
        this.content = content;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ChatMessage(String sender, String getter, String content, Date date) {
        this.sender = sender;
        this.getter = getter;
        this.content = content;
        this.date = date;
    }
}
