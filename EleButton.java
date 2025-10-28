import javax.swing.JButton;
import java.awt.*;

public class EleButton extends JButton {
    private static final long serialVersionUID = 1L;

    public EleButton(String text, int fontSize, int x, int y, int width, int height) {
        super(text);
        this.setBackground(new Color(2, 117, 216));
        this.setForeground(Color.white);
        this.setFont(EleMent.getFont(fontSize));
        this.setBounds(x, y, width, height);
    }
}