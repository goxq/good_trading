package client.ui.old;


import client.ui.old.MainPage_old;

import javax.swing.*;

public class TableRefreshThread_old implements Runnable{
    @Override
    public void run() {
        try {
            while (true) {
                if(MainPage_old.connectionStatus==0){
                    JOptionPane.showMessageDialog(null, "无法连接到服务器！");
                }
                Thread.sleep(10000);
                MainPage_old.table.setModel(MainPage_old.getTableModel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

