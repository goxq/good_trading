package server.chat.model;

import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThread {
    public static HashMap<String,ServerConClientThread> hm = new HashMap<String, ServerConClientThread>();
    //向hm中添加一个客户端通讯线程
    public static void addClientThread(String uid,ServerConClientThread ct){
        hm.put(uid,ct);
    }
    public static void deleteClientThread(String uid){
        hm.remove(uid);
    }
    public static ServerConClientThread getClientThread(String uid){
        return (ServerConClientThread)hm.get(uid);
    }

    //返回当前在线的人的情况
    public static String getAllOnLineUserID(){
        //使用迭代器完成
        Iterator it =hm.keySet().iterator();
        String res = "";
        while (it.hasNext()){
            res+=it.next().toString()+" ";
        }
        return res;
    }
}
