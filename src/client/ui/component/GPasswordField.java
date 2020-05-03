package client.ui.component;

import client.ui.util.MyColor;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GPasswordField extends JPasswordField implements MouseListener {
    Color border_color = MyColor.BLUE_A700;
    public GPasswordField(String text, int width, int height) {
        super(text, width);
        this.setOpaque(false);
        this.setFont(new Font("微软雅黑", Font.PLAIN,15));
        this.setForeground(MyColor.WHITE);
        this.setPreferredSize(new Dimension(0,height));
        this.addMouseListener(this);
    }
    public GPasswordField(int width, int height) {
        super(width);
        this.setOpaque(false);
        this.setFont(new Font("微软雅黑", Font.PLAIN,15));
        this.setPreferredSize(new Dimension(0,height));
        this.addMouseListener(this);
    }

    @Override
    public void paintBorder(Graphics g) {
        super.paintComponents(g);
        g.setColor(this.border_color);
        g.fillRect(0,this.getHeight()-6,this.getWidth(),3);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.setText("");
        this.setForeground(MyColor.BLACK);
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.border_color = MyColor.enter_background;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.border_color = MyColor.BLUE_A700;
        repaint();
    }
}
