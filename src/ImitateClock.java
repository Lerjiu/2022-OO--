import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.time.LocalTime;

public class ImitateClock extends JComponent {
    public void paint(Graphics g){
        //获取当前宽度
        int width=getWidth();
        System.out.println("width:" + width);
        //获取当高度
        int height=getHeight();
        System.out.println("height:" + height);
        //钟表半径初始化
        int r = 0;
        //选择高度和宽度较小的一个作为半径抉择标准
        if(width>=height){
            r=(height/2)-30;
        }else {
            r=(width/2)-30;
        }
        Graphics2D g2d=(Graphics2D)  g ;
        //设置图形边缘抗锯齿
        RenderingHints rh=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);
        rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHints(rh);
        //钟表外圈
        g2d.drawOval(50,50,2*r,2*r);
        //钟表外圈
        g2d.drawOval(45,45,2*r+10,2*r+10);
        //获取当前时针与12点所形成的夹角
        double hour=(((LocalTime.now().getHour()*60)+LocalTime.now().getMinute())*Math.PI)/360.0;
        //获取当前分针与12点所形成的夹角
        double minute=LocalTime.now().getMinute()*Math.PI/30.0;
        //获取当前秒针与12点所形成的夹角
        double second=LocalTime.now().getSecond() * Math.PI/30.0;
        //计算缩放比例
        double bi=((double) r/483);
        //计算绘制小时刻度所在的位置
        double  r1=1.06*r-(50*(bi+0.03)),r2=1.02*r-(50*(bi+0.03));
        double cx=r+50,cy=r+50;
        //绘制小时刻度
        g2d.setPaint(Color.red);
        for(double i = 0;i<2.0*Math.PI;i+=(Math.PI/6.0)){
            double lx,ly,rx,ry;
            lx=1.05*r1*Math.sin(i);
            ly=1.05*r1*Math.cos(i);
            rx=1.05*r2*Math.sin(i);
            ry=1.05*r2*Math.cos(i);
            g2d.draw(new Line2D.Double(cx+lx,cy-ly,cx+rx,cy-ry));
        }
        //绘制分钟刻度
        double r1_1=1.06*r-(80*(bi+0.03))+bi*5;
        for(double i = 0;i<2.0*Math.PI;i+=(Math.PI/30.0)){
            double lx,ly,rx,ry;
            lx=1.1*r1_1*Math.sin(i);
            ly=1.1*r1_1*Math.cos(i);
            rx=r*Math.sin(i);
            ry=r*Math.cos(i);
            g2d.draw(new Line2D.Double(cx+lx,cy-ly,cx+rx,cy-ry));
        }
        //时针
        g2d.setPaint(Color.black);
        g2d.setStroke(new BasicStroke(4.0f));
        g2d.draw(new Line2D.Double(cx,cy,cx+0.6*r*Math.sin(hour),cy-0.6*r*Math.cos(hour)));
        //分针
        g2d.setPaint(Color.green);
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.draw(new Line2D.Double(cx,cy,cx+0.7*r*Math.sin(minute),cy-0.7*r*Math.cos(minute)));
        //秒针
        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.draw(new Line2D.Double(cx,cy,cx+0.8*r*Math.sin(second),cy-0.8*r*Math.cos(second)));
        Font  f=new Font("Serif",Font.PLAIN,(int)(50*(bi+0.03)));
        g2d.setFont(f);
        g2d.setColor(Color.black);
        int h = LocalTime.now().getHour();
        int m=LocalTime.now().getMinute();
        int s=LocalTime.now().getSecond();
        g2d.drawString(""+h+":"+m+":"+s,10,50);
        int count=0;
        for(double i=0;i<2.0*Math.PI;i+=(Math.PI/6.0)){
            double r1_2=1.06*r-(70*(bi+0.03));
            int lx,ly;
            int rx1=r+50,ry1=r+50;
            lx=(int)(r1_2*Math.sin(i));
            ly=(int)(r1_2*Math.cos(i));
            if(count == 0){
                //因为12点对应数组中为0，所以单独拿出来修改为12
                g2d.drawString("12",(int)(rx1+lx-(22*bi)),(int)(ry1-ly+(12*bi)));
                count++;
            }else {
                String a=String.valueOf(count);
                g2d.drawString(a,(int)(rx1+lx-(8*bi)),(int)(ry1-ly+(12*bi)));
                count++;
            }
        }
    }
}
