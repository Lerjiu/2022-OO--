import javax.swing.*;

/**
 * 用于显示个人中心的封装类
 */
public class PersonalCenter {
    private JTabbedPane personalCenterTabbedPane;
    private UserInformation userInformation;
    private JTabbedPane loginAndRegister;
    private LoginBox loginBox;
    private LogoutBox logoutBox;
    private RegisterBox registerBox;
    private JFrame jFrame;

    /**
     * 构造时初始化
     * @param jFrame 个人中心下辖模块的构建，需要提供父窗口
     */
    public PersonalCenter(JFrame jFrame) {
        this.jFrame = jFrame;

        personalCenterTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        userInformation = new UserInformation();
        loginAndRegister = new JTabbedPane(JTabbedPane.TOP);
        loginBox = new LoginBox(jFrame);
        loginBox.addButtonListener(this);
        registerBox = new RegisterBox(jFrame);

        loginAndRegister.add("登录", loginBox.getLoginBox());
        loginAndRegister.add("注册", registerBox.getLoginBox());

        personalCenterTabbedPane.add("个人信息", userInformation.getUserInformationBox());
        personalCenterTabbedPane.add("登录注册", loginAndRegister);

        logoutBox = new LogoutBox(jFrame);
        logoutBox.addButtonListener(this);
    }

    /**
     * 更新该组件
     */
    public void updatePersonalCenter() {
        userInformation.updateUserInformation();
        loginBox.updateLoginBox();
        registerBox.updateRegisterBox();
        logoutBox.updateLogoutBox();
        if (!UserManage.isLogin()) {
            personalCenterTabbedPane.remove(1);
            personalCenterTabbedPane.add("登录注册", loginAndRegister);
        } else {
            personalCenterTabbedPane.remove(1);
            personalCenterTabbedPane.add("退出登录", logoutBox.getLogoutBox());
        }
    }

    /**
     *
     * @return 返回构造后的组件
     */
    public JTabbedPane getPersonalCenterTabbedPane() {
        return personalCenterTabbedPane;
    }

}
