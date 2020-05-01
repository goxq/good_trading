package client.ui;

import client.CScontrol;
import client.ui.component.GGoodsLabel;
import client.ui.component.GListBackPanel;
import client.ui.util.FontConfig;
import common.entity.Order;
import common.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class AlreadyBuyPanel extends JPanel {
    User user;
    List<Order> orderList;
    MainPage mainPage;

    public AlreadyBuyPanel(User user,MainPage mainPage) throws Exception {
        this.user = user;
        this.mainPage =mainPage;
        this.setOpaque(false);
//        orderList = CScontrol.getBuyerOrderList(user.getUserID());
//        this.setLayout(new GridLayout(orderList.size(), 1, 0, 5));
        upDate();
    }

//    public void showOrders() {
//        int length = orderList.size();
//        if (length == 0) {
//            this.setLayout(new BorderLayout());
//            JLabel txt = new JLabel("uhhh，你还没有购买商品鸭！");
//            txt.setFont(FontConfig.font1);
//            this.add(txt, BorderLayout.CENTER);
//        } else {
//            for (int i = 0; i < orderList.size(); i++) {
//                Order order = orderList.get(i);
//                String auction = (order.getIsAuction() == 1) ? "拍卖" : "直售";
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String dateString = formatter.format(order.getBuyDate());
//
//                String text = "<html>\n" +
//                        "<body>\n" +
//                        "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">" + order.getName() +
//                        "</p>" +
//                        "<p>" +
//                        "<span style=\"font-family:微软雅黑;font-size:28px;color:white;font-weight:400;\">价格：</span>\n" +
//                        "<span style=\"font-family:微软雅黑;font-size:30px;color:white;font-weight:700;\">" + order.getPrice() + "</span>\n" +
//                        "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp数量:" + order.getNums() + "</span>\n" +
//                        "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + auction + "</span>\n" +
//                        "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>" + dateString + "</span>\n" +
//                        "</p>" +
//                        "</body>" +
//                        "</html>";
//                ImageIcon ii = new ImageIcon(order.getPicPath());
//                System.out.println(order.getPicPath());
//                ii.setImage(ii.getImage().getScaledInstance(MainPage.height / 4 - 30, MainPage.height / 4 - 30, Image.SCALE_DEFAULT));
//                GGoodsLabel goodsLabel = new GGoodsLabel(text, ii, JLabel.LEFT);
//                //goodsLabel.setPreferredSize(new Dimension(3 * this.getWidth() / 5, 2 * height / 7));
//                GListBackPanel listBackPanel = new GListBackPanel(new FlowLayout(FlowLayout.LEFT));
//                listBackPanel.add(goodsLabel);
//                listBackPanel.setOpaque(false);
//                this.add(listBackPanel);
//                goodsLabel.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mouseClicked(MouseEvent e) {
//                        super.mouseClicked(e);
//                        OrderPanel op = new OrderPanel(order,mainPage);
//                        mainPage.addPanel(order.getOrderID(),op);
//                        mainPage.showPanel(order.getOrderID());
//                    }
//                });
//            }
//        }
//    }

    public void upDate() throws Exception {
        this.removeAll();
        orderList = CScontrol.getBuyerOrderList(user.getUserID());
        int length = orderList.size();
        if (length == 0) {
            this.setLayout(new BorderLayout());
            JLabel txt = new JLabel("uhhh，你还没有购买商品鸭！",JLabel.CENTER);
            txt.setFont(FontConfig.font1);
            this.add(txt, BorderLayout.CENTER);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.setLayout(new GridLayout(length, 1, 0, 5));
            for (int i = 0; i < orderList.size(); i++) {
                Order order = orderList.get(i);
                String dateString = formatter.format(order.getBuyDate());
                String text;
                if(order.getIsAuction()==1){
                    text = "<html>\n" +
                            "<body>\n" +
                            "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">" + order.getName() +
                            "</p>" +
                            "<p>" +
                            "<span style=\"font-family:微软雅黑;font-size:28px;color:white;font-weight:400;\">成交价:</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:30px;color:white;font-weight:700;\">" + order.getAuctionPrice() + "</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp数量:" + order.getNums() + "</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp拍卖 原价格:" + order.getPrice() + "</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>购买完成时间:&nbsp&nbsp " + dateString + "</span>\n" +
                            "</p>" +
                            "</body>" +
                            "</html>";
                }else {
                    text = "<html>\n" +
                            "<body>\n" +
                            "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">" + order.getName() +
                            "</p>" +
                            "<p>" +
                            "<span style=\"font-family:微软雅黑;font-size:28px;color:white;font-weight:400;\">价格：</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:30px;color:white;font-weight:700;\">" + order.getPrice() + "</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp数量:" + order.getNums() + "</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + "直售" + "</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>购买完成时间:&nbsp&nbsp " + dateString + "</span>\n" +
                            "</p>" +
                            "</body>" +
                            "</html>";
                }


                ImageIcon ii = new ImageIcon(order.getPicPath());
                ii.setImage(ii.getImage().getScaledInstance(MainPage.height / 4 - 30, MainPage.height / 4 - 30, Image.SCALE_DEFAULT));
                GGoodsLabel goodsLabel = new GGoodsLabel(text, ii, JLabel.LEFT);
                //goodsLabel.setPreferredSize(new Dimension(3 * this.getWidth() / 5, 2 * height / 7));
                GListBackPanel listBackPanel = new GListBackPanel(new FlowLayout(FlowLayout.LEFT));
                listBackPanel.add(goodsLabel);
                listBackPanel.setOpaque(false);
                this.add(listBackPanel);
                goodsLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        BuyerSingleOrderPanel op = new BuyerSingleOrderPanel(order,mainPage);
                        mainPage.addPanel(order.getOrderID(),op);
                        mainPage.showPanel(order.getOrderID());
                    }
                });
            }
        }
    }
}
