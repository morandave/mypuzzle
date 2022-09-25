import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainJFrame extends JFrame implements Runnable{

    //菜单
    //菜单栏
    JMenuBar jmenubar;
    //菜单  菜单、等级、帮助
    JMenu menu,menuclass,menuhelp;
    //菜单项  开始、退出、图片更换、关于游戏、游戏记录、清空记录
    JMenuItem itembegin,itemend,itemchange,itemabout,itemrecord,itemclear;
    //单选菜单项  简单、一般、困难
    JRadioButtonMenuItem itemeasy,itemnormal,itemhard;
    //中间面板
    JPanel jp;
    //左面板
    LeftJPanel lp;
    //右面板
    RightJPanel rp;
    //访问的图片
    URL url;
    //显示计时标签
    JLabel total_time;
    //起止时间
    long startTime,endTime;
    //创建线程对象，实现计时功能
    Thread th;
    //显示步数的标签
    JLabel total_count;
    //构造方法
    public MainJFrame(){
        //标题设置
        setTitle("拼图游戏");
        //窗体大小
        setSize(1440, 780);
        //窗体位置在容器/屏幕的正中间
        setLocationRelativeTo(null);
        //窗体大小不可变
        setResizable(false);
        //实现界面菜单初始化
        //创建一个线程对象
        th=new Thread(this);
        //界面菜单初始化
        menuinit();
        //各面板的初始化
        init();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        //开始菜单
        itembegin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //启动线程
                //如果线程没有启动，则调用start方法启动
                if(!th.isAlive()) th.start();
                startTime=System.currentTimeMillis();
                rp.times=0;
                rp.randomOrder();
            }
        });
        //结束游戏
        itemend.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        //选择难易度itemeasy,itemnormal,itemhard
        itemeasy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //第一，传递2*2到右面板
                rp.hs=2;
                rp.ls=2;
                //第二，调用右面板组件初始化的方法
                rp.init(url);
            }
        });
        itemnormal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //第一，传递3*3到右面板
                rp.hs=3;
                rp.ls=3;
                //第二，调用右面板组件初始化的方法
                rp.init(url);
            }
        });
        itemhard.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //第一，传递4*4到右面板
                rp.hs=4;
                rp.ls=4;
                //第二，调用右面板组件初始化的方法
                rp.init(url);
            }
        });
        //游戏记录显示
        itemrecord.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //info存储要显示的内容
                String info="";
                try {
                    //判断文件是否存在
                    File f = new File("D:\\游戏记录.dat");
                    if(f.exists()) {
                        //创建指向***的文件字符输入流对象
                        FileReader fr = new FileReader("D:\\游戏记录.dat");
                        //读取数据
                        char[] chs = new char[1024];
                        int len;
                        while((len=fr.read(chs))!=-1) {
                            //读取的结果放在info中
                            info+=new String(chs,0,len);
                        }

                        fr.close();
                        //通过消息框显示结果
                        JOptionPane.showMessageDialog(null, info);
                    }else {
                        JOptionPane.showMessageDialog(null, "游戏记录为空！");
                    }
                }catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        //关于游戏
        itemabout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "关于拼图游戏\r\n版本：v2.0\r\n作者：LWL\r\n欢迎进入游戏！");
            }
        });
        //清空记录
        itemclear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File f = new File("D:\\游戏记录.dat");
                if(f.exists()) {
                    f.delete();
                }
            }
        });
        //实现图片的更换
        itemchange.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //显示一个打开对话框，选择一个图片文件，将文件转换成url对象，调用左右面板的相应方法
                JFileChooser jfc=new JFileChooser();
                //设置文件的扩展名
                jfc.setFileFilter(new FileNameExtensionFilter("图片格式(jpg|png|gif|jpeg)", "jpg","png","gif","jpeg"));
                //弹出打开对话框
                int sd = jfc.showOpenDialog(MainJFrame.this);
                if (sd==jfc.APPROVE_OPTION)//如果用户选择了打开按钮
                {
                    //获取用户选择的文件完整名称
                    String file=jfc.getSelectedFile().getAbsolutePath();
                    try {
                        url=new URL("file:\\"+file);
                        //更新两个面板的图片
                        lp.init(url);
                        rp.init(url);
                    } catch (MalformedURLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void init() {
        jp=new JPanel();
        //设置中间面板的布局方式
        jp.setLayout(new GridLayout(1,2));
        //提供左右面板的图片
        url=this.getClass().getResource("小狗.jpg");
        //创建左面板
        lp=new LeftJPanel();
        //对标签初始化
        lp.init(url);
        //将左面板添加到中间面板
        jp.add(lp);
        //创建右面板
        rp=new RightJPanel();
        //右面板的按钮初始化
        rp.init(url);
        //将右面板添加到中间面板
        jp.add(rp);
        //将中间面板添加到窗体
        add(jp);
    }

    public void menuinit() {
        jmenubar=new JMenuBar();
        menu=new JMenu("菜单");
        menuclass=new JMenu("等级");
        menuhelp=new JMenu("帮助");
        itembegin=new JMenuItem("开始游戏");
        itemend=new JMenuItem("结束游戏");
        itemchange=new JMenuItem("更换图片");
        itemabout=new JMenuItem("关于游戏");
        itemrecord=new JMenuItem("游戏记录");
        itemclear=new JMenuItem("清空记录");
        itemeasy=new JRadioButtonMenuItem("简单");
        itemnormal=new JRadioButtonMenuItem("一般");
        itemhard=new JRadioButtonMenuItem("困难");
        //为单选菜单分组，实现多选一
        ButtonGroup bg=new ButtonGroup();
        bg.add(itemeasy);
        bg.add(itemnormal);
        bg.add(itemhard);
        //添加菜单
        menu.add(itembegin);
        menu.add(itemend);
        menu.add(itemchange);

        menuclass.add(itemeasy);
        menuclass.add(itemnormal);
        menuclass.add(itemhard);

        menuhelp.add(itemabout);
        menuhelp.add(itemrecord);
        menuhelp.add(itemclear);

        jmenubar.add(menu);
        jmenubar.add(menuclass);
        jmenubar.add(menuhelp);

        //菜单栏添加到窗体
        this.setJMenuBar(jmenubar);
        itemeasy.setSelected(true);
        //创建一个线程对象
        th=new Thread(this);
        total_time=new JLabel("用时:");
        total_time.setForeground(Color.red);
        jmenubar.add(new JLabel("                    "));
        jmenubar.add(total_time);
        total_count=new JLabel("步数:");
        total_count.setForeground(Color.red);
        jmenubar.add(new JLabel("                    "));
        jmenubar.add(total_count);
    }

    public static void main(String[] args) {
        new MainJFrame();
    }
    //实现计时并定时显示的run（）方法
    @Override
    public void run() {
        while(true) {
            endTime=System.currentTimeMillis();
            total_time.setText("用时："+(endTime-startTime)/1000+"秒");
            total_count.setText("步数:第"+rp.times+"步");
            try {
                Thread.sleep(500);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
