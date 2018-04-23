import java.awt.*;

public class Paddle extends RectAngle {
    private double speed = 8;
    private int leftLimit = 0;
    private int rightLimit = 0;

    public Paddle(double x, double y, int width, int height, double angle, Color color, int leftLimit, int rightLimit) {
        super(x, y, width, height, angle, color);

        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }

    public void moveLeft(double delta) {
        x -= speed * delta;
        x = Math.max(x, leftLimit);
    }

    public void moveRight(double delta) {
        x += speed * delta;
        x = Math.min(x, rightLimit);
    }
}
