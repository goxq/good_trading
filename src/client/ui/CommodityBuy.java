package client.ui;


import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import common.entity.Comment;
import common.entity.User;

public class CommodityBuy extends JFrame implements ActionListener{
    JLabel name;
    JLabel price;
    JLabel cName;
    JTextField tfPrice;
    JButton buy;
    JButton addCar;
    JButton comment;
    JButton images;
    JPanel namePanel;
    JPanel pricePanel;
    JPanel b1;
    JPanel b2;

    User user1;
    public CommodityBuy() {
        super("商品详情");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(4,1));

        name = new JLabel("商品名称：");
//		tfName = new JTextField(15);
        price = new JLabel("价格：");
        tfPrice = new JTextField(15);
        images = new JButton("查看图片");
        comment = new JButton("查看评论");
        buy = new JButton("购买");
        addCar = new JButton("加入购物车");
        buy.addActionListener(this);
        addCar.addActionListener(this);
        comment.addActionListener(this);
        images.addActionListener(this);

        namePanel = new JPanel();
        pricePanel = new JPanel();
        b1 = new JPanel();
        b2 = new JPanel();
        namePanel.add(name);
//		namePanel.add(tfName);
        pricePanel.add(price);
        pricePanel.add(tfPrice);
        b1.add(images);
        b1.add(comment);
        b2.add(buy);
        b2.add(addCar);

        add(namePanel);
        add(pricePanel);
        add(b1);
        add(b2);


        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }




    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==buy) {
            JOptionPane.showMessageDialog(this, "购买成功，卖家会联系您");
        }
        if(e.getSource()==addCar) {
            JOptionPane.showMessageDialog(this, "加车成功，您可以在购物车中查看");
        }
        if(e.getSource()==comment) {
            //new Comment();
        }
        if(e.getSource()==images) {
            //new CommodityImages();
        }
    }
    public static void main(String[] args) {
        CommodityBuy buy=new CommodityBuy();
    }

}

