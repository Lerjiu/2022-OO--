import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Calendar;

/**
 * 计划的实体类，实现序列化，用于存储与传输
 */
public class Plan implements Serializable {
    /**
     * {@value serialVersionUID}
     */
    private static final long serialVersionUID = 2L;
    /**
     * {@value NOT_STARTED}
     * {@value UNFINISHED}
     * {@value FINISHED}
     * {@value UNFINISHED}
     */
    public static final int NOT_STARTED = 0;
    public static final int UNFINISHED = 1;
    public static final int FINISHED = 2;
    public static final int RUNNING = 3;

    private String planTitle;
    private String planDescribe;
    private Calendar start;
    private Calendar end;
    private int planStatus;
    private JButton finishButton = new JButton("完成计划");
//    private PlanTable planTable;

    /**
     *
     * @param planTable         plan所在planTable，用于plan更改时，通知planTable更新
     * @param planTitle         plan标题
     * @param planDescribe      plan详细描述（可以为空）
     * @param startHour         一下均为plan相关时间
     * @param startMinute
     * @param endHour
     * @param endMinute
     */
    public Plan(PlanTable planTable, String planTitle,String planDescribe, String startHour, String startMinute, String endHour, String endMinute) {
        this.start = Calendar.getInstance();
        this.end = Calendar.getInstance();
        initPlanStatus();
//        this.planTable = planTable;
        addListenerForButton(planTable);

        this.planTitle = planTitle;
        this.planDescribe = planDescribe;
//        Date date = new Date();
//        String yearS = String.format("%ty", date);
//
//        int year = Integer.parseInt(yearS);
//        int month = Integer.parseInt(String.format("%tm", date));
//        int day = Integer.parseInt(String.format("%te", date));
        this.start.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startHour));
        this.start.set(Calendar.MINUTE, Integer.parseInt(startMinute));
        this.start.set(Calendar.SECOND, 0);
        this.start.set(Calendar.MILLISECOND, 0);
        this.end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endHour));
        this.end.set(Calendar.MINUTE, Integer.parseInt(endMinute));
        this.end.set(Calendar.SECOND, 0);
        this.end.set(Calendar.MILLISECOND, 0);
    }

    /**
     * @param planTitle
     */
    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    /**
     * @return planTitle
     */
    public String getPlanTitle() {
        return planTitle;
    }

    /**
     * @param planDescribe
     */
    public void setPlanDescribe(String planDescribe) {
        this.planDescribe = planDescribe;
    }

    /**
     * @param start
     */
    public void setStart(Calendar start) {
        this.start = start;
    }

    /**
     * @param end
     */
    public void setEnd(Calendar end) {
        this.end = end;
    }

    /**
     * @return planDescribe
     */
    public String getPlanDescribe() {
        return planDescribe;
    }

    /**
     * @return start
     */
    public Calendar getStart() {
        return start;
    }

    /**
     * @return end
     */
    public Calendar getEnd() {
        return end;
    }

    /**
     * @return planStatus
     */
    public int getPlanStatus() {
        return planStatus;
    }

    /**
     * @param planStatus
     */
    public void setPlanStatus(int planStatus) {
        this.planStatus = planStatus;
    }

    /**
     * @return finishButton 完成plan的按钮
     */
    public JButton getFinishButton() {
        return finishButton;
    }

    /**
     * @return String plan状态对应的字符串，用于显示
     */
    public String getStringPlanStatus() {
        if (planStatus == Plan.NOT_STARTED) {
            return "未开始";
        } else if (planStatus == Plan.UNFINISHED) {
            return "未完成";
        } else if (planStatus == Plan.FINISHED) {
            return "已完成";
        } else {
            return "进行中";
        }
    }

    /**
     *
     * @return String plan时间对应的字符串，用于显示
     */
    public String getStringPlanTime() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d",getStart().get(Calendar.HOUR_OF_DAY)));
        sb.append(':');
        sb.append(String.format("%02d",getStart().get(Calendar.MINUTE)));
        sb.append('-');
        sb.append(String.format("%02d",getEnd().get(Calendar.HOUR_OF_DAY)));
        sb.append(':');
        sb.append(String.format("%02d",getEnd().get(Calendar.MINUTE)));
        return sb.toString();
//        return getStart().get(Calendar.HOUR_OF_DAY) + ":" + getStart().get(Calendar.MINUTE)
//                + "-" + getEnd().get(Calendar.HOUR_OF_DAY) + ":" + getEnd().get(Calendar.MINUTE);
    }

    /**
     * 初始化plan状态
     */
    private void initPlanStatus() {
        Calendar now = Calendar.getInstance();
        if(start.after(now)) {
            planStatus = NOT_STARTED;
        } else if (start.before(now) && end.after(now)) {
            planStatus = RUNNING;
        }
    }

    /**
     * @deprecated 选择在PlanTable中实现该功能（虽然实际区别不大）
     * 实时监督并更新plan状态
     */
    public void updatePlanStatus() {
        if (planStatus != Plan.FINISHED && planStatus != Plan.UNFINISHED) {
            Calendar now = Calendar.getInstance();
            if(start.after(now)) {
                planStatus = NOT_STARTED;
            } else if (start.before(now) && end.after(now)) {
                planStatus = RUNNING;
            } else if (end.before(now)) {
                planStatus = UNFINISHED;
            }
        }
    }

    public void addListenerForButton(PlanTable planTable) {
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (planStatus == Plan.NOT_STARTED) {
                    JOptionPane.showMessageDialog(planTable.getFrame(), "计划还未开始~");
                } else if (planStatus == Plan.UNFINISHED) {
                    int ret = JOptionPane.showConfirmDialog(planTable.getFrame(),
                            "错过了计划截止时间，您是否真的完成了计划？",
                            "未完成计划",
                            JOptionPane.YES_NO_OPTION);
                    if (ret == JOptionPane.YES_OPTION) {
                        planStatus = FINISHED;
                        planTable.updatePlanTable();
                        PlanManage.updateDayPlanList();
                    }
                } else if (planStatus == Plan.FINISHED) {
                    JOptionPane.showMessageDialog(planTable.getFrame(), "计划已经完成过了");
                } else if (planStatus == Plan.RUNNING) {
                    planStatus = FINISHED;
                    planTable.updatePlanTable();
                    PlanManage.updateDayPlanList();
                    JOptionPane.showMessageDialog(planTable.getFrame(), "完成了该计划，向下一个目标加油！");
                }
            }
        });
        finishButton.setEnabled(false);
        finishButton.setEnabled(true);
    }

    public void removeListenerForButton() {
        ActionListener[] actionListeners = finishButton.getActionListeners();
        for (ActionListener a : actionListeners) {
            finishButton.removeActionListener(a);
        }
    }
}
