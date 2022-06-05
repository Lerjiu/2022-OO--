import jb2011.lnf.beautyeye.widget.border.BERoundBorder;

import javax.swing.*;
import java.awt.*;

public class RunningPlanBox {
    private Box showRunningPlanBox;
    private JPanel planTitle;
    private JPanel planTime;
    private JPanel planDescribe;
    private JLabel planTitleJLabel;
    private JLabel planTimeJLabel;
    private JLabel planDescribeJLabel;
    private JTextArea planTitleJTA;
    private JTextArea planTimeJTA;
    private JTextArea planDescribeJTA;

    public RunningPlanBox() {
        Color color = new Color(0xa1,0xc4,0xfd);

        showRunningPlanBox = Box.createVerticalBox();
        planTitle = new JPanel();
        planTime = new JPanel();
        planDescribe = new JPanel();

        planTitleJLabel = new JLabel("计划标题");
        planTimeJLabel = new JLabel("计划时间");
        planDescribeJLabel = new JLabel("详细信息");

        planTitleJTA = new JTextArea(1, 40);
        planTitleJTA.setEnabled(false);
        planTitleJTA.setText("暂无进行中的计划");

        planTimeJTA = new JTextArea(1, 40);
        planTimeJTA.setEnabled(false);
        planTimeJTA.setText("00:00-00:00");

        planDescribeJTA = new JTextArea(8, 40);
        planDescribeJTA.setLineWrap(true);
        planDescribeJTA.setWrapStyleWord(true);
        planDescribeJTA.setEnabled(false);
        planDescribeJTA.setText("暂无进行中的计划");


        planTitle.add(planTitleJLabel);
        planTitle.add(planTitleJTA);

        planTime.add(planTimeJLabel);
        planTime.add(planTimeJTA);

        planDescribe.add(planDescribeJLabel);
        planDescribe.add(planDescribeJTA);
//        planTitle.setMinimumSize(new Dimension(300, 100));
//        planTime.setMinimumSize(new Dimension(300, 100));
//        planDescribe.setMinimumSize(new Dimension(300, 200));
        BERoundBorder border = new BERoundBorder(color,6);
        planTitle.setBorder(border);
        planTime.setBorder(border);
        planDescribe.setBorder(border);

        showRunningPlanBox.add(planTitle);
        showRunningPlanBox.add(planTime);
        showRunningPlanBox.add(planDescribe);
        showRunningPlanBox.setMinimumSize(new Dimension(500, 500));
    }

    public Box getRunningPlanBox() {
        return showRunningPlanBox;
    }

    public void updateRunningPlanBox(Plan plan) {
        if (plan == null) {
            planTitleJTA.setText("暂无进行中的计划");
            planTimeJTA.setText("00:00-00:00");
            planDescribeJTA.setText("暂无进行中的计划");
        } else {
            planTitleJTA.setText(plan.getPlanTitle());
            planTimeJTA.setText(plan.getStringPlanTime());
            planDescribeJTA.setText(plan.getPlanDescribe());
        }
    }
}
