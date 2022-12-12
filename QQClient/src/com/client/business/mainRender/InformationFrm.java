package com.client.business.mainRender;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.tree.TreePath;

import com.common.UserInfoBean;


/**
 * 个人详细资料界面
 */
public class InformationFrm extends JFrame {
    InformationFrm MainFrm;
    int Mwide = 460, Mheight = 660;//窗体的长宽
    private int x, y;//窗体的位置，拖动时的位置
    private int offsetX, offsetY;//拖动窗体偏移位置
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    static String editBk = "Image/MainIcon/editBk.png";
    static String Background = "Image/MainIcon/background.png";
    static String touxiang = "Image/MainIcon/qqicons\\DefaultFace.png";
    static String select = "Image/MainIcon/qqicons\\select.png";
    static String Pclose = "Image/MainIcon/close.png";//
    static String Pclose1 = "Image/MainIcon/close1.png";
    static String Pmin = "Image/MainIcon/min.png";
    static String Pmin1 = "Image/MainIcon/min1.png";
    static String Pskin = "Image/MainIcon/skin.png";
    static String Pskin1 = "Image/MainIcon/skin1.png";
    static String Ptask = "Image/MainIcon/task.png";//


    static String touxiang1 = "Image/MainIcon/qqicons\\Catch00001.jpg";
    static String touxiang2 = "Image/MainIcon/qqicons\\Catch00002.jpg";
    static String touxiang3 = "Image/MainIcon/qqicons\\Catch00003.jpg";
    static String touxiang4 = "Image/MainIcon/qqicons\\Catch00004.jpg";
    static String touxiang5 = "Image/MainIcon/qqicons\\Catch00005.jpg";
    static String touxiang6 = "Image/MainIcon/qqicons\\Catch00006.jpg";
    static String treeNode = "Image/MainIcon/treeNode.png";
    static String treeNode1 = "Image/MainIcon/treeNode1.png";
    static String Pmask = "Image/MainIcon/mask.png";
    //////////// 状态图标
    static String Ponline = "Image/MainIcon/Status\\imonline.png";
    static String Poffline = "Image/MainIcon/Status\\imoffline.png";
    static String Pbusy = "Image/MainIcon/Status\\busy.png";
    static String away = "Image/MainIcon/Status\\away.png";
    static String invisible = "Image/MainIcon/Status\\invisible.png";
    static String Qme = "Image/MainIcon/Status\\Qme.png";
    static String mute = "Image/MainIcon/Status\\mute.png";
    ///C:\Users\Administrator\Desktop\课程设计\topIcon
//	/////////////////////////最顶层的那排图标按钮
    static String Pzone = "Image/MainIcon/topIcon\\qzone.png";
    static String Pblog = "Image/MainIcon/topIcon\\wblog.png";
    static String Pemail = "Image/MainIcon/topIcon\\mail.png";
    static String Pshop = "Image/MainIcon/topIcon\\paipai.png";
    static String Pmoney = "Image/MainIcon/topIcon\\purse.png";
    static String Pmeber = "Image/MainIcon/topIcon\\friend.png";
    static String Pweb = "Image/MainIcon/topIcon\\soso.png";
    //	//	/////////////////////////最底层的那排图标按钮
    static String PApp = "Image/MainIcon/MainTopToolBar\\appbox_mgr_btn.png";
    static String QQSafe = "Image/MainIcon/MainTopToolBar\\QQSafe.png";
    static String SystemSet = "Image/MainIcon/MainTopToolBar\\SystemSet.png";
    static String find = "Image/MainIcon/MainTopToolBar\\find.png";
    static String message = "Image/MainIcon/MainTopToolBar\\message.png";
    static String groupmainpage = "Image/MainIcon/MainTopToolBar\\groupmainpage.png";
    /////////////////////\
    JTextField name;
    JTextArea sign;
    JRadioButton sex_man, sex_woman;
    JComboBox year, month, day;  //生日
    JTextField telephone;  //电话
    JTextField email;  //电子邮箱
    JTextField address; //地址
    JPasswordField password;
    JPasswordField resure_password;
    //标签

    JLabel l_name;
    JLabel l_password;
    JLabel l_repassword;
    JLabel l_sex;
    JLabel l_bir;
    JLabel l_xz;
    JLabel l_pho;
    JLabel l_bloodType;
    JLabel l_email;
    JLabel l_adr;
    JLabel l_xl;
    JLabel l_icon;
    JLabel l_qq;
    JTextField qq;
    ////////////////\
    UserInfoBean userInfo;
    final JPanel EditFace;//编辑面板
    final JPanel show = new JPanel();//显示资料面板
    JTextArea geren = new JTextArea("");
    JLabel gexing = new JLabel();
    JLabel phone = new JLabel();
    JLabel zhanghao = new JLabel();
    JLabel ai = new JLabel();

