import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HistoryPlanTable implements PlanAddible {
    private static final Object[] columName = {"计划", "状态", "时间"};
    private JTable planTable;
    private DefaultTableModel model;
    private PlanTableRenderer planTableRenderer;
    private Box planTableBox;

    public HistoryPlanTable() {
        model = new DefaultTableModel(columName, 0);
        planTable = new JTable(model) {
            @Override
            public boolean isCellSelected(int row, int column) {
                return false;
            }
        };

        planTable.setRowHeight(80);
        planTableRenderer = new PlanTableRenderer();
        planTable.setDefaultRenderer(Object.class, planTableRenderer);
        planTable.setCellSelectionEnabled(false);

        planTable.getColumn(columName[0]).setMinWidth(300);
        planTable.getColumn(columName[1]).setMinWidth(80);
        planTable.getColumn(columName[1]).setMaxWidth(80);
        planTable.getColumn(columName[2]).setMinWidth(150);
        planTable.getColumn(columName[2]).setMaxWidth(150);

        planTableBox = Box.createVerticalBox();
        planTableBox.add(planTable);
        planTableBox.add(Box.createVerticalGlue());
    }

    public int addPlan(Plan plan) {
        if (plan.getPlanStatus() == Plan.NOT_STARTED) {
            plan.setPlanStatus(Plan.UNFINISHED);
        }
        Object[] row = new Object[3];
        row[0] = plan;
        row[1] = plan;
        row[2] = plan;

        if (model.getRowCount() == 0) {
            model.addRow(row);
            return 0;
        }

        Plan firstPlan = (Plan) model.getValueAt(0, 0);

        if (plan.getEnd().compareTo(firstPlan.getStart()) <= 0) {
            model.insertRow(0, row);
            return 0;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            Plan planI = (Plan) model.getValueAt(i, 0);
            if (plan.getStart().compareTo(planI.getEnd()) >= 0) {
                continue;
            } else {
                model.insertRow(i, row);
                return i;
            }
        }

        model.addRow(row);
        return model.getRowCount() - 1;
    }

    public Box getPlanTableBox() {
        return planTableBox;
    }
}
