package common.entity;
public class User implements java.io.Serializable{
    private static final long serialVersionUID = 7453004356676632114L;
    private String userID;
    private String password;

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

