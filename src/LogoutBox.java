import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutBox {
    private Box logoutBox;
    private JPanel namePanel;
    private JLabel nameLabel;
    private JTextField nameField;
    private JPanel logoutButtonPanel;
    private JButton logoutButton;
    private JFrame jFrame;

    public LogoutBox(JFrame jFrame) {
        this.jFrame = jFrame;

        logoutBox = Box.createHorizontalBox();

        namePanel = new JPanel();
        nameLabel = new JLabel("用户名");
        nameField = new JTextField(40);
        nameField.setEnabled(false);
        nameField.setText(UserManage.getUserName());

        logoutButtonPanel = new JPanel();
        logoutButton = new JButton("退出登录");

        namePanel.add(nameLabel);
        namePanel.add(nameField);

        logoutButtonPanel.add(logoutButton);

        logoutBox.add(namePanel);
        logoutBox.add(logoutButtonPanel);
    }

    public void addButtonListener(PersonalCenter p) {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserManage.logout();
                p.updatePersonalCenter();
                JOptionPane.showMessageDialog(jFrame, "成功退出登录");
            }
        });
    }

    public void updateLogoutBox() {
        nameField.setText(UserManage.getUserName());
    }

    public Box getLogoutBox() {
        return logoutBox;
    }
}
