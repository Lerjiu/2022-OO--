import javax.swing.*;
import java.awt.*;

public class ButtonBox {
    private Box buttonBox;
    private Box buttons;
    public ButtonBox() {
        buttonBox = Box.createVerticalBox();
        buttons = Box.createVerticalBox();
        buttonBox.add(buttons);
        buttonBox.add(Box.createVerticalGlue());
    }

    public Box getButtonBox() {
        return buttonBox;
    }

    public void insertButton(JButton jButton, int pos) {
        JPanel jPanel = new JPanel();
        jPanel.add(jButton);
        jPanel.setPreferredSize(new Dimension(100,80));
        buttons.add(jPanel, pos);

        setButtonBoxMaxSize();
    }

    public void addButton(JButton jButton) {
        JPanel jPanel = new JPanel();
        jPanel.add(jButton);
        jPanel.setPreferredSize(new Dimension(100,80));

        buttons.add(jPanel);
        setButtonBoxMaxSize();
    }

    public void removeButton(int pos) {
        buttons.remove(pos);
        setButtonBoxMaxSize();
    }

    private void setButtonBoxMaxSize() {
        int count = buttons.getComponentCount();
        buttons.setMaximumSize(new Dimension(100, count * 80));
    }
}
