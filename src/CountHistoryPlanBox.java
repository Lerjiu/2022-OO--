import javax.swing.*;
import java.awt.*;

/**
 * 用于显示历史计划统计的封装类
 */
public class CountHistoryPlanBox {
    private Box countHistoryPlanBox;
    private Box leftCountHistoryPlanBox;
    private Box rightCountHistoryPlanBox;
    private JPanel dayPlanNumPanel;
    private JPanel planNumPanel;
    private JPanel dayPlanFinishNumPanel;
    private JPanel planFinishNumPanel;
    private JPanel planFinishRatePanel;
    private JPanel dayPlanFinishRatePanel;
    private JLabel dayPlanNumLabel;
    private JLabel planNumLabel;
    private JLabel dayPlanFinishNumLabel;
    private JLabel planFinishNumLabel;
    private JLabel planFinishRateLabel;
    private JLabel dayPlanFinishRateLabel;
    private JTextArea dayPlanNumJTA;
    private JTextArea planNumJTA;
    private JTextArea dayPlanFinishNumJTA;
    private JTextArea planFinishNumJTA;
    private JTextArea planFinishRateJTA;
    private JTextArea dayPlanFinishRateJTA;

    /**
     * 构造时初始化
     */
    public CountHistoryPlanBox() {
        countHistoryPlanBox = Box.createHorizontalBox();
        leftCountHistoryPlanBox = Box.createVerticalBox();
        rightCountHistoryPlanBox = Box.createVerticalBox();

        dayPlanNumPanel = new JPanel();
        planNumPanel = new JPanel();
        dayPlanFinishNumPanel = new JPanel();
        planFinishNumPanel = new JPanel();
        dayPlanFinishRatePanel = new JPanel();
        planFinishRatePanel = new JPanel();

        dayPlanNumLabel = new JLabel("    制定计划天数：");
        planNumLabel = new JLabel("    制定计划个数：");
        dayPlanFinishNumLabel = new JLabel("    完成计划天数：");
        planFinishNumLabel = new JLabel("    完成计划个数：");
        dayPlanFinishRateLabel = new JLabel("完成计划天数比例：");
        planFinishRateLabel = new JLabel("完成计划数量比例：");

        dayPlanNumJTA = new JTextArea(1, 10);
        dayPlanNumJTA.setEnabled(false);
        dayPlanNumJTA.setText(String.format("%d", PlanManage.getHistoryDayPlanNum()));

        planNumJTA = new JTextArea(1, 10);
        planNumJTA.setEnabled(false);
        planNumJTA.setText(String.format("%d", PlanManage.getHistoryPlanNum()));

        dayPlanFinishNumJTA = new JTextArea(1, 10);
        dayPlanFinishNumJTA.setEnabled(false);
        dayPlanFinishNumJTA.setText(String.format("%d", PlanManage.getHistoryDayPlanFinishNum()));

        planFinishNumJTA = new JTextArea(1, 10);
        planFinishNumJTA.setEnabled(false);
        planFinishNumJTA.setText(String.format("%d", PlanManage.getHistoryPlanFinishNum()));

        dayPlanFinishRateJTA = new JTextArea(1, 10);
        dayPlanFinishRateJTA.setEnabled(false);
        dayPlanFinishRateJTA.setText(String.format("%.2f%%", PlanManage.getHistoryDayPlanFinishRate()));

        planFinishRateJTA = new JTextArea(1, 10);
        planFinishRateJTA.setEnabled(false);
        planFinishRateJTA.setText(String.format("%.2f%%", PlanManage.getHistoryPlanFinishRate()));

        dayPlanNumPanel.add(dayPlanNumLabel);
        dayPlanNumPanel.add(dayPlanNumJTA);

        planNumPanel.add(planNumLabel);
        planNumPanel.add(planNumJTA);

        dayPlanFinishNumPanel.add(dayPlanFinishNumLabel);
        dayPlanFinishNumPanel.add(dayPlanFinishNumJTA);

        planFinishNumPanel.add(planFinishNumLabel);
        planFinishNumPanel.add(planFinishNumJTA);

        dayPlanFinishRatePanel.add(dayPlanFinishRateLabel);
        dayPlanFinishRatePanel.add(dayPlanFinishRateJTA);

        planFinishRatePanel.add(planFinishRateLabel);
        planFinishRatePanel.add(planFinishRateJTA);

        leftCountHistoryPlanBox.add(dayPlanNumPanel);
        leftCountHistoryPlanBox.add(planNumPanel);
        leftCountHistoryPlanBox.add(dayPlanFinishNumPanel);
        leftCountHistoryPlanBox.add(planFinishNumPanel);
        leftCountHistoryPlanBox.add(dayPlanFinishRatePanel);
        leftCountHistoryPlanBox.add(planFinishRatePanel);

        leftCountHistoryPlanBox.setMinimumSize(new Dimension(500, 500));

        Color lightGreen = new Color(0xc1,0xdf,0xc4);
        Color gray = new Color(0xcf,0xd9,0xdf);

        PieChart dayPlanFinishChart = new PieChart();
        dayPlanFinishChart.addPart(PlanManage.getHistoryDayPlanFinishNum(), null, lightGreen);
        dayPlanFinishChart.addPart(PlanManage.getHistoryDayPlanNum() - PlanManage.getHistoryDayPlanFinishNum(), null, gray);

        PieChart planFinishChart = new PieChart();
        planFinishChart.addPart(PlanManage.getHistoryPlanFinishNum(), null, lightGreen);
        planFinishChart.addPart(PlanManage.getHistoryPlanNum() - PlanManage.getHistoryPlanFinishNum(), null, gray);


        JLabel finishLabel = new JLabel("已完成");
        finishLabel.setForeground(lightGreen);
        JLabel unfinishedLabel = new JLabel("未完成");
        unfinishedLabel.setForeground(gray);

        rightCountHistoryPlanBox.add(finishLabel);
        rightCountHistoryPlanBox.add(unfinishedLabel);
        rightCountHistoryPlanBox.add(new JLabel("完成计划天数比例"));
        rightCountHistoryPlanBox.add(dayPlanFinishChart);
        rightCountHistoryPlanBox.add(new JLabel("完成计划数量比例"));
        rightCountHistoryPlanBox.add(planFinishChart);

        countHistoryPlanBox.add(leftCountHistoryPlanBox);
        countHistoryPlanBox.add(rightCountHistoryPlanBox);
    }

    /**
     *
     * @return 返回构造后的组件
     */
    public Box getCountHistoryPlanBox() {
        return countHistoryPlanBox;
    }
}
