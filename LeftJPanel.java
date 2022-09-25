import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
//左面板类
public class LeftJPanel extends JPanel {
    JLabel  jl;
    int width=700;
    int height=700;

    //构造方法
    //标签创建，指定图片，放置到面板中
    public LeftJPanel(){
        //左面板大小
        setSize(width,height);
        jl=new JLabel();
        jl.setSize(width,height);
        //把标签添加到面板中
        this.add(jl);
    }
    public void init(URL url){


        //绝对路径：访问文件是从盘符开始
        // ImageIcon icon=new ImageIcon("D:\\1picture\\s4.jpg");
        //相对路径：访问路径不是从盘符开始，可以是\，也可以是一个文件夹
        // ImageIcon icon=new ImageIcon("s4.jpg");//参数是字符串的相对路径，相对于当前项目根目录
        //相对路径下url的获取

//	   //绝对路径的url的获取
//	   URL url=null;
//	try {
//		url = new URL("file:\\D:\\1picture\\5.jpg");
//	} catch (MalformedURLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
        ImageIcon icon=new ImageIcon(url);
        //方法一：图片缩放
//	   Image image = icon.getImage();
//	   Image image2 = image.getScaledInstance(700, 700, 1);
//	   ImageIcon icon2 = new ImageIcon(image2);
//	   jl.setIcon(icon2);
        //链式编程方式实现图片缩放
        jl.setIcon(new ImageIcon(icon.getImage().getScaledInstance(width, height, 1)));
        //刷新界面
        validate();
    }
}
