import javax.swing.*;

/**
 * 用于显示计划的选择时间的封装类
 */
public class TimeComboBox {
    private JComboBox<String> timeComboBox;
    private DefaultComboBoxModel<String> model;
    private int limit;

    /**
     * 构造时初始化
     * @param limit
     */
    public TimeComboBox(int limit) {
        this.limit = limit;
        model = new DefaultComboBoxModel<>();
        timeComboBox = new JComboBox<>(model);
        timeComboBox.setMaximumRowCount(5);
        addItem();
    }

    /**
     * 根据limit添加不同数量的元素
     */
    private void addItem() {
        for (int i = 0; i < limit; i++) {
            model.addElement(String.format("%02d",i));
        }
    }

    /**
     *
     * @return 返回构造后的组件
     */
    public JComboBox<String> getTimeComboBox() {
        return timeComboBox;
    }
}
