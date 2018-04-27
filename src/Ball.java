import java.awt.*;

public class Ball {
    private Game game;

    private double x = 0;
    private double y = 0;
    private int width = 0;
    private int height = 0;

    private double velX = 4;
    private double velY = 4;

    private Color color;

    public Ball(Game game, double x, double y, int height, int width, Color color) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void update(double delta) {
        x += velX * delta;
        y += velY * delta;

        if(x - width / 2 < 0 || x + width / 2 > game.getWidth())
            velX *= -1;

        if(y - height / 2 < 0 || y + height / 2 > game.getHeight())
            velY *= -1;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        //g2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[] { 7.9f }, 0));
        g2.drawOval((int) (x - width / 2), (int) (y - height / 2), width, height);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
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

    public int getRadius() {
        return width / 2;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}