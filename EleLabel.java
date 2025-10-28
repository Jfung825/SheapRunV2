import javax.swing.JLabel;

public class EleLabel extends JLabel {
    private static final long serialVersionUID = 1L;

    public EleLabel(String text, int fontSize, int x, int y, int width, int height) {
        super(text);
        setFont(EleMent.getFont(fontSize));
        setBounds(x, y, width, height);
    }
}