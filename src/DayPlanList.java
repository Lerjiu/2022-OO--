import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 用于封装一天的plan，实现序列化，进行存储与传输
 */
public class DayPlanList implements Serializable {
    /**
     * {@value serialVersionUID}
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Plan> dayPlanList = new ArrayList<>();
    private Calendar thisDay;

    /**
     * 构造时初始化日期
     */
    public DayPlanList() {
        this.thisDay = Calendar.getInstance();
    }

    /**
     * 添加计划
     * @param plan
     */
    public void addDayPlan(Plan plan) {
        dayPlanList.add(plan);
    }

    /**
     *
     * @return 返回该日完成计划数量
     */
    public int getPlanFinishNum() {
        int num = 0;
        for (Plan plan : dayPlanList) {
            if (plan.getPlanStatus() == Plan.FINISHED) {
                num++;
            }
        }
        return num;
    }

    /**
     * 清除计划（用于今日计划更改时，全部清除再全部载入）
     */
    public void clear() {
        dayPlanList.clear();
    }

    /**
     *
     * @return 返回plan数量
     */
    public int size() {
        return dayPlanList.size();
    }

    /**
     *
     * @param index
     * @return 返回对应位置的计划
     */
    public Plan get(int index) {
        return dayPlanList.get(index);
    }
}
