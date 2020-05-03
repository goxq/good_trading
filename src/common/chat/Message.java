package common.chat;

import java.util.Date;

public class Message implements java.io.Serializable{
    private static final long serialVersionUID = 6565103272548716698L;
    private String messageType;
    private String sender;
    private String getter;
    private String con;
    private Date date;

    @Override
    public String toString() {
        return "Message{" +
                "messageType='" + messageType + '\'' +
                ", sender='" + sender + '\'' +
                ", getter='" + getter + '\'' +
                ", con='" + con + '\'' +
                ", date=" + date +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
