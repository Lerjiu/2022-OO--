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

    public RunningPlanBox() {
        showRunningPlanBox = Box.createVerticalBox();
        planTitle = new JPanel();
        planTime = new JPanel();
        planDescribe = new JPanel();

        planTitleJLabel = new JLabel("暂无进行中的计划");
        planTimeJLabel = new JLabel("00:00-00:00");
        planDescribeJLabel = new JLabel("暂无进行中的计划");
        planDescribeJLabel.setSize(new Dimension(500, 0));

        planTitle.add(planTitleJLabel);
        planTime.add(planTimeJLabel);
        planDescribe.add(planDescribeJLabel);
//        planTitle.setMinimumSize(new Dimension(300, 100));
//        planTime.setMinimumSize(new Dimension(300, 100));
//        planDescribe.setMinimumSize(new Dimension(300, 200));
        BERoundBorder border = new BERoundBorder(new Color(0xa1,0xc4,0xfd),6);
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
            planTitleJLabel.setText("暂无进行中的计划");
            planTimeJLabel.setText("00:00-00:00");
            planDescribeJLabel.setText("暂无进行中的计划");
        } else {
            planTitleJLabel.setText(plan.getPlanTitle());
            planTimeJLabel.setText(plan.getStringPlanTime());
            try {
                jLabelLineWrap(planDescribeJLabel, "<html>" + plan.getPlanDescribe() + "</html>");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void jLabelLineWrap(JLabel jLabel, String longString)
            throws InterruptedException {
        StringBuilder builder = new StringBuilder("");
        char[] chars = longString.toCharArray();
        FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
        int start = 0;
        int len = 0;
        while (start + len < longString.length()) {
            while (true) {
                len++;
                if (start + len > longString.length())break;
                if (fontMetrics.charsWidth(chars, start, len)
                        > jLabel.getWidth()) {
                    break;
                }
            }
            System.out.println("-------------------");
            builder.append(chars, start, len-1).append("<br/>");
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longString.length()-start);
        builder.append("");
        jLabel.setText(builder.toString());
    }
}
