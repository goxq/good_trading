package client.ui;

import client.CScontrol;
import client.chat.model.QqClientUser;
import client.chat.tools.ClientConServerThread;
import client.chat.tools.ManageFriendList;
import client.chat.view.FriendList;
import client.ui.util.MyColor;
import common.chat.Message;
import common.chat.MessageType;
import common.entity.Commodity;
import common.entity.User;


import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectOutputStream;
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
    JButton searchButton;
    JButton refreshButton;


    static int connectionStatus = 1;//当前连接服务器状态，每隔10秒判断一次。0 代表不成功，1代表成功
    static JTable table;
    static JPanel panel;
    static Object tableContent[][];
    static Object tableName[] = {"商品名称", "价格", "商品数量", "是否拍卖"};
    static DefaultTableModel tableModel;
    User user1;
    static List<Commodity> commodityList;//商品列表
    //构造函数接受Login传来的User
    public MainPage(User user) {
        super("校园闲置交易系统 用户: " + user.getUserID() + " 已登录");
        this.user1 = user;//把全局变量user1作为user2的别名
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        //设置菜单
        bar = new JMenuBar();
        bar.setBorderPainted(false);
        bar.setBackground(MyColor.COSMO_DARK_GRAY);


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

        jMenu1.setFont(new Font("微软雅黑", Font.BOLD,14));
        jMenu2.setFont(new Font("微软雅黑", Font.BOLD,14));
        jMenu3.setFont(new Font("微软雅黑", Font.BOLD,14));
        jMenu1.setForeground(Color.white);
        jMenu2.setForeground(Color.white);
        jMenu3.setForeground(Color.white);

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

        item1Jmenu1.setActionCommand("Chat");
        item1Jmenu2.setActionCommand("MenuOfAddGoods");
        part2_item2Jmenu2.setActionCommand("alreadyPost");



        panel = new JPanel();

        panel.setBackground(MyColor.COSMO_MEDIUM_GRAY);
        panel.setBounds(10, 10, 12, 20);

        SearchTextField = new JTextField("请输入要查询的商品名称");
        searchButton = new JButton("查询");
        refreshButton = new JButton("刷新");


        refreshButton.setBackground(Color.lightGray);
        refreshButton.setFont(new Font("黑体", Font.BOLD,17));
        refreshButton.setForeground(Color.white);
        refreshButton.setBorderPainted(false);


        refreshButton.addActionListener(this);
        refreshButton.setActionCommand("RefreshButton");


        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panel.add(SearchTextField);
        panel.add(searchButton);
        panel.add(refreshButton);
        add(panel, BorderLayout.NORTH);

        TableRefreshThread trt = new TableRefreshThread();
        Thread t = new Thread(trt);
        //开线程刷新界面
        t.start();
        table = new GoodsTable(getTableModel());
        //添加双击事件
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount()==2){
                    int row = ((JTable)e.getSource()).rowAtPoint(e.getPoint());//获得行位置
                    Commodity commodity = commodityList.get(row);
                    /*
                    打开商品详情界面
                     */

                }
            }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
        //validate();
        setVisible(true);

    }

    public static DefaultTableModel getTableModel() {

        try {
            commodityList = CScontrol.getGoodsListToServer();
            tableContent = new Object[commodityList.size()][4];
            for (int i = 0; i < commodityList.size(); i++) {
                tableContent[i][0] = commodityList.get(i).getName();
                tableContent[i][1] = commodityList.get(i).getPrice();
                tableContent[i][2] = commodityList.get(i).getNums();
                if (commodityList.get(i).getIsAuction() == 1)
                    tableContent[i][3] = "拍卖";
                else
                    tableContent[i][3] = "不拍卖";
            }
            //能进来说明连接正常。。
            connectionStatus=1;
            //这里判断是否能连接到服务器
        } catch (Exception exc) {
            System.out.println("无法连接到服务器!");
            connectionStatus=0;
        }
        tableModel = new DefaultTableModel(tableContent,tableName);
        return tableModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String searchStr = SearchTextField.getText();
            //TODO:搜索
        } else if (e.getActionCommand().equals("RefreshButton")) {
            table.setModel(getTableModel());
        } else if (e.getActionCommand().equals("MenuOfAddGoods")) {
            System.out.println("点了一下");
            new AddGoods(user1);
        }else if(e.getActionCommand().equals("Chat")){
            QqClientUser qqClientUser = new QqClientUser();
            if(qqClientUser.checkUser(user1)){
                List<User> userList;
                try {
                    //获取全部好友
                    userList = CScontrol.getAllUsers();
                    FriendList fl = new FriendList(user1.getUserID(),userList);
                    ManageFriendList.addFriendList(user1.getUserID(),fl);
                    //登录成功后要求服务器返回一个在线的包
                    ObjectOutputStream oos = new ObjectOutputStream(ClientConServerThread.s.getOutputStream());
                    //做一个message
                    Message m = new Message();
                    m.setMessageType(MessageType.message_get_onLineFriend);
                    //指明我要这个号的在线好友列表
                    m.setSender(user1.getUserID());
                    oos.writeObject(m);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }else {
                JOptionPane.showMessageDialog(this,"用户名或密码错误");

            }
        }
        //TODO:写各个菜单的点击事件
    }

    public static void main(String[] args) {
        User userInMainForTest = new User("admin", "admin123");
        new MainPage(userInMainForTest);
    }
}

class GoodsTable extends JTable{

    public GoodsTable(TableModel dm) {
        super(dm);
        setRowHeight(30);
        setFont(new Font("微软雅黑",Font.PLAIN,14));
        setSelectionBackground(Color.lightGray);
        JTableHeader header = this.getTableHeader();
        header.setFont(new Font("微软雅黑", Font.BOLD,15));
        header.setResizingAllowed(false);
        header.setReorderingAllowed(false);
        header.setBackground(MyColor.BLUE_100);
        this.setBackground(MyColor.BLUE_50);
    }

    public GoodsTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
        setRowHeight(30);
        setFont(new Font("微软雅黑",Font.PLAIN,14));
        setSelectionBackground(Color.lightGray);
        JTableHeader header = this.getTableHeader();
        header.setFont(new Font("微软雅黑", Font.BOLD,15));
        header.setResizingAllowed(false);
        header.setReorderingAllowed(false);
        header.setBackground(MyColor.BLUE_100);
        this.setBackground(MyColor.BLUE_50);

    }

    @Override
    public boolean isCellEditable(int row, int column) {                // 表格不可编辑
        return false;
    }
}


