package client.ui;

import client.CScontrol;
import common.entity.Commodity;
import common.entity.User;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


class MainPage extends JFrame implements ActionListener {
    JMenuBar bar;
    JMenuItem item1Jmenu1;
    JMenuItem item1Jmenu2;
    JMenuItem part1_item2Jmenu2;
    JMenuItem part2_item2Jmenu2;
    JMenuItem item1Jmenu3;
    JMenuItem item2Jmenu3;
    JTextField SearchTextField;
    JButton SearchButton;
    JButton RefreshButton;
    JTable table;
    JPanel panel;
    Object tableContent[][];
    Object tableName[] = {"商品名称", "价格", "商品数量", "是否拍卖"};
    int count;//用来每次刷新时记录表格的行数
    User user1;
    //构造函数接受Login传来的User
    public MainPage(User user2) {
        super("校园闲置交易系统 用户: " + user2.getUserID() + " 已登录");
        user1 = user2;//把全局变量user1作为user2的别名

        setSize(800, 500);
        setLocationRelativeTo(null);

        //设置菜单
        bar = new JMenuBar();
        item1Jmenu1 = new JMenuItem("打开聊天窗口");
        item1Jmenu2 = new JMenuItem("发布商品");
        part1_item2Jmenu2 = new JMenuItem("销售情况统计");
        part2_item2Jmenu2 = new JMenuItem("查看已发布的商品");
        item1Jmenu3 = new JMenuItem("查看购物车");
        item2Jmenu3 = new JMenuItem("查看已购买列表");

        JMenu jMenu1 = new JMenu("通用");
        JMenu jMenu2 = new JMenu("我是卖家");
        JMenu item2Jmenu2 = new JMenu("售出情况");
        item2Jmenu2.add(part1_item2Jmenu2);
        item2Jmenu2.add(part2_item2Jmenu2);
        JMenu jMenu3 = new JMenu("我是买家");
        jMenu1.add(item1Jmenu1);
        jMenu2.add(item1Jmenu2);
        jMenu2.add(item2Jmenu2);
        jMenu3.add(item1Jmenu3);
        jMenu3.add(item2Jmenu3);
        //添加菜单到菜单条
        bar.add(jMenu1);
        bar.add(jMenu2);
        bar.add(jMenu3);
        setJMenuBar(bar);
        //给各个菜单添加点击事件
        item1Jmenu1.addActionListener(this);
        item1Jmenu2.addActionListener(this);
        item1Jmenu3.addActionListener(this);
        item2Jmenu3.addActionListener(this);
        part1_item2Jmenu2.addActionListener(this);
        part2_item2Jmenu2.addActionListener(this);

        item1Jmenu2.setActionCommand("MenuOfAddGoods");


        panel = new JPanel();
        //panel.setBackground(Color.blue);
        panel.setBounds(10, 10, 12, 20);

        SearchTextField = new JTextField("请输入要查询的商品名称");
        SearchButton = new JButton("查询");
        RefreshButton = new JButton("刷新");

        RefreshButton.addActionListener(this);
        RefreshButton.setActionCommand("RefreshButton");


        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panel.add(SearchTextField);
        panel.add(SearchButton);
        panel.add(RefreshButton);
        add(panel, BorderLayout.NORTH);

        getTable();
        setVisible(true);
    }

    public void getTable() {

        try {
            count = CScontrol.getGoodsListLenToServer();
            tableContent = new Object[count][4];

            List<Commodity> list = CScontrol.getGoodsListToServer();
            for (int i = 0; i < list.size(); i++) {
                tableContent[i][0] = list.get(i).getName();
                tableContent[i][1] = list.get(i).getPrice();
                tableContent[i][2] = list.get(i).getNums();
                if (list.get(i).getIsAuction() == 1)
                    tableContent[i][3] = "拍卖";
                else
                    tableContent[i][3] = "不拍卖";
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        table = new JTable(tableContent, tableName);
        table.setRowHeight(30);
        getContentPane().removeAll();
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);
        setJMenuBar(bar);
        validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SearchButton) {
            String searchStr = SearchTextField.getText();
            //TODO:搜索
        } else if (e.getActionCommand().equals("RefreshButton")) {
            getTable();
        } else if (e.getActionCommand().equals("MenuOfAddGoods")) {
            System.out.println("点了一下");
            new AddGoods(user1);
        }
        //TODO:写各个菜单的点击事件
    }

    public static void main(String[] args) {
        User userInMainForTest = new User("admin", "admin123");
        new MainPage(userInMainForTest);
    }
}


