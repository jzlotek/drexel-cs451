package tbc.client.components;

import javax.swing.*;
import java.awt.*;

public class SpriteLabel extends JLabel
{
    /*
     * Constructor for a SpriteLabel displaying a specific ImageIcon
     */
    public SpriteLabel(ImageIcon _image)
    {
        super(_image);
        setImage(_image);
    }

    /*
     * Constructor for a SpriteLabel displaying a specific ImageIcon and with the given alignment
     */
    public SpriteLabel(ImageIcon _image, int _align)
    {
        super(_image, _align);
        setImage(_image);
    }

    /*
     * Set the image to display
     */
    public void setImage(ImageIcon _image)
    {
        setIcon(_image);

        setMinimumSize(new Dimension(_image.getIconWidth(), _image.getIconHeight()));
        setPreferredSize(new Dimension(_image.getIconWidth(), _image.getIconHeight()));
        setMaximumSize(new Dimension(_image.getIconWidth(), _image.getIconHeight()));
    }

    /*
     * Check whether the bounds of this label contain a given point
     */
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
