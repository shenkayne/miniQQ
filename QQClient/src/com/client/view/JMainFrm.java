package com.client.view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.tree.TreePath;

import com.client.business.mainRender.ColorConvertOp;
import com.client.business.mainRender.CombListRenderer;

import com.client.business.mainRender.IconNodeRenderer;
import com.client.business.mainRender.InformationFrm;
import com.client.business.mainRender.MyTreeUI1;
import com.client.business.mainRender.findFriendFrm;
import com.client.business.uiManager.Login;
import com.tools.ClientToServer;
import com.common.Port;
import com.common.UserInfoBean;
import com.tools.ClientToServerThread;
import com.client.business.mainRender.IconNode;


public class JMainFrm extends JFrame {
//    public static void main(String[] args) {
//
//    }

    private static final long serialVersionUID = 1L;
    JMainFrm MainFrm;
    public IconNode Root = new IconNode(null, null);//定义根节点
    JPanel abc = new JPanel();//添加控件的面板
    int Mwide = 300, Mheight = 650;//窗体的长宽
    private int x, y;//窗体的位置，拖动时的位置
    int MAX_wide = 500;//最大宽度
    private int offsetX, offsetY;//拖动窗体偏移位置
    Image ImageBk;    //背景图片
    JLabel Bk_label;//背景label
    boolean isHide = false;//窗体是否隐藏
    JLabel nickname = new JLabel();
    JLabel sign = new JLabel();

    public findFriendFrm FindFriendFrm = null;//查找好友面板
    public int groupNumber = 0;//存放分组个数
    public String groupName[] = new String[20];//存放分组名字

    private JTabbedPane tabeld = null;
    /**
     * 图片资源
     */
    static String Background = "Image/MainIcon/qq.png";
    static String Pclose = "Image/MainIcon/close.png";//
    static String Pclose1 = "Image/MainIcon/close1.png";
    static String Pmin = "Image/MainIcon/min.png";
    static String Pmin1 = "Image/MainIcon/min1.png";
    static String Pskin = "Image/MainIcon/skin.png";
    static String Pskin1 = "Image/MainIcon/skin1.png";

    static String select = "Image/MainIcon/qqicons\\select.png";
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


    /////////////////////////最顶层的那排图标按钮
    static String Flush = "Image/MainIcon/topIcon\\flush.png";

    static String find = "Image/MainIcon/MainTopToolBar\\find.png";

    String face = "";

    public ClientToServer cts;
    Login logout;
    private UserInfoBean userInfo;
    public Hashtable friendInfoTable;
    public int n;

