import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sheap {
    public int x, y;
    public int health = 180;
    public int speed = 90;
    public int baseY;
    private int jumpCount = 0;
    
    // สำหรับอนิเมชั่นวิ่ง
    private BufferedImage runImage1;
    private BufferedImage runImage2;
    private BufferedImage jumpImage;
    private boolean useImage1 = true;
    private int animationCounter = 0;
    private static final int ANIMATION_SPEED = 10; // ยิ่งน้อยยิ่งเร็ว
    private boolean isJumping = false;

    public Sheap() {
        loadImages();
    }

    public Sheap(int x, int y) {
        this.x = x;
        this.y = y;
        this.baseY = y;
        loadImages();
    }

    private void loadImages() {
        try {
            // โหลดรูปวิ่ง 2 รูป
            runImage1 = ImageIO.read(getClass().getClassLoader().getResource("res/sheap1.png"));
            runImage2 = ImageIO.read(getClass().getClassLoader().getResource("res/sheap2.png"));
            // โหลดรูปกระโดด (ถ้ามี ถ้าไม่มีจะใช้รูปวิ่ง)
            try {
                jumpImage = ImageIO.read(getClass().getClassLoader().getResource("res/sheap_jump.png"));
            } catch (Exception e) {
                jumpImage = runImage1; // ถ้าไม่มีรูปกระโดด ใช้รูปวิ่งแทน
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jump(JPanel panel) {
        if (jumpCount < 2) {
            jumpCount++;
            isJumping = true;
            
            y -= speed;
            panel.repaint();

            Timer fall = new Timer(450, e -> {
                y += speed;
                panel.repaint();
                
                if (y >= baseY) {
                    y = baseY;
                    jumpCount = 0;
                    isJumping = false;
                }
            });
            fall.setRepeats(false);
            fall.start();
        }
    }

    public void resetJump() {
        jumpCount = 0;
        y = baseY;
        isJumping = false;
    }

    public BufferedImage getImage() {
        // ถ้ากำลังกระโดด ใช้รูปกระโดด
        if (isJumping && jumpImage != null) {
            return jumpImage;
        }
        
        // อนิเมชั่นวิ่ง - สลับรูป
        animationCounter++;
        if (animationCounter >= ANIMATION_SPEED) {
            animationCounter = 0;
            useImage1 = !useImage1;
        }
        
        // ส่งกลับรูปที่เลือก
        if (useImage1 && runImage1 != null) {
            return runImage1;
        } else if (runImage2 != null) {
            return runImage2;
        }
        
        // Fallback ถ้าไม่มีรูป
        return runImage1;
    }
}