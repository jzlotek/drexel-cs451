package tbc.client.components;

import javax.swing.*;
import java.awt.*;

public class SpriteLabel extends JLabel
{
    public SpriteLabel(ImageIcon _image)
    {
        super(_image);
        setImage(_image);
    }

    public SpriteLabel(ImageIcon _image, int _align)
    {
        super(_image, _align);
        setImage(_image);
    }

    public void setImage(ImageIcon _image)
    {
        setIcon(_image);

        setMinimumSize(new Dimension(_image.getIconWidth(), _image.getIconHeight()));
        setPreferredSize(new Dimension(_image.getIconWidth(), _image.getIconHeight()));
        setMaximumSize(new Dimension(_image.getIconWidth(), _image.getIconHeight()));
    }

    public boolean containsPoint(Point _point)
    {
        Rectangle rect = new Rectangle();
        rect.x = getLocationOnScreen().x;
        rect.y = getLocationOnScreen().y;
        rect.width = getWidth();
        rect.height = getHeight();

        return rect.contains(_point);
    }
}
