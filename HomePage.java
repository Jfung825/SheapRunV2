import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomePage extends JPanel {
    private static final long serialVersionUID = 1L;

    public HomePage(long bestRecord, ActionListener listener) {
        this.setBackground(new Color(52, 152, 219));
        this.setBounds(0, 0, 1000, 600);
        this.setFocusable(true);
        this.setLayout(null);

        // Title
        EleLabel title = new EleLabel("SHEAP RUN", 60, 300, 80, 400, 100);
        title.setForeground(Color.white);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // Best Record
        EleLabel record = new EleLabel("Best Record: " + bestRecord, 25, 300, 200, 400, 50);
        record.setForeground(new Color(255, 255, 255, 200));
        record.setHorizontalAlignment(SwingConstants.CENTER);

        // Start Button
        EleButton startBtn = new EleButton("START", 20, 350, 320, 300, 60);
        startBtn.setActionCommand("start");
        startBtn.addActionListener(listener);

        // Quit Button
        EleButton quitBtn = new EleButton("QUIT", 20, 350, 400, 300, 60);
        quitBtn.setActionCommand("quit");
        quitBtn.setBackground(new Color(231, 76, 60));
        quitBtn.addActionListener(listener);

        // Instructions
        EleLabel instruction = new EleLabel("Press SPACE or UP to Jump", 18, 200, 500, 600, 40);
        instruction.setForeground(new Color(255, 255, 255, 150));
        instruction.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(title);
        this.add(record);
        this.add(startBtn);
        this.add(quitBtn);
        this.add(instruction);
    }
}