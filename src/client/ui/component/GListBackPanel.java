package client.ui.component;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;

public class GListBackPanel extends JPanel {
    public GListBackPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public GListBackPanel(LayoutManager layout) {
        super(layout);
    }

    public GListBackPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public GListBackPanel() {
        super();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon ii = new ImageIcon("images/listbkg.png");
        g.drawImage(ii.getImage(), 0, 0, this.getWidth()-10, this.getHeight(),this);
//        System.out.println(this.getWidth());
    }
}
