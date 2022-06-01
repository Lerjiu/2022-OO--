import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlanDetailDialog {
    private JDialog planDetailDialog;
    private PlanTable planTable;
    private Plan plan;
    private Box contentBox;
    private Box setPlanBox;
    private Box box;
    private Box titleBox;
    private Box describeBox;
    private Box buttonBox;
    private JTextField planTitle;
    private JTextArea planDescribe;
    private JLabel titleJLabel;
    private JLabel describeJLabel;
    private JButton okButton;
    private JButton cancelButton;
    private JScrollPane jScrollPane;
    private JComboBox<String> startHourComboBox;
    private JComboBox<String> startMinuteComboBox;
    private JComboBox<String> endHourComboBox;
    private JComboBox<String> endMinuteComboBox;
    private Box startTimeBox;
    private Box endTimeBox;
    private Box timeBox;
    private JPanel startHourPanel;
    private JPanel startMinutePanel;
    private JPanel endHourPanel;
    private JPanel endMinutePanel;

    public PlanDetailDialog(JFrame jFrame, PlanTable planTable) {
        System.out.println("创建plan detail dialog");
        this.planTable = planTable;
        this.planDetailDialog = new JDialog(jFrame, "计划信息", true) {
            @Override
            public void dispose() {
                System.out.println("in dispose" + planTable.getIsModify());
                planTable.setModify(false);
                System.out.println("in dispose" + planTable.getIsModify());
                super.dispose();
            }
        };

        planDetailDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        this.plan = planTable.getSelectedPlan();

        titleJLabel = new JLabel("计划");
        describeJLabel = new JLabel("详细信息");

        planTitle = new JTextField(40);
        planTitle.setText(plan.getPlanTitle());
        LimitTextLength.limitTextLength(planTitle,15);

        planDescribe = new JTextArea(5,40);
        planDescribe.setText(plan.getPlanDescribe());
        LimitTextLength.limitTextLength(planDescribe,100);
        planDescribe.setLineWrap(true);
        planDescribe.setWrapStyleWord(true);
        jScrollPane = new JScrollPane(planDescribe);

        titleBox = Box.createHorizontalBox();
        describeBox = Box.createHorizontalBox();
        titleBox.add(titleJLabel);
        titleBox.add(planTitle);
        describeBox.add(describeJLabel);
        describeBox.add(jScrollPane);
        titleBox.setPreferredSize(new Dimension(600,40));
        describeBox.setPreferredSize(new Dimension(600,200));

        box = Box.createVerticalBox();

        okButton = new JButton("确认");
        cancelButton = new JButton("取消");
        buttonBox = Box.createHorizontalBox();
        buttonBox.add(okButton);
        buttonBox.add(cancelButton);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pattern pattern = Pattern.compile("[\\s]*");
                Matcher matcher = pattern.matcher(planTitle.getText());
                if (!matcher.matches()) {
                    if (legalSettingTime()) {
                        Plan plan = new Plan(planTable,planTitle.getText(),
                                planDescribe.getText(),
                                (String) startHourComboBox.getSelectedItem(),
                                (String) startMinuteComboBox.getSelectedItem(),
                                (String) endHourComboBox.getSelectedItem(),
                                (String) endMinuteComboBox.getSelectedItem());
                        planTable.removeSelectedPlan();
                        int pos = planTable.addPlan(plan);
                        planTable.setSelectedPlan(pos);

                        JOptionPane.showMessageDialog(planDetailDialog,"修改成功");
                    }

                } else {
                    JOptionPane.showMessageDialog(planDetailDialog,"计划不能为空");
                }

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planDetailDialog.dispose();
            }
        });

        startTimeBox = Box.createHorizontalBox();
        endTimeBox = Box.createHorizontalBox();
        timeBox = Box.createVerticalBox();
        startHourComboBox = new TimeComboBox(24).getTimeComboBox();
        startMinuteComboBox = new TimeComboBox(60).getTimeComboBox();
        endHourComboBox = new TimeComboBox(24).getTimeComboBox();
        endMinuteComboBox = new TimeComboBox(60).getTimeComboBox();
        startHourComboBox.setSelectedIndex(plan.getStart().get(Calendar.HOUR_OF_DAY));
        startMinuteComboBox.setSelectedIndex(plan.getStart().get(Calendar.MINUTE));
        endHourComboBox.setSelectedIndex(plan.getEnd().get(Calendar.HOUR_OF_DAY));
        endMinuteComboBox.setSelectedIndex(plan.getEnd().get(Calendar.MINUTE));

        startHourPanel = new JPanel();
        startMinutePanel = new JPanel();
        endHourPanel = new JPanel();
        endMinutePanel = new JPanel();
        startHourPanel.add(startHourComboBox);
        startMinutePanel.add(startMinuteComboBox);
        endHourPanel.add(endHourComboBox);
        endMinutePanel.add(endMinuteComboBox);
        startTimeBox.add(Box.createHorizontalGlue());
        startTimeBox.add(startHourPanel);
        startTimeBox.add(startMinutePanel);
        startTimeBox.add(Box.createHorizontalGlue());
        endTimeBox.add(Box.createHorizontalGlue());
        endTimeBox.add(endHourPanel);
        endTimeBox.add(endMinutePanel);
        endTimeBox.add(Box.createHorizontalGlue());
        timeBox.add(Box.createVerticalGlue());
        timeBox.add(startTimeBox);
        timeBox.add(endTimeBox);
        timeBox.add(Box.createVerticalGlue());

        contentBox = Box.createVerticalBox();
        contentBox.add(titleBox);
        contentBox.add(describeBox);
        setPlanBox = Box.createHorizontalBox();
        setPlanBox.add(contentBox);
        setPlanBox.add(timeBox);
        box.add(setPlanBox);
        box.add(buttonBox);
        box.setPreferredSize(new Dimension(800,300));

        planDetailDialog.add(box);
        planDetailDialog.setLocation(300,300);
        planDetailDialog.setSize(box.getPreferredSize());
    }

    public JDialog getPlanDetailDialog() {
        return planDetailDialog;
    }

    public void setVisible(boolean visible) {
        planDetailDialog.setVisible(visible);
    }

    private boolean legalSettingTime() {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        int startHour = startHourComboBox.getSelectedIndex();
        int startMinute = startMinuteComboBox.getSelectedIndex();
        int endHour = endHourComboBox.getSelectedIndex();
        int endMinute = endMinuteComboBox.getSelectedIndex();
        startCalendar.set(Calendar.HOUR_OF_DAY, startHour);
        startCalendar.set(Calendar.MINUTE, startMinute);
        endCalendar.set(Calendar.HOUR, endHour);
        endCalendar.set(Calendar.MINUTE, endMinute);

        Calendar now = Calendar.getInstance();
        if (endCalendar.before(now)) {
            JOptionPane.showMessageDialog(planDetailDialog, "计划截止时间不能早于当前时间~");
            return false;
        } else if (planTable.modifyOverlapPlan(startCalendar, endCalendar)) {
            JOptionPane.showMessageDialog(planDetailDialog, "和已有计划重叠啦~");
            return false;
        }
        return true;
    }
}
