package client.ui.component;

import client.ui.util.MyColor;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GButtonItem extends JButton implements MouseListener {
    public GButtonItem() {
        super();
    }

    public GButtonItem(Icon icon) {
        super(icon);
    }

    public GButtonItem(String text) {
        super(text);
    }

    public GButtonItem(Action a) {
        super(a);
    }

    public GButtonItem(String text, Icon icon) {
        super(text, icon);
        this.setBackground(MyColor.exit_background);
        this.setForeground(MyColor.exit_font);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
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

    }
}
