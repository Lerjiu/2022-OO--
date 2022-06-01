import javax.swing.*;
import java.awt.*;

public class FunctionBox {
    private Box functionBox;

    public FunctionBox() {
        functionBox = Box.createHorizontalBox();
    }

    public Box getFunctionBox() {
        return functionBox;
    }

    public void addItem(Component c) {
        functionBox.add(c);
    }
}
