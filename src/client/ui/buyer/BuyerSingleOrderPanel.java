package client.ui.buyer;

import client.CScontrol;
import client.chat.tools.ManageChatWindow;
import client.chat.view.ChatWindow;
import client.ui.MainPage;
import client.ui.component.GButton;
import client.ui.util.FontConfig;
import client.ui.util.GBC;
import client.ui.util.MyColor;
import client.utils.CodecUtil;
import common.entity.Comment;
import common.entity.Commodity;
import common.entity.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BuyerSingleOrderPanel extends JPanel implements ActionListener {
    Order order;
    MainPage mainPage;

    public BuyerSingleOrderPanel(Order order, MainPage mainPage) {
        this.order = order;
        this.mainPage = mainPage;
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        GButton bt_back = new GButton("返回");
        bt_back.addActionListener(e -> {
            this.mainPage.showPanel("jp_al_buy");
            this.mainPage.deletePanel(this);
        });
        topPanel.add(bt_back);
        topPanel.setBackground(MyColor.BLUE_300);



        GridBagLayout gbl = new GridBagLayout();
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(gbl);

        JPanel goodInfoPanel = new JPanel(new BorderLayout());
        JPanel imgPanel = new JPanel();
        //imgPanel.setPreferredSize(new Dimension(350,350));
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

        JLabel priceAndNumsPanel = new JLabel("价格:" + order.getPrice() + "    你购买的数量:" + order.getNums(), JLabel.CENTER);
        if(order.getIsAuction()==1){
            priceAndNumsPanel = new JLabel("初始价:"+order.getPrice()+"   "+"拍卖成交价:"+order.getAuctionPrice(),JLabel.CENTER);
        }
        priceAndNumsPanel.setFont(new Font("微软雅黑", Font.PLAIN, 15));


        JLabel auctionLabel = new JLabel("该商品为: " + ((order.getIsAuction() == 1) ? "拍卖" : "直售") + " 类型", JLabel.CENTER);
        auctionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        GButton buyButton = new GButton("你已购买 发表评论！");
        buyButton.addActionListener(this);
        buyButton.setActionCommand("commentButton");
        buyButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        GButton chatButton = new GButton("联系卖家");
        chatButton.addActionListener(this);
        chatButton.setActionCommand("chatButton");
        chatButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        buyButton.setPreferredSize(new Dimension(255, 20));
        chatButton.setPreferredSize(new Dimension(255, 20));
        buttonPanel.add(buyButton, BorderLayout.WEST);
        buttonPanel.add(chatButton, BorderLayout.EAST);

        JLabel sellerAndTimePanel = new JLabel();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(order.getBuyDate());
        sellerAndTimePanel.setText("卖家:" + order.getSellerID() + "  我的下单时间:" + dateString);
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
                JLabel posterAndDate = new JLabel("发布者:"+comment.getUserID()+"      发布时间:"+postDate,JLabel.RIGHT);
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
        //this.add(leftPanel,BorderLayout.WEST);
        //this.add(rightPanel,BorderLayout.EAST);
        //this.add(bottomPanel,BorderLayout.SOUTH);
        this.add(jsp, BorderLayout.CENTER);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("commentButton")){
            JTextArea jta = new JTextArea();
            jta.setPreferredSize(new Dimension(550,200));
            jta.setFont(FontConfig.font3);
            jta.setLineWrap(true);
            JScrollPane jsp = new JScrollPane(jta);
            jsp.getVerticalScrollBar().setUnitIncrement(15);
            int result = JOptionPane.showConfirmDialog(this,jsp,"说说商品怎么样吧",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            if(result==0){
                String content = jta.getText().trim();
                if(!content.equals("")){
                    Comment comment = new Comment(order.getCommodityID(),content,mainPage.getUserId(),new Date());
                    try {
                        CScontrol.addComment(comment);
                        JOptionPane.showMessageDialog(this,"发表成功","提示",JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(this,"发表失败，请输入有效评论！","提示",JOptionPane.WARNING_MESSAGE);
                }
            }
        }else if(e.getActionCommand().equals("chatButton")){
            ChatWindow chatWindow = ManageChatWindow.getChatWindow(mainPage.getUserId()+" "+order.getSellerID());
            chatWindow.setVisible(true);
        }
    }
    @Override
    public String toString() {
        return "这是一条订单 商品名:" + order.getName() + " 订单号:" + order.getOrderID();
    }
}
