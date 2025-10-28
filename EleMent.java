import java.awt.*;
import java.io.InputStream;

public class EleMent {
    public static Font getFont(int size) {
        try {
            InputStream is = EleMent.class.getClassLoader().getResourceAsStream("res/Mali-Bold.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont((float) size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.BOLD, size); // fallback
        }
    }
}