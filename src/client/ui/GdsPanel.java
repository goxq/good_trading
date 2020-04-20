package client.ui;

import client.CScontrol;
import client.ui.MainPage;
import client.ui.component.GGoodsLabel;
import client.ui.component.GListBackPanel;
import common.entity.Commodity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GdsPanel extends JPanel {
    List<Commodity> commodityList;
    MainPage mainPage;
    public GdsPanel(MainPage mainPage) throws Exception {
        this.mainPage = mainPage;
        commodityList = CScontrol.getGoodsListToServer(mainPage.getUserId());
        this.setLayout(new GridLayout(commodityList.size(), 1, 0, 5));
        this.setOpaque(false);
        showGds();
    }
    public void upDate() throws Exception {
        this.removeAll();
        commodityList = CScontrol.getGoodsListToServer(mainPage.getUserId());
        this.setLayout(new GridLayout(commodityList.size(), 1, 0, 5));

        for (int i = 0; i < commodityList.size(); i++) {
            Commodity commodity = commodityList.get(i);
            String auction = (commodity.getIsAuction() == 1) ? "拍卖" : "直售";
            String date1[] = commodity.getPostDate().toString().split(" ");
            String date = date1[0] + " " + date1[1] + " " + date1[2] + " " + date1[5];
            String text = "<html>\n" +
                    "<body>\n" +
                    "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">" + commodity.getName() +
                    "</p>" +
                    "<p>" +
                    "<span style=\"font-family:微软雅黑;font-size:28px;color:white;font-weight:400;\">价格：</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:30px;color:white;font-weight:700;\">" + commodity.getPrice() + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp数量:" + commodityList.get(i).getNums() + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + auction + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>" + date + "</span>\n" +
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
                    SingleGoodPanel sgp = new SingleGoodPanel(commodity,mainPage);
                    mainPage.addPanel(commodity.getId(),sgp);
                    mainPage.showPanel(commodity.getId());
                }
            });
        }
    }
    public void showGds() throws Exception {
        for (int i = 0; i < commodityList.size(); i++) {
            Commodity commodity = commodityList.get(i);
            String auction = (commodity.getIsAuction() == 1) ? "拍卖" : "直售";
            String date1[] = commodity.getPostDate().toString().split(" ");
            String date = date1[0] + " " + date1[1] + " " + date1[2] + " " + date1[5];
            String text = "<html>\n" +
                    "<body>\n" +
                    "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">" + commodity.getName() +
                    "</p>" +
                    "<p>" +
                    "<span style=\"font-family:微软雅黑;font-size:28px;color:white;font-weight:400;\">价格：</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:30px;color:white;font-weight:700;\">" + commodity.getPrice() + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp余量:" + commodity.getNums() + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + auction + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>" + date + "</span>\n" +
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
                    SingleGoodPanel sgp = new SingleGoodPanel(commodity,mainPage);
                    mainPage.addPanel(commodity.getId(),sgp);
                    mainPage.showPanel(commodity.getId());
                }
            });
        }
    }

}
