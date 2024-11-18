import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InverseKinematicsVisualizer extends JPanel implements ActionListener {
    private static final int TIMER_DELAY = 30;
    private static final double ARM_LENGTH1 = 300;
    private static final double ARM_LENGTH2 = 200;
    private static double ARM_LENGTH3 = 0;
    private double angle1 = 0;
    private double angle2 = 0;
    private double inBetween = 0;
    private double targetX = 0;
    private double targetY = 0;
    private double time = 0;
    private double radius = 150; // Default radius of the circle
    private boolean drawCircle = true; // Default to drawing a circle

    private JTextField radiusField;

    public InverseKinematicsVisualizer() {
        Timer timer = new Timer(TIMER_DELAY, this);
        timer.start();

        JFrame frame = new JFrame("2-Joint Inverse Kinematics Visualizer");
        frame.setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Radius:"));
        radiusField = new JTextField(String.valueOf(radius), 5);
        controlPanel.add(radiusField);
        JButton setRadiusButton = new JButton("Set Radius");
        setRadiusButton.addActionListener(e -> setRadius());
        controlPanel.add(setRadiusButton);

        JButton switchShapeButton = new JButton("Switch Shape");
        switchShapeButton.addActionListener(e -> switchShape());
        controlPanel.add(switchShapeButton);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void setRadius() {
        try {
            radius = Double.parseDouble(radiusField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number");
        }
    }

    private void switchShape() {
        drawCircle = !drawCircle;
        angle1 = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate joint positions
        double x1 = ARM_LENGTH1 * Math.cos(angle1);
        double y1 = ARM_LENGTH1 * Math.sin(angle1);
        double x2 = x1 + ARM_LENGTH2 * Math.cos(angle1 + angle2);
        double y2 = y1 + ARM_LENGTH2 * Math.sin(angle1 + angle2);
        // Draw length
        g2d.drawString(""+ARM_LENGTH3,(getWidth() / 2)-80, (getHeight() / 2)-80);

        // Draw arms
        g2d.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2 + (int) x1, getHeight() / 2 + (int) y1);
        g2d.drawLine(getWidth() / 2 + (int) x1, getHeight() / 2 + (int) y1, getWidth() / 2 + (int) x2, getHeight() / 2 + (int) y2);
        g2d.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2 + (int) targetX, getHeight() / 2 + (int) targetY);

        // Draw joints
        g2d.fillOval(getWidth() / 2 + (int) x1 - 5, getHeight() / 2 + (int) y1 - 5, 10, 10);
        g2d.fillOval(getWidth() / 2 + (int) x2 - 5, getHeight() / 2 + (int) y2 - 5, 10, 10);

        // Draw shapes
        if (drawCircle) {
            g2d.drawArc(getWidth()/2-(int)radius,getHeight()/2-(int)radius,(int)radius*2,(int)radius*2,0,360);
        }
        else{
            g2d.drawRect(getWidth()/2-(int)radius,getHeight()/2-(int)radius,(int)radius*2,(int)radius*2);
        }

        // Draw target
        g2d.setColor(Color.RED);
        g2d.fillOval(getWidth() / 2 + (int) targetX - 5, getHeight() / 2 + (int) targetY - 5, 10, 10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angle1 += 0.03;
        System.out.println("Before: " +angle1);
        angle1 = angle1 % (Math.PI*2);
        System.out.println("After: " +angle1);
        if(drawCircle) {
            ARM_LENGTH3=radius;
        }
        else if((Math.PI*(1.75)) < angle1+inBetween || angle1+inBetween < Math.PI/4 ) {
            targetX = radius;

            targetY = radius*Math.sin(angle1+inBetween);


            ARM_LENGTH3=Math.sqrt(targetX*targetX+targetY*targetY);
            System.out.println("Test: " +ARM_LENGTH3);

        }
        inBetween = Math.acos((ARM_LENGTH3*ARM_LENGTH3+ARM_LENGTH1*ARM_LENGTH1-ARM_LENGTH2*ARM_LENGTH2)/(2*ARM_LENGTH3*ARM_LENGTH1));
        //targetX = ARM_LENGTH3*Math.cos(inBetween+angle1);
        //targetY = ARM_LENGTH3*Math.sin(inBetween+angle1);
        angle2 =Math.PI-Math.acos((ARM_LENGTH2*ARM_LENGTH2+ARM_LENGTH1*ARM_LENGTH1-ARM_LENGTH3*ARM_LENGTH3)/(2*ARM_LENGTH2*ARM_LENGTH1));


        /*if (drawCircle) {
            // Update target position (circle)
            targetX = radius * Math.cos(time);
            targetY = radius * Math.sin(time);
        } else {
            // Update target position (square)
            double t = time % (4 * Math.PI);
            if (t < Math.PI) {
                targetX = radius * Math.cos(t);
                targetY = radius;
            } else if (t < 2 * Math.PI) {
                targetX = -radius;
                targetY = radius * Math.cos(t - Math.PI);
            } else if (t < 3 * Math.PI) {
                targetX = -radius * Math.cos(t - 2 * Math.PI);
                targetY = -radius;
            } else {
                targetX = radius;
                targetY = radius * -Math.cos(t - 3 * Math.PI);

            }
        }

        // Inverse kinematics calculations
        double dx = targetX;
        double dy = targetY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        angle2 = Math.acos((distance * distance - ARM_LENGTH1 * ARM_LENGTH1 - ARM_LENGTH2 * ARM_LENGTH2) / (2 * ARM_LENGTH1 * ARM_LENGTH2));
        angle1 = Math.atan2(dy, dx) - Math.atan2(ARM_LENGTH2 * Math.sin(angle2), ARM_LENGTH1 + ARM_LENGTH2 * Math.cos(angle2));*/

        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InverseKinematicsVisualizer::new);
    }
}