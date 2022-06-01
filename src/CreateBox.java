import javax.swing.*;
import java.awt.*;

public class CreateBox {
    private Box createBox;
    private JButton createJButton;
    private ImageIcon createIcon;
    private JLabel createJLabel;

    public CreateBox() {
        createBox = Box.createVerticalBox();
        createIcon = new ImageIcon("img\\create.png");
        createIcon.setImage(createIcon.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
        createJButton = new JButton(createIcon);

        createJLabel = new JLabel();

        createJLabel.setFont(new Font("黑体",Font.PLAIN,12));
        createJLabel.setText("  新建  ");
        createJLabel.setHorizontalTextPosition(JLabel.CENTER);
        createBox.add(createJButton);
        createBox.add(createJLabel);
    }

    public Box getCreateBox(){
        return createBox;
    }
}
