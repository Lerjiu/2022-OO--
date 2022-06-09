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
    private static int historyDayPlanNum = 0;
    private static int historyPlanNum = 0;
    private static int historyPlanFinishNum = 0;
    private static int historyDayPlanFinishNum = 0;

    private static int tmp_dayPlanNum = 0;
    private static int tmp_dayPlanFinishNum = 0;

    private static boolean getDayPlan(File file, PlanAddible planAddible) {
        DayPlanList dayPlanList = null;
        ObjectInputStream objectInputStream = null;
        if (file.length() == 0) {
            return false;
        }
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
        } catch (EOFException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            dayPlanList = (DayPlanList) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try {
            objectInputStream.close();
            System.out.println("close");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < dayPlanList.size(); i++) {
            planAddible.addPlan(dayPlanList.get(i));
        }

        tmp_dayPlanNum = dayPlanList.size();
        tmp_dayPlanFinishNum = dayPlanList.getPlanFinishNum();

        return true;
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
        System.out.println("update");
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

        for (File file : files) {
            if (file.getName().equals(getPlanFileName(Calendar.getInstance(), true)))
                continue;

            if (isPlanFile(file)) {
//                System.out.println("match");
                planFiles.add(file);
            }
        }

        Collections.sort(planFiles, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });

        historyDayPlanNum = planFiles.size();

        for (File file : planFiles) {
            HistoryPlanTable historyPlanTable = new HistoryPlanTable();
            if (!getDayPlan(file, historyPlanTable)) {
                file.delete();
                historyDayPlanNum--;
                continue;
            }
            historyPlanNum += tmp_dayPlanNum;
            historyPlanFinishNum += tmp_dayPlanFinishNum;
            if ((tmp_dayPlanFinishNum == tmp_dayPlanNum) && (tmp_dayPlanFinishNum != 0)) {
                historyDayPlanFinishNum++;
            }
            tmp_dayPlanNum = 0;
            tmp_dayPlanFinishNum = 0;
            jTabbedPane.add(file.getName().replace(".plan", ""), historyPlanTable.getPlanTableBox());
//            System.out.println("add");
        }

    }

    public static int getHistoryDayPlanNum() {
        return historyDayPlanNum;
    }

    public static int getHistoryPlanNum() {
        return historyPlanNum;
    }

    public static int getHistoryPlanFinishNum() {
        return historyPlanFinishNum;
    }

    public static int getHistoryDayPlanFinishNum() {
        return historyDayPlanFinishNum;
    }

    public static double getHistoryDayPlanFinishRate() {
        System.out.println(historyDayPlanFinishNum);
        return 100.0 * historyDayPlanFinishNum / historyDayPlanNum;
    }

    public static double getHistoryPlanFinishRate() {
        System.out.println(historyPlanFinishNum);
        return 100.0 * historyPlanFinishNum / historyPlanNum;
    }

    public static boolean isPlanFile(File file) {
        Pattern pattern = Pattern.compile("^.*\\.plan$");
        Matcher matcher = pattern.matcher(file.getName());;
        return matcher.matches();
    }
}
