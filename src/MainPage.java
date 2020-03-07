import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainPage extends JFrame implements ActionListener {
    JTextField SearchTextField;
    JButton SearchButton;

    public MainPage(String username){
        super("校园闲置物品交易 用户: "+username+" 已登录");//ghghgh
        setSize(800,500);
        setLocationRelativeTo(null);
        setMenus();
        getTable();

        JPanel panel = new JPanel();
        //panel.setBackground(Color.blue);
        panel.setBounds(10,10,12,20);
        SearchTextField = new JTextField("输入要购买的商品名称来查询");
        SearchButton = new JButton("查询");
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
        panel.add(SearchTextField);
        panel.add(SearchButton);
        add(panel,BorderLayout.NORTH);

        setVisible(true);
    }

    JMenuItem item1Jmenu1 = new JMenuItem("打开聊天窗口");
    JMenuItem item1Jmenu2 = new JMenuItem("发布商品");
    JMenuItem part1_item2Jmenu2 = new JMenuItem("销售情况统计");
    JMenuItem part2_item2Jmenu2 = new JMenuItem("查看已发布的商品");
    JMenuItem item1Jmenu3 = new JMenuItem("查看购物车");
    JMenuItem item2Jmenu3 = new JMenuItem("查看已购买列表");
    public void setMenus(){
        JMenuBar bar = new JMenuBar();
        JMenu jMenu1= new JMenu("通用");
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
    }

    JTable table;
    Object tableContent[][];
    Object name[]={"商品名称","价格","是否为拍卖"};
    public void getTable(){
        //（假的
        tableContent = new Object[50][3];
        for(int i = 0;i<50;i++){
           tableContent[i][0]="胡萝卜";
           tableContent[i][1]="3";
           tableContent[i][2]="是";
        }
        table = new JTable(tableContent,name);
        table.setRowHeight(30);
        add(new JScrollPane(table),BorderLayout.CENTER);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
        if(e.getSource()==SearchButton){
            String searchStr = SearchTextField.getText();
            //TODO:搜索
        }
        //TODO:写各个菜单的点击事件
    }
}
