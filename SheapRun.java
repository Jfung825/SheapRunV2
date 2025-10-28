import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SheapRun extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private Dimension size = new Dimension(1000, 600);
    private long bestRecord = 0;
    private Game currentGame; // เก็บ reference ของเกมปัจจุบัน

    public SheapRun() {
        setting();
        showHomePage();
    }

    private void setting() {
        this.setTitle("Sheap Run");
        this.setSize(size);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void showHomePage() {
        removeContent();
        HomePage homePage = new HomePage(bestRecord, this);
        this.getContentPane().add(homePage);
        revalidate();
    }
    
    private void removeContent() {
        this.getContentPane().removeAll();
        this.getContentPane().repaint();
    }
    
    private void startGame() {
        removeContent();
        currentGame = new Game(this);
        // รีเซ็ตเกมก่อนเริ่มเล่น
        currentGame.resetGame();
        this.getContentPane().add(currentGame);
        currentGame.requestFocusInWindow();
        revalidate();
    }

    public void endGame(long point) {
        System.out.println("endGame called with point: " + point); // Debug
        
        // Update best record
        if (point > bestRecord) {
            bestRecord = point;
        }
        
        // ใช้ SwingUtilities.invokeLater เพื่อให้แน่ใจว่าการเปลี่ยนหน้าจอเกิดขึ้นใน EDT
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    System.out.println("Creating end game menu..."); // Debug
                    removeContent();
                    
                    Menu gameOverMenu = new Menu(point, bestRecord, SheapRun.this);
                    getContentPane().add(gameOverMenu);
                    
                    revalidate();
                    repaint();
                    
                    System.out.println("End game menu added successfully"); // Debug
                    
                } catch (Exception e) {
                    System.out.println("Error in endGame: " + e.getMessage());
                    e.printStackTrace();
                    
                    // Fallback - สร้างหน้าจบเกมแบบง่ายๆ
                    JPanel fallbackPanel = new JPanel();
                    fallbackPanel.setBackground(new Color(241, 98, 69));
                    fallbackPanel.setLayout(new GridBagLayout());
                    
                    GridBagConstraints gbc = new GridBagConstraints();
                    
                    JLabel gameOverLabel = new JLabel("Game Over!");
                    gameOverLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
                    gameOverLabel.setForeground(Color.WHITE);
                    gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(20, 0, 20, 0);
                    fallbackPanel.add(gameOverLabel, gbc);
                    
                    JLabel scoreLabel = new JLabel("Score: " + point);
                    scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
                    scoreLabel.setForeground(Color.WHITE);
                    gbc.gridy = 1;
                    fallbackPanel.add(scoreLabel, gbc);
                    
                    JButton restartBtn = new JButton("RESTART");
                    restartBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
                    restartBtn.setActionCommand("restart");
                    restartBtn.addActionListener(SheapRun.this);
                    restartBtn.setPreferredSize(new Dimension(200, 50));
                    gbc.gridy = 2; gbc.insets = new Insets(30, 0, 10, 0);
                    fallbackPanel.add(restartBtn, gbc);
                    
                    JButton homeBtn = new JButton("HOME");
                    homeBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
                    homeBtn.setActionCommand("home");
                    homeBtn.addActionListener(SheapRun.this);
                    homeBtn.setPreferredSize(new Dimension(200, 50));
                    gbc.gridy = 3; gbc.insets = new Insets(10, 0, 20, 0);
                    fallbackPanel.add(homeBtn, gbc);
                    
                    getContentPane().add(fallbackPanel);
                    revalidate();
                    repaint();
                    
                    System.out.println("Fallback end game panel created"); // Debug
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        System.out.println("Action performed: " + cmd); // Debug
        
        if ("start".equals(cmd)) {
            startGame();
        } else if ("restart".equals(cmd)) {
            startGame();
        } else if ("quit".equals(cmd)) {
            System.exit(0);
        } else if ("home".equals(cmd)) {
            showHomePage();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SheapRun();
            }
        });
    }
}