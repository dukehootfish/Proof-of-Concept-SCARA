import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InverseKinematicsVisualizer extends JPanel implements ActionListener {
    private static final int TIMER_DELAY = 30;
    private static final double ARM_LENGTH1 = 100;
    private static final double ARM_LENGTH2 = 100;
    private double angle1 = 0;
    private double angle2 = 0;
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
        angle1 = 0; // Reset angle1 to 0 when shape changes
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angle1 += 0.01; // Increment angle1 in a constant way
        repaint();
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

        // Draw arms
        g2d.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2 + (int) x1, getHeight() / 2 + (int) y1);
        g2d.drawLine(getWidth() / 2 + (int) x1, getHeight() / 2 + (int) y1, getWidth() / 2 + (int) x2, getHeight() / 2 + (int) y2);

        // Draw joints
        g2d.fillOval(getWidth() / 2 + (int) x1 - 5, getHeight() / 2 + (int) y1 - 5, 10, 10);
        g2d.fillOval(getWidth() / 2 + (int) x2 - 5, getHeight() / 2 + (int) y2 - 5, 10, 10);

        // Draw target
        g2d.setColor(Color.RED);
        // Additional drawing logic...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InverseKinematicsVisualizer::new);
    }
}
