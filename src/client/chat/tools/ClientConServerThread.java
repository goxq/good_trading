package client.chat.tools;


import client.chat.view.ChatWindow;
import common.chat.Message;
import common.chat.MessageType;


import javax.swing.*;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConServerThread extends Thread {
    public static Socket s;


    public ClientConServerThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //不停的读取从服务器端发来的消息
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();
                //System.out.println("读取到从服务器发来的"+m.getSender()+"给"+m.getGetter()+"内容:"+m.getCon());

                //如果是普通消息包，就走正常的方法
                if (m.getMessageType().equals(MessageType.message_comm_mes)) {
                    //把信息显示到该显示的窗口
                    ChatWindow chatWindow = ManageChatWindow.getChatWindow(m.getGetter() + " " + m.getSender());
                    chatWindow.setVisible(true);
                    chatWindow.showMessage(m);
                } else if (m.getMessageType().equals(MessageType.message_ret_onLineFriend)) {
                    String getter = m.getGetter();
                    String[] onLineFriends = m.getCon().split(" ");//con里面是刚刚上线的人的id
                    for(int i=0;i<onLineFriends.length;i++){
                        ChatWindow chatWindow = ManageChatWindow.getChatWindow(getter + " " + onLineFriends[i]);
                        chatWindow.setTitle("我正在和"+onLineFriends[i]+"聊天   对方在线");
                        chatWindow.setIconImage(new ImageIcon("images/onLine.png").getImage());
                    }
                } else if(m.getMessageType().equals(MessageType.message_ret_offLineFriend)){
                    String getter = m.getGetter();
                    String con = m.getCon();//con里面是刚刚上线的人的id
                    ChatWindow chatWindow = ManageChatWindow.getChatWindow(getter + " " + con);
                    chatWindow.setTitle("我正在和"+con+"聊天   对方离线");
                    chatWindow.setIconImage(new ImageIcon("images/offLine.png").getImage());
                }
            }
        } catch (Exception ee) {
            System.out.println("无法连接到服务器");
        }
    }
}
