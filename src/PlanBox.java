import jb2011.lnf.beautyeye.widget.border.BERoundBorder;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class PlanBox extends Box implements ListCellRenderer {
    JLabel planTitle;
    JLabel planDescribe;
    JLabel planTime;
    public PlanBox() {
        super(BoxLayout.X_AXIS);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        this.removeAll();

        Plan plan = (Plan)value;
        planTitle = new JLabel(plan.getPlanTitle());
        planDescribe = new JLabel(plan.getPlanDescribe());
        planTime = new JLabel(plan.getStart().get(Calendar.HOUR_OF_DAY) + ":" + plan.getStart().get(Calendar.MINUTE)
                + "-" + plan.getEnd().get(Calendar.HOUR_OF_DAY) + ":" + plan.getEnd().get(Calendar.MINUTE));


        BERoundBorder border = new BERoundBorder(new Color(0xa1,0xc4,0xfd),6);

        add(planTitle);

        add(Box.createHorizontalGlue());
        add(new Button("未完成"));
        add(planTime);

        setBorder(border);

        return this;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600,80);
    }

}
