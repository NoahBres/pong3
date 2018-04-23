import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {
    private int width = 960;
    private int height = 700;

    private final Dimension size = new Dimension(width, height);
    private final String title = "pong3";

    private Game game;
    private JFrame frame;

    Display(Game game) {
        this.game = game;
        setIgnoreRepaint(true);

        frame = new JFrame(title);
        frame.setPreferredSize(size);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addKeyListener(game);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.render(g);
    }
}
