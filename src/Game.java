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
    private Paddle p2;
    private Paddle p3;
    private Ball ball;

    private boolean leftArrowPressed = false;
    private boolean rightArrowPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;

    private int paddleHeight = 8;
    private int boardSideLength = 600;
    private double boardPaddleLimits = 0.8;
    private int paddleBoardPadding = 20;

    private int cx = 0;
    private int cy = 0;
    private int cr = 0;
    private int rx = 0;
    private int ry = 0;
    private int rw = 0;
    private int rh = 0;
    private int clx = 0;
    private int cly = 0;

    Game() {
        display = new Display(this);

        board = new Board(display.getWidth() / 2, display.getHeight() / 2,
                            boardSideLength, (int) (boardSideLength / 2 * Math.sqrt(3)),
                        0, 10);
        //board.setY(board.getY() + board.getY() - board.getOrthocenterY());
        //board.DEBUG = true;
        ball = new Ball(this, board.getOrthocenterX(), board.getOrthocenterY(), 30, 30, Color.white);
        p1 = new Paddle(board.getBottomSide().getX(),
                    (board.getBottomSide().getY() - board.getSideThickness() / 2 + paddleHeight / 2) - paddleBoardPadding,
                    80, paddleHeight,
                    0, new Color(255, 135, 135),
                    (int) (board.getBottomSide().getX() - board.getBottomSide().getWidth() * boardPaddleLimits / 2),
                    (int) (board.getBottomSide().getX() + board.getBottomSide().getWidth() * boardPaddleLimits / 2));
        // TODO Fix x and y translation to center this
        p2 = new Paddle(board.getLeftSide().getX() + ((board.getSideThickness() / 2 + paddleBoardPadding)),
                (board.getLeftSide().getY()),
                //(board.getBottomSide().getY() - board.getSideThickness() / 2 + 4) - 50,
                80, 8,
                -60, Color.pink,//new Color(42, 217, 54),
                (int) (board.getBottomSide().getX() - board.getBottomSide().getWidth() * boardPaddleLimits / 2),
                (int) (board.getBottomSide().getX() + board.getBottomSide().getWidth() * boardPaddleLimits / 2));
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

    private void update(double delta) {
        ball.update(delta);

        if(leftArrowPressed) p1.moveLeft(delta);
        if(rightArrowPressed) p1.moveRight(delta);
        if(wPressed) p2.moveLeft(delta);
        if(sPressed) p2.moveRight(delta);

//        if(isCircleAndRectangleCollided((int) ball.getX(), (int) ball.getY(),
//                                        ball.getRadius(),
//                                        board.getBottomSide().getX(), board.getBottomSide().getY(),
//                                        board.getBottomSide().getWidth(), board.getBottomSide().getHeight(),
//                                        (int) board.getBottomSide().getAngle())) {
//            ball.setVelY(-ball.getVelY());
//            System.out.println("Collide bottom");
        if(isCircleAndRectangleCollided((int) ball.getX(), (int) ball.getY(),
                ball.getRadius(),
                board.getLeftSide().getX(), board.getLeftSide().getY(),
                board.getLeftSide().getWidth(), board.getLeftSide().getHeight(),
                (int) board.getLeftSide().getAngle())) {
            ball.setVelX(-ball.getVelX());
            ball.setVelY(-ball.getVelY());
            System.out.println("Collide Left");
        } //else if(isCircleAndRectangleCollided((int) ball.getX(), (int) ball.getY(),
//                ball.getRadius(),
//                board.getRightSide().getX(), board.getRightSide().getY(),
//                board.getRightSide().getWidth(), board.getRightSide().getHeight(),
//                (int) board.getRightSide().getAngle())) {
//            ball.setVelX(-ball.getVelX());
//            System.out.println("Collide right");
//        }
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

            g2.setColor(Color.orange);
            g2.drawOval(cx - cr, cy - cr, cr * 2, cr * 2);

            board.draw(g2);
            p1.draw(g2);
            p2.draw(g2);
            ball.draw(g2);

            g2.setColor(Color.orange);
            g2.fillRect(rx, ry, rw, rh);

            g2.setColor(Color.magenta);
            g2.drawOval(clx - 1, cly - 1, 3, 3);
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
            case KeyEvent.VK_W:
                wPressed = true;
                break;
            case KeyEvent.VK_S:
                sPressed = true;
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
            case KeyEvent.VK_W:
                wPressed = false;
                break;
            case KeyEvent.VK_S:
                sPressed = false;
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
                                                 int circleRadius,
                                                 int rectX, int rectY,
                                                 int rectWidth, int rectHeight,
                                                 int rectAngle) {
        rectX -= rectWidth / 2;
        rectY -= rectHeight / 2;

        double unrotatedCircleX = Math.cos(Math.toRadians(rectAngle)) * (circleX - rectX) -
                                  Math.sin(Math.toRadians(rectAngle)) * (circleY - rectY) + rectX;
        double unrotatedCircleY = Math.sin(Math.toRadians(rectAngle)) * (circleX - rectX) +
                                  Math.cos(Math.toRadians(rectAngle)) * (circleY - rectY) + rectY;

        cx = (int) unrotatedCircleX;
        cy = (int) unrotatedCircleY;
        cr = circleRadius;

        rx = rectX;
        ry = rectY;
        rw = rectWidth;
        rh = rectHeight;


        double closestX, closestY;

        if(unrotatedCircleX < rectX) closestX = rectX;
        else if(unrotatedCircleX > rectX + rectWidth) closestX = rectX + rectWidth;
        else closestX = unrotatedCircleX;

        if(unrotatedCircleY < rectY) closestY = rectY;
        else if(unrotatedCircleY > rectY + rectHeight) closestY = rectY + rectHeight;
        else closestY = unrotatedCircleY;

        clx = (int) closestX;
        cly = (int) closestY;

        double distance = findDistance(unrotatedCircleX, unrotatedCircleY, closestX, closestY);

        return distance < circleRadius;
    }
}
