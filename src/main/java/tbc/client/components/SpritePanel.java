package tbc.client.components;

import java.awt.*;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SpritePanel extends JPanel
{
    ImageIcon image;

    /*
     * Constructor for a panel displaying the given image
     */
    public SpritePanel(String _path)
    {
        super();
        setImage(_path);
    }

    /*
     * Get the image displayed on this panel
     */
    public ImageIcon getImage()
    {
        return image;
    }

    /*
     * Set the image to display
     */
    public void setImage(String _path)
    {
        InputStream stream = SpriteLabel.class.getResourceAsStream(_path);

        try
        {
            image = new ImageIcon(ImageIO.read(stream));

            setBounds(new Rectangle(image.getIconWidth(), image.getIconHeight()));

            System.out.println("Image Icon Dimensions: (" + image.getIconWidth() + ", " + image.getIconHeight() + ")");
        }
        catch (Exception e)
        {
            System.out.println("Encountered an error reading an image from file");
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        image.paintIcon(this, g, 0, 0);
    }
}
