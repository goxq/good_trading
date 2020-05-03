package client.ui.seller;

import client.tool.CScontrol;
import client.ui.MainPage;
import client.utils.CodecUtil;
import client.ui.component.GButton;
import client.ui.component.GTextField;
import client.ui.util.FontConfig;
import client.ui.util.MyColor;
import common.entity.Commodity;
import common.entity.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;

public class AddGdsPanel extends JPanel implements ActionListener {
    User user;
    int width = 4* MainPage.width/5;
    int height = MainPage.height;

    GTextField tf1;
    GTextField tf2;
    GTextField tf3;
    JLabel tip;
    JRadioButton rb1;
    JRadioButton rb2;
    public AddGdsPanel(User user){
        this.user = user;
        JPanel jptf1= new JPanel();
        JPanel jptf2= new JPanel();
        JPanel jptf3= new JPanel();
        tf1 = new GTextField("请输入商品名称及描述",50,50);
        tf2 = new GTextField("请输入商品数量",10,50);
        tf3 = new GTextField("请输入商品价格",10,50);

        tf1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(tf2.getText().equals("")){
                    tf2.setText("请输入商品数量");
                    tf2.setForeground(MyColor.WHITE);
                }
                if(tf3.getText().equals("")){
                    tf3.setText("请输入商品价格");
                    tf3.setForeground(MyColor.WHITE);
                }

            }
        });
        tf2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(tf1.getText().equals("")){
                    tf1.setText("请输入商品名称及描述");
                    tf1.setForeground(MyColor.WHITE);
                }
                if(tf3.getText().equals("")){
                    tf3.setText("请输入商品价格");
                    tf3.setForeground(MyColor.WHITE);
                }

            }
        });
        tf3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(tf1.getText().equals("")){
                    tf1.setText("请输入商品名称及描述");
                    tf1.setForeground(MyColor.WHITE);
                }
                if(tf2.getText().equals("")){
                    tf2.setText("请输入商品数量");
                    tf2.setForeground(MyColor.WHITE);
                }

            }
        });
        jptf1.add(tf1);
        jptf2.add(tf2);
        jptf3.add(tf3);

        JPanel auctionP = new JPanel();
        JLabel jl = new JLabel("请选择是否拍卖");
        jl.setForeground(MyColor.WHITE);
        jl.setFont(FontConfig.font3);
        jl.setOpaque(false);
        rb1 = new JRadioButton("不拍卖");
        rb2 = new JRadioButton("拍卖");
        rb1.setFont(FontConfig.font2);
        rb2.setFont(FontConfig.font2);
        rb1.setForeground(MyColor.WHITE);
        rb2.setForeground(MyColor.WHITE);
        ButtonGroup bGroup = new ButtonGroup();
        bGroup.add(rb1);
        bGroup.add(rb2);
        rb1.setSelected(true);
        rb1.setOpaque(false);
        rb2.setOpaque(false);

        auctionP.add(jl);
        auctionP.add(rb1);
        auctionP.add(rb2);

        JPanel jp1,jp2;

        GButton bt_add_img = new GButton("添加图片",2*width/5,50,15);
        tip = new JLabel("");
        tip.setOpaque(false);
        tip.setFont(FontConfig.font3);
        tip.setForeground(MyColor.AMBER_400);
        bt_add_img.addActionListener(this);
        bt_add_img.setActionCommand("bt_add_img");
        GButton bt_add = new GButton("发布！",3*width/5,50,20);
        bt_add.addActionListener(this);
        bt_add.setActionCommand("bt_add");
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp1.add(bt_add_img);
        jp1.add(tip);
        jp2.add(bt_add);
        this.setLayout(new GridLayout(6,1,5,5));
        this.setOpaque(false);
        jptf1.setOpaque(false);
        jptf2.setOpaque(false);
        jptf3.setOpaque(false);
        auctionP.setOpaque(false);
        jp1.setOpaque(false);
        jp2.setOpaque(false);
        this.add(jptf1);
        this.add(jptf2);
        this.add(jptf3);
        this.add(auctionP);
        this.add(jp1);
        this.add(jp2);
    }

    Commodity commodity = new Commodity();
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("bt_add_img")){
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("图像文件(jpg/gif/png)","png","jpg","jpeg","gif");
            chooser.setFileFilter(filter);
            int ret = chooser.showOpenDialog(this);
            if(ret==JFileChooser.APPROVE_OPTION){
                File file = chooser.getSelectedFile();
                commodity.setPicPath(file.getAbsolutePath());
                tip.setText("已选择："+file.getAbsolutePath());
                System.out.println("已选择："+file.getAbsolutePath());
            }else {
                tip.setText("你未选择商品图片，将使用默认图片");
                commodity.setPicPath("images/default.jpg");
                System.out.println("你未选择商品图片，将使用默认图片");
            }
        }else if(e.getActionCommand().equals("bt_add")){
            if(tf3.getText().equals("")
                    ||tf1.getText().equals("")
                    ||tf2.getText().equals("")){
                JOptionPane.showMessageDialog(this, "请填写相关信息");
            }else if(commodity.getPicPath()==null){
                int op = JOptionPane.showConfirmDialog(this,"确定不添加商品图片吗？将使用默认图片","请确认"
                        ,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(op==JOptionPane.YES_OPTION){
                    commodity.setPicPath("images/default.jpg");
                    System.out.println("你未选择商品图片，将使用默认图片");

                    commodity.setId(CodecUtil.createOrderId());
                    commodity.setUserID(user.getUserID());
                    commodity.setName(tf1.getText());
                    commodity.setPrice(Double.parseDouble(tf3.getText()));
                    commodity.setNums(Integer.parseInt(tf2.getText()));
                    commodity.setPostDate(new Date());
                    System.out.println("在添加商品："+new Date());
                    //isAuction属性已经在RadioBox的点击事件里设置
                    //comment属性还没设置
                    if(rb1.isSelected()){
                        commodity.setIsAuction(0);
                    }else
                        commodity.setIsAuction(1);
                    if(commodity.getIsAuction()==1&&(commodity.getNums()>1))
                    {
                        JOptionPane.showMessageDialog(this,"拍卖商品只能只能发布数量只能为1哦~");
                        return;
                    }
                    add(commodity);
                    commodity.setPicPath(null);//将图片路径设置为空
                    tf1.setText("");
                    tf2.setText("");
                    tf3.setText("");
                    tip.setText("");
                }else if(op==JOptionPane.NO_OPTION){

                }
            }
            else {
                commodity.setId(CodecUtil.createOrderId());
                commodity.setUserID(user.getUserID());
                commodity.setName(tf1.getText());
                commodity.setPrice(Double.parseDouble(tf3.getText()));
                commodity.setNums(Integer.parseInt(tf2.getText()));
                commodity.setPostDate(new Date());
                System.out.println("在添加商品："+new Date());
                if(rb1.isSelected()){
                    commodity.setIsAuction(0);
                }else
                    commodity.setIsAuction(1);

                if(commodity.getIsAuction()==1&&(commodity.getNums()>1))
                {
                    JOptionPane.showMessageDialog(this,"拍卖商品只能只能发布数量只能为1哦~");
                    return;
                }
                add(commodity);
                commodity.setPicPath(null);//将图片路径设置为空
                tf1.setText("请输入商品名称及描述");
                tf1.setForeground(MyColor.WHITE);
                tf2.setText("请输入商品数量");
                tf2.setForeground(MyColor.WHITE);
                tf3.setText("请输入商品价格");
                tf3.setForeground(MyColor.WHITE);
                tip.setText("");
            }
        }
    }
    public void add(Commodity commodity){

        try{
            int result = CScontrol.addGoodsToServer(user.getUserID(),commodity);
            if(result==1)
                JOptionPane.showMessageDialog(this,"添加成功！");
            else
                JOptionPane.showMessageDialog(this,"添加失败！");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
