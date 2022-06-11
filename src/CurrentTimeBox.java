import javax.swing.*;
import java.awt.*;

/**
 * 用于显示当前时间的封装类
 */
public class CurrentTimeBox {
    private Box showTimeBox;
    private ImitateClock imitateClock;

    /**
     * 构造时初始化
     */
    public CurrentTimeBox() {
        showTimeBox = Box.createVerticalBox();
        imitateClock = new ImitateClock();
//        imitateClock.setSize(new Dimension(500, 500));
//        imitateClock.setPreferredSize(new Dimension(500, 500));

        showTimeBox.add(imitateClock);
        showTimeBox.setMinimumSize(new Dimension(500, 500));
    }

    /**
     *
     * @return 返回构造后的组件
     */
    public Box getShowTimeBox() {
        return showTimeBox;
    }

    /**
     * 重绘组件
     */
    public void repaintClock() {
        imitateClock.repaint();
    }
}
