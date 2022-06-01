import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FunctionBar {
    private JToolBar functionBar;
    private Action createAction;

    private static ImageIcon createIcon = new ImageIcon("img\\create.png");
    static {
        createIcon.setImage(createIcon.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
    }
    public FunctionBar(JFrame jFrame, PlanTable planTable) {
        functionBar = new JToolBar();
        createAction = new AbstractAction("新建",createIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateDialog createDialog = new CreateDialog(jFrame, planTable);
                createDialog.getCreateJDialog().setVisible(true);
            }
        };
        functionBar.add(createAction);
    }

    public JToolBar getFunctionBar() {
        return functionBar;
    }
}
