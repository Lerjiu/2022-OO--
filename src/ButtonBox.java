import javax.swing.*;
import java.awt.*;

/**
 * 用于显示plan对应button的封装类
 */
public class ButtonBox {
    private Box buttonBox;
    private Box buttons;

    /**
     * 构造时初始化
     */
    public ButtonBox() {
        buttonBox = Box.createVerticalBox();
        buttons = Box.createVerticalBox();
        buttonBox.add(buttons);
        buttonBox.add(Box.createVerticalGlue());
    }

    /**
     *
     * @return 返回构造后的组件
     */
    public Box getButtonBox() {
        return buttonBox;
    }

    /**
     * 将button放到指定位置，即对应plan的同行
     * @param jButton
     * @param pos
     */
    public void insertButton(JButton jButton, int pos) {
        JPanel jPanel = new JPanel();
        jPanel.add(jButton);
        jPanel.setPreferredSize(new Dimension(100,80));
        buttons.add(jPanel, pos);

        setButtonBoxMaxSize();
    }

    /**
     * 将button直接放到最后的位置
     * @param jButton
     */
    public void addButton(JButton jButton) {
        JPanel jPanel = new JPanel();
        jPanel.add(jButton);
        jPanel.setPreferredSize(new Dimension(100,80));

        buttons.add(jPanel);
        setButtonBoxMaxSize();
    }

    /**
     * 移除计划后，将对应button移除
     * @param pos
     */
    public void removeButton(int pos) {
        buttons.remove(pos);
        setButtonBoxMaxSize();
    }

    /**
     * 用于控制button的显示大小
     */
    private void setButtonBoxMaxSize() {
        int count = buttons.getComponentCount();
        buttons.setMaximumSize(new Dimension(100, count * 80));
    }
}
