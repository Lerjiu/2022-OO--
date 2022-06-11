import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 用于显示退出登录的封装类
 */
public class LogoutBox {
    private Box logoutBox;
    private JPanel namePanel;
    private JLabel nameLabel;
    private JTextField nameField;
    private JPanel logoutButtonPanel;
    private JButton logoutButton;
    private JFrame jFrame;

    /**
     * 构造时初始化
     * @param jFrame 显示提示窗口等需要父窗口
     */
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

    /**
     * 为button添加监听事件
     * @param p 点击button执行对应逻辑后，通知个人中心，即PersonalCenter进行更新
     */
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

    /**
     * 更新该组件
     */
    public void updateLogoutBox() {
        nameField.setText(UserManage.getUserName());
    }

    /**
     *
     * @return 返回构造后的组件
     */
    public Box getLogoutBox() {
        return logoutBox;
    }
}