    public InformationFrm(UserInfoBean userInfo) {
        EditFace = editface(userInfo);
        this.userInfo = userInfo;
        geren.setBounds(0, 0, 400, 200);
        geren.setLocation(90, 66);
        geren.setEditable(false);
        geren.setOpaque(false);
        show.setOpaque(false);
        MainFrm = this;
        this.setLocation((int) (screenSize.getWidth() - Mwide) / 2,
                (int) (screenSize.getHeight() - Mheight) / 2);
        this.setSize(Mwide, Mheight);
        this.setResizable(false);
        this.setUndecorated(true);//不需要标题栏等装饰
        this.addMouseMotionListener(new MyMouseAdapter());
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                offsetX = e.getX();
                offsetY = e.getY();
            }
        });
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ShowTouXiang(this);


        JPanel abc = new JPanel();
        abc.setBounds(this.getBounds());
        abc.setLocation(0, 0);
        abc.setOpaque(true);
        abc.setLayout(null);

        final JButton B_close = new JButton("关闭");
        B_close.setBounds(0, 0, 30, 20);
        B_close.setLocation(this.getBounds().width - 20, 0);
        B_close.setIcon(new ImageIcon(Pclose1));
        B_close.setOpaque(false);
        B_close.setBorder(null);
        B_close.setBackground(Color.white);
        B_close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }

            public void mouseEntered(MouseEvent e) {
                B_close.setIcon(new ImageIcon(Pclose));
            }

            public void mouseExited(MouseEvent e) {
                B_close.setIcon(new ImageIcon(Pclose1));
            }
        });
        abc.add(B_close);

        final JButton B_min = new JButton("min");
        B_min.setBounds(0, 0, 30, 20);
        B_min.setLocation(this.getBounds().width - 40, 0);
        B_min.setIcon(new ImageIcon(Pmin));
        B_min.setOpaque(false);
        B_min.setBorder(null);
        B_min.setBackground(Color.white);
        B_min.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
//				dispose();//窗口最小化时dispose该窗口  
                setVisible(false);

            }

            public void mouseEntered(MouseEvent e) {
                B_min.setIcon(new ImageIcon(Pmin1));
            }

            public void mouseExited(MouseEvent e) {
                B_min.setIcon(new ImageIcon(Pmin));
            }
        });
        //abc.add(B_min);

        final JButton B_skin = new JButton("min");
        B_skin.setBounds(0, 0, 30, 20);
        B_skin.setLocation(this.getBounds().width - 60, 0);
        B_skin.setIcon(new ImageIcon(Pskin));
        B_skin.setOpaque(false);
        B_skin.setBorder(null);
        B_skin.setBackground(Color.white);
        B_skin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }

            public void mouseEntered(MouseEvent e) {
                B_skin.setIcon(new ImageIcon(Pskin1));
            }

            public void mouseExited(MouseEvent e) {
                B_skin.setIcon(new ImageIcon(Pskin));
            }
        });
        //	abc.add(B_skin);
        //////////////
        ///选项卡

        UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);//此UIManager可以使JTabbedPane变的透明
        JLabel Linformation = new JLabel("资料");
        Linformation.setOpaque(false);
//        JLabel Lphoto = new JLabel("相册");
//        Lphoto.setBackground(Color.red);
//        Lphoto.setOpaque(false);
//        JLabel Ldongtai = new JLabel("动态");
//        Ldongtai.setOpaque(false);
//        JLabel Llabel = new JLabel("标签");
//        Llabel.setOpaque(false);
//        JLabel Luser = new JLabel("账户");
//        Luser.setOpaque(false);
//        JLabel Lphone = new JLabel("手机");
//        Lphone.setBackground(Color.red);
//        Lphone.setOpaque(false);
//        JLabel Lask = new JLabel("问问");
//        Lask.setOpaque(false);
//        JLabel Lgame = new JLabel("游戏");
//        Lgame.setOpaque(false);
//        JLabel Lpet = new JLabel("宠物");
//        Lpet.setOpaque(false);
        JTabbedPane tabeld = new JTabbedPane();

        final JScrollPane JSPane;        //滚动面板,用于显示信息的，下面还有一个用于编辑信息的

        show.setBounds(0, 0, 400, 600);
        show.setLocation(0, 0);
        show.setLayout(null);

        JPanel MS = new JPanel();  //在此面板上显示展示信息的面板和编辑信息的面板
        MS.setBounds(0, 0, 500, 800);
        MS.setLocation(0, 0);
        MS.setOpaque(false);
        MS.setLayout(null);
        MS.add(show);

        EditFace.setVisible(false);
        JSPane = new JScrollPane(EditFace);
        show.setBounds(0, 0, 400, 600);
        show.setLocation(0, 0);
        show.setLayout(null);

        JSPane.getViewport().setOpaque(false);

        MS.add(EditFace);


        tabeld.addTab("资料", MS);////show   JSPane

