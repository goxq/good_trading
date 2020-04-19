package client.ui;

import client.CScontrol;
import client.ui.MainPage;
import client.ui.component.GGoodsLabel;
import client.ui.component.GListBackPanel;
import common.entity.Commodity;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GdsPanel extends JPanel {
    List<Commodity> commodityList;

    public GdsPanel() throws Exception {
        commodityList = CScontrol.getGoodsListToServer();
        this.setLayout(new GridLayout(commodityList.size(), 1, 0, 5));
        this.setOpaque(false);
        showGds();
    }
    public void upDate() throws Exception {
        this.removeAll();
        commodityList = CScontrol.getGoodsListToServer();
        this.setLayout(new GridLayout(commodityList.size(), 1, 0, 5));

        for (int i = 0; i < commodityList.size(); i++) {
            String auction = (commodityList.get(i).getIsAuction() == 1) ? "拍卖" : "直售";
            String date1[] = commodityList.get(i).getPostDate().toString().split(" ");
            String date = date1[0] + " " + date1[1] + " " + date1[2] + " " + date1[5];
            String text = "<html>\n" +
                    "<body>\n" +
                    "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">" + commodityList.get(i).getName() +
                    "</p>" +
                    "<p>" +
                    "<span style=\"font-family:微软雅黑;font-size:28px;color:white;font-weight:400;\">价格：</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:30px;color:white;font-weight:700;\">" + commodityList.get(i).getPrice() + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp数量:" + commodityList.get(i).getNums() + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + auction + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>" + date + "</span>\n" +
                    "</p>" +
                    "</body>" +
                    "</html>";
            ImageIcon ii = new ImageIcon(commodityList.get(i).getPicPath());
            ii.setImage(ii.getImage().getScaledInstance(MainPage.height / 4 - 30, MainPage.height / 4 - 30, Image.SCALE_DEFAULT));
            GGoodsLabel goodsLabel = new GGoodsLabel(text, ii, JLabel.LEFT);
            //goodsLabel.setPreferredSize(new Dimension(3 * this.getWidth() / 5, 2 * height / 7));
            GListBackPanel listBackPanel = new GListBackPanel(new FlowLayout(FlowLayout.LEFT));
            listBackPanel.add(goodsLabel);
            listBackPanel.setOpaque(false);
            this.add(listBackPanel);
            //right_panel.add(jsp1, "jp_gds");//商品列表
        }
    }
    public void showGds() throws Exception {
        for (int i = 0; i < commodityList.size(); i++) {
            String auction = (commodityList.get(i).getIsAuction() == 1) ? "拍卖" : "直售";
            String date1[] = commodityList.get(i).getPostDate().toString().split(" ");
            String date = date1[0] + " " + date1[1] + " " + date1[2] + " " + date1[5];
            String text = "<html>\n" +
                    "<body>\n" +
                    "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">" + commodityList.get(i).getName() +
                    "</p>" +
                    "<p>" +
                    "<span style=\"font-family:微软雅黑;font-size:28px;color:white;font-weight:400;\">价格：</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:30px;color:white;font-weight:700;\">" + commodityList.get(i).getPrice() + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp数量:" + commodityList.get(i).getNums() + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:20px;color:white;font-weight:400;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + auction + "</span>\n" +
                    "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>" + date + "</span>\n" +
                    "</p>" +
                    "</body>" +
                    "</html>";
            ImageIcon ii = new ImageIcon(commodityList.get(i).getPicPath());
            ii.setImage(ii.getImage().getScaledInstance(MainPage.height / 4 - 30, MainPage.height / 4 - 30, Image.SCALE_DEFAULT));
            GGoodsLabel goodsLabel = new GGoodsLabel(text, ii, JLabel.LEFT);
            //goodsLabel.setPreferredSize(new Dimension(3 * this.getWidth() / 5, 2 * height / 7));
            GListBackPanel listBackPanel = new GListBackPanel(new FlowLayout(FlowLayout.LEFT));
            listBackPanel.add(goodsLabel);
            listBackPanel.setOpaque(false);
            this.add(listBackPanel);
            //right_panel.add(jsp1, "jp_gds");//商品列表
        }
    }
}