    /**
     * 构造方法
     */
    public JMainFrm(ClientToServer cts, UserInfoBean userInfo, Hashtable friendInfoTable, int n) throws IOException {
        this.cts = cts;
        logout = new Login(cts);
        this.userInfo = userInfo;
        this.friendInfoTable = friendInfoTable;
        this.n = n;
        MainFrm = this;

        System.out.println(Background);
        this.setTitle("迷你版QQ");
        nickname.setBounds(0, 0, 200, 30);
        nickname.setLocation(3, 10);
        nickname.setFont(new Font("楷体", Font.BOLD, 30));
        nickname.setForeground(Color.white);
        nickname.setText(userInfo.getNickname());


        sign.setBounds(3, 30, 300, 30);

        sign.setFont(new Font("楷体", Font.ITALIC, 15));
        sign.setForeground(Color.white);
        sign.setText("签名:(" + userInfo.getSign() + ")");
        face = "Image/MainIcon/qqicons\\Catch0000" + userInfo.getPhotoID() + ".jpg";//头像
        // TODO 显示头像这部分代码只用一个JLabel的话，总是显示了一下就消失了，封装方法并且用两个JLabel一起后就解决了
        ShowTouXiang(this);

        abc.setBounds(this.getBounds());
        abc.setLayout(null);
        abc.setOpaque(false);
        abc.add(nickname); //昵称
        abc.add(sign);//签名


        this.setResizable(false);//不能再调整大小
        this.setUndecorated(true);//不需要标题栏等装饰
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (screenSize.getWidth() - Mwide) / 2, (int) (screenSize.getHeight() - Mheight) / 2);
        this.setSize(Mwide, Mheight);
        this.setBackground(Color.blue);
        this.addMouseMotionListener(new MyMouseAdapter());
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
                if (isHide) {
                    MainFrm.setLocation(MainFrm.getLocationOnScreen().x, 0);
                    isHide = false;
                    MainFrm.setAlwaysOnTop(false);
                }
            }

            public void mouseExited(MouseEvent e) {
                if (MainFrm.getLocationOnScreen().y <= 0) {

                    MainFrm.setLocation(MainFrm.getLocationOnScreen().x, 3 - Mheight);
                    isHide = true;
                    MainFrm.setAlwaysOnTop(true);
                }
            }

            public void mousePressed(MouseEvent e) {
                offsetX = e.getX();
                offsetY = e.getY();
            }

            public void mouseReleased(MouseEvent e) {
            }
        });
        //////背景图片
        Bk_label = new JLabel();
        Bk_label.setBounds(MainFrm.getBounds());
        Bk_label.setLocation(0, 0);
        Bk_label.setIcon(new ImageIcon(Background));

        ////////////状态按钮/隐身，在线等。。。

        JComboBox icb = new JComboBox();//定义标准组合框
        icb.setMaximumRowCount(7);//设置最大显示行
        icb.setRenderer(new CombListRenderer());//调用单元格设置（这里使用了刚才创建的类）
        icb.setBackground(new Color(112, 100, 200, 166));//设置背景色

        icb.addItem(new Object[]{new ImageIcon(Ponline), "我在线上"});
        icb.addItem(new Object[]{new ImageIcon(Qme), "Q我吧"});
        icb.addItem(new Object[]{new ImageIcon(away), "离开"});
        icb.addItem(new Object[]{new ImageIcon(Pbusy), "忙碌"});
        icb.addItem(new Object[]{new ImageIcon(mute), "勿扰"});
        icb.addItem(new Object[]{new ImageIcon(invisible), "隐身"});
        icb.addItem(new Object[]{new ImageIcon(Poffline), "离线"});

        icb.setBounds(0, 0, 100, 20);
        icb.setLocation(75, 52);
        icb.setBorder(null);
        abc.add(icb);


//		****************搜索栏***************************
        JLabel serch = new JLabel("搜索栏（没时间搞了，直接写死成JLabel了）");
        serch.setBackground(Color.cyan);
        serch.setBounds(0, 0, 296, 30);
        serch.setLocation(2, 100);
        abc.add(serch);
//		*****************最上面的那排图标按钮***************8
        JButton BFlush, BBlog, BEmail, BShop, BMoney, BMember, BWeb;
        BFlush = new JButton("刷新好友列表",new ImageIcon(Flush));
        BFlush.setBounds(0, 0, 150, 20);
        BFlush.setLocation(75, 75);
//        BFlush.setIcon(new ImageIcon(Pzone));
        BFlush.setContentAreaFilled(false);
        BFlush.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update();
                JOptionPane.showMessageDialog(null, "好友列表已刷新");
            }
        });
        abc.add(BFlush);


        JButton Bserch;
        Bserch = new JButton("添加好友",new ImageIcon(find));            //查找
        Bserch.setBounds(0, 0, 150, 50);
        Bserch.setLocation(75, 590);
        Bserch.setContentAreaFilled(false);
        Bserch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (FindFriendFrm == null) {
                    FindFriendFrm = new findFriendFrm(MainFrm, MainFrm.userInfo);
                }
            }
        });

        abc.add(Bserch);
