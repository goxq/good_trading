package client.ui.seller.statistics;

import client.CScontrol;
import client.ui.MainPage;
import client.ui.component.GButton;
import client.ui.util.FontConfig;
import client.ui.util.MyColor;
import common.entity.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CountPanel extends JPanel {
    JPanel contentPanel;
    List<Order> orderList;
    MainPage mainPage;
    JPanel firstPanel;
    public CountPanel(MainPage mainPage){
        this.mainPage = mainPage;
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        GButton bt7 = new GButton("最近七天");
        bt7.addActionListener(e -> {
            try {
                upDate(mainPage.getUserId(),7);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        GButton btMonth = new GButton("最近一月");
        btMonth.addActionListener(e -> {
            try {
                upDate(mainPage.getUserId(),30);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        GButton btX = new GButton("最近x天");
        btX.addActionListener(e -> {
            try {
                JTextField jtf = new JTextField();
                jtf.setFont(FontConfig.font3);
                int result = JOptionPane.showConfirmDialog(mainPage,jtf,"请输入要查询的天数",JOptionPane.OK_CANCEL_OPTION);
                if(result == JOptionPane.OK_OPTION){
                    try{
                        int days = Integer.parseInt(jtf.getText().trim());
                        upDate(mainPage.getUserId(),days);
                    }catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(mainPage,"输入有误，请重新输入");
                    }
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        topPanel.add(bt7);
        topPanel.add(btMonth);
        topPanel.add(btX);
        topPanel.setBackground(MyColor.BLUE_300);
        this.add(topPanel,BorderLayout.NORTH);

        firstPanel = new JPanel(new BorderLayout());
        JLabel jLabel = new JLabel("请点击上方按钮来生成相应报表",JLabel.CENTER);
        jLabel.setFont(FontConfig.font1);
        firstPanel.add(jLabel,BorderLayout.CENTER);
        firstPanel.add(jLabel);
        this.add(firstPanel,BorderLayout.CENTER);
        validate();
    }

    public void upDate(String seller, int days) throws Exception {
        if(contentPanel != null){
            contentPanel.removeAll();
        }
        this.remove(firstPanel);
        this.orderList = CScontrol.getOrdersByDate(seller,days);
        contentPanel = new JPanel();
        if(orderList.size()==0){
            contentPanel.setLayout(new BorderLayout());
            validate();
            JLabel jLabel = new JLabel("暂无数据",JLabel.CENTER);
            jLabel.setFont(FontConfig.font1);
            contentPanel.add(jLabel,BorderLayout.CENTER);
            this.add(contentPanel);
            validate();
        }else {
            contentPanel.setLayout(new BorderLayout());
            validate();
            CountTable cTable = new CountTable(this.getTableModel());
            JScrollPane jsp = new JScrollPane(cTable);
            jsp.getVerticalScrollBar().setUnitIncrement(15);
            contentPanel.add(jsp,BorderLayout.CENTER);
            this.add(contentPanel);
            validate();
        }
    }
    public DefaultTableModel getTableModel() {
        Object[][] tableContent = null;
        try {
            //orderList = CScontrol.getOrdersByDate(seller,days);
            tableContent = new Object[orderList.size()][6];
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < orderList.size(); i++) {
                Order order = orderList.get(i);
                if(order.getIsAuction()==0){
                    tableContent[i][0] = order.getName();
                    tableContent[i][1] = order.getPrice();
                    tableContent[i][2] = "----";
                    tableContent[i][3] = order.getNums();
                    tableContent[i][4] = "不拍卖";
                    tableContent[i][5] = formatter.format(orderList.get(i).getBuyDate());
                }else {
                    tableContent[i][0] = order.getName();
                    tableContent[i][1] = order.getPrice();
                    tableContent[i][2] = order.getAuctionPrice();
                    tableContent[i][3] = order.getNums();
                    tableContent[i][4] = "拍卖";
                    tableContent[i][5] = formatter.format(orderList.get(i).getBuyDate());
                }
            }

        } catch (Exception exc) {
           exc.printStackTrace();
        }
        Object tableName[] = {"商品名称", "原价格","拍卖价格", "订单数量", "是否拍卖","订单时间"};
        DefaultTableModel tableModel = new DefaultTableModel(tableContent,tableName);
        return tableModel;
    }
}
