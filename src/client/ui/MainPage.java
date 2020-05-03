package client.ui;

import client.CScontrol;
import client.chat.model.QqClientUser;
import client.chat.tools.ClientConServerThread;
import client.chat.tools.ManageChatWindow;
import client.chat.view.ChatWindow;
import client.ui.auction.BuyAucOutpriceListPanel;
import client.ui.buyer.AlreadyBuyPanel;
import client.ui.buyer.GdsPanel;
import client.ui.component.GBackGroundPanel;
import client.ui.component.GButtonItem;
import client.ui.component.SearchField;
import client.ui.seller.AddGdsPanel;
import client.ui.seller.AlreadyPostPanel;
import client.ui.seller.statistics.CountPanel;
import client.ui.util.MyColor;
import common.chat.Message;
import common.chat.MessageType;
import common.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectOutputStream;
import java.util.List;

public class MainPage extends JFrame {
    public static int width = 1140;
    public static int height = 650;
    String currentPanel = "jp_gds";
    GBackGroundPanel jp_main;
    JPanel jp_left, jp_al_buy;
    JPanel right_panel;
    GButtonItem bt1, bt2, bt3, bt4, bt5, bt6;
    CardLayout cl;
    String clicked;
    GdsPanel gdsPanel;
    AlreadyPostPanel alreadyPostPanel;
    AlreadyBuyPanel alreadyBuyPanel;
    BuyAucOutpriceListPanel buyAucOutpriceListPanel;
    CountPanel countPanel;

    private User user;


    public static void main(String[] args) throws Exception {
        User user = new User("admin", "admin123");
        CScontrol.loginToServer(user.getUserID(), user.getPassword());
        new MainPage(user);
    }

    public MainPage(User user) throws Exception {
        this.user = user;
        this.setTitle("校园杂货店 当前用户: " + user.getUserID());
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        //启动聊天
        List<User> userList = CScontrol.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            ChatWindow cw = new ChatWindow(this.getUserId(), userList.get(i).getUserID(),this);
            ManageChatWindow.addChatWindow(this.getUserId() + " " + userList.get(i).getUserID(), cw);
        }
        QqClientUser qqClientUser = new QqClientUser();
        qqClientUser.checkUser(this.getUser());
        Message m = new Message();
        m.setMessageType(MessageType.message_get_onLineFriend);
        m.setSender(user.getUserID());
        ObjectOutputStream oos = new ObjectOutputStream(ClientConServerThread.s.getOutputStream());
        oos.writeObject(m);



        //1.商品列表
        gdsPanel = new GdsPanel(this);
        JScrollPane jsp1 = new JScrollPane(gdsPanel);
        jsp1.getVerticalScrollBar().setUnitIncrement(20);
        //2.已购买商品列表
        alreadyBuyPanel = new AlreadyBuyPanel(user, this);
        JScrollPane jsp3_alreadyBuy = new JScrollPane(alreadyBuyPanel);
        jsp3_alreadyBuy.getVerticalScrollBar().setUnitIncrement(20);
        //3.添加商品panel
        AddGdsPanel addGdsPanel = new AddGdsPanel(user);
        addGdsPanel.setOpaque(false);

        //4.此用户已发布商品panel
        alreadyPostPanel = new AlreadyPostPanel(user, this);
        JScrollPane jsp2_alreadyPost = new JScrollPane(alreadyPostPanel);
        jsp2_alreadyPost.getVerticalScrollBar().setUnitIncrement(20);

