import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Environment {
    public int x, y, startX, speed, eType;
    public Timer timer;  
    public static final int CLOUD = 0, BUILDING = 1;

    public Environment(int x, int y, JPanel panel, int eType, int speed) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.speed = speed;
        this.eType = eType;
        move(panel);
    }

    private void move(JPanel panel) {
        timer = new Timer(10, e -> {
            if (x + 400 < 0) {
                x = startX;
            }
            x -= speed;
            panel.repaint();
        });
        timer.start();
    }

    public BufferedImage getImage() {
        String fileName = (eType == CLOUD) ? "cloud.png" : "building.png";
        try {
            return ImageIO.read(getClass().getClassLoader().getResource("res/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}