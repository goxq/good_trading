package client.ui.component;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.plaf.LabelUI;
import java.awt.*;

public class GGoodsLabel extends JLabel {
    int width = 1140;
    int height = 650;
    public GGoodsLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }

    public GGoodsLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public GGoodsLabel(String text) {
        super(text);
    }

    public GGoodsLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public GGoodsLabel(Icon image) {
        super(image);
    }

    public GGoodsLabel() {
        super();
    }
}
