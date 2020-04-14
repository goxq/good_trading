package client.chat.model;


import client.chat.tools.ClientConServerThread;
import common.chat.Message;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class QqClientConServer {
    public Socket s;

    public boolean sendLoginInfoToServer(Object o){
        boolean b = false;
        try{
            s = new Socket("127.0.0.1",9999);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(o);

            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Message ms = (Message)ois.readObject();
            if(ms.getMessageType().equals("1")){
                ClientConServerThread ccst = new ClientConServerThread(s);
                //启动该通讯线程
                ccst.start();
                //ManageClientConServerThread.addClientConServerThread(((User)o).getUserID(),ccst);
                b = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }
}
