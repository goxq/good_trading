/*
聊天界面
因为客户端要处于读取的状态，因此把它做成一个线程
 */
package client.chat.view;


import client.chat.tools.ClientConServerThread;
import common.chat.Message;
import common.chat.MessageType;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

public class ChatWindow extends JFrame implements ActionListener{
    TextArea textArea;
    JButton button;
    JPanel panel;
    JTextField textField;
    String sender;
    String getter;

    public ChatWindow(String sender, String getter){
        super(sender+"正在和"+getter+"聊天");
        this.sender = sender;
        this.getter = getter;
        setSize(400,300);

        BorderLayout bl = new BorderLayout();
        this.setLayout(bl);
        textArea = new TextArea();
        button = new JButton("发送");
        textField = new JTextField(20);
        panel = new JPanel();
        panel.add(textField);
        panel.add(button);
        this.add(textArea,BorderLayout.CENTER);
        this.add(panel,BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button.addActionListener(this);
        setVisible(true);
    }


    public static void main(String[] args) {
        new ChatWindow("admin","小明");
    }

    //写一个方法显示消息
    public void showMessage(Message m){
        String info = m.getSender()+"对"+m.getGetter()+"说："+m.getCon()+"\n";
        this.textArea.append(info);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            Message m = new Message();
            m.setSender(sender);
            m.setGetter(getter);
            m.setMessageType(MessageType.message_comm_mes);
            m.setCon(textField.getText());
            //发送给服务器
            try {
                ObjectOutputStream oos = new ObjectOutputStream(ClientConServerThread.s.getOutputStream());
                oos.writeObject(m);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

//    @Override
//    public void run() {
//        while (true){
//            try{
//                //一直处于读取信息的状态，读取不到就等待
//                ObjectInputStream ois = new ObjectInputStream(QqClientConServer.s.getInputStream());
//                Message m = (Message)ois.readObject();
//                String info = m.getSender()+"对"+m.getGetter()+"说："+m.getCon()+"\n";
//                this.textArea.append(info);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
}
