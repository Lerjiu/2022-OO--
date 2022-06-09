import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterBox {
    private Box registerBox;
    private JPanel namePanel;
    private JPanel passWordPanel;
    private JPanel registerButtonPanel;
    private JButton registerButton;
    private Box name;
    private Box nameBox;
    private Box nameLimitBox;
    private Box passWord;
    private Box passWordBox;
    private Box passWordLimitBox;
    private JLabel nameLabel;
    private JLabel nameLimitLabel;
    private JLabel passWordLabel;
    private JLabel passWordLimitLabel;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JFrame jFrame;

    public RegisterBox(JFrame jFrame) {
        this.jFrame = jFrame;

        registerBox = Box.createVerticalBox();

        namePanel = new JPanel();
        passWordPanel = new JPanel();
        registerButtonPanel = new JPanel();

        registerButton = new JButton("注册");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().length() < 3 || passwordField.getPassword().length < 8) {
                    JOptionPane.showMessageDialog(jFrame, "请检查用户名或密码长度~");
                } else if (!UserManage.checkPassWordLegal(String.valueOf(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(jFrame, "请检查密码是否符合规则");
                } else {
                    UserManage.setUser(nameField.getText(), String.valueOf(passwordField.getPassword()));
                    if (UserManage.register()) {
                        JOptionPane.showMessageDialog(jFrame, "注册成功");
                    } else {
                        UserManage.resetUser();
                        JOptionPane.showMessageDialog(jFrame, "注册失败，用户名已存在");
                    }
                }
            }
        });

        name = Box.createVerticalBox();
        passWord =Box.createVerticalBox();
        nameBox = Box.createHorizontalBox();
        nameLimitBox = Box.createHorizontalBox();
        passWordBox = Box.createHorizontalBox();
        passWordLimitBox = Box.createHorizontalBox();

        nameLabel = new JLabel("用户名：");
        nameLimitLabel = new JLabel("用户名3-10个字符");
        nameLimitLabel.setForeground(Color.gray);
        passWordLabel = new JLabel("  密码：");
        passWordLimitLabel = new JLabel("密码8-20个字符，由字母、数字、下划线组成");
        passWordLimitLabel.setForeground(Color.gray);

        nameField = new JTextField(40);
        LimitTextLength.limitTextLength(nameField, 10);
        passwordField = new JPasswordField(40);
        LimitTextLength.limitTextLength(passwordField, 20);

        nameBox.add(nameLabel);
        nameBox.add(nameField);
        nameLimitBox.add(nameLimitLabel);

        passWordBox.add(passWordLabel);
        passWordBox.add(passwordField);
        passWordLimitBox.add(passWordLimitLabel);

        name.add(nameBox);
        name.add(nameLimitBox);

        passWord.add(passWordBox);
        passWord.add(passWordLimitBox);

        namePanel.add(name);

        passWordPanel.add(passWord);

        registerButtonPanel.add(registerButton);

        registerBox.add(namePanel);
        registerBox.add(passWordPanel);
        registerBox.add(registerButtonPanel);
    }

    public void updateRegisterBox() {
        nameField.setText("");
        passwordField.setText("");
    }

    public Box getLoginBox() {
        return registerBox;
    }


}
