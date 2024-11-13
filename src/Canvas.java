import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class Canvas extends JComponent {
    private int width;
    private int height;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
    }
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle2D.Double rect = new Rectangle2D.Double(50,75,100,250);
        g2d.setColor(Color.BLACK);
        g2d.fill(rect);
    }
}
