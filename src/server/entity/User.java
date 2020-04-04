package server.entity;
public class User {
    private static String userID;
    private static String password;

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }
    public User(){
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

