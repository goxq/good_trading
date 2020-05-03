package client.ui.seller;

import client.CScontrol;
import client.ui.MainPage;
import client.ui.auction.SellerAuctionListPanel;
import client.ui.component.GGoodsLabel;
import client.ui.component.GListBackPanel;
import client.ui.util.FontConfig;
import common.entity.Commodity;
import common.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class AlreadyPostPanel extends JPanel {
    List<Commodity> commodityList;
    User user;
    MainPage mainPage;
    public AlreadyPostPanel(User user,MainPage mainPage) throws Exception {
        this.user = user;
        this.mainPage = mainPage;
        this.setOpaque(false);
        upDate();
    }

    public void upDate() throws Exception {
        this.removeAll();
        commodityList = CScontrol.getAlreadyPost(user.getUserID());
        int length = commodityList.size();
        this.setLayout(new GridLayout(length, 1, 0, 5));
        if (length == 0) {
            this.setLayout(new BorderLayout());
            JLabel txt = new JLabel("uhhh，你还没有发布商品鸭！",JLabel.CENTER);
            txt.setFont(FontConfig.font1);
            this.add(txt, BorderLayout.CENTER);
        }else{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < length; i++) {
                Commodity commodity = commodityList.get(i);
                if(commodity.getIsAuction()==0){
                    String auction = (commodity.getIsAuction() == 1) ? "拍卖" : "直售";
                    String isSold = commodity.getIsSold() ? "该商品已有人购买，点击查看！(≧∇≦)ﾉ" : "该商品暂时无人购买( ˘•灬•˘ )";
                    String dateString = formatter.format(commodity.getPostDate());
                    String text = "<html>\n" +
                            "<body>\n" +
                            "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">" + commodity.getName() +
                            "</p>" +
                            "<p>" +
                            "<span style=\"font-family:微软雅黑;font-size:28px;color:white;font-weight:400;\">价格：</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:30px;color:white;font-weight:700;\">" + commodity.getPrice() + "</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp发布的数量:" + commodity.getNums() + "</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + auction + "</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>发布时间：" + dateString +"&nbsp&nbsp&nbsp&nbsp&nbsp"+isSold+ "</span>\n" +
                            "</p>" +
                            "</body>" +
                            "</html>";
                    ImageIcon ii = new ImageIcon(commodity.getPicPath());
                    ii.setImage(ii.getImage().getScaledInstance(MainPage.height / 4 - 30, MainPage.height / 4 - 30, Image.SCALE_DEFAULT));
                    GGoodsLabel goodsLabel = new GGoodsLabel(text, ii, JLabel.LEFT);
                    GListBackPanel listBackPanel = new GListBackPanel(new FlowLayout(FlowLayout.LEFT));
                    listBackPanel.add(goodsLabel);
                    listBackPanel.setOpaque(false);
                    this.add(listBackPanel);
                    if(commodity.getIsSold()){
                        goodsLabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                try {
                                    SellerOrderPanel sellerOrderPanel = new SellerOrderPanel(commodity,mainPage);
                                    mainPage.addPanel(commodity.getId()+"0",sellerOrderPanel);
                                    mainPage.showPanel(commodity.getId()+"0");
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        });
                    }else {
                        goodsLabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                try {
                                    if(JOptionPane.showConfirmDialog(mainPage,"你确定要下架该商品吗？","请确定",JOptionPane.OK_CANCEL_OPTION)==0){
                                        CScontrol.deleteAlreadyGood(commodity.getId());
                                        JOptionPane.showMessageDialog(mainPage,"下架成功！");
                                    }
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        });
                    }
                }else {
                    String dateString = formatter.format(commodity.getPostDate());
                    int auctionNum = CScontrol.getNumsWithComIdInAuc(commodity.getId());
                    String txt = (auctionNum > 0) ? "已有人出价，点击查看！(≧∇≦)ﾉ" : "还没有人出价";
                    if(commodity.getIsSold()){
                        txt = "该商品已拍卖成功！";
                    }
                    String text = "<html>\n" +
                            "<body>\n" +
                            "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">" + commodity.getName() +
                            "</p>" +
                            "<p>" +
                            "<span style=\"font-family:微软雅黑;font-size:28px;color:white;font-weight:400;\">价格：</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:30px;color:white;font-weight:700;\">" + commodity.getPrice() + "</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp发布的数量:" + commodity.getNums() + "</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp拍卖</span>\n" +
                            "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>发布时间：" + dateString +"&nbsp&nbsp&nbsp&nbsp&nbsp"+txt+ "</span>\n" +
                            "</p>" +
                            "</body>" +
                            "</html>";
                    ImageIcon ii = new ImageIcon(commodity.getPicPath());
                    ii.setImage(ii.getImage().getScaledInstance(MainPage.height / 4 - 30, MainPage.height / 4 - 30, Image.SCALE_DEFAULT));
                    GGoodsLabel goodsLabel = new GGoodsLabel(text, ii, JLabel.LEFT);
                    GListBackPanel listBackPanel = new GListBackPanel(new FlowLayout(FlowLayout.LEFT));
                    listBackPanel.add(goodsLabel);
                    listBackPanel.setOpaque(false);
                    this.add(listBackPanel);

                    if(auctionNum>0){//表示已经有人出价
                        goodsLabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                if(commodity.getIsSold()){
                                    SellerOrderPanel sellerOrderPanel = null;
                                    try {
                                        sellerOrderPanel = new SellerOrderPanel(commodity,mainPage);
                                        mainPage.addPanel(commodity.getId()+"0",sellerOrderPanel);
                                        mainPage.showPanel(commodity.getId()+"0");
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                }else {
                                    try {
                                        SellerAuctionListPanel salp = new SellerAuctionListPanel(commodity,mainPage,"alreadyPostPanel");
                                        mainPage.addPanel(commodity.getId()+"sa",salp);
                                        mainPage.showPanel(commodity.getId()+"sa");
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

}
