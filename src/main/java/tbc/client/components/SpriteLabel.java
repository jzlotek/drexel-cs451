package tbc.client.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class SpriteLabel extends JLabel {
    /*
     * Constructor for a SpriteLabel displaying a specific image from a path
     */
    public SpriteLabel(String _path) {
        super();
        setImage(_path);
    }

    /*
     * Constructor for a SpriteLabel displaying a specific image from a path and with a specific alignment
     */
    public SpriteLabel(String _path, int _align) {
        super();
        setImage(_path);
        setHorizontalAlignment(_align);
    }

    /*
     * Set the image to display
     */
    public void setImage(String _path) {
        InputStream stream = SpriteLabel.class.getResourceAsStream(_path);

        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(stream));
            setIcon(icon);

            setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        } catch (Exception e) {
            System.out.println("Encountered an error reading an image from file");
            e.printStackTrace();
        }
    }

    /*
     * Check whether the bounds of this label contain a given point
     */
    public boolean containsPoint(Point _point) {
        Rectangle rect = new Rectangle();
        rect.x = getLocationOnScreen().x;
        rect.y = getLocationOnScreen().y;
        rect.width = getWidth();
        rect.height = getHeight();

        return rect.contains(_point);
    }
}
