package client.ui.seller.statistics;

import client.ui.util.MyColor;
import common.entity.Order;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

public class CountTable extends JTable {
    List<Order> orderList;
    Object tableContent[][];

    public CountTable(TableModel dm) {
        super(dm);
        setRowHeight(30);
        setFont(new Font("微软雅黑",Font.PLAIN,14));
        setSelectionBackground(Color.lightGray);
        JTableHeader header = this.getTableHeader();
        header.setFont(new Font("微软雅黑", Font.BOLD,15));
        header.setReorderingAllowed(false);
        header.setBackground(MyColor.BLUE_100);
        this.setBackground(MyColor.BLUE_50);
    }

    @Override
    public boolean isCellEditable(int row, int column) {                // 表格不可编辑
        return false;
    }
}