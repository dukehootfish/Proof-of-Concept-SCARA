public class Vector {

    private double x;
    private double y;
    private double r;
    private double theta;

    public Vector(double r, double theta){
        this.r = r;
        this.theta = theta;
        this.x = r * Math.cos(theta);
        this.y = r * Math.sin(theta);

    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getR() {
        return r;
    }
    public double getTheta() {
        return theta;
    }

    public void setX(double x) {
        this.x = x;
        theta = Math.atan2(x, y);
        r = Math.sqrt(x * x + y * y);

    }
    public void setY(double y) {
        this.y = y;
        theta = Math.atan2(x, y);
        r = Math.sqrt(x * x + y * y);
    }
    public void setR(double r) {
        this.r = r;
        this.x = r * Math.cos(theta);
        this.y = r * Math.sin(theta);

    }
    public void setTheta(double theta) {
        this.theta = theta;
        this.x = r * Math.cos(theta);
        this.y = r * Math.sin(theta);
    }
}
