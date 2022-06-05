import javax.swing.*;

public class PageTabbedPane {
    private JTabbedPane pageTabbedPane;
    private Box todayPlanBox;
    private JScrollPane historyPlanJScrollPane;
    private Box countPlanBox;
    private JSplitPane runningPlanSplitPane;

    public PageTabbedPane(Box todayPlanBox, JScrollPane historyPlanJScrollPane, Box countPlanBox, JSplitPane runningPlanSplitPane) {
        this.todayPlanBox = todayPlanBox;
        this.historyPlanJScrollPane = historyPlanJScrollPane;
        this.countPlanBox = countPlanBox;
        this.runningPlanSplitPane = runningPlanSplitPane;

        pageTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        pageTabbedPane.add("今日计划", todayPlanBox);
        pageTabbedPane.add("历史计划", historyPlanJScrollPane);
        pageTabbedPane.add("计划统计", countPlanBox);
        pageTabbedPane.add("当前计划", runningPlanSplitPane);
    }

    public JTabbedPane getPageTabbedPane() {
        return pageTabbedPane;
    }
}