//        tabeld.addTab("相册", Lphoto);
//        tabeld.addTab("动态", Ldongtai);
//        tabeld.addTab("标签", Llabel);
//        tabeld.addTab("账户", Luser);
//        tabeld.addTab("手机", Lphone);
//        tabeld.addTab("问问", Lask);
//        tabeld.addTab("游戏", Lgame);
//        tabeld.addTab("宠物", Lpet);

//		*************给资料里面添加控件*************************


        JButton edit = new JButton("编辑资料");
        edit.setBounds(0, 0, 120, 20);
        edit.setContentAreaFilled(false);
        edit.setLocation(0, 0);
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                show.setVisible(false);
                EditFace.setVisible(true);
            }
        });
        show.add(edit);

//		************添加要显示的信息************************
//		账号
        JLabel Lage = new JLabel();
        Lage.setBounds(0, 0, 300, 33);
        Lage.setText("账号:");
        Lage.setLocation(20, 30);
        show.add(Lage);

        JLabel Lage1 = new JLabel();
        Lage1.setBounds(0, 0, 300, 33);
        Lage1.setText("" + userInfo.getQq());
        Lage1.setLocation(60, 30);
        show.add(Lage1);
//		个人说明
        JLabel Lpersonnalety = new JLabel();
        Lpersonnalety.setBounds(0, 0, 111, 33);
        Lpersonnalety.setText("个人说明:");
        Lpersonnalety.setLocation(20, 60);
        show.add(Lpersonnalety);
        show.add(geren);

        geren.setText(userInfo.getSign());
//		个人
        JLabel Lperson = new JLabel();
        Lperson.setBounds(0, 0, 111, 33);
        Lperson.setText("个人:");
        Lperson.setLocation(20, 120);
        show.add(Lperson);

        JLabel Lperson1 = new JLabel();
        Lperson1.setBounds(0, 0, 300, 33);
        Lperson1.setText("性别：" + userInfo.getSex() + " 生日：" + userInfo.getBirthday());
        Lperson1.setLocation(60, 120);
        show.add(Lperson1);
//		电话
        JLabel Lphone1 = new JLabel();
        Lphone1.setBounds(0, 0, 111, 33);
        Lphone1.setText("电话:");
        Lphone1.setLocation(20, 150);
        show.add(Lphone1);

        JLabel Lphone2 = new JLabel();
        Lphone2.setBounds(0, 0, 300, 33);
        Lphone2.setText(userInfo.getTelephone());
        Lphone2.setLocation(60, 150);
        show.add(Lphone2);
//		手机
        JLabel Lphone3 = new JLabel();
        Lphone3.setBounds(0, 0, 111, 33);
        Lphone3.setText("手机:");
        Lphone3.setLocation(20, 180);
        show.add(Lphone3);

        JLabel Lphone4 = new JLabel();
        Lphone4.setBounds(0, 0, 300, 33);
        Lphone4.setText(userInfo.getTelephone());
        Lphone4.setLocation(60, 180);
        show.add(Lphone4);
//		主页
        JLabel Lmain = new JLabel();
        Lmain.setBounds(0, 0, 111, 33);
        Lmain.setText("主页:");
        Lmain.setLocation(20, 210);
        show.add(Lmain);

        JLabel Lmain1 = new JLabel();
        Lmain1.setBounds(0, 0, 300, 33);
        Lmain1.setText("http//:www." + userInfo.getQq() + "qq.com");
        Lmain1.setLocation(60, 210);
        show.add(Lmain1);
//		邮箱
        JLabel LMail = new JLabel();
        LMail.setBounds(0, 0, 111, 33);
        LMail.setText("邮箱:");
        LMail.setLocation(20, 250);
        show.add(LMail);

        JLabel LMail2 = new JLabel();
        LMail2.setBounds(0, 0, 300, 33);
        LMail2.setText(userInfo.getEmail());
        LMail2.setLocation(60, 250);
        show.add(LMail2);
