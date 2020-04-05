package client.ui;

import client.CScontrol;
import com.sun.tools.javac.Main;
import common.entity.Commodity;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TableRefreshThread implements Runnable{
    @Override
    public void run() {
        try {
            while (true) {
                if(MainPage.connectionStatus==0){
                    JOptionPane.showMessageDialog(null, "无法连接到服务器！");
                }
                Thread.sleep(10000);
                MainPage.table.setModel(MainPage.getTableModel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

