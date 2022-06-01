import javax.swing.*;

public class PlanList {
    private JList<Plan> planJList;
    private DefaultListModel<Plan> model;

    public PlanList() {
        this.model = new DefaultListModel<>();
        this.planJList = new JList<>(model);
        this.planJList.setVisibleRowCount(5);
        this.planJList.setFixedCellHeight(-1);
        this.planJList.setCellRenderer(new PlanBox());
    }

    public void addPlan(Plan plan) {
        model.addElement(plan);
    }

    public void removeAll() {
        model.removeAllElements();
    }

    public JList<Plan> getPlanJList() {
        return planJList;
    }
}