//		学校
        JLabel School = new JLabel();
        School.setBounds(0, 0, 111, 100);
        School.setText("学校:");
        School.setLocation(20, 300);
        show.add(School);

        JLabel Schoolans = new JLabel();
        Schoolans.setBounds(0, 0, 111, 100);
        Schoolans.setText("华中农业大学");
        Schoolans.setLocation(60, 300);
        show.add(Schoolans);

        tabeld.setBounds(0, 160, MainFrm.getBounds().width + 2, Mheight - 200);
        tabeld.setBackground(new Color(0, 0, 0, 0));

        abc.add(tabeld);

        JLabel bk = new JLabel();
        bk.setBounds(this.getBounds());
        bk.setIcon(new ImageIcon(Background));
        bk.setLocation(0, 0);
        abc.add(bk);
        this.add(abc);
        this.setVisible(true);

    }

    class MyMouseAdapter extends MouseAdapter {

        public void mouseDragged(MouseEvent e) {
            x = e.getXOnScreen();
            y = e.getYOnScreen();

            if (y <= 0) {
                y = 0;

            }
            setLocation(x - offsetX, y - offsetY);
        }
    }

    public void ShowTouXiang(InformationFrm informationFrm) {
        /**
         * 该方法显示头像的
         */
        final JLabel Ltouxiang = new JLabel();
        final JLabel Lselect = new JLabel();
        Ltouxiang.setBounds(0, 0, 68, 68);
        Ltouxiang.setLocation(10, 74);
        ImageIcon icon = new ImageIcon();
        Ltouxiang.setIcon(icon);

        Lselect.setBounds(0, 0, 60, 60);
        Lselect.setLocation(14, 80);
        String face = "Image/MainIcon/qqicons\\Catch0000" + userInfo.getPhotoID() + ".jpg";
        ImageIcon icon1 = new ImageIcon(face);
        Lselect.setIcon(icon1);
        informationFrm.add(Lselect);

        Ltouxiang.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
                Ltouxiang.setIcon(new ImageIcon(select));
            }

            public void mouseExited(MouseEvent e) {
                Ltouxiang.setIcon(new ImageIcon());
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }
        });
        informationFrm.add(Ltouxiang);

    }

    public JPanel editface(UserInfoBean userInfo) {//TODO
        final JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(400, 600));
        contentPanel.setBounds(0, 0, 500, 800);
        contentPanel.setLocation(0, 0);
        contentPanel.setOpaque(true);
        contentPanel.setLayout(null);

        JButton save = new JButton("保存");
        save.setBounds(0, 0, 60, 30);
        save.setLocation(120, 0);
        save.setOpaque(false);
        save.setContentAreaFilled(false);
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//			在此处添加对数据的保存，要将信息写入数据库

            }
        });
        contentPanel.add(save);

        JButton cancel = new JButton("取消");
        cancel.setBounds(0, 0, 60, 30);
        cancel.setLocation(180, 0);
        cancel.setOpaque(false);
        cancel.setContentAreaFilled(false);
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//			在此处添加对数据的保存，要将信息写入数据库
                EditFace.setVisible(false);
                show.setVisible(true);
            }
        });
        contentPanel.add(cancel);
        {
            l_qq = new JLabel("  QQ号码:");
            l_qq.setFont(new Font("楷体", 0, 18));
            l_qq.setBounds(60, 150, 100, 20);
            l_qq.setVisible(false);
            contentPanel.add(l_qq);
        }
        {//qq号码编辑框
            qq = new JTextField();
            qq.setBounds(150, 145, 200, 30);
            qq.setForeground(Color.green);
            qq.setFont(new Font("楷体", 2, 22));

            qq.setVisible(false);

            qq.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                    // TODO Auto-generated method stub
                    int keyChar = e.getKeyChar();
                    if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {

                    } else {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }

            });


            contentPanel.add(qq);

        }

        {//用户名标签
            String str_gm = "    昵称:";
            l_name = new JLabel(str_gm);
            l_name.setFont(new Font("楷体", 0, 18));
            l_name.setBounds(60, 80, 100, 20);
            contentPanel.add(l_name);
        }
        {//密码标签

            String str_gm = "    密码:";
            l_password = new JLabel(str_gm);
            l_password.setFont(new Font("楷体", 0, 18));
            l_password.setBounds(60, 115, 100, 20);
            contentPanel.add(l_password);
        }

        {//确认密码标签

            String str_gm = "确认密码:";
            l_repassword = new JLabel(str_gm);
            l_repassword.setFont(new Font("楷体", 0, 18));
            l_repassword.setBounds(60, 150, 100, 20);
            contentPanel.add(l_repassword);
        }

        {//性别标签
            l_sex = new JLabel("    性别:");
            l_sex.setFont(new Font("楷体", 0, 18));
            l_sex.setBounds(60, 185, 100, 20);
            contentPanel.add(l_sex);
        }
        {//生日标签
            l_bir = new JLabel("    生日:");
            l_bir.setFont(new Font("楷体", 0, 18));
            l_bir.setBounds(60, 220, 100, 20);
            contentPanel.add(l_bir);
        }

        {//电话标签
            l_pho = new JLabel("    电话:");
            l_pho.setFont(new Font("楷体", 0, 18));
            l_pho.setBounds(60, 360, 100, 20);
            contentPanel.add(l_pho);
        }


        {//电子邮箱标签
            l_email = new JLabel("电子邮箱:");
            l_email.setFont(new Font("楷体", 0, 18));
            l_email.setBounds(60, 395, 100, 20);
            contentPanel.add(l_email);
        }
        {//所在地标签
            l_adr = new JLabel("  所在地:");
            l_adr.setFont(new Font("楷体", 0, 18));
            l_adr.setBounds(60, 420, 100, 20);
            contentPanel.add(l_adr);
        }

        {//用户名输入框
            name = new JTextField();
            name.setBounds(150, 75, 200, 30);
            name.setForeground(Color.green);
            name.setFont(new Font("楷体", 2, 22));
            name.setText(userInfo.getNickname());
            contentPanel.add(name);
        }

        {//密码输入框
            password = new JPasswordField();
            password.setBounds(150, 110, 200, 30);
            password.setFont(new Font("宋体", 0, 14));
            contentPanel.add(password);
        }
        {//密码输入框
            resure_password = new JPasswordField();
            resure_password.setBounds(150, 145, 200, 30);
            resure_password.setFont(new Font("宋体", 0, 14));
            contentPanel.add(resure_password);
        }
        {//性别
            sex_man = new JRadioButton("男");
            sex_woman = new JRadioButton("女");
            sex_man.setOpaque(false);
            sex_woman.setOpaque(false);
            sex_man.setFont(new Font("楷体", 0, 16));
            sex_woman.setFont(new Font("楷体", 0, 16));
            sex_man.setBounds(150, 180, 60, 30);
            sex_woman.setBounds(305, 180, 60, 30);
            contentPanel.add(sex_man);
            contentPanel.add(sex_woman);

            sex_man.setSelected(true);

        }
        {//生日
            int i;
            year = new JComboBox();
            for (i = 2014; i > 1894; i--) {
                year.addItem("" + i + "年");
            }
            year.setBounds(150, 215, 70, 30);
            year.setOpaque(false);
            year.setFont(new Font("楷体", 0, 14));
            month = new JComboBox();
            for (i = 1; i < 13; i++) {
                month.addItem("" + i + "月");
            }
            month.setBounds(310, 215, 55, 30);
            month.setOpaque(false);
            month.setFont(new Font("楷体", 0, 14));
            day = new JComboBox();
            for (i = 1; i < 32; i++) {
                day.addItem("" + i + "日");
            }
            day.setBounds(375, 215, 55, 30);
            day.setOpaque(false);
            day.setFont(new Font("楷体", 0, 14));
            contentPanel.add(year);
            contentPanel.add(month);
            contentPanel.add(day);
        }

        {
            telephone = new JTextField();
            telephone.setBounds(150, 355, 200, 30);
            telephone.setFont(new Font("楷体", 0, 14));
            telephone.setText(userInfo.getTelephone());
            telephone.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                    int keyChar = e.getKeyChar();
                    if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {

                    } else {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }

            });

            contentPanel.add(telephone);

        }
        {
            email = new JTextField();
            email.setBounds(150, 390, 200, 30);
            email.setFont(new Font("楷体", 0, 14));
            email.setText(userInfo.getEmail());
            contentPanel.add(email);
        }
        {
            address = new JTextField();
            address.setBounds(150, 430, 200, 30);
            address.setFont(new Font("楷体", 0, 14));
            address.setText(userInfo.getAddress());
            contentPanel.add(address);
        }
        return contentPanel;
    }

    public JPanel editFace() {
        /**
         * 此函数返回编辑面板
         */
        final JPanel edit = new JPanel();
        final JLabel per = new JLabel("个人说明:");

        final JTextArea per1 = new JTextArea("");
        per1.setBounds(0, 0, 400, 50);
        per1.setLocation(0, 0);
        per1.setText("");
        per1.setOpaque(false);

        edit.setPreferredSize(new Dimension(400, 600));
        edit.setBounds(0, 0, 500, 800);
        edit.setLocation(0, 0);
        edit.setOpaque(true);
        edit.setLayout(null);

        JButton save = new JButton("保存");
        save.setBounds(0, 0, 60, 30);
        save.setLocation(120, 0);
        save.setOpaque(false);
        save.setContentAreaFilled(false);
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//			在此处添加对数据的保存，要将信息写入数据库
                if (per1.getText() != "") {
                    geren.setText(per1.getText());
                }
                EditFace.setVisible(false);
                show.setVisible(true);
            }
        });
        edit.add(save);

        JButton cancel = new JButton("取消");
        cancel.setBounds(0, 0, 60, 30);
        cancel.setLocation(180, 0);
        cancel.setOpaque(false);
        cancel.setContentAreaFilled(false);
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//			在此处添加对数据的保存，要将信息写入数据库
                EditFace.setVisible(false);
                show.setVisible(true);
            }
        });
        edit.add(cancel);