        SearchField searchField = new SearchField(gdsPanel, this);
        bt1 = new GButtonItem("全部商品", null);
        bt1.setBackground(MyColor.enter_background);
        bt1.setForeground(MyColor.enter_font);
        bt2 = new GButtonItem("已购买", null);
        bt3 = new GButtonItem("发布商品", null);
        bt4 = new GButtonItem("我已发布", null);
        bt5 = new GButtonItem("我已出价(拍卖)", null);
        bt6 = new GButtonItem("订单报表统计", null);
        bt1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bt2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bt3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bt4.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bt5.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bt6.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bt1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clicked = e.getSource().toString();
                bt1.setBackground(MyColor.enter_background);
                bt1.setForeground(MyColor.enter_font);
                bt2.setBackground(MyColor.exit_background);
                bt2.setForeground(MyColor.exit_font);
                bt3.setBackground(MyColor.exit_background);
                bt3.setForeground(MyColor.exit_font);
                bt4.setBackground(MyColor.exit_background);
                bt4.setForeground(MyColor.exit_font);
                bt5.setBackground(MyColor.exit_background);
                bt5.setForeground(MyColor.exit_font);
                bt6.setBackground(MyColor.exit_background);
                bt6.setForeground(MyColor.exit_font);
                try {
                    gdsPanel.upDate(CScontrol.getGoodsListToServer(getUserId()));//更新商品列表界面
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                showPanel("jp_gds");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (clicked == null || !clicked.equals(e.getSource().toString())) {
                    ((GButtonItem) e.getSource()).setBackground(MyColor.exit_background);
                    ((GButtonItem) e.getSource()).setForeground(MyColor.exit_font);
                }
            }
        });
        bt2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clicked = e.getSource().toString();
                bt2.setBackground(MyColor.enter_background);
                bt2.setForeground(MyColor.enter_font);
                bt1.setBackground(MyColor.exit_background);
                bt1.setForeground(MyColor.exit_font);
                bt3.setBackground(MyColor.exit_background);
                bt3.setForeground(MyColor.exit_font);
                bt4.setBackground(MyColor.exit_background);
                bt4.setForeground(MyColor.exit_font);
                bt5.setBackground(MyColor.exit_background);
                bt5.setForeground(MyColor.exit_font);
                bt6.setBackground(MyColor.exit_background);
                bt6.setForeground(MyColor.exit_font);
                try {//更新界面
                    alreadyBuyPanel.upDate();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                showPanel("jp_al_buy");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (clicked == null || !clicked.equals(e.getSource().toString())) {
                    ((GButtonItem) e.getSource()).setBackground(MyColor.exit_background);
                    ((GButtonItem) e.getSource()).setForeground(MyColor.exit_font);
                }
            }
        });
        bt3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clicked = e.getSource().toString();
                bt3.setBackground(MyColor.enter_background);
                bt3.setForeground(MyColor.enter_font);
                bt1.setBackground(MyColor.exit_background);
                bt1.setForeground(MyColor.exit_font);
                bt2.setBackground(MyColor.exit_background);
                bt2.setForeground(MyColor.exit_font);
                bt4.setBackground(MyColor.exit_background);
                bt4.setForeground(MyColor.exit_font);
                bt5.setBackground(MyColor.exit_background);
                bt5.setForeground(MyColor.exit_font);
                bt6.setBackground(MyColor.exit_background);
                bt6.setForeground(MyColor.exit_font);
                showPanel("addGdsPanel");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (clicked == null || !clicked.equals(e.getSource().toString())) {
                    ((GButtonItem) e.getSource()).setBackground(MyColor.exit_background);
                    ((GButtonItem) e.getSource()).setForeground(MyColor.exit_font);
                }
            }
        });
        bt4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clicked = e.getSource().toString();
                bt4.setBackground(MyColor.enter_background);
                bt4.setForeground(MyColor.enter_font);
                bt1.setBackground(MyColor.exit_background);
                bt1.setForeground(MyColor.exit_font);
                bt2.setBackground(MyColor.exit_background);
                bt2.setForeground(MyColor.exit_font);
                bt3.setBackground(MyColor.exit_background);
                bt3.setForeground(MyColor.exit_font);
                bt5.setBackground(MyColor.exit_background);
                bt5.setForeground(MyColor.exit_font);
                bt6.setBackground(MyColor.exit_background);
                bt6.setForeground(MyColor.exit_font);
                try {
                    alreadyPostPanel.upDate();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                showPanel("alreadyPostPanel");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (clicked == null || !clicked.equals(e.getSource().toString())) {
                    ((GButtonItem) e.getSource()).setBackground(MyColor.exit_background);
                    ((GButtonItem) e.getSource()).setForeground(MyColor.exit_font);
                }
            }
        });
        bt5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clicked = e.getSource().toString();
                bt5.setBackground(MyColor.enter_background);
                bt5.setForeground(MyColor.enter_font);
                bt1.setBackground(MyColor.exit_background);
                bt1.setForeground(MyColor.exit_font);
                bt2.setBackground(MyColor.exit_background);
                bt2.setForeground(MyColor.exit_font);
                bt3.setBackground(MyColor.exit_background);
                bt3.setForeground(MyColor.exit_font);
                bt4.setBackground(MyColor.exit_background);
                bt4.setForeground(MyColor.exit_font);
                bt6.setBackground(MyColor.exit_background);
                bt6.setForeground(MyColor.exit_font);
                try {
                    if (buyAucOutpriceListPanel == null) {
                        buyAucOutpriceListPanel = new BuyAucOutpriceListPanel(MainPage.this);
                        addPanel("buyAucOutpriceListPanel", buyAucOutpriceListPanel);
                        showPanel("buyAucOutpriceListPanel");
                    } else {
                        buyAucOutpriceListPanel.upDate();
                        showPanel("buyAucOutpriceListPanel");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (clicked == null || !clicked.equals(e.getSource().toString())) {
                    ((GButtonItem) e.getSource()).setBackground(MyColor.exit_background);
                    ((GButtonItem) e.getSource()).setForeground(MyColor.exit_font);
                }
            }
        });
        bt6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clicked = e.getSource().toString();
                bt6.setBackground(MyColor.enter_background);
                bt6.setForeground(MyColor.enter_font);
                bt1.setBackground(MyColor.exit_background);
                bt1.setForeground(MyColor.exit_font);
                bt2.setBackground(MyColor.exit_background);
                bt2.setForeground(MyColor.exit_font);
                bt3.setBackground(MyColor.exit_background);
                bt3.setForeground(MyColor.exit_font);
                bt4.setBackground(MyColor.exit_background);
                bt4.setForeground(MyColor.exit_font);
                bt5.setBackground(MyColor.exit_background);
                bt5.setForeground(MyColor.exit_font);
                try {
                    countPanel = new CountPanel(MainPage.this);
                    addPanel("countPanel", countPanel);
                    showPanel("countPanel");

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (clicked == null || !clicked.equals(e.getSource().toString())) {
                    ((GButtonItem) e.getSource()).setBackground(MyColor.exit_background);
                    ((GButtonItem) e.getSource()).setForeground(MyColor.exit_font);
                }
            }
        });


        jp_left = new JPanel(new GridLayout(10, 1));
        jp_left.setBackground(MyColor.exit_background);
        jp_left.setPreferredSize(new Dimension(width / 5, height));
        jp_left.add(searchField);
        jp_left.add(bt1);
        jp_left.add(bt5);
        jp_left.add(bt2);
        jp_left.add(bt3);
        jp_left.add(bt4);
        jp_left.add(bt6);

        cl = new CardLayout();
        right_panel = new JPanel(cl);

        right_panel.add(jsp1, "jp_gds");//商品列表
        right_panel.add(jsp3_alreadyBuy, "jp_al_buy");//已购买
        right_panel.add(addGdsPanel, "addGdsPanel");
        right_panel.add(jsp2_alreadyPost, "alreadyPostPanel");

        right_panel.setOpaque(false);
        jp_main = new GBackGroundPanel();
        jp_main.setLayout(new BorderLayout());
        jp_main.add(jp_left, BorderLayout.WEST);
        jp_main.add(right_panel, BorderLayout.CENTER);
        this.setContentPane(jp_main);


        setVisible(true);
    }

    //管理card里的panel
    public void addPanel(String name, JPanel panel) {
        this.right_panel.add(panel, name);
    }

    public void showPanel(String name) {
        this.cl.show(right_panel, name);
        currentPanel = name;
        validate();
    }

    public void deletePanel(JPanel panel) {
        this.right_panel.remove(panel);
        System.out.println(panel + " 已从rightPanel中删除");
    }

    public String getUserId() {
        return user.getUserID();
    }
    public User getUser(){
        return this.user;
    }
    public String getCurrentPanel() {
        return currentPanel;
    }


}