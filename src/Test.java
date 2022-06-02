import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Test {
    private static void init() {
        Font font = new Font("黑体",Font.PLAIN,19);
        UIManager.put("Button.font", font);
        UIManager.put("CheckBox.font", font);
        UIManager.put("CheckBoxMenuItem.acceleratorFont", font);
        UIManager.put("CheckBoxMenuItem.font", font);
        UIManager.put("ColorChooser.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("DesktopIcon.font", font);
        UIManager.put("EditorPane.font", font);
        UIManager.put("FormattedTextField.font", font);
        UIManager.put("InternalFrame.titleFont", font);
        UIManager.put("Label.font", font);
        UIManager.put("List.font", font);
        UIManager.put("Menu.acceleratorFont", font);
        UIManager.put("Menu.font", font);
        UIManager.put("MenuBar.font", font);
        UIManager.put("MenuItem.acceleratorFont", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("OptionPane.font", font);
        UIManager.put("Panel.font", font);
        UIManager.put("PasswordField.font", font);
        UIManager.put("PopupMenu.font", font);
        UIManager.put("ProgressBar.font", font);
        UIManager.put("RadioButton.font", font);
        UIManager.put("RadioButtonMenuItem.acceleratorFont", font);
        UIManager.put("RadioButtonMenuItem.font", font);
        UIManager.put("ScrollPane.font", font);
        UIManager.put("Spinner.font", font);
        UIManager.put("TabbedPane.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextPane.font", font);
        UIManager.put("TitledBorder.font", font);
        UIManager.put("ToggleButton.font", font);
        UIManager.put("ToolBar.font", font);
        UIManager.put("ToolTip.font", font);
        UIManager.put("Tree.font", font);
        UIManager.put("Viewport.font", font);

        UIManager.put("Label.font",font);

        try {
            jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UIManager.put("RootPane.setupButtonVisible", false);
    }

    public static void main(String[] args) {
        Test.init();

        JFrame jFrame = new JFrame("Test");

//        PlanList plans = new PlanList();
//
//        for (int i = 0; i < 10; i++) {
//            plans.addPlan(new Plan("plan" + i,"plan" + i, "12","0","13","0"));
//        }
        ButtonBox buttonBox = new ButtonBox();

        PlanTable plans = new PlanTable(buttonBox, jFrame);

//        for (int i = 0; i < 10; i++) {
//            Plan plan = new Plan(plans,"plan" + i,"plan" + i, "12","0","13","0");
//            plans.addPlan(plan);
//            buttonBox.addButton(plan.getFinishButton());
//        }
        Box planBox = Box.createHorizontalBox();

        planBox.add(plans.getPlanTableBox());
        planBox.add(buttonBox.getButtonBox());
        JScrollPane planScrollPane = new JScrollPane(planBox);

        FunctionBox functionBox = new FunctionBox();
        CreateBox createBox = new CreateBox();
        functionBox.addItem(createBox.getCreateBox());
        FunctionBar functionBar = new FunctionBar(jFrame,plans);
        Box box = Box.createVerticalBox();
//        box.add(functionBox.getFunctionBox());
        box.add(functionBar.getFunctionBar());
        box.add(planScrollPane);
        jFrame.add(box);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(200,200,1200,600);
        jFrame.setVisible(true);

        TimerTask checkPlanStatus = new TimerTask() {
            @Override
            public void run() {
                Calendar now = Calendar.getInstance();
                plans.checkPlanStatus(now);
            }
        };

        new Timer().schedule(checkPlanStatus, 0, 2000);

    }
}
