import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class DayPlanList implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Plan> dayPlanList = new ArrayList<>();
    private Calendar thisDay;

    public DayPlanList() {
        this.thisDay = Calendar.getInstance();
    }

    public void addDayPlan(Plan plan) {
        dayPlanList.add(plan);
    }


    public boolean isSameDay(Calendar now) {
        if (thisDay.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                && thisDay.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
            return true;
        }
        return false;
    }

    public void clear() {
        dayPlanList.clear();
    }

    public int size() {
        return dayPlanList.size();
    }

    public Plan get(int index) {
        return dayPlanList.get(index);
    }
}
