
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class PieChart extends JPanel
{
    //数据：pie表示一小块
    private List<Sector>partList =new ArrayList<>();

    //color如果为null，则由内部自动分配
    public void addPart(double amount,Object tag,Color color)
    {
        if(amount<=0)return;

        Sector p=new Sector(amount, color, tag);

        partList.add(p);
    }

    //根据每一个饼的数值，分配角度，自动分配颜色
    private void calculate()
    {
        if(partList.size()==0) return;

        //求出总数量
        double totalAmount=0;
        for(Sector p : partList)
            totalAmount=totalAmount+p.getAmount();

        //分配每一份所占的角度
        int totalDegrees=0;
        for(int i=0;i<partList.size();i++)
        {
            Sector p=partList.get(i);
            p.setDegree((int)(360*p.getAmount()/totalAmount));
            if(p.getDegree()<3)
                p.setDegree(3); //有的份额太小，最低分配3角度
            if(i==partList.size()-1)
                p.setDegree(360-totalDegrees);  //确保占满360度
            totalDegrees+=p.getDegree();
        }

        //取得一个最大的正方形
        int width=this.getWidth();
        int height=this.getHeight();
        int w=width;
        int h=width;
        if(h>height)
        {
            h=height;
            w=height;
        }
        Rectangle rect = new Rectangle((width-w)/2,(height-h)/2,w,h);
        rect.grow(-4, -4);  //往里面缩一点

        //计算每一个扇形的形状
        int start=90;
        for(Sector p : partList)
        {
            p.setShape(new Arc2D.Double(rect, start, p.getDegree(), Arc2D.PIE));
            start=start+p.getDegree();
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        // TODO Auto-generated method stub
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        Graphics2D g2d = (Graphics2D) g;

        // 平滑绘制 （ 反锯齿 )
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //计算每一份额的角度和扇形
        this.calculate();
        for(Sector p:partList)
        {
            if(p.getShape() != null)
            {
                g2d.setPaint(p.getColor());
                g2d.fill( p.getShape() );
            }
        }

    }

}