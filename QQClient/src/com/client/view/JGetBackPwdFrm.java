package com.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.client.business.resourceManager.ResourceManager;
import com.client.business.uiManager.Getpwd;
import com.client.business.uiManager.Register;
import com.common.UserInfoBean;


public class JGetBackPwdFrm extends JFrame implements ActionListener, ItemListener {

    public static void main(String[] args) {


        new JGetBackPwdFrm(10001, true);
    }

    private static final long serialVersionUID = 1L;
    BackgroundPanel contentPanel = new BackgroundPanel();
    JButton btn_min, btn_close;
    JTextField question1, question2, question3;
    JComboBox cb_question1, cb_question2, cb_question3;
    JButton btn_ok, btn_find;
    JLabel label;
    public String knight = "mibao";
    private int qq;
    public UserInfoBean user;

    int no[] = new int[3];
    Hashtable<Integer, String> hash;
    boolean isGetpwd;
    String pwd;
    JRegisterFrm jregister;
    Getpwd getpwd;

    public JGetBackPwdFrm(UserInfoBean user, boolean isGetpwd, JRegisterFrm jregister) {
        this.user = user;
        this.isGetpwd = isGetpwd;
        this.jregister = jregister;
        common(true);
    }

    public JGetBackPwdFrm(int qq, boolean isGetpwd) {
        this.qq = qq;
        this.isGetpwd = isGetpwd;

        hash = new Hashtable<Integer, String>();
        getpwd = new Getpwd();
        pwd = getpwd.getPwdQA(qq, no, hash);

        common(false);
    }

