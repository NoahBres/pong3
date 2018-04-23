import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {
    private Display display;
    private boolean running = false;

    private long lastFpsTime = 0;
    private int fps = 0;
    private int fpsShow = 0;

    private Color bgColor = new Color(52, 54, 58);

    private Board board;
    private Paddle p1;
    private Ball ball;

    private boolean leftArrowPressed = false;
    private boolean rightArrowPressed = false;

    private int boardSideLength = 600;

    Game() {
        display = new Display(this);

        board = new Board(display.getWidth() / 2, display.getHeight() / 2,
                            boardSideLength, (int) (boardSideLength / 2 * Math.sqrt(3)),
                        0, 10);
        //board.setY(board.getY() + board.getY() - board.getOrthocenterY());
        //board.DEBUG = true;
        ball = new Ball(this, board.getOrthocenterX(), board.getOrthocenterY(), 30, 30, Color.white);
        p1 = new Paddle(board.getBottomSide().getX(),
                    (board.getBottomSide().getY() - board.getSideThickness() / 2 + 4) - 20,
                    80, 8,
                    0, new Color(255, 135, 135),
                    (int) (board.getBottomSide().getX() - board.getBottomSide().getWidth() * .8 / 2),
                    (int) (board.getBottomSide().getX() + board.getBottomSide().getWidth() * .8 / 2));
    }

    public void start() {
        if(running) return;

        running = true;
        loop();
    }

    public void loop() {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long TARGET_TIME = 1000000000 / TARGET_FPS;

        while(running) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) TARGET_TIME);

            lastFpsTime += updateLength;
            fps++;

            if(lastFpsTime >= 1000000000) {
                fpsShow = fps;
                lastFpsTime = 0;
                fps = 0;
            }

            update(delta);
            display.repaint();

            try {
                Thread.sleep(
                    (lastLoopTime - System.nanoTime() + TARGET_TIME) / 1000000
                );
            } catch(Exception e) {

            }
        }
    }

    public void update(double delta) {
        ball.update(delta);

        if(leftArrowPressed) p1.moveLeft(delta);
        if(rightArrowPressed) p1.moveRight(delta);
    }

    public void render(Graphics g) {
        g.setColor(bgColor);
        g.fillRect(0, 0, display.getWidth(), display.getHeight());

        if(g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            g2.setColor(Color.WHITE);
            g2.setFont(new Font(g2.getFont().getFontName(), Font.PLAIN, 20));
            g2.drawString(Integer.toString(fpsShow), display.getWidth() - 40, 30);

            //g2.rotate(Math.toRadians(45), display.getWidth() / 2, display.getHeight() / 2);

            board.draw(g2);
            p1.draw(g2);
            ball.draw(g2);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                leftArrowPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightArrowPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                leftArrowPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightArrowPressed = false;
                break;
        }
    }

    public int getWidth() {
        return display.getWidth();
    }

    public int getHeight() {
        return display.getHeight();
    }

    private double findDistance(double fromX, double fromY, double toX, double toY) {
        double a = Math.abs(fromX - toX);
        double b = Math.abs(fromY - toY);

        return Math.sqrt((a * a) + (b * b));
    }

    private boolean isCircleAndRectangleCollided(int circleX, int circleY,
                                                 int circleWidth, int circleHeight,
                                                 int circleRadius,
                                                 int rectX, int rectY,
                                                 int rectWidth, int rectHeight,
                                                 int rectAngle) {

        double unrotatedCircleX = Math.cos(rectAngle) * (circleX - rectX) -
                                  Math.sin(rectAngle) * (circleY - rectY) + rectX;
        double unrotatedCircleY = Math.sin(rectAngle) * (circleX - rectX) +
                                  Math.cos(rectAngle) * (circleY - rectY) + rectY;

        double closestX, closestY;

        if(unrotatedCircleX < rectX) closestX = rectX;
        else if(unrotatedCircleX > rectX + rectWidth) closestX = rectX + rectWidth;
        else closestX = unrotatedCircleX;

        if(unrotatedCircleY < rectY) closestY = rectY;
        else if(unrotatedCircleY > rectY + rectHeight) closestY = rectY + rectHeight;
        else closestY = unrotatedCircleY;

        boolean collision = false;

        double distance = findDistance(unrotatedCircleX, unrotatedCircleY, closestX, closestY);

        return distance < circleRadius;
    }
}
