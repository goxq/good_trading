package client.ui.component;

import client.CScontrol;
import client.ui.buyer.GdsPanel;
import client.ui.MainPage;
import client.ui.util.FontConfig;
import client.ui.util.MyColor;
import common.entity.Commodity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SearchField extends JPanel {
    GdsPanel gdsPanel;
    MainPage mainPage;

    public SearchField(GdsPanel gdsPanel, MainPage mainPage) {
        this.gdsPanel = gdsPanel;
        this.mainPage = mainPage;
        this.setLayout(null);
        JTextField tf = new JTextField();
        tf.setOpaque(false);
        tf.setBounds(33, 0, 190, 65);
        tf.setBorder(null);
        tf.setFont(new Font("微软雅黑", Font.ITALIC, 15));
        tf.setForeground(MyColor.GRAY_400);
        tf.setText("输入商品名按下回车以搜索");
        tf.setFocusable(false);

        tf.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tf.setFocusable(true);
            }
        });
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (mainPage.getCurrentPanel().equals("jp_gds")) {
                        String name = ((JTextField) e.getSource()).getText().trim();
                        try {
                            List<Commodity> commodityList = CScontrol.getSpecificCommodity(name, mainPage.getUserId());
                            System.out.println("list大小：" + commodityList.size());
                            gdsPanel.upDate(commodityList);
                            mainPage.showPanel("jp_gds");

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }else
                        JOptionPane.showMessageDialog(mainPage,"搜索功能暂只支持全部商品");
                }
            }
        });
        tf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                tf.setText("");
                tf.setEnabled(true);
                tf.setFont(FontConfig.font3);
                tf.setForeground(MyColor.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                tf.setFont(new Font("微软雅黑", Font.ITALIC, 15));
                tf.setForeground(MyColor.GRAY_400);
                tf.setText("输入商品名按下回车以搜索");
            }
        });
        //tf.setPreferredSize(new Dimension(20,20));

        this.add(tf);
        this.setBackground(new Color(210, 226, 237));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ImageIcon img = new ImageIcon("images/search.png");
        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
