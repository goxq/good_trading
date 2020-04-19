package client.ui.component;

import client.ui.util.MyColor;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GButton extends JButton implements MouseListener {
    public GButton(String text) {
        super(text);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(MyColor.exit_background);
        this.setFont(new Font("微软雅黑", Font.BOLD,15));
        this.setForeground(MyColor.WHITE);
        this.addMouseListener(this);

    }
    public GButton(String text,int width,int height,int font_size) {
        super(text);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(MyColor.exit_background);
        this.setFont(new Font("微软雅黑", Font.BOLD,font_size));
        this.setForeground(MyColor.exit_font);
        this.setPreferredSize(new Dimension(width,height));
        this.addMouseListener(this);

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setForeground(MyColor.enter_font);
        this.setBackground(MyColor.enter_background);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setForeground(MyColor.exit_font);
        this.setBackground(MyColor.exit_background);
    }
}