//	    *************个性签名******************
        JLabel sign = new JLabel("个性签名:");
        sign.setBounds(0, 0, 100, 30);
        sign.setLocation(20, 40);
        edit.add(sign);

        final JTextArea Textsign = new JTextArea();
        Textsign.setBounds(0, 0, 400, 50);
        Textsign.setLocation(20, 70);

        Textsign.setOpaque(false);

        final JScrollPane JSPane = new JScrollPane(Textsign);
        JSPane.setBounds(0, 0, 400, 50);
        JSPane.setLocation(20, 70);
        JSPane.setOpaque(false);
        JSPane.getViewport().setOpaque(false);
        Textsign.addMouseListener(new BoxListener(JSPane));
        edit.add(JSPane);

//	*********************个人说明***********************

        per.setBounds(0, 0, 100, 30);
        per.setLocation(20, 120);
        edit.add(per);


        final JScrollPane JSPane1 = new JScrollPane(per1);
        JSPane1.setBounds(0, 0, 400, 50);
        JSPane1.setLocation(20, 150);
        JSPane1.setOpaque(false);
        JSPane1.getViewport().setOpaque(false);
        per1.addMouseListener(new BoxListener(JSPane1));
        edit.add(JSPane1);

//	****************昵称
        JLabel Lnicheng = new JLabel("昵     称:");
        Lnicheng.setBounds(0, 0, 60, 30);
        Lnicheng.setLocation(20, 210);
        edit.add(Lnicheng);
        final JTextField nicheng = new JTextField();
        nicheng.setBounds(0, 0, 140, 30);
        nicheng.setLocation(70, 210);
        nicheng.setOpaque(false);
        nicheng.addMouseListener(new BoxListener(nicheng));
        edit.add(nicheng);
