package client.ui.old;
import common.entity.Commodity;
import common.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


public class AddGoods_old extends JFrame implements ActionListener {
    JTextField jtfCommodityName;
    JTextField jtfCommodityPrice;
    JTextField jtfCommodityNums;
    JLabel jlCommodityName;
    JLabel jlCommodityPrice;
    JLabel jlCommodityNums;
    JButton button;
    JPanel panelName;
    JPanel panelPrice;
    JPanel panelNums;
    JPanel panelRadioButton;
    JPanel panelButton;
    JRadioButton rb1;
    JRadioButton rb2;
    ButtonGroup bGroup;



    User user1;
    Commodity commodity1;
    public AddGoods_old(User user3){//接受从别的页面传来的user
        super("添加商品");
        user1 = user3;
        commodity1 = new Commodity();
        setSize(300,200);
        setLocationRelativeTo(null);
        jlCommodityName = new JLabel("商品名称");
        jtfCommodityName = new JTextField(15);
        jlCommodityPrice = new JLabel("商品价格");
        jtfCommodityPrice = new JTextField(15);
        jlCommodityNums = new JLabel("商品数量");
        jtfCommodityNums = new JTextField(15);
        button = new JButton("添加");
        button.setActionCommand("addButton");
        button.addActionListener(this);
        panelName = new JPanel();
        panelPrice = new JPanel();
        panelNums = new JPanel();
        panelButton = new JPanel();

        panelName.add(jlCommodityName);
        panelName.add(jtfCommodityName);
        panelPrice.add(jlCommodityPrice);
        panelPrice.add(jtfCommodityPrice);
        panelNums.add(jlCommodityNums);
        panelNums.add(jtfCommodityNums);
        panelButton.add(button);

        rb1 = new JRadioButton("不拍卖");
        rb2 = new JRadioButton("拍卖");
        rb1.addActionListener(this);
        rb1.setActionCommand("rb1");
        rb2.addActionListener(this);
        rb2.setActionCommand("rb2");

        rb1.setSelected(true);

        bGroup = new ButtonGroup();
        bGroup.add(rb1);
        bGroup.add(rb2);

        panelRadioButton = new JPanel();
        panelRadioButton.add(rb1);
        panelRadioButton.add(rb2);

        setLayout(new GridLayout(5,1));
        add(panelName);
        add(panelPrice);
        add(panelNums);
        add(panelRadioButton);
        add(panelButton);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("addButton")){
            if(jtfCommodityPrice.getText().equals("")
                    ||jtfCommodityName.getText().equals("")
                    ||jtfCommodityNums.getText().equals("")){
                JOptionPane.showMessageDialog(this, "请填写相关信息");
            }
            else {

                commodity1.setUserID(user1.getUserID());
                commodity1.setName(jtfCommodityName.getText());
                commodity1.setPrice(Double.parseDouble(jtfCommodityPrice.getText()));
                commodity1.setNums(Integer.parseInt(jtfCommodityNums.getText()));
                commodity1.setPostDate(new Date());
                System.out.println(new Date());
                //isAuction属性已经在RadioBox的点击事件里设置
                //comment属性还没设置

                add();
                jtfCommodityPrice.setText("");
                jtfCommodityName.setText("");
                jtfCommodityNums.setText("");

            }
        }
        else if(e.getActionCommand().equals("rb1")){
            commodity1.setIsAuction(0);
        }
        else if(e.getActionCommand().equals("rb2")){
            commodity1.setIsAuction(1);
        }
    }



    public void add(){

        try{
            //int result = CScontrol.addGoodsToServer(user1.getUserID(),commodity1.getName(),commodity1.getPrice(),commodity1.getNums(),commodity1.getIsAuction(),commodity1.getPostDate());
            // if(result==1)
                JOptionPane.showMessageDialog(this,"添加成功！");
            //else
                JOptionPane.showMessageDialog(this,"添加失败！");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        User userInMainForTest = new User("admin","admin123");
        new AddGoods_old(userInMainForTest);
    }
}
