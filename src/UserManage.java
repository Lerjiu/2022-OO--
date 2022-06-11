import com.JavaPro.dao.UserDao;
import com.JavaPro.dao.PlanFileDao;
import com.JavaPro.model.User;
import com.JavaPro.util.DbUtil;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于用户管理
 */
public class UserManage {
    private static User user = null;
    private static boolean isLogin = false;
    private static UserDao userDao = new UserDao();
    private static PlanFileDao planFileDao = new PlanFileDao();

    /**
     * 根据本地文件user构造用户类对象
     * @return 是否成功
     */
    public static boolean loadUser() {
        File userFile = new File("user");
        if (!userFile.exists()) {
            return false;
        }

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    new FileInputStream(userFile));

            user = (User) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * @return user
     */
    public static User getUser() {
        if (user != null) System.out.println("getUser" + user.getUserName());
        return user;
    }

    /**
     * 设置user的name和passWord
     * @param name
     * @param passWord
     */
    public static void setUser(String name, String passWord) {
        user = new User();
        user.setUserName(name);
        user.setPassword(passWord);
    }

    /**
     * 重置user，令user = null
     */
    public static void resetUser() {
        user = null;
    }

    /**
     *
     * @return user的name
     */
    public static String getUserName() {
        if (user == null) {
            return "无用户";
        } else {
            return user.getUserName();
        }
    }

    /**
     * 登录
     * @return 是否成功
     */
    public static boolean login() {
        try {
            if (userDao.login(DbUtil.getConnection(), user) == null)
                return false;
            writeUserFile();
            isLogin = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @return 是否处于登录状态
     */
    public static boolean isLogin() {
        return isLogin;
    }

    /**
     * 注册
     * @return 是否成功
     */
    public static boolean register() {
        try {
            if (userDao.register(DbUtil.getConnection(), user) == 0)
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void logout() {
        isLogin = false;
        resetUser();
        deleteUserFile();
    }

    /**
     * 根据user写入user文件
     */
    private static void writeUserFile() {
        File userFile = new File("user");
        if (!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("写入自动登录文件失败");
                return;
            }
        }

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(userFile));

            objectOutputStream.writeObject(user);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除user文件（退出登录时删除）
     */
    private static void deleteUserFile() {
        File userFile = new File("user");
        userFile.delete();
    }

    /**
     *
     * @return 计划完成天数超越多少用户
     */
    public static double dayPlanFinishBetterThan() {
        user.setDayPlanFinishNum(PlanManage.getHistoryDayPlanFinishNum());
        try {
            userDao.setDayFinishNum(DbUtil.getConnection(), user);
            return userDao.getDayPlanFinishBetterThan(DbUtil.getConnection(), user);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     *
     * @return 计划完成个数超越多少用户
     */
    public static double planFinishBetterThan() {
        user.setPlanFinishNum(PlanManage.getHistoryPlanFinishNum());
        try {
            userDao.setPlanFinishNum(DbUtil.getConnection(), user);
            return userDao.getPlanFinishBetterThan(DbUtil.getConnection(), user);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 检查密码是否合法
     * @param passWord
     * @return 密码是否合法
     */
    public static boolean checkPassWordLegal(String passWord) {
        Pattern pattern = Pattern.compile("^[\\w_]{8,20}$");
        Matcher matcher = pattern.matcher(passWord);
        System.out.println(passWord);
        System.out.println(matcher.matches());
        return matcher.matches();
    }
}
