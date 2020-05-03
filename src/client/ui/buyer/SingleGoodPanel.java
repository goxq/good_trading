package client.ui.buyer;

import client.CScontrol;
import client.chat.model.QqClientUser;
import client.chat.tools.ClientConServerThread;
import client.chat.tools.ManageChatWindow;
import client.chat.tools.ManageFriendList;
import client.chat.view.ChatWindow;
import client.chat.view.FriendList;
import client.ui.MainPage;
import client.ui.util.FontConfig;
import client.utils.CodecUtil;
import client.ui.component.GButton;
import client.ui.util.GBC;
import client.ui.util.MyColor;
import client.utils.ManageCommodity;
import common.chat.Message;
import common.chat.MessageType;
import common.entity.Comment;
import common.entity.Commodity;
import common.entity.Order;
import common.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.util.List;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleGoodPanel extends JPanel implements ActionListener {
    Commodity commodity;
    MainPage mainPage;

    public SingleGoodPanel(Commodity commodity, MainPage mainPage) {
        this.commodity = commodity;
        this.mainPage = mainPage;
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        GButton bt_back = new GButton("返回");
        bt_back.addActionListener(e -> {
            this.mainPage.showPanel("jp_gds");
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
        ImageIcon img = new ImageIcon(commodity.getPicPath());
        img.setImage(img.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
        imgLabel.setIcon(img);
        imgPanel.add(imgLabel);

        JPanel textPanel = new JPanel(new GridLayout(5, 1, 0, 5));
        textPanel.setBackground(MyColor.BLUE_200);
        textPanel.setPreferredSize(new Dimension(519, 300));
        JLabel nameLabel = new JLabel(commodity.getName(), JLabel.CENTER);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));

        JLabel priceAndNumsPanel = new JLabel("价格:" + commodity.getPrice() + "    余量:" + commodity.getNums(), JLabel.CENTER);
        priceAndNumsPanel.setFont(new Font("微软雅黑", Font.PLAIN, 15));


        JLabel auctionLabel = new JLabel("该商品为: " + ((commodity.getIsAuction() == 1) ? "拍卖" : "直售") + " 类型", JLabel.CENTER);
        auctionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        GButton buyButton = new GButton("立即购买！");
        buyButton.addActionListener(this);
        buyButton.setActionCommand("buyButton");
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

        JLabel tipLabel = new JLabel("商品评论", JLabel.CENTER);
        tipLabel.setFont(new Font("方正喵呜体", Font.BOLD, 25));
        tipLabel.setPreferredSize(new Dimension(0, 100));
        centerPanel.add(tipLabel, new GBC(0, 2, 1, 1)
                .setFill(GBC.BOTH).setWeight(50, 100));

        //评论面板
        List<Comment> commentList = commodity.getCommentList();
        int size = commentList.size();
        JPanel commentPanel = null;
        if (size != 0) {
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
                JLabel posterAndDate = new JLabel("发布者:" + comment.getUserID() + "      发布时间:" + postDate, JLabel.RIGHT);
                posterAndDate.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                posterAndDate.setBackground(MyColor.YELLOW_50);

                eachCommentPanel.add(contentText, BorderLayout.CENTER);
                eachCommentPanel.add(posterAndDate, BorderLayout.SOUTH);
                commentPanel.add(eachCommentPanel);
            }
        } else {
            JLabel txt = new JLabel("该商品暂时还没有人评论哦！", JLabel.CENTER);
            txt.setBackground(MyColor.YELLOW_50);
            txt.setFont(new Font("方正喵呜体", Font.BOLD, 15));
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
        if (e.getActionCommand().equals("buyButton")) {
            if (commodity.getUserID().equals(mainPage.getUserId())) {
                JOptionPane.showMessageDialog(this, "不能购买自己发布的商品！", "提示", JOptionPane.WARNING_MESSAGE);
            } else {
                Object numsObj = JOptionPane.showInputDialog(this, "请输入购买数量:", "购买确认", JOptionPane.OK_CANCEL_OPTION);
                if (numsObj == null) {
                } else {
                    String nums = (String) numsObj;
                    int numsInteger = 0;
                    try {
                        numsInteger = Integer.parseInt(nums);
                        if (numsInteger <= commodity.getNums() && numsInteger > 0) {
                            //下面是非拍卖的购买操作
                            //订单类里面的图 在server端设置为服务器的路径
                            Order order = new Order(CodecUtil.createOrderId(), commodity.getId(), mainPage.getUserId(), commodity.getUserID(),
                                    commodity.getPrice(), commodity.getName(), Integer.parseInt(nums), 0, new Date());
                            try {
                                int a = CScontrol.BuyToServer(order);
                                if (a == 1) {
                                    JOptionPane.showMessageDialog(this, "购买成功");
                                    commodity.setNums(commodity.getNums() - numsInteger);
                                } else {
                                    JOptionPane.showMessageDialog(this, "购买失败");
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        } else
                            JOptionPane.showMessageDialog(this, "购买数量有误，请重新购买", "提示", JOptionPane.WARNING_MESSAGE);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(this, "购买数量有误，请重新购买", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } else if (e.getActionCommand().equals("chatButton")) {
            if(mainPage.getUserId().equals(commodity.getUserID())){
                JOptionPane.showMessageDialog(mainPage,"不能和自己聊天==");
                return;
            }
            ChatWindow chatWindow = ManageChatWindow.getChatWindow(mainPage.getUserId()+" "+commodity.getUserID());
            chatWindow.setVisible(true);
        }
    }

    @Override
    public String toString() {
        return "这是一条商品 商品名:" + commodity.getName() + " 商品号:" + commodity.getId();
    }
}
