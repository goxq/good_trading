package client.ui;

import client.CScontrol;
import client.ui.component.GBackGroundPanel;
import client.ui.component.GButtonItem;
import client.ui.util.MyColor;
import common.entity.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPage extends JFrame {
    public static int width = 1140;
    public static int height = 650;
    GBackGroundPanel jp_main;
    JPanel  jp_left, jp_al_buy;
    JPanel right_panel;
    GButtonItem bt1, bt2, bt3,bt4;
    CardLayout cl;
    String clicked;
    GdsPanel gdsPanel;
    AlreadyPostPanel alreadyPostPanel;
    AlreadyBuyPanel alreadyBuyPanel;

    private User user;


    public static void main(String[] args) throws Exception {
        User user = new User("admin", "admin123");
        CScontrol.loginToServer(user.getUserID(),user.getPassword());
        new MainPage(user);
    }

    public MainPage(User user) throws Exception {
        this.user = user;
        this.setTitle("校园杂货店 当前用户: "+user.getUserID());
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        bt1 = new GButtonItem("全部商品", null);
        bt2 = new GButtonItem("已购买", null);
        bt3 = new GButtonItem("发布商品", null);
        bt4 = new GButtonItem("我已发布",null);

        bt1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bt2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bt3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bt4.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        bt1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clicked = e.getSource().toString();
                bt1.setBackground(MyColor.exit_background);
                bt1.setForeground(MyColor.exit_font);
                bt2.setBackground(MyColor.exit_background);
                bt2.setForeground(MyColor.exit_font);
                bt3.setBackground(MyColor.exit_background);
                bt3.setForeground(MyColor.exit_font);
                ((GButtonItem) e.getSource()).setForeground(MyColor.enter_font);
                ((GButtonItem) e.getSource()).setBackground(MyColor.enter_background);
                try {
                    gdsPanel.upDate();//更新商品列表界面
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                cl.show(right_panel, "jp_gds");
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
                bt1.setBackground(MyColor.exit_background);
                bt1.setForeground(MyColor.exit_font);
                bt2.setBackground(MyColor.exit_background);
                bt2.setForeground(MyColor.exit_font);
                bt3.setBackground(MyColor.exit_background);
                bt3.setForeground(MyColor.exit_font);
                ((GButtonItem) e.getSource()).setForeground(MyColor.enter_font);
                ((GButtonItem) e.getSource()).setBackground(MyColor.enter_background);
                cl.show(right_panel, "jp_al_buy");
                try {//更新界面
                    alreadyBuyPanel.upDate();
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
        bt3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clicked = e.getSource().toString();
                bt1.setBackground(MyColor.exit_background);
                bt1.setForeground(MyColor.exit_font);
                bt2.setBackground(MyColor.exit_background);
                bt2.setForeground(MyColor.exit_font);
                bt3.setBackground(MyColor.exit_background);
                bt3.setForeground(MyColor.exit_font);
                ((GButtonItem) e.getSource()).setForeground(MyColor.enter_font);
                ((GButtonItem) e.getSource()).setBackground(MyColor.enter_background);
                cl.show(right_panel, "addGdsPanel");
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
                bt1.setBackground(MyColor.exit_background);
                bt1.setForeground(MyColor.exit_font);
                bt2.setBackground(MyColor.exit_background);
                bt2.setForeground(MyColor.exit_font);
                bt3.setBackground(MyColor.exit_background);
                bt3.setForeground(MyColor.exit_font);
                ((GButtonItem) e.getSource()).setForeground(MyColor.enter_font);
                ((GButtonItem) e.getSource()).setBackground(MyColor.enter_background);
                cl.show(right_panel, "alreadyPostPanel");
                try {
                    alreadyPostPanel.upDate();
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
        jp_left.add(bt1);
        jp_left.add(bt2);
        jp_left.add(bt3);
        jp_left.add(bt4);



        //1.商品列表
        gdsPanel = new GdsPanel(this);
        JScrollPane jsp1 = new JScrollPane(gdsPanel);

        //2.已购买商品列表
        alreadyBuyPanel = new AlreadyBuyPanel(user,this);
        JScrollPane jsp3_alreadyBuy = new JScrollPane(alreadyBuyPanel);

        //3.添加商品panel
        AddGdsPanel addGdsPanel = new AddGdsPanel(user);
        addGdsPanel.setOpaque(false);

        //4.此用户已发布商品panel
        alreadyPostPanel = new AlreadyPostPanel(user);
        JScrollPane jsp2_alreadyPost = new JScrollPane(alreadyPostPanel);

        cl = new CardLayout();
        right_panel = new JPanel(cl);

        right_panel.add(jsp1, "jp_gds");//商品列表
        right_panel.add(jsp3_alreadyBuy, "jp_al_buy");//已购买
        right_panel.add(addGdsPanel, "addGdsPanel");
        right_panel.add(jsp2_alreadyPost,"alreadyPostPanel");

        right_panel.setOpaque(false);
        jp_main = new GBackGroundPanel();
        jp_main.setLayout(new BorderLayout());
        jp_main.add(jp_left, BorderLayout.WEST);
        jp_main.add(right_panel, BorderLayout.CENTER);
        this.setContentPane(jp_main);

        setVisible(true);
    }
    //管理card里的panel
    public void addPanel(String name, JPanel panel){
        this.right_panel.add(panel,name);
    }
    public void showPanel(String name){
        this.cl.show(right_panel,name);
        repaint();
    }
    public void deletePanel(JPanel panel){
        this.right_panel.remove(panel);
        System.out.println(panel+" 已从rightPanel中删除");
    }
    public String getUserId(){
        return user.getUserID();
    }

}