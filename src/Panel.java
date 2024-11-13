import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Panel extends JPanel implements ActionListener {
    Vector v = new Vector(100,0);
    Vector v2 = new Vector(50,0);

    int diameter = 250;
    Vector v3 = new Vector(diameter/2,0);
    final int width = 1000;
    final int height = 1000;
    Timer timer;
    int x = 150, y = 150;
    Panel() {
        this.setPreferredSize(new Dimension(width, height));
        timer = new Timer(100, this);
        timer.start();
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(500,500,500+(int)v.getX(),500+(int)v.getY());
        g2d.drawLine(500+(int)v.getX(),500+(int)v.getY(),500+(int)v.getX()+(int)v2.getX(),500+(int)v.getY()+(int)v2.getY());
        g2d.drawArc(375,375, diameter, diameter,0,360);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        v.setTheta(v.getTheta()+(Math.PI/50));
        repaint();
    }
}
