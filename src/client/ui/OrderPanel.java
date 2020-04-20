package client.ui;

import client.CScontrol;
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

public class OrderPanel extends JPanel implements ActionListener {
    Order order;
    MainPage mainPage;
    public OrderPanel(Order order, MainPage mainPage) {
        this.order = order;
        this.mainPage = mainPage;
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JButton bt_back = new JButton("返回");
        bt_back.addActionListener(e -> {
            this.mainPage.showPanel("jp_al_buy");
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
        ImageIcon img = new ImageIcon(order.getPicPath());
        img.setImage(img.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
        imgLabel.setIcon(img);
        imgPanel.add(imgLabel);

        JPanel textPanel = new JPanel(new GridLayout(5, 1, 0, 5));
        textPanel.setBackground(MyColor.YELLOW_500);
        textPanel.setPreferredSize(new Dimension(519, 300));
        JLabel nameLabel = new JLabel(order.getName(), JLabel.CENTER);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));

        JLabel priceAndNumsPanel = new JLabel("价格:" + order.getPrice() + "    购买量:" + order.getNums(), JLabel.CENTER);
        priceAndNumsPanel.setFont(new Font("微软雅黑", Font.PLAIN, 15));


        JLabel auctionLabel = new JLabel("该商品为: " + ((order.getIsAuction() == 1) ? "拍卖" : "直售") + " 类型", JLabel.CENTER);
        auctionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        GButton commentButton = new GButton("你已购买 发表评论！");
        commentButton.addActionListener(this);
        commentButton.setActionCommand("commentButton");
        commentButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        GButton chatButton = new GButton("联系卖家");
        chatButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        commentButton.setPreferredSize(new Dimension(255, 20));
        chatButton.setPreferredSize(new Dimension(255, 20));
        buttonPanel.add(commentButton, BorderLayout.WEST);
        buttonPanel.add(chatButton, BorderLayout.EAST);

        JLabel sellerAndTimePanel = new JLabel();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(order.getBuyDate());
        sellerAndTimePanel.setText("卖家:" + order.getSellerID() + "  我的下单时间:" + dateString);
        sellerAndTimePanel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        sellerAndTimePanel.setForeground(MyColor.exit_font);


        textPanel.add(nameLabel);
        textPanel.add(priceAndNumsPanel);
        textPanel.add(auctionLabel);
        textPanel.add(buttonPanel);
        textPanel.add(sellerAndTimePanel);

        goodInfoPanel.add(imgPanel, BorderLayout.WEST);
        goodInfoPanel.add(textPanel, BorderLayout.EAST);
        centerPanel.add(goodInfoPanel, new GBC(0, 0, 1, 2)
                .setFill(GBC.BOTH).setWeight(50, 100));


        JPanel commentPanel = new JPanel(new GridLayout(10, 1, 0, 0));
        for (int i = 0; i < 10; i++) {
            JPanel btp = new JPanel();
            JButton tsb = new JButton(i + "");
            btp.add(tsb);
            commentPanel.add(btp);
        }
        centerPanel.add(commentPanel, new GBC(0, 2, 1, 1)
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
        if(e.getActionCommand().equals("commentButton")){
            JTextArea jta = new JTextArea();
            jta.setPreferredSize(new Dimension(550,200));
            jta.setFont(FontConfig.font3);
            jta.setLineWrap(true);
            JScrollPane jsp = new JScrollPane(jta);
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
        }
    }
    @Override
    public String toString() {
        return "这是一条订单 商品名:" + order.getName() + " 订单号:" + order.getOrderID();
    }
}