//		******************上面的那排图标按钮end**************************
        final JButton B_close = new JButton("关闭");
        B_close.setBounds(0, 0, 30, 20);
        B_close.setLocation(this.getBounds().width - 20, 0);
        B_close.setIcon(new ImageIcon(Pclose1));
        B_close.setOpaque(false);
        B_close.setBorder(null);
        B_close.setBackground(Color.white);
        B_close.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (JOptionPane.showConfirmDialog(null, "<html><font size=3>确定退出吗？</html>", "系统提示", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
                    try {
                        logout.userLogout();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    ClientToServerThread ctst = Port.comm.get(1);
                    ctst.closeSocket();
                    System.exit(0);
                } else {
                    return;
                }
            }

            public void mouseEntered(MouseEvent e) {
                B_close.setIcon(new ImageIcon(Pclose));
            }

            public void mouseExited(MouseEvent e) {
                B_close.setIcon(new ImageIcon(Pclose1));
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
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
        B_min.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }

            public void mouseEntered(MouseEvent e) {
                B_min.setIcon(new ImageIcon(Pmin1));
            }

            public void mouseExited(MouseEvent e) {
                B_min.setIcon(new ImageIcon(Pmin));
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }
        });
        abc.add(B_min);

        final JButton B_skin = new JButton("min");
        B_skin.setBounds(0, 0, 30, 20);
        B_skin.setLocation(this.getBounds().width - 60, 0);
        B_skin.setIcon(new ImageIcon(Pskin));
        B_skin.setOpaque(false);
        B_skin.setBorder(null);
        B_skin.setBackground(Color.white);
        B_skin.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
                B_skin.setIcon(new ImageIcon(Pskin1));
            }

            public void mouseExited(MouseEvent e) {
                B_skin.setIcon(new ImageIcon(Pskin));
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }
        });
        abc.add(B_skin);
        //////////////
        ///选项卡
        UpdateFriendList();

        ////
        JLabel bk = new JLabel();
        bk.setBounds(0, 0, 300, 420);
        bk.setLocation(0, 157);
        bk.setIcon(new ImageIcon(Pmask));
        abc.add(bk);

        abc.add(Bk_label);

        this.add(abc);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.setVisible(true);


    }

    /**
     * 对鼠标适配
     */
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

    /**
     * 该方法显示头像的
     */
    public void ShowTouXiang(JMainFrm MainFrm) {

        final JLabel Ltouxiang = new JLabel();//辅助不让头像框消失的框，负责点击

        final JLabel Lselect = new JLabel(); //头像框

//        Ltouxiang.setBounds(0, 0, 68, 68);
//        Ltouxiang.setLocation(10, 30);
        Ltouxiang.setBounds(10, 50, 50, 50);
//        Ltouxiang.setLocation(10, 50);
        ImageIcon icon = new ImageIcon();
        Ltouxiang.setIcon(icon);

//        Lselect.setBounds(0, 0, 60, 60);
//        Lselect.setLocation(14, 34);
        Lselect.setBounds(10, 50, 50, 50);
//        Lselect.setLocation(10, 54);
        ImageIcon icon1 = new ImageIcon(MainFrm.face);
        Lselect.setIcon(icon1);
        MainFrm.add(Lselect);

        Ltouxiang.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                new InformationFrm(userInfo);
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

    }

    /**
     * 好友列表
     * @param cts
     * @param userInfo
     * @param friendInfoTable
     * @param n
     * @return
     * @throws IOException
     */
    public JTabbedPane createFriendList(ClientToServer cts, final UserInfoBean userInfo, final Hashtable friendInfoTable, final int n) throws IOException {
//		*********************创建默认分组**
        groupNumber = 0;
//		IconNode haoyou=new IconNode("好友");
//		IconNode strager=new IconNode("陌生人");
//		IconNode black=new IconNode("黑名单");
        //haoyou.add(new IconNode(""));
        //strager.add(new IconNode(""));
        //black.add(new IconNode(""));
        Root = new IconNode(null, null);
//		Root.add(haoyou);
//		Root.add(strager);
//		Root.add(black);//添加黑名单
        /**
         * 根据从数据库获取的分组和好友等信息创建好友列表*********
         */
        IconNode root[] = new IconNode[20]; //假设最多20个分组
        boolean isfind = false;//定义是找到相同分组
        String groupname = "";//存放临时取出来的分组名字
        int total = 0;//存放总的分组数

        /**
         * 实现Enumeration接口的对象生成一系列元素，每次一个。
         * 对nextElement方法的连续调用将返回序列中的连续元素。
         * 例如，要打印Vector  v的所有元素: for(枚举  E = v.elements();e.hasMoreElements ();
         * System.out.println (e.nextElement ());
         * 方法用于枚举向量的元素、哈希表的键和哈希表中的值。
         * 枚举还用于指定SequenceInputStream的输入流。
         * 注意:该接口的功能与Iterator接口相同。
         * 此外，Iterator添加了一个可选的删除操作，并具有更短的方法名称。
         * 新的实现应该考虑优先使用Iterator而不是Enumeration。
         *
         *  public synchronized Enumeration<V> elements() {
         *         return this.<V>getEnumeration(VALUES);
         *     }
         * 返回此散列表中值的枚举。在返回的对象上使用Enumeration方法按顺序获取元素。 返回值: 此散列表中值的枚举
         */
        Enumeration it = friendInfoTable.elements();

        for (int i = 0; i < n; i++) {
            total = groupNumber;
            isfind = false;
            UserInfoBean friend = (UserInfoBean) it.nextElement();
//            System.out.println("在"+friend.getSubGroupName()+"分组下的好友:"+friend.getNickname()+"在线否：" + friend.getStatus()+ " 好友QQ为：" + friend.getQq());
//            System.out.println("新建好友中：" + friend.getQq() + " " + friend.getNickname() + " " +
//                    friend.getSubGroupName() + "在线否：" + friend.getStatus());
            ///进行和已有的分组名字比较
            groupname = friend.getSubGroupName();
            if (groupNumber == 0) {
                groupNumber++;
                groupName[groupNumber] = groupname;
                root[groupNumber] = new IconNode(groupName[groupNumber]);//创建分组
                face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";
                // TODO 如果不在线就显示灰色头像的这部分代码后面可以封装到一个方法里面，等所有东西写完后再回来优化
                if (!friend.getStatus()) {//如果不在线，则灰显头像
                    try {
                        //添加好友到分组列表里
                        IconNode friendNode = new IconNode(ColorConvertOp.getGrayPicture(face),
                                friend.getNickname() + "  " + "(" + friend.getQq() + ")", friend.getSign());
                        root[groupNumber].add(friendNode);
                        friendNode.setQQ(Integer.toString(friend.getQq()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    //添加好友到分组列表里
                    IconNode friendNode = new IconNode(new ImageIcon(face),
                            friend.getNickname() + "  " + "(" + friend.getQq() + ")", friend.getSign());
                    root[groupNumber].add(friendNode);
                    friendNode.setQQ(Integer.toString(friend.getQq()));
                }
                Root.add(root[groupNumber]);

            } else {///进行和已有的分组名字比较
                for (int j = 1; j <= total; j++) {
                    if (groupName[j].equals(groupname)) {//如果分组名字一模一样
                        isfind = true;//找到了
                        face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";
                        if (!friend.getStatus()) {//如果不在线，则灰显头像
                            try {
                                //添加好友到分组列表里
                                IconNode friendNode = new IconNode(ColorConvertOp.getGrayPicture(face),
                                        friend.getNickname() + "  " + "(" + friend.getQq() + ")", friend.getSign());
                                root[groupNumber].add(friendNode);
                                friendNode.setQQ(Integer.toString(friend.getQq()));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            //添加好友到分组列表里
                            IconNode friendNode = new IconNode(new ImageIcon(face),
                                    friend.getNickname() + "  " + "(" + friend.getQq() + ")", friend.getSign());
                            root[groupNumber].add(friendNode);
                            friendNode.setQQ(Integer.toString(friend.getQq()));
                        }
                        Root.add(root[groupNumber]);
                        break;
                    } else if (j >= groupNumber && !isfind) {//如果分组名字不一样
                        groupNumber++;
                        groupName[groupNumber] = groupname;
                        root[groupNumber] = new IconNode(groupName[groupNumber]);//创建分组
                        face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";
                        if (!friend.getStatus()) {//如果不在线，则灰显头像
                            try {
                                //添加好友到分组列表里
                                IconNode friendNode = new IconNode(ColorConvertOp.getGrayPicture(face),
                                        friend.getNickname() + "  " + "(" + friend.getQq() + ")", friend.getSign());
                                root[groupNumber].add(friendNode);
                                friendNode.setQQ(Integer.toString(friend.getQq()));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            //添加好友到分组列表里
                            IconNode friendNode = new IconNode(new ImageIcon(face),
                                    friend.getNickname() + "  " + "(" + friend.getQq() + ")", friend.getSign());
                            root[groupNumber].add(friendNode);
                            friendNode.setQQ(Integer.toString(friend.getQq()));
                        }
                        Root.add(root[groupNumber]);
                    }
                }
            }
        }

        UIManager.put("Tree.collapsedIcon", new ImageIcon(treeNode));
        UIManager.put("Tree.expandedIcon", new ImageIcon(treeNode1));
        UIManager.put("Tree.OffX", new ImageIcon(treeNode1));

        final JTree tree = new JTree(Root);
        //对图片进行渲染
        final IconNodeRenderer render = new IconNodeRenderer();

        render.setBackground(Color.pink);
        tree.setCellRenderer(render); //设置单元格描述
        tree.setEditable(false); //设置树是否可编辑
        tree.setRootVisible(false);//设置树的根节点是否可视
        tree.setRowHeight(50);       //设置行距

        tree.setShowsRootHandles(true); //显示折叠/展开 图标

        tree.setToggleClickCount(1);//设置单击几次展开数节点

        tree.setOpaque(false);
        tree.setUI(new MyTreeUI1() {
        });

        /**
         * 点击好友
         */
        tree.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)//双击好友弹出对话框
                {
                    TreePath path = tree.getSelectionPath();//获取选中节点路径
                    Object node = path.getLastPathComponent();//通过路径将指针指向该节点
                    if (((IconNode) node).isLeaf())//如果该节点是叶子节点
                    {
                        System.out.println("叶子节点的名字为：" + ((IconNode) node).getText() + "QQ为：" + ((IconNode) node).getQQ());
                        String nodeQQ = ((IconNode) node).getQQ();

                        Enumeration it = friendInfoTable.elements();

                        for (int i = 0; i < n; i++) {

                            UserInfoBean friend = (UserInfoBean) it.nextElement();
                            String qq = Integer.toString(friend.getQq());
                            if (nodeQQ.equals(qq)) {
                                String sendIP = "127.0.0.1";
                                String receiveIP = "127.0.0.1";

                                InetAddress localAddr = null;
                                try {
                                    localAddr = InetAddress.getLocalHost();
                                } catch (UnknownHostException e1) {
                                    e1.printStackTrace();
                                }

                                receiveIP = localAddr.getHostAddress();

                                MainFrm.userInfo.setIP(receiveIP);
                                friend.setIP(receiveIP);


                                JChatFrm jcf = new JChatFrm(MainFrm.userInfo, friend);
                                jcf.setVisible(true);
                                break;
                            }
                        }
                    } else//不是叶子节点
                    {
                    }
                }
            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                final JTree tree = (JTree) e.getSource();
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());

                if (selRow != -1)//判断是否选中
                {
                    final Object node = selPath.getLastPathComponent();//通过路径将指针指向该节点
                    if (e.getModifiers() == InputEvent.BUTTON3_MASK && !((IconNode) node).isLeaf()) {

                        JPopupMenu popup = new JPopupMenu();
                        JMenuItem add = new JMenuItem("添加分组");
                        popup.add(add);
                        add.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                String result = JOptionPane.showInputDialog(MainFrm,
                                        "请输入要新增的分组名字", "新增分组",
                                        JOptionPane.INFORMATION_MESSAGE);
                                IconNode newGroup = new IconNode(result);//定义根节点
                                Root.add(newGroup);
                                tree.updateUI();
                            }
                        });

                        JMenuItem reName = new JMenuItem("重命名");
                        popup.add(reName);
                        add.setForeground(Color.red);
                        add.setBackground(Color.green);

                        JMenuItem delete = new JMenuItem("删除该组");
                        popup.add(delete);
                        delete.setForeground(Color.red);

                        popup.setForeground(Color.green);

                        popup.show(e.getComponent(), e.getX(), e.getY());

                    } else if (e.getModifiers() == InputEvent.BUTTON3_MASK && ((IconNode) node).isLeaf()) {//右键好友/叶子节点

                        JPopupMenu popup = new JPopupMenu();

                        JMenuItem check = new JMenuItem("查看资料");
                        popup.add(check);
                        check.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Enumeration it = friendInfoTable.elements();
                                String nodeQQ = ((IconNode) node).getQQ();
                                for (int i = 0; i < n; i++) {

                                    UserInfoBean friend = (UserInfoBean) it.nextElement();
                                    String qq = Integer.toString(friend.getQq());
                                    if (nodeQQ.equals(qq)) {
                                        new InformationFrm(friend);
                                    }
                                }

                            }
                        });

                        //TODO 这里有时间再写
                        JMenu chatRecord = new JMenu("消息记录");
                        /******************************消息记录的二级子菜单begin****************************/
                        JMenuItem local = new JMenuItem("查看本地消息");
                        JMenuItem manyou = new JMenuItem("查看漫游消息");
                        JMenuItem shangchuan = new JMenuItem("查看上传消息");

                        chatRecord.add(local);
                        chatRecord.add(manyou);
                        chatRecord.add(shangchuan);

