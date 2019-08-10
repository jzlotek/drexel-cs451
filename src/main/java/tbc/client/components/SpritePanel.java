package tbc.client.components;

import java.awt.*;

import javax.swing.*;

public class SpritePanel extends JPanel
{
    ImageIcon image;

    public SpritePanel(ImageIcon _image)
    {
        super();
        setImage(_image);
    }

    public ImageIcon getImage()
    {
        return image;
    }

    public void setImage(ImageIcon _image)
    {
        image = _image;

        //setSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
        setBounds(new Rectangle(image.getIconWidth(), image.getIconHeight()));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        image.paintIcon(this, g, 0, 0);
    }
}
