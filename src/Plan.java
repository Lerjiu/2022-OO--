import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class Plan {
    public static final int NOT_STARTED = 0;
    public static final int UNFINISHED = 1;
    public static final int FINISHED = 2;
    public static final int RUNNING = 3;

    private String planTitle;
    private String planDescribe;
    private Calendar start;
    private Calendar end;
    private int planStatus;
    private JButton finishButton;
    private PlanTable planTable;

    public Plan(PlanTable planTable, String planTitle,String planDescribe, String startHour, String startMinute, String endHour, String endMinute) {
        this.start = Calendar.getInstance();
        this.end = Calendar.getInstance();
        initPlanStatus();
        this.planTable = planTable;
        this.finishButton = new JButton("完成计划");
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planStatus = FINISHED;
                planTable.updatePlanTable();
            }
        });
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
        this.end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endHour));
        this.end.set(Calendar.MINUTE, Integer.parseInt(endMinute));
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanDescribe(String planDescribe) {
        this.planDescribe = planDescribe;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public String getPlanDescribe() {
        return planDescribe;
    }

    public Calendar getStart() {
        return start;
    }

    public Calendar getEnd() {
        return end;
    }

    public int getPlanStatus() {
        return planStatus;
    }

    public JButton getFinishButton() {
        return finishButton;
    }

    public static String getStringPlanStatus(int planStatus) {
        if (planStatus == Plan.NOT_STARTED) {
            return "未开始";
        } else if (planStatus == Plan.UNFINISHED) {
            return "未完成";
        } else {
            return "已完成";
        }
    }

    public String getStringPlanStatus() {
        if (planStatus == Plan.NOT_STARTED) {
            return "未开始";
        } else if (planStatus == Plan.UNFINISHED) {
            return "未完成";
        } else {
            return "已完成";
        }
    }

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

    private void initPlanStatus() {
        Calendar now = Calendar.getInstance();
        if(start.after(now)) {
            planStatus = NOT_STARTED;
        } else if (start.before(now) && end.after(now)) {
            planStatus = RUNNING;
        }
    }

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
}
