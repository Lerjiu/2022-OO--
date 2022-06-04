import javax.swing.*;
import java.awt.*;

public class CurrentTimeBox {
    private Box showTimeBox;
    private ImitateClock imitateClock;

    public CurrentTimeBox() {
        showTimeBox = Box.createVerticalBox();
        imitateClock = new ImitateClock();
//        imitateClock.setSize(new Dimension(500, 500));
//        imitateClock.setPreferredSize(new Dimension(500, 500));

        showTimeBox.add(imitateClock);
        showTimeBox.setMinimumSize(new Dimension(500, 500));
    }

    public Box getShowTimeBox() {
        return showTimeBox;
    }

    public void repaintClock() {
        imitateClock.repaint();
    }
}