/******************************消息记录的二级子菜单end****************************/
                        popup.add(chatRecord);
                        popup.addSeparator();


                        JMenuItem reName = new JMenuItem("修改备注姓名");
                        popup.add(reName);
                        reName.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String result = JOptionPane.showInputDialog(MainFrm,
                                        "输入要修改的备注名", "修改备注",
                                        JOptionPane.INFORMATION_MESSAGE);
                                // TODO 后续对数据库进行修改
                                System.out.println("你已成功修改备注");
                            }
                        });

                        JMenu move = new JMenu("移动联系人至");
/******************************移动联系人至的二级子菜单begin****************************/
                        JMenuItem friend = new JMenuItem("我的好友");
                        JMenuItem strager = new JMenuItem("陌生人");
                        JMenuItem black = new JMenuItem("黑名单");

/******************************移动联系人至的二级子菜单end****************************/
                        move.add(friend);
                        move.add(strager);
                        move.add(black);

                        popup.add(move);
                        move.setForeground(Color.red);

                        JMenuItem delete = new JMenuItem("删除好友");
                        delete.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ClientToServer cts = new ClientToServer();
                                new Thread(cts).start();

                                TreePath path = tree.getSelectionPath();//获取选中节点路径
                                Object node = path.getLastPathComponent();//通过路径将指针指向该节点
                                String nodeQQ = "10";
                                if (((IconNode) node).isLeaf())//如果该节点是叶子节点
                                {
                                    System.out.println("叶子节点的名字为：" + ((IconNode) node).getText() + "QQ为：" + ((IconNode) node).getQQ());
                                    nodeQQ = ((IconNode) node).getQQ();
                                }
                                if (nodeQQ.equals("10")) {
                                    JOptionPane.showMessageDialog(null, "请选择要删除的好友!");
                                }

                                tree.updateUI();


                                int qq = MainFrm.userInfo.getQq();
                                int fqq = Integer.parseInt(nodeQQ);
                                cts.deleteFriend(qq, fqq);
                                JOptionPane.showMessageDialog(null, "好友已删除!");

                                cts.closeConnect();

                            }

                        });
                        popup.add(delete);
                        popup.addSeparator();
                        popup.setForeground(Color.green);

                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }

                }
            }

            public void mouseReleased(MouseEvent e) {
            }
        });
        tree.setBounds(20, 20, 300, 600);
        tree.setLocation(10, 300);
        tree.setRootVisible(false); //不再显示根节点
        tree.putClientProperty("JTree.lineStyle", "None");

        UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);//此UIManager可以使JTabbedPane变的透明
        JLabel Lfrind = new JLabel("好友");
        Lfrind.setOpaque(false);
        JLabel Lgroup = new JLabel("群组");
        Lgroup.setOpaque(false);
        JTabbedPane tabeld = new JTabbedPane();
