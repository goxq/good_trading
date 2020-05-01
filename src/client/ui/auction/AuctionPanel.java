package client.ui.auction;

import client.CScontrol;
import client.ui.MainPage;
import client.ui.component.GButton;
import client.ui.component.GGoodsLabel;
import client.ui.component.GListBackPanel;
import client.ui.component.GTextField;
import client.ui.util.FontConfig;
import client.ui.util.GBC;
import client.ui.util.MyColor;
import client.utils.CodecUtil;
import common.entity.Auction;
import common.entity.Comment;
import common.entity.Commodity;
import common.entity.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AuctionPanel extends JPanel implements ActionListener {
    Commodity commodity;
    MainPage mainPage;
    List<Auction> auctionList;
    int length;
    JPanel auctionPriceListPanel;
    JPanel centerPanel;
    public AuctionPanel(Commodity commodity, MainPage mainPage,String backPage) throws Exception {
        this.commodity = commodity;
        this.mainPage = mainPage;
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        GButton bt_back = new GButton("返回");
        bt_back.addActionListener(e -> {
            this.mainPage.showPanel(backPage);
            this.mainPage.deletePanel(this);
        });
        topPanel.add(bt_back);
        topPanel.setBackground(MyColor.BLUE_300);


        GridBagLayout gbl = new GridBagLayout();
        centerPanel = new JPanel();
        centerPanel.setLayout(gbl);

        JPanel goodInfoPanel = new JPanel(new BorderLayout());
        JPanel imgPanel = new JPanel();
        imgPanel.setBackground(MyColor.BLUE_200);
        JLabel imgLabel = new JLabel();
        ImageIcon img = new ImageIcon(commodity.getPicPath());
        img.setImage(img.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
        imgLabel.setIcon(img);
        imgPanel.add(imgLabel);

        JPanel textPanel = new JPanel(new GridLayout(5, 1, 0, 5));
        textPanel.setBackground(MyColor.BLUE_200);
        textPanel.setPreferredSize(new Dimension(519, 300));
        JLabel nameLabel = new JLabel(commodity.getName(), JLabel.CENTER);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));

        JLabel priceAndNumsPanel = new JLabel("底价:" + commodity.getPrice() + "    余量:" + commodity.getNums(), JLabel.CENTER);
        priceAndNumsPanel.setFont(new Font("微软雅黑", Font.PLAIN, 15));


        JLabel auctionLabel = new JLabel("该商品为: " + ((commodity.getIsAuction() == 1) ? "拍卖" : "直售") + " 类型", JLabel.CENTER);
        auctionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        GButton auctionButton = new GButton("立即出价！");
        auctionButton.addActionListener(this);
        auctionButton.setActionCommand("auctionButton");
        auctionButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        GButton chatButton = new GButton("联系卖家");
        chatButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        auctionButton.setPreferredSize(new Dimension(255, 20));
        chatButton.setPreferredSize(new Dimension(255, 20));
        buttonPanel.add(auctionButton, BorderLayout.WEST);
        buttonPanel.add(chatButton, BorderLayout.EAST);


        JLabel sellerAndTimePanel = new JLabel();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(commodity.getPostDate());
        sellerAndTimePanel.setText("卖家:" + commodity.getUserID() + "  发布时间:" + dateString);
        sellerAndTimePanel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        sellerAndTimePanel.setForeground(MyColor.WHITE);


        textPanel.add(nameLabel);
        textPanel.add(priceAndNumsPanel);
        textPanel.add(auctionLabel);
        textPanel.add(buttonPanel);
        textPanel.add(sellerAndTimePanel);

        goodInfoPanel.add(imgPanel, BorderLayout.CENTER);
        goodInfoPanel.add(textPanel, BorderLayout.EAST);
        centerPanel.add(goodInfoPanel, new GBC(0, 0, 1, 2)
                .setFill(GBC.BOTH).setWeight(50, 100));

        JLabel tipLabel = new JLabel("竞价列表", JLabel.CENTER);
        tipLabel.setFont(new Font("方正喵呜体", Font.BOLD, 25));
        tipLabel.setPreferredSize(new Dimension(0, 100));
        centerPanel.add(tipLabel, new GBC(0, 2, 1, 1)
                .setFill(GBC.BOTH).setWeight(50, 100));

        updateAuctionList();

        centerPanel.setBackground(MyColor.YELLOW_200);
        JScrollPane jsp = new JScrollPane(centerPanel);
        jsp.getVerticalScrollBar().setUnitIncrement(15);


        this.add(topPanel, BorderLayout.NORTH);
        this.add(jsp, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("auctionButton")) {
            if(commodity.getUserID().equals(mainPage.getUserId())){
                JOptionPane.showMessageDialog(mainPage,"不能购买自己的商品！");
            }else {
                GTextField gtf = new GTextField("请输入你的竞价", 5, 28);
                gtf.setOpaque(false);
                gtf.setFont(FontConfig.font3);
                int result = JOptionPane.showConfirmDialog(mainPage, gtf, "请输入你的竞价，只能提交一次", JOptionPane.OK_CANCEL_OPTION);
                if (!(result == JOptionPane.CANCEL_OPTION||result == JOptionPane.CLOSED_OPTION)) {
                    try {
                        double price = Double.parseDouble(gtf.getText().trim());
                        if(price>commodity.getPrice()){
                            //价格验证在服务器
                            String aucID = CodecUtil.createOrderId();
                            Auction auction = new Auction(aucID,commodity.getId(), commodity.getPrice(), commodity.getUserID(), mainPage.getUserId(), price, new Date(),0);
                            int serverResult = CScontrol.addToAuction(auction);
                            if (serverResult == 1) {
                                JOptionPane.showMessageDialog(mainPage, "竞标成功！");
                                this.updateAuctionList();
                            } else {
                                JOptionPane.showMessageDialog(mainPage, "竞标失败，请确保价格高于当前最高价并刷新后重试（只能提交一次竞价哦）");
                            }
                        }else {
                            JOptionPane.showMessageDialog(mainPage, "竞标失败，请确保价格高于当前最高价并刷新后重试（只能提交一次竞价哦）");
                        }
                    }catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(mainPage, "输入价格有误，请重新输入高于当前最高价的竞价");
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(mainPage, "竞标失败，请刷新后重试（只能提交一次竞价哦）");
                    }
                }
            }
        }
    }

    public void updateAuctionList() throws Exception {
        //竞价列表panel
        //获取竞价列表
        if(auctionPriceListPanel!=null){
            centerPanel.remove(auctionPriceListPanel);
            auctionPriceListPanel.removeAll();
        }
        auctionList = CScontrol.getBuyerAuctionList(commodity.getId());
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
        centerPanel.add(auctionPriceListPanel, new GBC(0, 3, 1, 1)
                .setFill(GBC.BOTH).setWeight(50, 100).setAnchor(GridBagConstraints.CENTER));
        validate();
    }

    @Override
    public String toString() {
        return "这是一个拍卖界面 商品名:" + commodity.getName() + " 商品号:" + commodity.getId();
    }
}