import javafx.scene.control.ComboBox;

import javax.swing.*;

public class TimeComboBox {
    private JComboBox<String> timeComboBox;
    private DefaultComboBoxModel<String> model;
    private int limit;

    public TimeComboBox(int limit) {
        this.limit = limit;
        model = new DefaultComboBoxModel<>();
        timeComboBox = new JComboBox<>(model);
        timeComboBox.setMaximumRowCount(5);
        addItem();
    }

    private void addItem() {
        for (int i = 0; i < limit; i++) {
            model.addElement(String.format("%02d",i));
        }
    }

    public JComboBox<String> getTimeComboBox() {
        return timeComboBox;
    }
}