//		好友列表

        JScrollPane JSPane;        //滚动面板
        JSPane = new JScrollPane(tree);
        JSPane.setOpaque(false);
        JSPane.getViewport().setOpaque(false);
        JSPane.setBackground(new Color(1, 1, 1, 44));

        tabeld.addTab("", JSPane);
        tabeld.setIconAt(0, new ImageIcon(Pskin1));
        tabeld.addTab("群组", Lgroup);
        tabeld.setBounds(0, 130, MainFrm.getBounds().width + 2, Mheight - 200);
        tabeld.setBackground(new Color(0, 0, 0, 0));
        return tabeld;
    }

    public void UpdateFriendList() throws IOException {
        tabeld = createFriendList(cts, userInfo, friendInfoTable, n);
        abc.add(tabeld);
        MainFrm.add(abc);
    }

    /**
     * 刷新好友列表按钮点击后
     */
    public void Update() {
        cts.Update();//JMainFrm在这里被调用初始化
    }

    public void Update1(Hashtable friendInfoTable, int n) throws IOException {
        if (tabeld != null) {
            Root.removeAllChildren();
            this.friendInfoTable = friendInfoTable;
            System.out.println("重新创建好友列表");
            tabeld = createFriendList(cts, userInfo, this.friendInfoTable, n);
            abc.add(tabeld);
        }

    }

    public void UpdateFriendList(Hashtable friendInfoTable, int n) throws IOException {

        Enumeration it = friendInfoTable.elements();

        int count = Root.getChildCount();
        //JOptionPane.showMessageDialog(null, ""+count);
        for (int i = 0; i < count; i++) {
            IconNode node = (IconNode) Root.getChildAt(i);
            int count2 = node.getChildCount();
            //JOptionPane.showMessageDialog(null, ""+count2);
            for (int j = 0; j < count2; j++) {
                IconNode nodechild = (IconNode) node.getChildAt(j);
                String[] str = nodechild.getText();
                int lstr = str[0].indexOf("(");
                int rstr = str[0].indexOf(")");
                String qqStr = str[0].substring(lstr + 1, rstr);
                //	JOptionPane.showMessageDialog(null, qqStr);
                UserInfoBean friend = (UserInfoBean) it.nextElement();
                String ss = friend.getStatus() ? "上线" : "下线";
                //	JOptionPane.showMessageDialog(null, friend.getQq()+":"+ss);
                String strqq = "" + friend.getQq();
                if (qqStr.equals(strqq)) {
                    if (friend.getStatus()) {
                        face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";
                        nodechild.setIcon(new ImageIcon(face));
                        //JOptionPane.showMessageDialog(null, friend.getQq()+"：在线");
                    } else {
                        face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";
                        nodechild.setIcon(ColorConvertOp.getGrayPicture(face));
                    }

                }
            }
        }
    }


}
