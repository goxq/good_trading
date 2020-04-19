package client.ui.component;

import client.ui.util.MyColor;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;

public class GTextField extends JTextField implements MouseListener {
    Color border_color = MyColor.BLUE_A700;
    public GTextField() {
        super();
    }

    public GTextField(String text) {
        super(text);
    }

    public GTextField(int columns) {
        super(columns);
    }

    public GTextField(String text, int width, int height) {
        super(text, width);
        this.setOpaque(false);

        this.setFont(new Font("微软雅黑", Font.PLAIN,15));
        this.setForeground(MyColor.WHITE);
        this.setPreferredSize(new Dimension(0,height));
        this.addMouseListener(this);
    }

    public GTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
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
