import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

public class PlanTable {
    private static final Object[] columName = {"计划", "状态", "时间"};
    private boolean isModify;
    private PlanTable self;
    private JTable planTable;
    private DefaultTableModel model;
    private ButtonBox buttonBox;
    private Box planTableBox;
    private JFrame jFrame;

    public PlanTable(ButtonBox buttonBox, JFrame jFrame) {
        isModify = false;
        self = this;
        this.jFrame = jFrame;
        this.buttonBox = buttonBox;
        model = new DefaultTableModel(columName, 0);
        this.planTable = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        planTable.setRowHeight(80);
        PlanTableRenderer planTableRenderer = new PlanTableRenderer();
        planTable.setDefaultRenderer(Object.class, planTableRenderer);
        planTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        planTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("isModify：" + isModify);
                if (planTable.getSelectedRowCount() != 0 && isModify == false) {
                    isModify = true;
                    PlanDetailDialog planDetailDialog = new PlanDetailDialog(jFrame, self);
                    planDetailDialog.setVisible(true);
                    planTable.clearSelection();
                }
            }
        });

        planTable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        planTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                planTable.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        TableColumn planTitleColumn = planTable.getColumn(columName[0]);
        TableColumn planStatusColumn = planTable.getColumn(columName[1]);
        TableColumn planTimeColumn = planTable.getColumn(columName[2]);

        planTitleColumn.setMinWidth(300);
        planStatusColumn.setMinWidth(80);
        planStatusColumn.setMaxWidth(80);
        planTimeColumn.setMinWidth(150);
        planTimeColumn.setMaxWidth(150);

        planTableBox = Box.createVerticalBox();
        planTableBox.add(planTable);
        planTableBox.add(Box.createVerticalGlue());
    }

    public int addPlan(Plan plan) {
        System.out.println("plan num:" + model.getRowCount());
        System.out.println("start:" + plan.getStart());
        System.out.println("end:" + plan.getEnd());
        //为finishButton恢复监听事件，并且防止重复添加
        plan.removeListenerForButton();
        plan.addListenerForButton(self);
        Object[] row = new Object[3];
        row[0] = plan;
        row[1] = plan;
        row[2] = plan;

        if (model.getRowCount() == 0) {
            model.addRow(row);
            buttonBox.insertButton(plan.getFinishButton(), 0);
            return 0;
        }

        Plan firstPlan = (Plan) model.getValueAt(0, 0);

        if (plan.getEnd().compareTo(firstPlan.getStart()) <= 0) {
            model.insertRow(0, row);
            buttonBox.insertButton(plan.getFinishButton(), 0);
            return 0;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            Plan planI = (Plan) model.getValueAt(i, 0);
            System.out.println(planI.getStringPlanTime());
            System.out.println("start:" + planI.getStart());
            System.out.println("end:" + planI.getEnd());
            if (plan.getStart().compareTo(planI.getEnd()) >= 0) {
                continue;
            } else {
                model.insertRow(i, row);
                buttonBox.insertButton(plan.getFinishButton(), i);
                return i;
            }
        }

        model.addRow(row);
        buttonBox.addButton(plan.getFinishButton());
        return model.getRowCount()-1;
    }

    public void removeSelectedPlan() {
        buttonBox.removeButton(planTable.getSelectedRow());
        model.removeRow(planTable.getSelectedRow());
    }

    public Plan getSelectedPlan() {
        return (Plan) model.getValueAt(planTable.getSelectedRow(), 0);
    }

    public void setSelectedPlan(int pos) {
        planTable.setRowSelectionInterval(pos,pos);
    }

    public void updatePlanTable() {
        planTable.repaint();
    }

    public JTable getPlanTable() {
        return planTable;
    }

    public JFrame getFrame() {
        return jFrame;
    }

    public Box getPlanTableBox() {
        return planTableBox;
    }

    public boolean overlapPlan(Calendar start, Calendar end) {
        for (int i = 0; i < model.getRowCount(); i++) {
            Plan plan = (Plan) model.getValueAt(i,0);
            if ((start.compareTo(plan.getEnd()) < 0) && (end.compareTo(plan.getStart()) > 0)) {
                System.out.println("start:" + start);
                System.out.println("end:" + end);
                System.out.println("overlap: start:" + plan.getStart());
                System.out.println("overlap: end:" + plan.getEnd());
                return true;
            }
        }
        return false;
    }

    public boolean getIsModify() {
        return isModify;
    }

    public void setModify(boolean modify) {
        isModify = modify;
    }

    public boolean modifyOverlapPlan(Calendar start, Calendar end) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (i == planTable.getSelectedRow()) {
                System.out.println("continue");
                continue;
            }
            Plan plan = (Plan) model.getValueAt(i,0);
            if (start.compareTo(plan.getEnd()) < 0 && end.compareTo(plan.getStart()) > 0) {
                return true;
            }
        }
        return false;
    }

    public void checkPlanStatus(Calendar now) {
        for (int i = 0; i < model.getRowCount(); i++) {
            Plan plan = (Plan) model.getValueAt(i, 0);
            if (plan.getPlanStatus() == Plan.NOT_STARTED) {
                if (plan.getStart().before(now) && plan.getEnd().after(now)) {
                    plan.setPlanStatus(Plan.RUNNING);
                    updatePlanTable();
                } else if (plan.getEnd().before(now)) {
                    plan.setPlanStatus(Plan.UNFINISHED);
                    updatePlanTable();
                }
            } else if (plan.getPlanStatus() == Plan.RUNNING) {
                if (plan.getEnd().before(now)) {
                    plan.setPlanStatus(Plan.UNFINISHED);
                    updatePlanTable();
                }
            }
        }
    }

    public void updatePlanToDayPlanList(DayPlanList dayPlanList) {
        for (int i = 0; i < model.getRowCount(); i++) {
            Plan plan = (Plan) model.getValueAt(i, 0);
            dayPlanList.addDayPlan(plan);
        }
    }
}
