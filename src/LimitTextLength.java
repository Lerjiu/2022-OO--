import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LimitTextLength {
    public static void limitTextLength(JTextComponent textComponent, int length) {
        textComponent.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                String s = textComponent.getText();
                if (s.length() >= length) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                String s = textComponent.getText();
                if (s.length() >= length) {

                }
            }
        });
    }
}
