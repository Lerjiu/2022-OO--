import java.awt.*;
import java.awt.geom.Arc2D;

public class Sector {
    private double amount;   //该块区域所代表的数量
    private int degree;     //根据amount经计算得到
    private Color color;   //图例颜色
    private Object tag;   //相关数据对象，由外部指定
    private Arc2D shape; //实际绘制的形状

    public Sector(double amount, Color color, Object tag) {
        this.amount = amount;
        this.color = color;
        this.tag = tag;
    }

    public double getAmount() {
        return amount;
    }

    public int getDegree() {
        return degree;
    }

    public Arc2D getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public void setShape(Arc2D shape) {
        this.shape = shape;
    }
}
