import com.JavaPro.dao.PlanFileDao;
import com.JavaPro.model.PlanFile;
import com.JavaPro.util.DbUtil;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于管理计划
 * 进行应用界面所需plan数据的加载
 * 进行本地plan文件的维护
 * 进行服务器plan数据的维护
 */
public class PlanManage {
    //命名：xxxx-xx-xx-plan.plan
    private static PlanFileDao planFileDao = new PlanFileDao();
    private static PlanTable todayPlanTable;
    private static JTabbedPane historyPlans;
    private static DayPlanList todayPlanList = new DayPlanList();
    private static File todayPlanFile;
    private static int historyDayPlanNum = 0;
    private static int historyPlanNum = 0;
    private static int historyPlanFinishNum = 0;
    private static int historyDayPlanFinishNum = 0;

    private static int tmp_dayPlanNum = 0;
    private static int tmp_dayPlanFinishNum = 0;

    /**
     * 从file中构造出dayPlanList对象，向planAddible中加载一天的计划
     * @param file
     * @param planAddible
     * @return 执行是否成功
     */
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

    /**
     * 根据与日期返回规定的文件名，选择是否带后缀名
     * @param day
     * @param withSuffix
     * @return 规范的文件名
     */
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

    /**
     * 启动应用后，从文件中为planTable恢复今日的计划
     * @param planTable
     */
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

    /**
     * 登录后，执行此方法更新页面显示地数据
     */
    public static void getTodayPlan() {
        todayPlanTable.removeAllPlan();
        getTodayPlan(todayPlanTable);
    }

    /**
     * 用户更改今日计划后，更新对应文件
     */
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
        //每次更改本地当天的文件后，都上传更改
        updateRemotePlanFile();
    }

    /**
     * 应用启动后，从文件中为历史计划的显示加载数据
     * @param jTabbedPane 按天显示历史计划的组件
     */
    public static void getHistoryPlan(JTabbedPane jTabbedPane) {
        historyPlans = jTabbedPane;

        File directory = new File("");
        directory = new File(directory.getAbsolutePath());

        if (!directory.exists()) {
            directory.mkdir();
        }
//        System.out.println(directory.getAbsolutePath());
        File[] files = directory.listFiles();
//        System.out.println(files.length);

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
                System.out.println("history file delete" + file.delete());
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

    /**
     * 登录后，执行此方法更新页面显示地数据
     */
    public static void getHistoryPlan() {
        for (int i = 0; i < historyPlans.getTabCount(); i++) {
            historyPlans.remove(i);
        }
        getHistoryPlan(historyPlans);
    }

    /**
     *
     * @return 返回创建了计划的天数
     */
    public static int getHistoryDayPlanNum() {
        return historyDayPlanNum;
    }

    /**
     *
     * @return 返回创建计划的个数
     */
    public static int getHistoryPlanNum() {
        return historyPlanNum;
    }

    /**
     *
     * @return 返回完成计划的个数
     */
    public static int getHistoryPlanFinishNum() {
        return historyPlanFinishNum;
    }

    /**
     *
     * @return 返回完成计划的天数
     */
    public static int getHistoryDayPlanFinishNum() {
        return historyDayPlanFinishNum;
    }

    /**
     *
     * @return 返回完成计划天数的比例
     */
    public static double getHistoryDayPlanFinishRate() {
        System.out.println(historyDayPlanFinishNum);
        return 100.0 * historyDayPlanFinishNum / historyDayPlanNum;
    }

    /**
     *
     * @return 返回完成计划个数的比例
     */
    public static double getHistoryPlanFinishRate() {
        System.out.println(historyPlanFinishNum);
        return 100.0 * historyPlanFinishNum / historyPlanNum;
    }

    /**
     * 判断文件是不是.plan文件
     * @param file
     * @return 文件是不是.plan文件
     */
    public static boolean isPlanFile(File file) {
        Pattern pattern = Pattern.compile("^.*\\.plan$");
        Matcher matcher = pattern.matcher(file.getName());;
        return matcher.matches();
    }

    /**
     * 删除本地文件
     * @return 删除是否成功
     */
    public static boolean deleteLocalPlanFile() {
        File directory = new File("");
        directory = new File(directory.getAbsolutePath());

        File[] files = directory.listFiles();

        for (File file : files) {
            if (isPlanFile(file)) {
                if (!file.delete()) {
                    System.out.println("删除失败");
                    return false;
                }
            }
        }
        System.out.println("finish deleteLocalPlanFile()");
        return true;
    }

    /**
     * 获取服务器文件到本地
     * @return 是否成功
     */
    public static boolean getRemotePlanFile() {
        ArrayList<PlanFile> planFiles = new ArrayList<>();

        try {
            planFiles = planFileDao.getAllPlanFile(DbUtil.getConnection(), UserManage.getUser());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        for (PlanFile planFile : planFiles) {
            File file = new File(planFile.getfName());
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(planFile.getContent());
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        System.out.println("finish getRemotePlanFile()");
        return true;
    }

    /**
     * 上传本地文件到服务器
     * @return 是否成功
     */
    public static boolean uploadLocalPlanFile() {
        File directory = new File("");
        directory = new File(directory.getAbsolutePath());

        File[] files = directory.listFiles();

        ArrayList<File> planFiles = new ArrayList<>();

        for (File file : files) {
            if (isPlanFile(file)) {
                planFiles.add(file);
            }
        }

        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        for (File file : planFiles) {
            PlanFile planFile = new PlanFile();
            planFile.setFile(file);
            planFile.setfName(file.getName());
            try {
                fileInputStream = new FileInputStream(file);
                bytes = new byte[65536];
                fileInputStream.read(bytes, 0, (int)file.length());
                planFile.setContent(bytes);
                fileInputStream.close();
                if (planFileDao.planFileExist(DbUtil.getConnection(), UserManage.getUser(), planFile)) {
                    planFileDao.updatePlanFile(DbUtil.getConnection(), UserManage.getUser(), planFile);
                } else {
                    planFileDao.insertPlanFile(DbUtil.getConnection(), UserManage.getUser(), planFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        System.out.println("finish uploadLocalPlanFile()");
        return true;
    }

    /**
     * 更新服务器数据
     * @return 是否成功
     */
    public static boolean updateRemotePlanFile() {
        File file = new File(getPlanFileName(Calendar.getInstance(), true));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        PlanFile planFile = new PlanFile();
        planFile.setFile(file);
        planFile.setfName(file.getName());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[65536];
            fileInputStream.read(bytes, 0, (int) file.length());
            planFile.setContent(bytes);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            if (planFileDao.planFileExist(DbUtil.getConnection(), UserManage.getUser(), planFile)) {
                planFileDao.updatePlanFile(DbUtil.getConnection(), UserManage.getUser(), planFile);
            } else {
                planFileDao.insertPlanFile(DbUtil.getConnection(), UserManage.getUser(), planFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("finish updateRemotePlanFile()");
        return true;
    }
}
