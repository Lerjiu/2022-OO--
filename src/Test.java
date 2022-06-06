import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Timer;
import java.util.TimerTask;

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

//        FunctionBox functionBox = new FunctionBox();
//        CreateBox createBox = new CreateBox();
//        functionBox.addItem(createBox.getCreateBox());
        FunctionBar functionBar = new FunctionBar(jFrame,plans);

        Box topBox = Box.createHorizontalBox();
        topBox.add(functionBar.getFunctionBar());

        Box box = Box.createVerticalBox();
//        box.add(functionBox.getFunctionBox());
        box.add(topBox);
        box.add(planScrollPane);

        RunningPlanBox runningPlanBox = new RunningPlanBox();
        CurrentTimeBox currentTimeBox = new CurrentTimeBox();
        JSplitPane runningPlanSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, runningPlanBox.getRunningPlanBox(), currentTimeBox.getShowTimeBox());
        runningPlanSplitPane.setResizeWeight(1);
        runningPlanSplitPane.setContinuousLayout(true);

        JTabbedPane historyPlanJTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        historyPlanJTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        historyPlanJTabbedPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                JTabbedPane pane = (JTabbedPane) e.getSource();
                int units = e.getWheelRotation();
                int oldIndex = pane.getSelectedIndex();
                int newIndex = oldIndex + units;
                if (newIndex < 0)
                    pane.setSelectedIndex(0);
                else if (newIndex >= pane.getTabCount())
                    pane.setSelectedIndex(pane.getTabCount() - 1);
                else
                    pane.setSelectedIndex(newIndex);
            }
        });
        JScrollPane historyPlanJScrollPane = new JScrollPane(historyPlanJTabbedPane);

        PlanManage.getTodayPlan(plans);
        PlanManage.getHistoryPlan(historyPlanJTabbedPane);

        CountHistoryPlanBox countHistoryPlanBox = new CountHistoryPlanBox();

        PageTabbedPane pageTabbedPane = new PageTabbedPane(box, historyPlanJScrollPane, countHistoryPlanBox.getCountHistoryPlanBox(), runningPlanSplitPane);
        jFrame.add(pageTabbedPane.getPageTabbedPane());

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(200,200,1200,600);
        jFrame.setVisible(true);

        TimerTask checkPlanStatus = new TimerTask() {
            Plan oldRunningPlan = null;
            Plan newRunningPlan = null;

            @Override
            public void run() {
                boolean changed = false;
                changed = plans.checkPlanStatus();
                currentTimeBox.repaintClock();

                newRunningPlan = plans.getRunningPlan();
                if ((oldRunningPlan == null && newRunningPlan != null)
                    || (oldRunningPlan != null && newRunningPlan == null)
                    || (oldRunningPlan != null && newRunningPlan != null && (!oldRunningPlan.equals(newRunningPlan)))) {
                    runningPlanBox.updateRunningPlanBox(plans.getRunningPlan());
                }
                oldRunningPlan = newRunningPlan;

                if (changed) {
                    System.out.println("changed");
                    PlanManage.updateDayPlanList();
                }
            }
        };

        new Timer().schedule(checkPlanStatus, 0, 1000);

    }
}
