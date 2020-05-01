package client.ui.auction;

import client.CScontrol;
import client.ui.MainPage;
import client.ui.component.GGoodsLabel;
import client.ui.component.GListBackPanel;
import client.ui.util.FontConfig;
import client.utils.ManageCommodity;
import common.entity.Auction;
import common.entity.Commodity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class BuyAucOutpriceListPanel extends JPanel {
    List<Auction> auctionList;
    MainPage mainPage;
    int length;
    JPanel auctionListPanel;
    JScrollPane jsp;
    public BuyAucOutpriceListPanel(MainPage mainPage) throws Exception {
        this.mainPage = mainPage;
        this.setOpaque(false);
        upDate();
    }

    public void upDate() throws Exception {
        this.removeAll();
        this.setLayout(new BorderLayout());
        auctionList = CScontrol.getAuctionListWithSameBuyer(mainPage.getUserId());
        System.out.println("拿到了auctionList size：" + auctionList.size());
        length = auctionList.size();
        if (length == 0) {
            auctionListPanel = new JPanel(new BorderLayout());
            JLabel txt = new JLabel("你目前还没有参与拍卖哦", JLabel.CENTER);
            txt.setFont(FontConfig.font3);
            auctionListPanel.add(txt, BorderLayout.CENTER);
        } else {
            auctionListPanel = new JPanel(new GridLayout(length, 1, 0, 5));
            for (int i = 0; i < length; i++) {
                Auction auction = auctionList.get(i);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStringx = formatter.format(auction.getDate());
                String textIsSold = auction.getIsSold() == 0 ? "卖家还未出手该商品，点击查看":"卖家已出手该商品，请到已购买查看是否中标";
                String text = "<html>\n" +
                        "<body>\n" +
                        "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">出价：" + auction.getPrice() +
                        "</p>" +
                        "<p>" +
                        "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>出价时间：" + dateStringx + "&nbsp&nbsp&nbsp"+textIsSold+"</span>\n" +
                        "</p>" +
                        "</body>" +
                        "</html>";
                ImageIcon ii = new ImageIcon(auction.getPicPath());
                ii.setImage(ii.getImage().getScaledInstance(MainPage.height / 4 - 30, MainPage.height / 4 - 30, Image.SCALE_DEFAULT));
                GGoodsLabel goodsLabel = new GGoodsLabel(text, ii, JLabel.LEFT);
                GListBackPanel listBackPanel = new GListBackPanel(new FlowLayout(FlowLayout.LEFT));
                listBackPanel.add(goodsLabel);
                listBackPanel.setOpaque(false);
                auctionListPanel.add(listBackPanel);
                if(auction.getIsSold()==0){
                    goodsLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            Commodity commodity = ManageCommodity.getCommodity(auction.getCommodityID());
                            AuctionPanel ap = null;
                            try {
                                ap = new AuctionPanel(commodity,mainPage,"buyAucOutpriceListPanel");
                                mainPage.addPanel(commodity.getId()+"ba",ap);
                                mainPage.showPanel(commodity.getId()+"ba");
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }

                        }
                    });
                }
            }
        }
        jsp = new JScrollPane(auctionListPanel);
        jsp.getVerticalScrollBar().setUnitIncrement(15);
        this.add(jsp,BorderLayout.CENTER);
        validate();
    }

}