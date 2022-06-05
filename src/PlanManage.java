import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlanManage {
    //命名：xxxx-xx-xx-plan
    private static ArrayList<DayPlanList> allPlanList = new ArrayList<>();
    private static PlanTable todayPlanTable;
    private static DayPlanList todayPlanList = new DayPlanList();
    private static File todayPlanFile;

    private static void getDayPlan(File file, PlanAddible planAddible) {
        DayPlanList dayPlanList = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    new FileInputStream(file));

            dayPlanList = (DayPlanList) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        for (int i = 0; i < dayPlanList.size(); i++) {
            planAddible.addPlan(dayPlanList.get(i));
        }
    }

    public static String getPlanFileName(Calendar day, boolean withSuffix) {
        StringBuilder sb = new StringBuilder();
        sb.append(day.get(Calendar.YEAR));
        sb.append('-');
        sb.append(String.format("%02d", day.get(Calendar.MONTH) + 1));
        sb.append('-');
        sb.append(String.format("%02d", day.get(Calendar.DAY_OF_MONTH)));
        sb.append('-');
        sb.append("plan");
        sb.append(".plan");

        return sb.toString();
    }

    //file->planTable
    public static void getTodayPlan(PlanTable planTable) {
        todayPlanTable = planTable;

        try {
            todayPlanFile = new File(getPlanFileName(Calendar.getInstance(), true));
            if (!todayPlanFile.exists()) {
                todayPlanFile.createNewFile();
            }

            getDayPlan(todayPlanFile, planTable);

        } catch (EOFException e) {
            System.out.println(todayPlanList == null);
            System.out.println("无计划");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //planTable->file
    public static void updateDayPlanList() {
        todayPlanList.clear();
        todayPlanTable.updatePlanToDayPlanList(todayPlanList);

        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(todayPlanFile));

            objectOutputStream.writeObject(todayPlanList);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getHistoryPlan(JTabbedPane jTabbedPane) {
        File directory = new File("");
        directory = new File(directory.getAbsolutePath());

        if (!directory.exists()) {
            directory.mkdir();
        }
//        System.out.println(directory.getAbsolutePath());
        File[] files = directory.listFiles();
        System.out.println(files.length);

        ArrayList<File> planFiles = new ArrayList<>();

        Pattern pattern = Pattern.compile("^.*\\.plan$");
        Matcher matcher;
        for (File file : files) {
            if (file.getName().equals(getPlanFileName(Calendar.getInstance(), true)))
                continue;
            matcher = pattern.matcher(file.getName());
            if (matcher.matches()) {
                System.out.println("match");
                planFiles.add(file);
            }
        }

        Collections.sort(planFiles, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });

        for (File file : planFiles) {
            HistoryPlanTable historyPlanTable = new HistoryPlanTable();
            getDayPlan(file, historyPlanTable);
            jTabbedPane.add(file.getName().replace(".plan", ""), historyPlanTable.getPlanTableBox());
//            System.out.println("add");
        }

//        for (int i = 0; i < 20; i++) {
//            jTabbedPane.add(String.format("%d", i), Box.createHorizontalBox());
//        }
    }
}
