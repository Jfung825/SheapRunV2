import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Wave {
    public int x, y, speed;
    public int height; // 1 = ชั้นเดียว, 2 = สองชั้น
    public Timer timer; 

    public Wave(int x, int y, int speed, JPanel panel) {
        this(x, y, speed, panel, 1); // default ความสูง 1 ชั้น
    }

    public Wave(int x, int y, int speed, JPanel panel, int height) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.height = height;
        move(panel);
    }

    public void move(JPanel panel) {
        timer = new Timer(speed, e -> {
            if (x <= 0) {
                x = 1000 + (int) (300 + Math.random() * 1000);
                // สุ่มความสูง 1 หรือ 2 ชั้น
                height = (Math.random() < 0.3) ? 2 : 1;
            }
            x -= 30;
            panel.repaint();
        });
        timer.start();
    }

    public BufferedImage getImage() {
        try {
            return ImageIO.read(getClass().getClassLoader().getResource("res/roundheybale.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}



//private Thread moveThread;
//private volatile boolean running = true;
//
//public void move(JPanel panel) {
//    moveThread = new Thread(() -> {
//        while (running) {
//            try {
//                if (x <= 0) {
//                    x = 1000 + (int) (300 + Math.random() * 1000);
//                    height = (Math.random() < 0.3) ? 2 : 1;
//                }
//                x -= 30;
//                
//                // ใช้ SwingUtilities.invokeLater เพื่อ repaint ใน EDT
//                SwingUtilities.invokeLater(() -> panel.repaint());
//                
//                Thread.sleep(speed); // หน่วงเวลา
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                break;
//            }
//        }
//    });
//    moveThread.start();
//}
//
//public void stopMovement() {
//    running = false;
//    if (moveThread != null) {
//        moveThread.interrupt();
//    }
//}