//	****************姓名
        JLabel Lname = new JLabel("姓     名:");
        Lname.setBounds(0, 0, 60, 30);
        Lname.setLocation(230, 210);
        edit.add(Lname);
        final JTextField name = new JTextField();
        name.setBounds(0, 0, 140, 30);
        name.setLocation(280, 210);
        name.setOpaque(false);
        name.addMouseListener(new BoxListener(name));
        edit.add(name);
//	****************英文名
        JLabel Lname1 = new JLabel("英文名:");
        Lname1.setBounds(0, 0, 60, 30);
        Lname1.setLocation(20, 260);
        edit.add(Lname1);
        final JTextField name1 = new JTextField();
        name1.setBounds(0, 0, 140, 30);
        name1.setLocation(70, 260);
        name1.setOpaque(false);
        name1.addMouseListener(new BoxListener(name1));
        edit.add(name1);

//	****************背景

        return edit;

    }

    /**
     * 对文本框添加个鼠标移动效果
     */
    class BoxListener implements MouseListener {
        final LineBorder myboder = new LineBorder(Color.gray);
        final LineBorder myboder1 = new LineBorder(Color.red);
        JComponent com;

        BoxListener() {

        }

        BoxListener(JComponent com) {
            this.com = com;
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            com.setBorder(myboder1);
        }

        public void mouseExited(MouseEvent e) {
            com.setBorder(myboder);
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

    }
}
