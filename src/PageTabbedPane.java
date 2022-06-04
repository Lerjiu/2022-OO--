import javax.swing.*;

public class PageTabbedPane {
    private JTabbedPane pageTabbedPane;
    private Box todayPlanBox;
    private Box historyPlanBox;
    private Box countPlanBox;
    private Box runningPlanBox;

    public PageTabbedPane(Box todayPlanBox, Box historyPlanBox, Box countPlanBox, Box runningPlanBox) {
        this.todayPlanBox = todayPlanBox;
        this.historyPlanBox = historyPlanBox;
        this.countPlanBox = countPlanBox;
        this.runningPlanBox = runningPlanBox;

        pageTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        pageTabbedPane.add("今日计划", todayPlanBox);
        pageTabbedPane.add("历史计划", historyPlanBox);
        pageTabbedPane.add("计划统计", countPlanBox);
        pageTabbedPane.add("当前计划", runningPlanBox);
    }

    public JTabbedPane getPageTabbedPane() {
        return pageTabbedPane;
    }
}
