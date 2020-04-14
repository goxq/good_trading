package client.chat.tools;


import client.chat.view.ChatWindow;
import client.chat.view.FriendList;
import common.chat.Message;
import common.chat.MessageType;


import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConServerThread extends Thread {
    public static Socket s;


    public ClientConServerThread(Socket s){
        this.s = s;
    }

    @Override
    public void run() {
        while (true){
            //不停的读取从服务器端发来的消息
            try{
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message)ois.readObject();
                //System.out.println("读取到从服务器发来的"+m.getSender()+"给"+m.getGetter()+"内容:"+m.getCon());

                //如果是普通消息包，就走正常的方法
                if(m.getMessageType().equals(MessageType.message_comm_mes)){
                    //把信息显示到该显示的窗口
                    ChatWindow chatWindow = ManageChatWindow.getChatWindow(m.getGetter()+" "+m.getSender());
                    chatWindow.showMessage(m);
                }else if(m.getMessageType().equals(MessageType.message_ret_onLineFriend)){
                    String getter = m.getGetter();

                    System.out.println(getter);
                    //修改相应的好友列表
                    FriendList fl = ManageFriendList.getFriendList(getter);
                    //更新好友列表
                    if(fl!=null){
                        fl.upDateFriendList(m);
                    }
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
