/*
已发布中已卖出的商品点进去显示的界面
 */
package client.ui;

import client.CScontrol;
import client.ui.component.GButton;
import client.ui.component.GGoodsLabel;
import client.ui.component.GListBackPanel;
import client.ui.util.FontConfig;
import client.ui.util.MyColor;
import common.entity.Commodity;
import common.entity.Order;
import common.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class SellerOrderPanel extends JPanel {
    Commodity commodity1;
    MainPage mainPage;
    List<Order> orderList;
    public SellerOrderPanel(Commodity commodity,MainPage mainPage) throws Exception {
        this.commodity1 = commodity;
        this.mainPage = mainPage;
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        GButton bt_back = new GButton("返回");
        bt_back.addActionListener(e -> {
            this.mainPage.showPanel("alreadyPostPanel");
            this.mainPage.deletePanel(this);
        });
        topPanel.add(bt_back);
        topPanel.setBackground(MyColor.BLUE_300);
        this.add(topPanel,BorderLayout.NORTH);
        upDate();
    }
    public void upDate() throws Exception{
        JPanel orderListPanel = new JPanel();
        orderListPanel.removeAll();
        orderList = CScontrol.getSellerOrderList(commodity1,mainPage.getUserId());
        System.out.println("拿到了orderlist size："+orderList.size());
        int length = orderList.size();
        orderListPanel.setLayout(new GridLayout(length, 1, 0, 5));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < length; i++) {
            Order order = orderList.get(i);
            String auction = (order.getIsAuction() == 1) ? "拍卖" : "直售";
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
                        "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>购买完成时间:&nbsp&nbsp " + dateString + "&nbsp&nbsp买家:"+order.getBuyerID()+"</span>\n" +
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
            GListBackPanel listBackPanel = new GListBackPanel(new FlowLayout(FlowLayout.LEFT));
            listBackPanel.add(goodsLabel);
            listBackPanel.setOpaque(false);
            orderListPanel.add(listBackPanel);

            goodsLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    SellerSingleOrderPanel ssop = new SellerSingleOrderPanel(order,mainPage);
                    mainPage.addPanel(order.getOrderID(),ssop);//因为自己作为卖家能进入的订单肯定不会在已购买中进入
                    mainPage.showPanel(order.getOrderID());
                }
            });
        }
        JScrollPane jsp = new JScrollPane(orderListPanel);
        this.add(jsp,BorderLayout.CENTER);
    }
    @Override
    public String toString() {
        return "这是一条订单列表界面(卖家) ";
    }
}
