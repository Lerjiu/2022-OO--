import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

public class PlanManage {
    //命名：xxxx-xx-xx-plan
    private static ArrayList<DayPlanList> allPlanList = new ArrayList<>();
    private static PlanTable todayPlanTable;
    private static DayPlanList dayPlanList = new DayPlanList(Calendar.getInstance());
    private static File file;

    public static void getTodayPlan(PlanTable planTable) {
        todayPlanTable = planTable;
        Calendar now = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(now.get(Calendar.YEAR));
        sb.append('-');
        sb.append(String.format("%02d", now.get(Calendar.MONTH) + 1));
        sb.append('-');
        sb.append(String.format("%02d", now.get(Calendar.DAY_OF_MONTH)));
        sb.append('-');
        sb.append("plan");
        try {
            file = new File(sb.toString());
            if (!file.exists()) {
                file.createNewFile();
            }

            ObjectInputStream objectInputStream = new ObjectInputStream(
                    new FileInputStream(file));

            dayPlanList = (DayPlanList) objectInputStream.readObject();
            objectInputStream.close();
//            System.out.println(dayPlanList == null);

            for (int i = 0; i < dayPlanList.size(); i++) {
                todayPlanTable.addPlan(dayPlanList.get(i));
            }
//            System.out.println(dayPlanList.isSameDay(Calendar.getInstance()));
        } catch (EOFException e) {
            System.out.println(dayPlanList == null);
            System.out.println("无计划");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void updateDayPlanList() {
        dayPlanList.clear();
        todayPlanTable.updatePlanToDayPlanList(dayPlanList);

        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(file));

            objectOutputStream.writeObject(dayPlanList);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
