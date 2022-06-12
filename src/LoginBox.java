import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 用于显示登录的封装类
 */
public class LoginBox {
    private Box loginBox;
    private JPanel namePanel;
    private JPanel passWordPanel;
    private JPanel loginButtonPanel;
    private JButton loginButton;
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

    /**
     * 构造时初始化
     * @param jFrame 显示提示窗口等需要父窗口
     */
    public LoginBox(JFrame jFrame) {
        this.jFrame = jFrame;

        loginBox = Box.createVerticalBox();

        namePanel = new JPanel();
        passWordPanel = new JPanel();
        loginButtonPanel = new JPanel();

        loginButton = new JButton("登录");


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

        loginButtonPanel.add(loginButton);

        loginBox.add(namePanel);
        loginBox.add(passWordPanel);
        loginBox.add(loginButtonPanel);
    }

    /**
     * 为button添加监听事件
     * @param p 点击button执行对应逻辑后，通知个人中心，即PersonalCenter进行更新
     */
    public void addButtonListener(PersonalCenter p) {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().length() < 3 || passwordField.getPassword().length < 8) {
                    JOptionPane.showMessageDialog(jFrame, "请检查用户名或密码长度~");
                } else if (!UserManage.checkPassWordLegal(String.valueOf(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(jFrame, "请检查密码是否符合规则");
                } else {
                    UserManage.setUser(nameField.getText(), String.valueOf(passwordField.getPassword()));
//                    System.out.println("setuser" + UserManage.getUser().getUserName());
                    if (UserManage.login()) {
                        p.updatePersonalCenter();
                        JOptionPane.showMessageDialog(jFrame, "登陆成功");

                        //手动登录后，询问本地文件是否属于该登录账号，进行清除或上传
                        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(jFrame, "本地数据是否属于该账号？", "验证", JOptionPane.YES_NO_OPTION)) {
                            PlanManage.uploadLocalPlanFile();
                        } else {
                            PlanManage.deleteLocalPlanFile();
                        }
                        PlanManage.getRemotePlanFile();

                        //重新加载页面
                        PlanManage.getTodayPlan();
                        PlanManage.getHistoryPlan();
                    } else {
                        UserManage.resetUser();
                        JOptionPane.showMessageDialog(jFrame, "登陆失败，请检查用户名和密码是否正确");
                    }
                }
            }
        });
    }

    /**
     * 更新本组件显示
     */
    public void updateLoginBox() {
        nameField.setText("");
        passwordField.setText("");
    }

    /**
     *
     * @return 返回构造后的组件
     */
    public Box getLoginBox() {
        return loginBox;
    }
}
