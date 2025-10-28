import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Menu extends JPanel {
    private static final long serialVersionUID = 1L;

    public Menu(long point, long bestRecord, ActionListener listener) {
        System.out.println("Creating Menu with point: " + point + ", bestRecord: " + bestRecord); // Debug
        
        this.setBackground(new Color(241, 98, 69));
        this.setBounds(0, 0, 1000, 600);
        this.setPreferredSize(new Dimension(1000, 600));
        this.setFocusable(true);
        this.setLayout(null);
        this.setVisible(true);

        try {
            EleLabel status = new EleLabel("You Died!", 40, 400, 80, 200, 100);
            status.setForeground(Color.white);
            status.setVisible(true);

            EleLabel showPoint = new EleLabel("Score: " + point, 30, 400, 180, 200, 100);
            showPoint.setForeground(Color.white);
            showPoint.setVisible(true);

            EleLabel showBest = new EleLabel("Best: " + bestRecord, 25, 400, 250, 200, 100);
            showBest.setForeground(new Color(255, 255, 255, 200));
            showBest.setVisible(true);

            EleButton restart = new EleButton("RESTART", 15, 380, 340, 240, 50);
            restart.setActionCommand("restart");
            restart.addActionListener(listener);
            restart.setVisible(true);

            EleButton home = new EleButton("HOME", 15, 380, 410, 240, 50);
            home.setActionCommand("home");
            home.setBackground(new Color(52, 152, 219));
            home.addActionListener(listener);
            home.setVisible(true);

            this.add(status);
            this.add(showPoint);
            this.add(showBest);
            this.add(restart);
            this.add(home);
            
            System.out.println("Menu components added successfully"); // Debug
            
        } catch (Exception e) {
            System.out.println("Error creating menu components: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback - สร้าง menu แบบง่ายๆ ด้วย standard Swing components
            this.removeAll();
            this.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            
            JLabel fallbackTitle = new JLabel("Game Over!");
            fallbackTitle.setFont(new Font("SansSerif", Font.BOLD, 40));
            fallbackTitle.setForeground(Color.WHITE);
            gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(20, 0, 20, 0);
            this.add(fallbackTitle, gbc);
            
            JLabel fallbackScore = new JLabel("Score: " + point);
            fallbackScore.setFont(new Font("SansSerif", Font.PLAIN, 30));
            fallbackScore.setForeground(Color.WHITE);
            gbc.gridy = 1;
            this.add(fallbackScore, gbc);
            
            JLabel fallbackBest = new JLabel("Best: " + bestRecord);
            fallbackBest.setFont(new Font("SansSerif", Font.PLAIN, 25));
            fallbackBest.setForeground(Color.WHITE);
            gbc.gridy = 2;
            this.add(fallbackBest, gbc);
            
            JButton fallbackRestart = new JButton("RESTART");
            fallbackRestart.setFont(new Font("SansSerif", Font.BOLD, 20));
            fallbackRestart.setActionCommand("restart");
            fallbackRestart.addActionListener(listener);
            fallbackRestart.setPreferredSize(new Dimension(200, 50));
            gbc.gridy = 3; gbc.insets = new Insets(30, 0, 10, 0);
            this.add(fallbackRestart, gbc);
            
            JButton fallbackHome = new JButton("HOME");
            fallbackHome.setFont(new Font("SansSerif", Font.BOLD, 20));
            fallbackHome.setActionCommand("home");
            fallbackHome.addActionListener(listener);
            fallbackHome.setPreferredSize(new Dimension(200, 50));
            fallbackHome.setBackground(new Color(52, 152, 219));
            fallbackHome.setForeground(Color.WHITE);
            gbc.gridy = 4; gbc.insets = new Insets(10, 0, 20, 0);
            this.add(fallbackHome, gbc);
            
            System.out.println("Fallback menu created"); // Debug
        }
        
        this.revalidate();
        this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("Menu paintComponent called"); // Debug
    }
}