    public void common(boolean isSelect) {
        setUndecorated(true);
        setResizable(false);
        setBounds(500, 50, 797, 563);
        {
            contentPanel.setLayout(null);
            contentPanel.addMouseMotionListener(new MouseAdapter() {
                private Point draggingAnchor = null;

                @Override
                public void mouseMoved(MouseEvent e) {
                    draggingAnchor = new Point(e.getX() + contentPanel.getX(), e.getY() + contentPanel.getY());


                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    setLocation(e.getLocationOnScreen().x - draggingAnchor.x, e.getLocationOnScreen().y - draggingAnchor.y);
                }
            });
        }

        {
            label = new JLabel("为了避免遗忘，请填写真实信息，这将帮助您以后回答问题快速找回QQ密码。");
            label.setBounds(272, 145, 500, 32);
            label.setFont(new Font("宋体", 0, 14));
            contentPanel.add(label);
        }

        {//问题1输入框
            question1 = new JTextField();
            question1.setBounds(319, 242, 297, 32);
            question1.setFont(new Font("楷体", 0, 14));
            contentPanel.add(question1);
        }

        {//问题2输入框
            question2 = new JTextField();
            question2.setBounds(319, 336, 297, 32);
            question2.setFont(new Font("楷体", 0, 14));
            contentPanel.add(question2);
        }

        {//问题3输入框
            question3 = new JTextField();
            question3.setBounds(319, 430, 297, 32);
            question3.setFont(new Font("楷体", 0, 14));
            contentPanel.add(question3);
        }

        {//问题一
            cb_question1 = new JComboBox();
            cb_question1.addItem("您母亲的姓名是？");
            cb_question1.addItem("您的学号是？");
            cb_question1.addItem("您母亲的生日是？");
            cb_question1.addItem("您高中的班主任姓名是？");

            if (!isSelect) {
                cb_question1.setSelectedIndex(no[0]);

                cb_question1.setForeground(Color.BLUE);
                cb_question1.setBackground(Color.white);
                //	    cb_question1.setEnabled(false);
            }
            cb_question1.setFont(new Font("楷体", 0, 14));
            cb_question1.setBounds(319, 195, 297, 30);
            contentPanel.add(cb_question1);
        }

        {//问题二
            cb_question2 = new JComboBox();

            cb_question2.addItem("您最熟悉的童年好友姓名是？");
            cb_question2.addItem("您的偶像姓名是？");
            cb_question2.addItem("您最熟悉的学校宿舍室友的名字？");
            cb_question2.addItem("对您影响最大的人是？");
            if (!isSelect) {
                cb_question2.setSelectedIndex(no[1] - 4);
                //   cb_question2.setEnabled(false);
                cb_question2.setForeground(Color.BLUE);
                cb_question2.setBackground(Color.white);
                //	    cb_question2.setEnabled(false);
            }
            cb_question2.setFont(new Font("楷体", 0, 14));
            cb_question2.setBounds(319, 290, 297, 30);
            contentPanel.add(cb_question2);
        }

        {//问题三
            cb_question3 = new JComboBox();

            cb_question3.addItem("您最喜欢的水果是？");
            cb_question3.addItem("您最喜欢的运动是？");
            cb_question3.addItem("您的出生地是？");
            cb_question3.addItem("您的出生年份是？");
            if (!isSelect) {
                cb_question3.setSelectedIndex(no[2] - 8);
                //    cb_question3.setEnabled(false);
                cb_question3.setForeground(Color.BLUE);
                cb_question3.setBackground(Color.white);
                //  cb_question3.setEnabled(false);
            }
            cb_question3.setFont(new Font("楷体", 0, 14));
            cb_question3.setBounds(319, 384, 297, 30);
            contentPanel.add(cb_question3);
        }

        {//最小化
            btn_min = new JButton();
            btn_min.setBounds(730, 0, 35, 28);
            btn_min.setContentAreaFilled(false);


            contentPanel.add(btn_min);
            btn_min.addActionListener(this);//添加事件处理

        }

        {//关闭窗体
            btn_close = new JButton();
            btn_close.setBounds(765, 0, 35, 28);
            btn_close.setContentAreaFilled(false);
            contentPanel.add(btn_close);
            btn_close.addActionListener(this);//添加事件处理

        }

        if (!isGetpwd) {//设置密保
            btn_ok = new JButton("确认设置");
            btn_ok.setFont(new Font("楷体", 0, 20));
            btn_ok.setBounds(360, 500, 200, 40);
            //btn_ok.setContentAreaFilled(false);
            btn_ok.setVisible(true);
            contentPanel.add(btn_ok);
            btn_ok.addActionListener(this);//添加事件处理

        }

        if (isGetpwd) {//找回密码
            btn_find = new JButton("找回密码");
            btn_find.setFont(new Font("楷体", 0, 20));
            btn_find.setBounds(360, 500, 200, 40);
            //btn_ok.setContentAreaFilled(false);
            btn_find.setVisible(false);
            contentPanel.add(btn_find);
            btn_find.addActionListener(this);//添加事件处理

        }

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    class BackgroundPanel extends JPanel {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        //重写绘制组件方法
        public void paintComponent(Graphics g) {
            int x = 0, y = 0;

            //调用资源管理类中的加载图片资源方法，来加载背景图片
            ResourceManager imageResource = new ResourceManager();
            ImageIcon icon = new ImageIcon();
            icon = imageResource.GetImage(knight);
            //绘制窗口
            g.drawImage(icon.getImage(), x, y, icon.getIconWidth(), icon.getIconHeight(), this);
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub

    }

    public void closeWindows() {
        getpwd.close();
        this.dispose();
    }

    public void closeWindowsD() {
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == btn_close) {
            this.dispose();
        } else if (e.getSource() == btn_min) {
            setExtendedState(JFrame.ICONIFIED);
        } else if (e.getSource() == btn_ok) {
            no[0] = cb_question1.getSelectedIndex();
            no[1] = cb_question2.getSelectedIndex() + 4;
            no[2] = cb_question3.getSelectedIndex() + 8;
            String str1 = question1.getText().trim();
            String str2 = question2.getText().trim();
            String str3 = question3.getText().trim();
            hash = new Hashtable<Integer, String>();
            hash.put(no[0], str1);
            hash.put(no[1], str2);
            hash.put(no[2], str3);

            Register register = new Register();
            int qq = register.toRegister(user, no, hash);
            String str = "恭喜您注册成功！\n 您的QQ号为:" + qq;
            JOptionPane.showMessageDialog(null, str);
            register.close();

            jregister.closeWindows();
            closeWindowsD();

        } else if (e.getSource() == btn_find) {
            String str1 = question1.getText().trim();
            String str2 = question2.getText().trim();
            String str3 = question3.getText().trim();

            if (str1.equals(hash.get(no[0])) && str2.equals(hash.get(no[1])) && str3.equals(hash.get(no[2]))) {
                String str = "您的密码为:" + pwd + "\n请您牢记!";
                JOptionPane.showMessageDialog(null, str);
                closeWindows();
            } else {
                String str = "对不起,您输入的信息有误!";
                JOptionPane.showMessageDialog(null, str);
            }
        }
    }

}
