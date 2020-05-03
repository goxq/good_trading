package client.chat.view;


import client.chat.tools.ManageChatWindow;
import common.chat.Message;
import common.entity.User;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class FriendList extends JFrame implements MouseListener {
    JPanel jphy1,jphy2,jphy3;
    JButton jphy_jb1,jphy_jb2,jphy_jb3;
    JScrollPane jsp1;
    JLabel[] jbls;

    String myUserID;
    List<User> userList;

    public FriendList(String userID, List<User> userList){
        //处理第一张卡片(显示好友列表)
        super(userID);
        this.myUserID = userID;
        jphy_jb1 = new JButton("我的好友");
        jphy1 = new JPanel(new BorderLayout());
        jphy2 = new JPanel(new GridLayout(50,1,4,4));
        jphy3 = new JPanel(new GridLayout(2,1));


        //给jphy2初始化50个好友

        jbls = new JLabel[userList.size()];
        for(int i =0;i<jbls.length;i++){
            jbls[i] = new JLabel(userList.get(i).getUserID(),new ImageIcon("images/tx.png"),JLabel.LEFT);
            jbls[i].setEnabled(false);
            if(jbls[i].getText().equals(myUserID)){
                jbls[i].setEnabled(true);
            }
            jbls[i].addMouseListener(this);
            jphy2.add(jbls[i]);
        }

        jsp1 = new JScrollPane(jphy2);

        jphy1.add(jphy_jb1,BorderLayout.NORTH);
        jphy1.add(jsp1,BorderLayout.CENTER);
        jphy1.add(jphy3,BorderLayout.SOUTH);
        this.add(jphy1,BorderLayout.CENTER);
        this.setSize(300,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getClickCount()==2){
            //得到该好友的编号
            String getter = ((JLabel)e.getSource()).getText();
//            ChatWindow cw = new ChatWindow(myUserID,getter);

            //把聊天界面加入到管理类中
//            ManageChatWindow.addChatWindow(this.myUserID+" "+getter,cw);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel j1 = (JLabel)e.getSource();
        j1.setForeground(Color.blue);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel j1 = (JLabel)e.getSource();
        j1.setForeground(Color.BLACK);
    }

    public void upDateFriendList(Message m){
        String[] onLineFriends= m.getCon().split(" ");
        for(int i=0;i<onLineFriends.length;i++){
            for(int j = 0;j<jbls.length;j++){
                if(jbls[j].getText().equals(onLineFriends[i])){
                    jbls[j].setEnabled(true);
                }
            }
        }
    }
}
