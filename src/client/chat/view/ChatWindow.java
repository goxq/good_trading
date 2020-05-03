/*
聊天界面
因为客户端要处于读取的状态，因此把它做成一个线程
 */
package client.chat.view;


import client.chat.tools.ClientConServerThread;
import client.ui.MainPage;
import client.ui.component.GButton;
import client.ui.user.Login;
import client.ui.util.FontConfig;
import client.ui.util.MyColor;
import common.chat.Message;
import common.chat.MessageType;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ChatWindow extends JFrame implements ActionListener{
    TextArea textArea;
    GButton button;
    JPanel panel;
    JTextField textField;
    String sender;
    String getter;
    MainPage mainPage;
    public ChatWindow(String sender, String getter, MainPage mainPage){
        super("我正在和"+getter+"聊天   对方离线");
        this.sender = sender;
        this.getter = getter;
        this.mainPage = mainPage;
        this.setIconImage(new ImageIcon("images/offLine.png").getImage());
        setSize(500,300);

        BorderLayout bl = new BorderLayout();
        this.setLayout(bl);
        textArea = new TextArea();
        textArea.setEditable(false);
        button = new GButton("发送");
        textField = new JTextField(20);
        textField.setFont(FontConfig.font3);
        textField.setPreferredSize(new Dimension(40,30));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Message m = new Message();
                    m.setSender(sender);
                    m.setGetter(getter);
                    m.setMessageType(MessageType.message_comm_mes);
                    m.setCon(textField.getText());
                    m.setDate(new Date());
                    ChatWindow.this.textArea.setFont(FontConfig.font3);
                    ChatWindow.this.textArea.setForeground(MyColor.BLACK);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    ChatWindow.this.textArea.append(formatter.format(m.getDate())+"   我对"+getter+"说："+m.getCon()+"\n");
                    textField.setText("");
                    //发送给服务器
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(ClientConServerThread.s.getOutputStream());
                        oos.writeObject(m);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
        panel = new JPanel();
        panel.add(textField);
        panel.add(button);
        this.add(textArea,BorderLayout.CENTER);
        this.add(panel,BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button.addActionListener(this);
        this.setLocationRelativeTo(mainPage);
        setVisible(false);
    }
    //写一个方法显示消息
    public void showMessage(Message m){
        this.textArea.setFont(FontConfig.font3);
        this.textArea.setForeground(MyColor.BLUE_700);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String info =formatter.format(m.getDate())+"   "+ m.getSender()+"对我说："+m.getCon()+"\n";
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
            m.setDate(new Date());
            this.textArea.setFont(FontConfig.font3);
            this.textArea.setForeground(MyColor.BLACK);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.textArea.append(formatter.format(m.getDate())+"   我对"+getter+"说："+m.getCon()+"\n");
            textField.setText("");
            //发送给服务器
            try {
                ObjectOutputStream oos = new ObjectOutputStream(ClientConServerThread.s.getOutputStream());
                oos.writeObject(m);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
