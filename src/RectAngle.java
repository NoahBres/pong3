import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class RectAngle {
    protected double x = 0;
    protected double y = 0;
    protected int width = 0;
    protected int height = 0;
    protected double angle = 0;
    protected Color color = Color.WHITE;
    protected boolean fill = true;

    public RectAngle(double x, double y, int width, int height, double angle, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.color = color;
    }

    public void draw(Graphics2D g2) {
        Rectangle r = new Rectangle((int) x - width / 2, (int) y - height / 2, width, height);
        Path2D.Double path = new Path2D.Double();
        path.append(r, false);

        AffineTransform t = new AffineTransform();
        t.rotate(Math.toRadians(angle), x, y);
        path.transform(t);
        g2.setColor(color);
        if(fill) g2.fill(path);
        else g2.draw(path);
    }

    public int getX() {
        return (int) x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getY() {
        return (int) y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int leftCornerX() {
        return (int) x - width / 2;
    }

    public int leftCornerY() {
        return (int) y - height / 2;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }
}
