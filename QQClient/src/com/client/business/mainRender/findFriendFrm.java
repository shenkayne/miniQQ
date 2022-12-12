package com.client.business.mainRender;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.tools.ClientToServer;
import com.client.view.JMainFrm;
import com.common.UserInfoBean;

public class findFriendFrm extends JFrame {
    findFriendFrm MainFrm;
    int Mwide = 660, Mheight = 460;//窗体的长宽
    private int x, y;//窗体的位置，拖动时的位置
    private int offsetX, offsetY;//拖动窗体偏移位置
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    String face = "";
    public String name = "";//找到的用户的名字

    final JLabel Ltouxiang = new JLabel();
    final JLabel Lselect = new JLabel();
    final JTextArea info = new JTextArea();
    JButton add = new JButton("加为好友");

    static String editBk = "Image/MainIcon/editBk.png";
    static String Background = "Image/MainIcon/findbk.png";
    static String touxiang = "Image/MainIcon/qqicons\\DefaultFace.png";
    static String select = "Image/MainIcon/qqicons\\select.png";
    static String Pclose = "Image/MainIcon/close.png";//
    static String Pclose1 = "Image/MainIcon/close1.png";
    static String Pmin = "Image/MainIcon/min.png";
    static String Pmin1 = "Image/MainIcon/min1.png";
    static String Pskin = "Image/MainIcon/skin.png";
    static String Pskin1 = "Image/MainIcon/skin1.png";
    static String Ptask = "Image/MainIcon/task.png";//

    static String Pfind = "Image/MainIcon/MainTopToolBar\\find.png";

    ClientToServer cts = null;
    UserInfoBean friend = null;
    UserInfoBean user = null;

    public findFriendFrm(final JMainFrm JMain, UserInfoBean user) {

        MainFrm = this;
        this.user = user;
        this.setLocation((int) (screenSize.getWidth() - Mwide) / 2, (int) (screenSize.getHeight() - Mheight) / 2);
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
                JMain.FindFriendFrm = null;
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
//		/显示搜索到的好友
        /**
         * 该方法显示头像的
         */
        Ltouxiang.setBounds(0, 0, 68, 68);
        Ltouxiang.setLocation(196, 154);
        ImageIcon icon = new ImageIcon();
        Ltouxiang.setIcon(icon);
        Ltouxiang.setVisible(false);

        Lselect.setBounds(0, 0, 60, 60);
        Lselect.setLocation(200, 160);
        ImageIcon icon1 = new ImageIcon(touxiang);
        Lselect.setIcon(icon1);
        Lselect.setVisible(false);
        MainFrm.add(Lselect);


        info.setEditable(false);
        info.setOpaque(false);
        info.setBounds(0, 0, 200, 60);
        info.setLocation(260, 160);

        MainFrm.add(info);


        add.setBounds(0, 0, 90, 30);
        add.setLocation(260, 300);
        add.setContentAreaFilled(false);
		/**
		 * 添加好友
		 */
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cts = new ClientToServer();
                new Thread(cts).start();
                int fqq = friend.getQq();
                int qq = getQq();
                int subno = 0;
                cts.addFriend(qq, fqq, subno);
                JOptionPane.showMessageDialog(null, "添加好友成功!");
//                cts.closeConnect();
                //new selectGroupFrm( JMain,MainFrm);
            }

        });
        add.setVisible(false);
        MainFrm.add(add);

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
        MainFrm.add(Ltouxiang);
//		**************************查找*****************************
        final JTextField find = new JTextField();
        find.setBounds(0, 0, 200, 40);
        find.setLocation(200, 100);
        find.addMouseListener(new BoxListener(find));
        abc.add(find);

        JButton search = new JButton("查      找");
        search.setBounds(0, 0, 130, 40);
        search.setLocation(400, 100);
        search.setIcon(new ImageIcon(Pfind));
        search.setContentAreaFilled(false);
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO 搜索数据库中和输入的QQ号相匹配的用户
                int qq;
                String nickname = "";
                String str1 = find.getText().trim();
                if (isNumeric(str1)) {
                    qq = Integer.parseInt(str1);
                } else {
                    qq = 0;
                    nickname = str1;
                }
                cts = new ClientToServer();
                new Thread(cts).start();

                //通过QQ或用户名来搜索好友
                friend = cts.getUserInfo(qq, nickname);

                cts.closeConnect();

                if (friend == null) {
                    JOptionPane.showMessageDialog(null, "没有搜索到此用户");
                } else {
                    String nickName = friend.getNickname();
                    String bb = Integer.toString(friend.getQq());
                    if (find.getText().equals(nickName) || find.getText().equals(bb)) {
                        face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";
                        Lselect.setIcon(new ImageIcon(face));

                        String str = "(" + friend.getQq() + ")" + friend.getNickname() + " \n " + friend.getSign();
						System.out.println(str);
                        info.setText(str);
                        Ltouxiang.setVisible(true);
                        Lselect.setVisible(true);
                        add.setVisible(true);
                        name = nickName;

                    }
                }


            }

        });

        abc.add(search);

        JLabel bk = new JLabel();
        bk.setBounds(this.getBounds());
        bk.setIcon(new ImageIcon(Background));
        bk.setLocation(0, 0);
        abc.add(bk);

        this.add(abc);
        this.setVisible(true);

    }

    public int getQq() {
        return user.getQq();
    }

    public boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void ShowTouXiang(final findFriendFrm MainFrm, final JMainFrm JMain, String str) {


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

    public String getName() {
        return name;
    }
}
