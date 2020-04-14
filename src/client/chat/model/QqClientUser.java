package client.chat.model;


import common.entity.User;

public class QqClientUser {
    public boolean checkUser(User u){
       return new QqClientConServer().sendLoginInfoToServer(u);
    }
}
