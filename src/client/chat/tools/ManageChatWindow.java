/*
管理用户聊天界面的类
 */
package client.chat.tools;

import client.chat.view.ChatWindow;

import java.util.HashMap;

public class ManageChatWindow {
    private static HashMap<String, ChatWindow> hm = new HashMap<String,ChatWindow>();

    public static void addChatWindow(String loginIdAndFriendID, ChatWindow cw){
        hm.put(loginIdAndFriendID,cw);
    }
    public static ChatWindow getChatWindow(String loginIdAndFriendID){
        return (ChatWindow)hm.get(loginIdAndFriendID);
    }
}
