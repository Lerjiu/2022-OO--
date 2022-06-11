import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 用于显示功能栏的封装类
 */
public class FunctionBar {
    private JToolBar functionBar;
    private Action createAction;

    private static ImageIcon createIcon = new ImageIcon("img\\create.png");
    static {
        createIcon.setImage(createIcon.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
    }

    /**
     * 构造时初始化
     * @param jFrame 新建功能创建一个CreateDialog需要提供父窗口
     * @param planTable 新建功能创建一个CreateDialog需要提供planTable
     */
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

    /**
     *
     * @return 返回构造后的组件
     */
    public JToolBar getFunctionBar() {
        return functionBar;
    }
}
