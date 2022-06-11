import javax.swing.*;

/**
 * 用于显示个人信息的封装类
 */
public class UserInformation {
    private Box userInformationBox;
    private JPanel userNamePanel;
    private JPanel planFinishNumPanel;
    private JPanel planFinishBetterPanel;
    private JPanel dayPlanFinishNumPanel;
    private JPanel dayPlanFinishBetterPanel;
    private JLabel userNameLabel;
    private JLabel planFinishNumLabel;
    private JLabel planFinishBetterLabel;
    private JLabel dayPlanFinishNumLabel;
    private JLabel dayPlanFinishBetterLabel;
    private JTextArea userNameJTA;
    private JTextArea planFinishNumJTA;
    private JTextArea planFinishBetterJTA;
    private JTextArea dayPlanFinishNumJTA;
    private JTextArea dayPlanFinishBetterJTA;

    /**
     * 构造时初始化
     */
    public UserInformation() {
        userInformationBox = Box.createVerticalBox();

        userNamePanel = new JPanel();
        planFinishNumPanel = new JPanel();
        planFinishBetterPanel = new JPanel();
        dayPlanFinishNumPanel = new JPanel();
        dayPlanFinishBetterPanel = new JPanel();

        userNameLabel = new JLabel("      用户名：");
        planFinishNumLabel = new JLabel("完成计划个数：");
        planFinishBetterLabel = new JLabel("    超越用户：");
        dayPlanFinishNumLabel = new JLabel("完成计划天数：");
        dayPlanFinishBetterLabel = new JLabel("    超越用户：");

        userNameJTA = new JTextArea(1, 10);
        userNameJTA.setEnabled(false);
        userNameJTA.setText(UserManage.getUserName());

        planFinishNumJTA = new JTextArea(1, 10);
        planFinishNumJTA.setEnabled(false);
        if (UserManage.getUser() == null) {
            planFinishNumJTA.setText("暂无数据");
        } else {
            planFinishNumJTA.setText(String.format("%d", PlanManage.getHistoryPlanFinishNum()));
        }

        planFinishBetterJTA = new JTextArea(1, 10);
        planFinishBetterJTA.setEnabled(false);
        if (UserManage.getUser() == null) {
            planFinishBetterJTA.setText("暂无数据");
        } else {
            planFinishBetterJTA.setText(String.format("%.2f%%", 10.0));
        }

        dayPlanFinishNumJTA = new JTextArea(1, 10);
        dayPlanFinishNumJTA.setEnabled(false);
        if (UserManage.getUser() == null) {
            dayPlanFinishNumJTA.setText("暂无数据");
        } else {
            dayPlanFinishNumJTA.setText(String.format("%d", PlanManage.getHistoryDayPlanFinishNum()));
        }

        dayPlanFinishBetterJTA = new JTextArea(1, 10);
        dayPlanFinishBetterJTA.setEnabled(false);
        if (UserManage.getUser() == null) {
            dayPlanFinishBetterJTA.setText("暂无数据");
        } else {
            dayPlanFinishBetterJTA.setText(String.format("%.2f%%", 1.1));
        }

        userNamePanel.add(userNameLabel);
        userNamePanel.add(userNameJTA);

        planFinishNumPanel.add(planFinishNumLabel);
        planFinishNumPanel.add(planFinishNumJTA);

        planFinishBetterPanel.add(planFinishBetterLabel);
        planFinishBetterPanel.add(planFinishBetterJTA);

        dayPlanFinishNumPanel.add(dayPlanFinishNumLabel);
        dayPlanFinishNumPanel.add(dayPlanFinishNumJTA);

        dayPlanFinishBetterPanel.add(dayPlanFinishBetterLabel);
        dayPlanFinishBetterPanel.add(dayPlanFinishBetterJTA);

        userInformationBox.add(userNamePanel);
        userInformationBox.add(planFinishNumPanel);
        userInformationBox.add(planFinishBetterPanel);
        userInformationBox.add(dayPlanFinishNumPanel);
        userInformationBox.add(dayPlanFinishBetterPanel);
    }

    /**
     *
     * @return 返回构造后的组件
     */
    public Box getUserInformationBox() {
        return userInformationBox;
    }

    /**
     * 更新该组件
     */
    public void updateUserInformation() {

        userNameJTA.setText(UserManage.getUserName());

        if (UserManage.getUser() == null) {
            planFinishNumJTA.setText("暂无数据");
        } else {
            planFinishNumJTA.setText(String.format("%d", PlanManage.getHistoryPlanFinishNum()));
        }

        if (UserManage.getUser() == null) {
            planFinishBetterJTA.setText("暂无数据");
        } else {
            planFinishBetterJTA.setText(String.format("%.2f%%", UserManage.planFinishBetterThan()));
        }

        if (UserManage.getUser() == null) {
            dayPlanFinishNumJTA.setText("暂无数据");
        } else {
            dayPlanFinishNumJTA.setText(String.format("%d", PlanManage.getHistoryDayPlanFinishNum()));
        }

        if (UserManage.getUser() == null) {
            dayPlanFinishBetterJTA.setText("暂无数据");
        } else {
            dayPlanFinishBetterJTA.setText(String.format("%.2f%%", UserManage.dayPlanFinishBetterThan()));
        }
    }
}
