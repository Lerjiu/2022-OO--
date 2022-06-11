import javax.swing.*;

/**
 * 用于整个应用进行分页显示地封装类
 */
public class PageTabbedPane {
    private JTabbedPane pageTabbedPane;
    private Box todayPlanBox;
    private JScrollPane historyPlanJScrollPane;
    private Box countPlanBox;
    private JSplitPane runningPlanSplitPane;
    private JTabbedPane personalCenter;

    /**
     * 构造时初始化
     * @param todayPlanBox 今日计划
     * @param historyPlanJScrollPane 历史计划
     * @param countPlanBox 计划统计
     * @param runningPlanSplitPane 正在进行
     * @param personalCenter 个人中心
     */
    public PageTabbedPane(Box todayPlanBox, JScrollPane historyPlanJScrollPane, Box countPlanBox, JSplitPane runningPlanSplitPane, JTabbedPane personalCenter) {
        this.todayPlanBox = todayPlanBox;
        this.historyPlanJScrollPane = historyPlanJScrollPane;
        this.countPlanBox = countPlanBox;
        this.runningPlanSplitPane = runningPlanSplitPane;
        this.personalCenter = personalCenter;

        pageTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        pageTabbedPane.add("今日计划", todayPlanBox);
        pageTabbedPane.add("历史计划", historyPlanJScrollPane);
        pageTabbedPane.add("计划统计", countPlanBox);
        pageTabbedPane.add("当前计划", runningPlanSplitPane);
        pageTabbedPane.add("个人中心", personalCenter);
    }

    /**
     *
     * @return 返回构造后的组件
     */
    public JTabbedPane getPageTabbedPane() {
        return pageTabbedPane;
    }
}
