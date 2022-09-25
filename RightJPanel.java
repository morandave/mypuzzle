import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//import jdk.jfr.events.FileWriteEvent;
//右面板实现ActionListener接口，右面板也就成为了监听器
public class RightJPanel extends JPanel implements ActionListener{

    //面板的大小
    int width=700;
    int height=700;
    //定义按钮数组
    JButton[] jbs;
    //设置分割的行列数
    int hs=2,ls=2;
    //按钮的宽度和高度，指定是小图图片缩放的尺寸
    int widthbut,heightbut;

    //图片原始高度宽度
    int widthtp,heighttp;

    //小图的原始宽度高度
    int widthxt,heightxt;

    //实现步数计算的变量
    int times;

    //空白按钮
    JButton kb;
    public RightJPanel(){
        //面板布局是空布局
        setLayout(null);
        setSize(width,height);
        //init();

    }
    //创建按钮，并放置到右面板
    public void init(URL url) {
        //面板组件初始化前，先清除所有已有的组件
        this.removeAll();
        //创建按钮数组
        jbs=new JButton[hs*ls];
        //为每一个按钮实现初始化
        //计算按钮的宽度和高度
        //面板是700*700，拆分成3*3的9个区域
        //每一块区域的宽度  700/3
        //每一块区域的高度 700/3
        widthbut=width/ls;
        heightbut=height/hs;

        BufferedImage buf=null;
        try {
            buf = ImageIO.read(url);
            //获取原图的宽度、高度
            widthtp=buf.getWidth();
            heighttp=buf.getHeight();
            //获取小图的宽度和高度
            widthxt=widthtp/ls;
            heightxt=heighttp/hs;
            //每一块按钮的坐标位置确定
            for(int i=0;i<jbs.length;i++){
                jbs[i]=new JButton();
                jbs[i].setSize(widthbut,heightbut);
                //jbs[i].setText(i+"");
                //添加按钮前要确定坐标位置
                //横坐标 i=0   0    i=1   233    i=2   466
                //i=3   0   i=4  233
                //纵坐标 i=3
                jbs[i].setLocation((i%ls)*widthbut, i/ls*heightbut);
                //jbs[i].setIcon(null);
                //小图的获取
                BufferedImage subimage = buf.getSubimage(i%ls*widthxt, i/ls*heightxt, widthxt, heightxt);
                //小图的缩放
                Image image = subimage.getScaledInstance(widthbut, heightbut, 1);
                //将小图图片放置到按钮上
                jbs[i].setIcon(new ImageIcon(image));
                //添加按钮到右面板
                add(jbs[i]);
                //设置按钮不可用
                jbs[i].setEnabled(false);
                //设置按钮的监听,当按钮被单击，会到右面板中找actionPerformed方法执行
                jbs[i].addActionListener(this);
            }
            jbs[hs*ls-1].setIcon(null);
            kb=jbs[hs*ls-1];
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    //打乱按钮在面板中显示的顺序
    public void randomOrder(){
        //创建随机数对象
        Random rand=new Random();
        //打乱多次
        for(int i=0;i<hs*ls;i++){
            //随机索引
            int index1=rand.nextInt(hs*ls);
            int index2=rand.nextInt(hs*ls);
            int x1=jbs[index1].getX();
            int y1=jbs[index1].getY();
            int x2=jbs[index2].getX();
            int y2=jbs[index2].getY();
            jbs[index1].setLocation(x2, y2);
            jbs[index2].setLocation(x1, y1);
            jbs[i].setEnabled(true);
        }
    }

    //按钮的单击事件执行的代码
    @Override
    public void actionPerformed(ActionEvent e) {
        // 判断单击按钮和空白按钮是否相邻，如果相邻，则位置互换
        //获取用户单击的按钮 ,通过ActionEvent e的方法gerSource获取事件源
        JButton jb=(JButton)(e.getSource());
        //获取单击按钮和空白按钮的坐标
        int x1=jb.getX();
        int y1=jb.getY();
        int x2=kb.getX();
        int y2=kb.getY();
        //判断是否可以移动
        //Math.abs(x1-x2)/widthbut + Math.abs(y1-y2)/heightbut==1
        if (Math.abs(x1-x2)/widthbut + Math.abs(y1-y2)/heightbut==1){
            jb.setLocation(x2, y2);
            kb.setLocation(x1, y1);
            times++;
        }
        //判断是否拼图成功
        if (isWin()){
            JOptionPane.showMessageDialog(null, "恭喜你，拼图成功");
            //使得按钮不可用
            for(int i=0;i<jbs.length;i++){
                jbs[i].setEnabled(false);
            }
            //提示用户输入名称
            //使用输入对话框
            String name = JOptionPane.showInputDialog("请输入你的姓名：");
            String info = hs+"*"+ls+"拼图记录："+name+"的步数是："+times+"\r\n";
            JOptionPane.showMessageDialog(null, hs+"*"+ls+"拼图记录："+name+"的步数是："+times+"\r\n");
            try {
                FileWriter fw = new FileWriter("D:\\游戏记录.dat",true);
                fw.write(info);
                fw.close();
            }catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    //判断是否拼图成功
    public boolean isWin() {

        //获取每一个按钮的坐标
        for(int i=0;i<jbs.length;i++){
            //jbs[i].setLocation((i%ls)*widthbut, i/ls*heightbut);由之前坐标设置给出下面的x，y
            int x=jbs[i].getX()/widthbut;
            int y=jbs[i].getY()/heightbut;
            //判断，通过下标值，也可以获取按钮的坐标   横坐标  i%ls    纵坐标 i/ls
            if (i%ls!=x || i/ls!=y  ){
                return false;
            }
        }
        return true;
    }
}
