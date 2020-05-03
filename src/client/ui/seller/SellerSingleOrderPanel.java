package client.ui.seller;

import client.chat.tools.ManageChatWindow;
import client.chat.view.ChatWindow;
import client.ui.MainPage;
import client.ui.component.GButton;
import client.ui.util.FontConfig;
import client.ui.util.GBC;
import client.ui.util.MyColor;
import common.entity.Comment;
import common.entity.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class SellerSingleOrderPanel extends JPanel implements ActionListener {
    Order order;
    MainPage mainPage;

    public SellerSingleOrderPanel(Order order, MainPage mainPage) {
        this.order = order;
        this.mainPage = mainPage;
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        GButton bt_back = new GButton("返回");
        bt_back.addActionListener(e -> {
            this.mainPage.showPanel(order.getCommodityID()+"0");
            this.mainPage.deletePanel(this);
        });
        topPanel.add(bt_back);
        topPanel.setBackground(MyColor.BLUE_300);



        GridBagLayout gbl = new GridBagLayout();
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(gbl);

        JPanel goodInfoPanel = new JPanel(new BorderLayout());
        JPanel imgPanel = new JPanel();
        imgPanel.setBackground(MyColor.BLUE_200);
        JLabel imgLabel = new JLabel();
        ImageIcon img = new ImageIcon(order.getPicPath());
        img.setImage(img.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
        imgLabel.setIcon(img);
        imgPanel.add(imgLabel);

        JPanel textPanel = new JPanel(new GridLayout(5, 1, 0, 5));
        textPanel.setBackground(MyColor.BLUE_200);
        textPanel.setPreferredSize(new Dimension(519, 300));
        JLabel nameLabel = new JLabel(order.getName(), JLabel.CENTER);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));

        JLabel priceAndNumsPanel = new JLabel("价格:" + order.getPrice() + "    对方购买的数量:" + order.getNums(), JLabel.CENTER);
        if(order.getIsAuction()==1){
            priceAndNumsPanel = new JLabel("初始价:"+order.getPrice()+"   "+"拍卖成交价:"+order.getAuctionPrice(),JLabel.CENTER);
        }
        priceAndNumsPanel.setFont(new Font("微软雅黑", Font.PLAIN, 15));


        JLabel auctionLabel = new JLabel("该商品为: " + ((order.getIsAuction() == 1) ? "拍卖" : "直售") + " 类型", JLabel.CENTER);
        auctionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        GButton buyButton = new GButton("买家已下单，请联系约定交易");
        buyButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        GButton chatButton = new GButton("联系买家");
        chatButton.addActionListener(this);
        chatButton.setActionCommand("chatButton");
        chatButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        buyButton.setPreferredSize(new Dimension(300, 20));
        chatButton.setPreferredSize(new Dimension(215, 20));
        buttonPanel.add(buyButton, BorderLayout.WEST);
        buttonPanel.add(chatButton, BorderLayout.EAST);

        JLabel sellerAndTimePanel = new JLabel();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(order.getBuyDate());
        sellerAndTimePanel.setText("买家:" + order.getBuyerID() + "  对方下单时间:" + dateString);
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

        JLabel tipLabel = new JLabel("商品评论",JLabel.CENTER);
        tipLabel.setFont(new Font("方正喵呜体", Font.BOLD,25));
        tipLabel.setPreferredSize(new Dimension(0,100));
        centerPanel.add(tipLabel, new GBC(0, 2, 1, 1)
                .setFill(GBC.BOTH).setWeight(50, 100));

        //评论面板
        List<Comment> commentList = order.getCommentList();
        int size = commentList.size();
        JPanel commentPanel = null;
        if(size!=0){
            commentPanel = new JPanel(new GridLayout(size, 1, 0, 20));

            for (int i = 0; i < commentList.size(); i++) {
                Comment comment = commentList.get(i);
                JPanel eachCommentPanel = new JPanel(new BorderLayout());
                eachCommentPanel.setBackground(MyColor.YELLOW_50);
                JTextArea contentText = new JTextArea(comment.getContent());
                contentText.setLineWrap(true);
                contentText.setEnabled(false);
                contentText.setForeground(MyColor.BLACK);
                contentText.setFont(FontConfig.font3);
                contentText.setBackground(MyColor.YELLOW_50);

                String postDate = formatter.format(comment.getDate());
                JLabel posterAndDate = new JLabel("购买者:"+comment.getUserID()+"      下单时间:"+postDate,JLabel.RIGHT);
                posterAndDate.setFont(new Font("微软雅黑", Font.PLAIN,15));
                posterAndDate.setBackground(MyColor.YELLOW_50);

                eachCommentPanel.add(contentText,BorderLayout.CENTER);
                eachCommentPanel.add(posterAndDate,BorderLayout.SOUTH);
                commentPanel.add(eachCommentPanel);
            }
        }else {
            JLabel txt = new JLabel("该商品暂时还没有人评论哦！",JLabel.CENTER);
            txt.setBackground(MyColor.YELLOW_50);
            txt.setFont(new Font("方正喵呜体", Font.BOLD,15));
            commentPanel = new JPanel();
            commentPanel.setBackground(MyColor.YELLOW_50);
            commentPanel.add(txt);
        }

        centerPanel.add(commentPanel, new GBC(0, 3, 1, 1)
                .setFill(GBC.BOTH).setWeight(50, 100).setAnchor(GridBagConstraints.CENTER));

        centerPanel.setBackground(MyColor.YELLOW_200);
        JScrollPane jsp = new JScrollPane(centerPanel);


        this.add(topPanel, BorderLayout.NORTH);
        this.add(jsp, BorderLayout.CENTER);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("chatButton")){
            ChatWindow chatWindow = ManageChatWindow.getChatWindow(mainPage.getUserId()+" "+ order.getBuyerID());
            chatWindow.setVisible(true);
        }
    }

    @Override
    public String toString() {
        return "这是一条订单(卖家) 商品名:" + order.getName() + " 订单号:" + order.getOrderID();
    }
}
