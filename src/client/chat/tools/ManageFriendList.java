/*
管理friendlist类
 */
package client.chat.tools;



import client.chat.view.FriendList;

import java.util.HashMap;

public class ManageFriendList {
    private static HashMap<String, FriendList> hm = new HashMap<>();

    public static void addFriendList(String userID,FriendList fl){
        hm.put(userID,fl);
    }

    public static FriendList getFriendList(String userID){
        return (FriendList)hm.get(userID);
    }
}
