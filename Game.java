import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;

    private static final int BASE_Y = 400;
    private static final int sheap_SIZE = 80;
    private static final int WAVE_HEIGHT = 50;
    private static final int SPEED = 50;
    private long point = 0;
    private long lastJumpTime = 0;
    private boolean[] waveHit;
    private boolean isLevel2 = false; // เช็คว่าถึง 500 แต้มหรือยัง
    private boolean gameEnded = false; // เพิ่มตัวแปรเช็คว่าเกมจบแล้วหรือยัง

    private SheapRun display;
    private Sheap sheap = new Sheap(100, BASE_Y - 50);
    private Wave[] waves = makeWaves(4);
    private Environment[] clouds = makeEnv(2, Environment.CLOUD);
    private Environment building = new Environment(1000 - 100, BASE_Y - 150, this, Environment.BUILDING, 4);

    public Game(SheapRun display) {
        this.display = display;
        this.waveHit = new boolean[4];
        this.gameEnded = false; // รีเซ็ตสถานะเกม
        setBounds(0, 0, 1000, 600);
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        // ถ้าเกมจบแล้วไม่ต้องวาดอะไรเพิ่ม
        if (gameEnded) {
            return;
        }
        
        try {
            Graphics2D g2 = (Graphics2D) g;
            
            // เช็คว่าถึง 500 แต้มหรือยัง
            if (point >= 500 && !isLevel2) {
                isLevel2 = true;
            }
            
            drawBackground(g2);
            g2.setFont(EleMent.getFont(30));
            g2.setColor(Color.white);
            g2.drawString("Point : " + point, 750, 40);

            // Health
            drawsheapHealth(g2);

            // sheap
            g2.drawImage(sheap.getImage(), sheap.x, sheap.y, sheap_SIZE, sheap_SIZE, null);

            // Waves - วาดทั้งอุปสรรคความสูง 1 และ 2 ชั้น
            for (int i = 0; i < waves.length; i++) {
                drawWave(waves[i], g2, i);
            }

            point++;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawBackground(Graphics2D g2) {
        String skyFile = isLevel2 ? "res/sky2.png" : "res/sky.png";
        String dirFile = isLevel2 ? "res/dir2.png" : "res/dir.png";
        String cloudFile = isLevel2 ? "res/cloud2.png" : "res/cloud.png";
        String buildingFile = isLevel2 ? "res/building2.png" : "res/building.png";
        
        // Sky
        try {
            g2.drawImage(loadImage(skyFile), 0, 0, 2000, 1000, null);
        } catch (Exception e) {
            // Fallback สี
            Color skyColor = isLevel2 ? new Color(25, 25, 60) : new Color(135, 206, 235);
            g2.setColor(skyColor);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        
        // Building
        try {
            BufferedImage buildImg = ImageIO.read(getClass().getClassLoader().getResource(buildingFile));
            g2.drawImage(buildImg, building.x, building.y, 500, 200, null);
        } catch (Exception e) {
            Color buildColor = isLevel2 ? new Color(50, 50, 50) : Color.GRAY;
            g2.setColor(buildColor);
            g2.fillRect(building.x, building.y, 500, 200);
        }
        
        // Ground
        try {
            g2.drawImage(loadImage(dirFile), 0, BASE_Y + 10, 2000, 220, null);
        } catch (Exception e) {
            Color groundColor = isLevel2 ? new Color(69, 39, 19) : new Color(139, 69, 19);
            g2.setColor(groundColor);
            g2.fillRect(0, BASE_Y + 10, getWidth(), 220);
        }
        
        // Clouds
        for (Environment c : clouds) {
            try {
                BufferedImage cloudImg = ImageIO.read(getClass().getClassLoader().getResource(cloudFile));
                g2.drawImage(cloudImg, c.x, c.y, 250, 160, null);
            } catch (Exception e) {
                Color cloudColor = isLevel2 ? new Color(200, 200, 200) : Color.WHITE;
                g2.setColor(cloudColor);
                g2.fillOval(c.x, c.y, 250, 160);
            }
        }
    }

    private void drawsheapHealth(Graphics2D g2) {
    try {
        g2.drawImage(loadImage("res/heart.png"), 10, 20, 20, 20, null);
    } catch (Exception e) {
        g2.setColor(new Color(241, 98, 69));
        g2.fillOval(10, 20, 10, 10);
        g2.fillOval(15, 20, 10, 10);
        int[] xPoints = {15, 10, 20, 30};
        int[] yPoints = {25, 30, 35, 30};
        g2.fillPolygon(xPoints, yPoints, 4);
    }
    
    
    int displayHealth = Math.max(0, sheap.health);
    
    g2.setStroke(new BasicStroke(18.0f));
    g2.setColor(new Color(241, 98, 69));
    g2.drawLine(60, 30, 60 + displayHealth, 30);  // ใช้ displayHealth แทน
    g2.setColor(Color.white);
    g2.setStroke(new BasicStroke(6.0f));
    g2.drawRect(50, 20, 200, 20);
}

    private void drawWave(Wave wave, Graphics2D g2, int index) {
        // วาดอุปสรรคตามความสูง
        if (wave.height == 2) {
            // วาด 2 ชั้น
            try {
                BufferedImage img = wave.getImage();
                g2.drawImage(img, wave.x, wave.y - WAVE_HEIGHT, 40, WAVE_HEIGHT + 10, null);
                g2.drawImage(img, wave.x, wave.y - WAVE_HEIGHT * 2 - 10, 40, WAVE_HEIGHT + 10, null);
            } catch (Exception e) {
                g2.setColor(Color.ORANGE);
                g2.fillRect(wave.x, wave.y - WAVE_HEIGHT, 40, WAVE_HEIGHT + 10);
                g2.fillRect(wave.x, wave.y - WAVE_HEIGHT * 2 - 10, 40, WAVE_HEIGHT + 10);
            }
        } else {
            // วาด 1 ชั้น
            try {
                g2.drawImage(wave.getImage(), wave.x, wave.y - WAVE_HEIGHT, 40, WAVE_HEIGHT + 10, null);
            } catch (Exception e) {
                g2.setColor(Color.ORANGE);
                g2.fillRect(wave.x, wave.y - WAVE_HEIGHT, 40, WAVE_HEIGHT + 10);
            }
        }
        
        // เช็คการชน - สำหรับความสูง 2 ชั้น ต้องเช็คทั้ง 2 ระดับ
        boolean hit = false;
        if (wave.height == 2) {
            // เช็คชั้นล่าง
            hit = (sheap.x + sheap_SIZE > wave.x && sheap.x < wave.x + 40) && 
                  (sheap.y + sheap_SIZE >= wave.y - WAVE_HEIGHT);
            // เช็คชั้นบน
            if (!hit) {
                hit = (sheap.x + sheap_SIZE > wave.x && sheap.x < wave.x + 40) && 
                      (sheap.y + sheap_SIZE >= wave.y - WAVE_HEIGHT * 2 - 10) &&
                      (sheap.y <= wave.y - WAVE_HEIGHT);
            }
        } else {
            hit = Event.checkHit(sheap, wave, sheap_SIZE, WAVE_HEIGHT);
        }
        
        if (hit) {
            if (!waveHit[index]) {
                waveHit[index] = true;
                g2.setColor(new Color(241, 98, 69, 128));
                g2.fillRect(0, 0, getWidth(), getHeight());

                sheap.health -= 20;
                if (sheap.health <= 0) {
                    // เซ็ตสถานะเกมจบ
                    gameEnded = true;
                    // เรียก endGame และส่งคะแนนไป
                    display.endGame(this.point);
                    return; // หยุดการประมวลผลทันที
                }
            }
        } else {
            if (wave.x < sheap.x - 100) {
                waveHit[index] = false;
            }
        }
    }

    // เพิ่มเมธอดสำหรับรีเซ็ตเกม
    public void resetGame() {
        sheap.health = 180;
        this.point = 0;
        this.isLevel2 = false;
        this.gameEnded = false;
        sheap.resetJump();
        for (int i = 0; i < waveHit.length; i++) {
            waveHit[i] = false;
        }
        // สร้าง waves ใหม่
        this.waves = makeWaves(4);
    }

    private Wave[] makeWaves(int count) {
        Wave[] result = new Wave[count];
        int gap = 500;
        for (int i = 0; i < count; i++) {
            // สุ่มความสูง 1 หรือ 2 ชั้น (30% โอกาส 2 ชั้น)
            int height = (Math.random() < 0.3) ? 2 : 1;
            result[i] = new Wave(1000 + i * gap, BASE_Y, SPEED, this, height);
        }
        return result;
    }

    private Environment[] makeEnv(int count, int type) {
        Environment[] result = new Environment[count];
        int gap = 600;
        for (int i = 0; i < count; i++) {
            result[i] = new Environment(1000 + i * gap, 20, this, type, 10);
        }
        return result;
    }

    private Image loadImage(String path) throws IOException {
        return ImageIO.read(getClass().getClassLoader().getResource(path));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // ถ้าเกมจบแล้วไม่ให้กดปุ่มได้
        if (gameEnded) {
            return;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
            sheap.jump(this);
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}