package client.ui.auction;

import client.tool.CScontrol;
import client.ui.MainPage;
import client.ui.component.GButton;
import client.ui.component.GGoodsLabel;
import client.ui.component.GListBackPanel;
import client.ui.util.FontConfig;
import client.ui.util.MyColor;
import client.utils.CodecUtil;
import common.entity.Auction;
import common.entity.Commodity;
import common.entity.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SellerAuctionListPanel extends JPanel implements ActionListener {
    Commodity commodity;
    MainPage mainPage;
    List<Auction> auctionList;
    int length;
    JPanel auctionPriceListPanel;
    JPanel centerPanel;
    public SellerAuctionListPanel(Commodity commodity, MainPage mainPage,String backPage) throws Exception {
        this.commodity = commodity;
        this.mainPage = mainPage;
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        GButton bt_back = new GButton("返回");
        GButton bt_sell = new GButton("出手成交");
        bt_back.addActionListener(e -> {
            this.mainPage.showPanel(backPage);
            this.mainPage.deletePanel(this);
        });
        bt_sell.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(mainPage,"将结束拍卖，商品出售给当前最高竞价买家，确定吗？","请确定",JOptionPane.OK_CANCEL_OPTION);
            if(result==JOptionPane.OK_OPTION){
                Order order = new Order();
                order.setOrderID(CodecUtil.createOrderId());
                order.setCommodityID(commodity.getId());
                order.setBuyDate(new Date());
                order.setName(commodity.getName());

                try {
                    int out = CScontrol.auctionSell(order);
                    if(out == 1){
                        JOptionPane.showMessageDialog(mainPage,"出手成功，拍卖结束");
                    } else {
                        JOptionPane.showMessageDialog(mainPage,"出手失败，请刷新重试");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(mainPage,"出手失败，请刷新重试");
                }


            }
        });
        topPanel.add(bt_back);
        topPanel.add(bt_sell);
        topPanel.setBackground(MyColor.BLUE_300);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        updateAuctionList();

        centerPanel.setBackground(MyColor.YELLOW_200);
        JScrollPane jsp = new JScrollPane(centerPanel);
        jsp.getVerticalScrollBar().setUnitIncrement(15);


        this.add(topPanel, BorderLayout.NORTH);
        this.add(jsp, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void updateAuctionList() throws Exception {
        //竞价列表panel
        //获取竞价列表
        if(auctionPriceListPanel!=null){
            centerPanel.remove(auctionPriceListPanel);
            auctionPriceListPanel.removeAll();
        }
        auctionList = CScontrol.getSellerAuctionList(commodity.getId());
        System.out.println("拿到了auctionList size：" + auctionList.size());
        length = auctionList.size();
        if (length == 0) {
            auctionPriceListPanel = new JPanel(new BorderLayout());
            JLabel txt = new JLabel("目前还没有人出价", JLabel.CENTER);
            txt.setFont(FontConfig.font3);
            auctionPriceListPanel.add(txt, BorderLayout.CENTER);
        } else {
            auctionPriceListPanel = new JPanel(new GridLayout(length, 1, 0, 5));
            for (int i = 0; i < length; i++) {
                Auction auction = auctionList.get(i);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStringx = formatter.format(auction.getDate());
                String text = "<html>\n" +
                        "<body>\n" +
                        "<p style=\"font-family:微软雅黑;font-size:35px;color:white;font-weight:850;\">出价：" + auction.getPrice() +
                        "</p>" +
                        "<p>" +
                        "<span style=\"font-family:微软雅黑;font-size:15px;color:white;font-weight:400;\"><br/>出价时间：" + dateStringx + "&nbsp&nbsp&nbsp&nbsp&nbsp出价者：" + auction.getBuyer() + "</span>\n" +
                        "</p>" +
                        "</body>" +
                        "</html>";
                ImageIcon ii = new ImageIcon(auction.getPicPath());
                ii.setImage(ii.getImage().getScaledInstance(MainPage.height / 4 - 30, MainPage.height / 4 - 30, Image.SCALE_DEFAULT));
                GGoodsLabel goodsLabel = new GGoodsLabel(text, ii, JLabel.LEFT);
                GListBackPanel listBackPanel = new GListBackPanel(new FlowLayout(FlowLayout.LEFT));
                listBackPanel.add(goodsLabel);
                listBackPanel.setOpaque(false);
                auctionPriceListPanel.add(listBackPanel);
            }
        }
        centerPanel.add(auctionPriceListPanel, BorderLayout.CENTER);
        validate();
    }

    @Override
    public String toString() {
        return "这是一个拍卖商品列表界面(卖家) 商品名:" + commodity.getName() + " 商品号:" + commodity.getId();
    }
}