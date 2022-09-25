import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPintu extends JFrame{
    JLabel jl1,jl2,jl3,jl4;
    JTextField jtf;//文本框
    JPasswordField jpf;//密码
    JButton jb1,jb2;
    public LoginPintu() {
        this.setTitle("拼图游戏");
        setBounds(400,350,500,400);
        //设置窗体为流式布局
        setLayout(new GridLayout(20,1));
        //空布局
        setLayout(null);
        init();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        jb1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().trim().equals("admin")&&
                        new String(jpf.getPassword()).trim().equals("123"))
                {JOptionPane.showMessageDialog(null, "欢迎进入游戏！");
                    new MainJFrame();}
                else if(jtf.getText().trim().length()==0||
                        new String(jpf.getPassword()).trim().length()==0)
                {JOptionPane.showMessageDialog(null, "用户名或密码不能为空！");}
                else {JOptionPane.showMessageDialog(null, "用户名或密码错误！");}
            }
        });
        jb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //	System.exit(0);
                //获取事件源对象
                JButton jb=(JButton)e.getSource();
                jtf.setText(jb.getText());
            }
        });

    }
    public void init() {
        jl1=new JLabel("拼图游戏登录窗口");
        jl2=new JLabel("用户名：");
        jl3=new JLabel("密码：");
        jtf=new JTextField(10);
        jpf=new JPasswordField(10);
        jb1=new JButton("登录");
        jb2=new JButton("取消");
        jl1.setBounds(150,30,200,60);
        jl2.setBounds(100, 120, 180, 30);
        jtf.setBounds(200, 120, 180, 30);
        jl3.setBounds(100, 180, 180, 30);
        jpf.setBounds(200, 180, 180, 30);
        jb1.setBounds(100, 260, 100, 30);
        jb2.setBounds(220, 260, 100, 30);
        Font font = new Font("楷体",Font.PLAIN,25);
        jl1.setFont(font);
        jl1.setForeground(Color.red);
        add(jl1);
        add(jl2);
        add(jtf);
        add(jl3);
        add(jpf);
        add(jb1);
        add(jb2);
    }
    public static void main(String[] args) {
        new LoginPintu();
    }
}
