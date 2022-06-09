import com.JavaPro.dao.UserDao;
import com.JavaPro.model.User;
import com.JavaPro.util.DbUtil;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManage {
    private static User user = null;
    private static boolean isLogin = false;
    private static UserDao userDao = new UserDao();

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

    public static User getUser() {
        return user;
    }

    public static void setUser(String name, String passWord) {
        user = new User();
        user.setUserName(name);
        user.setPassword(passWord);
    }

    public static void resetUser() {
        user = null;
    }

    public static String getUserName() {
        if (user == null) {
            return "无用户";
        } else {
            return user.getUserName();
        }
    }

    public static boolean login() {
        try {
            userDao.login(DbUtil.getConnection(), user);
            writeUserFile();
            isLogin = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isLogin() {
        return isLogin;
    }

    public static boolean register() {
        try {
            userDao.register(DbUtil.getConnection(), user);
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

    private static void deleteUserFile() {
        File userFile = new File("user");
        userFile.delete();
    }

    public static boolean checkPassWordLegal(String passWord) {
        Pattern pattern = Pattern.compile("^[\\w_]{8,20}$");
        Matcher matcher = pattern.matcher(passWord);
        System.out.println(passWord);
        System.out.println(matcher.matches());
        return matcher.matches();
    }
}
