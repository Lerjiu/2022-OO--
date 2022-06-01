import jb2011.lnf.beautyeye.widget.border.BERoundBorder;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PlanTableRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Color backgroundColor;

        if (isSelected) {
//            background-image: linear-gradient(to right, #ffecd2 0%, #fcb69f 100%);
            backgroundColor = new Color(0xff,0xec,0xd2);
        } else {
            backgroundColor = Color.white;
        }


        BERoundBorder border = new BERoundBorder(new Color(0xa1,0xc4,0xfd),6);
        Plan plan = (Plan) value;
        Color JLabelColor;
        if (plan.getPlanStatus() == Plan.NOT_STARTED) {
//            background-image: linear-gradient(to top, #e6e9f0 0%, #eef1f5 100%);
            JLabelColor = new Color(0xe6,0xe9,0xf0);
        } else if (plan.getPlanStatus() == Plan.UNFINISHED) {
//            background-image: linear-gradient(to top, #cfd9df 0%, #e2ebf0 100%);
            JLabelColor = new Color(0xcf,0xd9,0xdf);
        } else if (plan.getPlanStatus() == Plan.FINISHED) {
//            background-image: linear-gradient(to top, #c1dfc4 0%, #deecdd 100%);
            JLabelColor = new Color(0xc1,0xdf,0xc4);
        } else {
//            background-image: linear-gradient(to top, #fff1eb 0%, #ace0f9 100%);
            JLabelColor = new Color(0xac,0xe0,0xee);
        }
        if (column == 0) {
            JLabel jLabel = new JLabel(plan.getPlanTitle());
            jLabel.setBorder(border);
            jLabel.setOpaque(true);
            jLabel.setBackground(backgroundColor);
            return jLabel;
        } else if (column == 1) {
            JLabel jLabel = new JLabel(plan.getStringPlanStatus());
            jLabel.setBorder(border);
            jLabel.setOpaque(true);
            jLabel.setBackground(JLabelColor);
            return jLabel;
        } else {
            JLabel jLabel = new JLabel(plan.getStringPlanTime());
            jLabel.setBorder(border);
            return jLabel;
        }
    }
}
