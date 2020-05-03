package client.ui.buyer;

import client.CScontrol;
import client.ui.MainPage;
import client.ui.auction.AuctionPanel;
import client.ui.component.GGoodsLabel;
import client.ui.component.GListBackPanel;
import client.utils.ManageCommodity;
import common.entity.Commodity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class GdsPanel extends JPanel {
    MainPage mainPage;
    public GdsPanel(MainPage mainPage) throws Exception {
        this.mainPage = mainPage;
        this.setOpaque(false);
        upDate(CScontrol.getGoodsListToServer(mainPage.getUserId()));
    }
    public void upDate(List<Commodity> commodities) {
        this.removeAll();
        this.setLayout(new GridLayout(commodities.size(), 1, 0, 5));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < commodities.size(); i++) {
            Commodity commodity = commodities.get(i);
            if(commodity.getIsAuction()==1){
                ManageCommodity.addCommodity(commodity.getId(),commodity);
            }
            String auction = (commodity.getIsAuction() == 1) ? "拍卖" : "直售";
            String dateString = formatter.format(commodity.getPostDate());
            String text = "<html>\n" +
                    "<body>\n" +
                    "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">" + commodity.getName() +
                    "</p>" +
                    "<p>" +
                    "<span style=\"font-family:微软雅黑;font-size:28px;color:white;font-weight:400;\">价格：</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:30px;color:white;font-weight:700;\">" + commodity.getPrice() + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp数量:" + commodity.getNums() + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + auction + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>发布时间：" + dateString +"&nbsp&nbsp&nbsp&nbsp&nbsp发布者："+ commodity.getUserID() +"</span>\n" +
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

            goodsLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if(commodity.getIsAuction()==0) {
                        SingleGoodPanel sgp = new SingleGoodPanel(commodity, mainPage);
                        mainPage.addPanel(commodity.getId(), sgp);
                        mainPage.showPanel(commodity.getId());
                    }else {
                        try {
                            AuctionPanel ap = new AuctionPanel(commodity,mainPage,"jp_gds");
                            mainPage.addPanel(commodity.getId()+"a",ap);
                            mainPage.showPanel(commodity.getId()+"a");
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                    }
                }
            });
        }
    }
}


