package client.ui.component;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;

public class GBackGroundPanel extends JPanel {

    public GBackGroundPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public GBackGroundPanel(LayoutManager layout) {
        super(layout);
    }

    public GBackGroundPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public GBackGroundPanel() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("images/bkg.png").getImage(),0,0,this.getWidth(),this.getHeight(),null);
    }
}
