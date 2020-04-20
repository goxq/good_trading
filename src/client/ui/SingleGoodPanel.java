package client.ui;

import client.CScontrol;
import client.ui.util.FontConfig;
import client.utils.CodecUtil;
import client.ui.component.GButton;
import client.ui.util.GBC;
import client.ui.util.MyColor;
import common.entity.Comment;
import common.entity.Commodity;
import common.entity.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        JButton bt_back = new JButton("返回");
        bt_back.addActionListener(e -> {
            this.mainPage.showPanel("jp_gds");
            this.mainPage.deletePanel(this);
        });
        topPanel.add(bt_back);
        topPanel.setBackground(MyColor.GREEN_A200);

        JPanel leftPanel = new JPanel();
        JButton bt1 = new JButton("嘿嘿");
        leftPanel.add(bt1);
        leftPanel.setBackground(MyColor.RED_200);

        JPanel rightPanel = new JPanel();
        JButton bt2 = new JButton("哈哈");
        rightPanel.add(bt2);
        rightPanel.setBackground(MyColor.PINK_200);

        JPanel bottomPanel = new JPanel();
        JButton bt3 = new JButton("唉");
        bottomPanel.add(bt3);
        bottomPanel.setBackground(MyColor.PURPLE_A700);

        GridBagLayout gbl = new GridBagLayout();
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(gbl);

        JPanel goodInfoPanel = new JPanel(new BorderLayout());
        JPanel imgPanel = new JPanel();
        //imgPanel.setPreferredSize(new Dimension(350,350));
        imgPanel.setBackground(MyColor.RED_200);
        JLabel imgLabel = new JLabel();
        ImageIcon img = new ImageIcon(commodity.getPicPath());
        img.setImage(img.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
        imgLabel.setIcon(img);
        imgPanel.add(imgLabel);

        JPanel textPanel = new JPanel(new GridLayout(5, 1, 0, 5));
        textPanel.setBackground(MyColor.YELLOW_500);
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
        sellerAndTimePanel.setForeground(MyColor.exit_font);


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
        List<Comment> commentList = commodity.getCommentList();
        JPanel commentPanel = new JPanel(new GridLayout(commentList.size(), 1, 0, 0));
        for (int i = 0; i < commentList.size(); i++) {
            Comment comment = commentList.get(i);
            JPanel eachCommentPanel = new JPanel(new GridLayout(2,1,0,0));
            JTextArea contentText = new JTextArea(comment.getContent());
            contentText.setLineWrap(true);
            contentText.setEnabled(false);
            contentText.setFont(FontConfig.font3);

            String postDate = formatter.format(comment.getDate());
            JLabel posterAndDate = new JLabel("发布者:"+comment.getUserID()+"      发布时间:"+postDate,JLabel.RIGHT);
            posterAndDate.setFont(new Font("微软雅黑", Font.PLAIN,15));
            posterAndDate.setForeground(MyColor.GRAY_400);

            eachCommentPanel.add(contentText);
            eachCommentPanel.add(posterAndDate);
            commentPanel.add(eachCommentPanel);
        }
        centerPanel.add(commentPanel, new GBC(0, 3, 1, 1)
                .setFill(GBC.BOTH).setWeight(50, 100));

        centerPanel.setBackground(MyColor.BROWN_400);
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
                JOptionPane.showMessageDialog(this, "不能购买自己发布的商品！:", "提示", JOptionPane.WARNING_MESSAGE);
            }else {
                String nums = JOptionPane.showInputDialog(this, "请输入购买数量:", "购买确认", JOptionPane.PLAIN_MESSAGE);
                if (Integer.parseInt(nums) <= commodity.getNums()) {
                    if (commodity.getIsAuction() == 0) {//下面是非拍卖的购买操作
                        //订单类里面的图 在server端设置为服务器的路径
                        Order order = new Order(CodecUtil.createOrderId(), commodity.getId(), mainPage.getUserId(), commodity.getUserID(),
                                commodity.getPrice(), commodity.getName(), Integer.parseInt(nums), 0, new Date());
                        try {
                            int a = CScontrol.BuyToServer(order);
                            if (a == 1) {
                                JOptionPane.showMessageDialog(this, "购买成功");
                            } else {
                                JOptionPane.showMessageDialog(this, "购买失败");
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "这是一条商品 商品名:" + commodity.getName() + " 商品号:" + commodity.getId();
    }


}
