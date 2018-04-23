import java.awt.*;

public class Board {
    private double x = 0;
    private double y = 0;
    private int width = 0;
    private int height = 0;
    private double angle = 0;

    private int sideThickness = 0;

    private RectAngle leftSide;
    private RectAngle rightSide;
    private RectAngle bottomSide;

    private int sideOffset = 25;
    private int leftOffsetX = 0;
    private int leftOffsetY = 0;
    private int rightOffsetX = 0;
    private int rightOffsetY = 0;
    private int bottomOffsetX = 0;
    private int bottomOffsetY = 0;

    public boolean DEBUG = false;
    private RectAngle outline;

    public Board(double x, double y, int width, int height, double angle, int sideThickness) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.sideThickness = sideThickness;

        bottomOffsetX = 0;
        bottomOffsetY = sideThickness;
        leftOffsetX = (int) (-sideOffset / 2);
        leftOffsetY = (int) (-sideOffset * Math.sqrt(3) / 2);
        rightOffsetX = (int) (sideOffset / 2);
        rightOffsetY = (int) (-sideOffset * Math.sqrt(3) / 2);

        bottomSide = new RectAngle(x + bottomOffsetX, (y + (height / 2) - (sideThickness / 2)) + bottomOffsetY, width, sideThickness, 0, new Color(211, 49, 52));
        leftSide = new RectAngle((x - width / 4) + leftOffsetX, y + leftOffsetY, width, sideThickness, -60, new Color(42, 217, 54));
        rightSide = new RectAngle((x + width / 4) + rightOffsetX, y + rightOffsetY, width, sideThickness, 60, new Color(48, 47, 215));

        outline = new RectAngle(x, y, width, height, angle, Color.red);
        outline.setFill(false);
    }

    public void draw(Graphics2D g2) {
        bottomSide.draw(g2);
        leftSide.draw(g2);
        rightSide.draw(g2);

        if(DEBUG) outline.draw(g2);
    }

    public int getOrthocenterX() {
        return (int) x;
    }

    public int getOrthocenterY() {
        return (int) (y - height / 2) + (width / 2);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;

        bottomSide.setX(x + bottomOffsetX);
        leftSide.setX((x - width / 4) + leftOffsetX);
        rightSide.setX((x + width / 4) + rightOffsetX);
        outline.setX(x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;

        bottomSide.setY((y + (height / 2) - (sideThickness / 2)) + bottomOffsetY);
        leftSide.setY(y + leftOffsetY);
        rightSide.setY(y + rightOffsetY);
        outline.setY(y);
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

    public RectAngle getLeftSide() {
        return leftSide;
    }

    public RectAngle getRightSide() {
        return rightSide;
    }

    public RectAngle getBottomSide() {
        return bottomSide;
    }

    public int getSideThickness() {
        return sideThickness;
    }
}
