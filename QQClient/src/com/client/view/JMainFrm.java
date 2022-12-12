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
    public IconNode Root = new IconNode(null, null);//������ڵ�
    JPanel abc = new JPanel();//��ӿؼ������
    int Mwide = 300, Mheight = 650;//����ĳ���
    private int x, y;//�����λ�ã��϶�ʱ��λ��
    int MAX_wide = 500;//�����
    private int offsetX, offsetY;//�϶�����ƫ��λ��
    Image ImageBk;    //����ͼƬ
    JLabel Bk_label;//����label
    boolean isHide = false;//�����Ƿ�����
    JLabel nickname = new JLabel();
    JLabel sign = new JLabel();

    public findFriendFrm FindFriendFrm = null;//���Һ������
    public int groupNumber = 0;//��ŷ������
    public String groupName[] = new String[20];//��ŷ�������

    private JTabbedPane tabeld = null;
    /**
     * ͼƬ��Դ
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
    //////////// ״̬ͼ��
    static String Ponline = "Image/MainIcon/Status\\imonline.png";
    static String Poffline = "Image/MainIcon/Status\\imoffline.png";
    static String Pbusy = "Image/MainIcon/Status\\busy.png";
    static String away = "Image/MainIcon/Status\\away.png";
    static String invisible = "Image/MainIcon/Status\\invisible.png";
    static String Qme = "Image/MainIcon/Status\\Qme.png";
    static String mute = "Image/MainIcon/Status\\mute.png";


    /////////////////////////��������ͼ�갴ť
    static String Flush = "Image/MainIcon/topIcon\\flush.png";

    static String find = "Image/MainIcon/MainTopToolBar\\find.png";

    String face = "";

    public ClientToServer cts;
    Login logout;
    private UserInfoBean userInfo;
    public Hashtable friendInfoTable;
    public int n;

    /**
     * ���췽��
     */
    public JMainFrm(ClientToServer cts, UserInfoBean userInfo, Hashtable friendInfoTable, int n) throws IOException {
        this.cts = cts;
        logout = new Login(cts);
        this.userInfo = userInfo;
        this.friendInfoTable = friendInfoTable;
        this.n = n;
        MainFrm = this;

        System.out.println(Background);
        this.setTitle("�����QQ");
        nickname.setBounds(0, 0, 200, 30);
        nickname.setLocation(3, 10);
        nickname.setFont(new Font("����", Font.BOLD, 30));
        nickname.setForeground(Color.white);
        nickname.setText(userInfo.getNickname());


        sign.setBounds(3, 30, 300, 30);

        sign.setFont(new Font("����", Font.ITALIC, 15));
        sign.setForeground(Color.white);
        sign.setText("ǩ��:(" + userInfo.getSign() + ")");
        face = "Image/MainIcon/qqicons\\Catch0000" + userInfo.getPhotoID() + ".jpg";//ͷ��
        // TODO ��ʾͷ���ⲿ�ִ���ֻ��һ��JLabel�Ļ���������ʾ��һ�¾���ʧ�ˣ���װ��������������JLabelһ���ͽ����
        ShowTouXiang(this);

        abc.setBounds(this.getBounds());
        abc.setLayout(null);
        abc.setOpaque(false);
        abc.add(nickname); //�ǳ�
        abc.add(sign);//ǩ��


        this.setResizable(false);//�����ٵ�����С
        this.setUndecorated(true);//����Ҫ��������װ��
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
        //////����ͼƬ
        Bk_label = new JLabel();
        Bk_label.setBounds(MainFrm.getBounds());
        Bk_label.setLocation(0, 0);
        Bk_label.setIcon(new ImageIcon(Background));

        ////////////״̬��ť/�������ߵȡ�����

        JComboBox icb = new JComboBox();//�����׼��Ͽ�
        icb.setMaximumRowCount(7);//���������ʾ��
        icb.setRenderer(new CombListRenderer());//���õ�Ԫ�����ã�����ʹ���˸ղŴ������ࣩ
        icb.setBackground(new Color(112, 100, 200, 166));//���ñ���ɫ

        icb.addItem(new Object[]{new ImageIcon(Ponline), "��������"});
        icb.addItem(new Object[]{new ImageIcon(Qme), "Q�Ұ�"});
        icb.addItem(new Object[]{new ImageIcon(away), "�뿪"});
        icb.addItem(new Object[]{new ImageIcon(Pbusy), "æµ"});
        icb.addItem(new Object[]{new ImageIcon(mute), "����"});
        icb.addItem(new Object[]{new ImageIcon(invisible), "����"});
        icb.addItem(new Object[]{new ImageIcon(Poffline), "����"});

        icb.setBounds(0, 0, 100, 20);
        icb.setLocation(75, 52);
        icb.setBorder(null);
        abc.add(icb);


//		****************������***************************
        JLabel serch = new JLabel("��������ûʱ����ˣ�ֱ��д����JLabel�ˣ�");
        serch.setBackground(Color.cyan);
        serch.setBounds(0, 0, 296, 30);
        serch.setLocation(2, 100);
        abc.add(serch);
//		*****************�����������ͼ�갴ť***************8
        JButton BFlush, BBlog, BEmail, BShop, BMoney, BMember, BWeb;
        BFlush = new JButton("ˢ�º����б�",new ImageIcon(Flush));
        BFlush.setBounds(0, 0, 150, 20);
        BFlush.setLocation(75, 75);
//        BFlush.setIcon(new ImageIcon(Pzone));
        BFlush.setContentAreaFilled(false);
        BFlush.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update();
                JOptionPane.showMessageDialog(null, "�����б���ˢ��");
            }
        });
        abc.add(BFlush);


        JButton Bserch;
        Bserch = new JButton("��Ӻ���",new ImageIcon(find));            //����
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
//		******************���������ͼ�갴ťend**************************
        final JButton B_close = new JButton("�ر�");
        B_close.setBounds(0, 0, 30, 20);
        B_close.setLocation(this.getBounds().width - 20, 0);
        B_close.setIcon(new ImageIcon(Pclose1));
        B_close.setOpaque(false);
        B_close.setBorder(null);
        B_close.setBackground(Color.white);
        B_close.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (JOptionPane.showConfirmDialog(null, "<html><font size=3>ȷ���˳���</html>", "ϵͳ��ʾ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
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
        ///ѡ�
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
     * ���������
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
     * �÷�����ʾͷ���
     */
    public void ShowTouXiang(JMainFrm MainFrm) {

        final JLabel Ltouxiang = new JLabel();//��������ͷ�����ʧ�Ŀ򣬸�����

        final JLabel Lselect = new JLabel(); //ͷ���

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
     * �����б�
     * @param cts
     * @param userInfo
     * @param friendInfoTable
     * @param n
     * @return
     * @throws IOException
     */
    public JTabbedPane createFriendList(ClientToServer cts, final UserInfoBean userInfo, final Hashtable friendInfoTable, final int n) throws IOException {
//		*********************����Ĭ�Ϸ���**
        groupNumber = 0;
//		IconNode haoyou=new IconNode("����");
//		IconNode strager=new IconNode("İ����");
//		IconNode black=new IconNode("������");
        //haoyou.add(new IconNode(""));
        //strager.add(new IconNode(""));
        //black.add(new IconNode(""));
        Root = new IconNode(null, null);
//		Root.add(haoyou);
//		Root.add(strager);
//		Root.add(black);//��Ӻ�����
        /**
         * ���ݴ����ݿ��ȡ�ķ���ͺ��ѵ���Ϣ���������б�*********
         */
        IconNode root[] = new IconNode[20]; //�������20������
        boolean isfind = false;//�������ҵ���ͬ����
        String groupname = "";//�����ʱȡ�����ķ�������
        int total = 0;//����ܵķ�����

        /**
         * ʵ��Enumeration�ӿڵĶ�������һϵ��Ԫ�أ�ÿ��һ����
         * ��nextElement�������������ý����������е�����Ԫ�ء�
         * ���磬Ҫ��ӡVector  v������Ԫ��: for(ö��  E = v.elements();e.hasMoreElements ();
         * System.out.println (e.nextElement ());
         * ��������ö��������Ԫ�ء���ϣ��ļ��͹�ϣ���е�ֵ��
         * ö�ٻ�����ָ��SequenceInputStream����������
         * ע��:�ýӿڵĹ�����Iterator�ӿ���ͬ��
         * ���⣬Iterator�����һ����ѡ��ɾ�������������и��̵ķ������ơ�
         * �µ�ʵ��Ӧ�ÿ�������ʹ��Iterator������Enumeration��
         *
         *  public synchronized Enumeration<V> elements() {
         *         return this.<V>getEnumeration(VALUES);
         *     }
         * ���ش�ɢ�б���ֵ��ö�١��ڷ��صĶ�����ʹ��Enumeration������˳���ȡԪ�ء� ����ֵ: ��ɢ�б���ֵ��ö��
         */
        Enumeration it = friendInfoTable.elements();

        for (int i = 0; i < n; i++) {
            total = groupNumber;
            isfind = false;
            UserInfoBean friend = (UserInfoBean) it.nextElement();
//            System.out.println("��"+friend.getSubGroupName()+"�����µĺ���:"+friend.getNickname()+"���߷�" + friend.getStatus()+ " ����QQΪ��" + friend.getQq());
//            System.out.println("�½������У�" + friend.getQq() + " " + friend.getNickname() + " " +
//                    friend.getSubGroupName() + "���߷�" + friend.getStatus());
            ///���к����еķ������ֱȽ�
            groupname = friend.getSubGroupName();
            if (groupNumber == 0) {
                groupNumber++;
                groupName[groupNumber] = groupname;
                root[groupNumber] = new IconNode(groupName[groupNumber]);//��������
                face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";
                // TODO ��������߾���ʾ��ɫͷ����ⲿ�ִ��������Է�װ��һ���������棬�����ж���д����ٻ����Ż�
                if (!friend.getStatus()) {//��������ߣ������ͷ��
                    try {
                        //��Ӻ��ѵ������б���
                        IconNode friendNode = new IconNode(ColorConvertOp.getGrayPicture(face),
                                friend.getNickname() + "  " + "(" + friend.getQq() + ")", friend.getSign());
                        root[groupNumber].add(friendNode);
                        friendNode.setQQ(Integer.toString(friend.getQq()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    //��Ӻ��ѵ������б���
                    IconNode friendNode = new IconNode(new ImageIcon(face),
                            friend.getNickname() + "  " + "(" + friend.getQq() + ")", friend.getSign());
                    root[groupNumber].add(friendNode);
                    friendNode.setQQ(Integer.toString(friend.getQq()));
                }
                Root.add(root[groupNumber]);

            } else {///���к����еķ������ֱȽ�
                for (int j = 1; j <= total; j++) {
                    if (groupName[j].equals(groupname)) {//�����������һģһ��
                        isfind = true;//�ҵ���
                        face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";
                        if (!friend.getStatus()) {//��������ߣ������ͷ��
                            try {
                                //��Ӻ��ѵ������б���
                                IconNode friendNode = new IconNode(ColorConvertOp.getGrayPicture(face),
                                        friend.getNickname() + "  " + "(" + friend.getQq() + ")", friend.getSign());
                                root[groupNumber].add(friendNode);
                                friendNode.setQQ(Integer.toString(friend.getQq()));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            //��Ӻ��ѵ������б���
                            IconNode friendNode = new IconNode(new ImageIcon(face),
                                    friend.getNickname() + "  " + "(" + friend.getQq() + ")", friend.getSign());
                            root[groupNumber].add(friendNode);
                            friendNode.setQQ(Integer.toString(friend.getQq()));
                        }
                        Root.add(root[groupNumber]);
                        break;
                    } else if (j >= groupNumber && !isfind) {//����������ֲ�һ��
                        groupNumber++;
                        groupName[groupNumber] = groupname;
                        root[groupNumber] = new IconNode(groupName[groupNumber]);//��������
                        face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";
                        if (!friend.getStatus()) {//��������ߣ������ͷ��
                            try {
                                //��Ӻ��ѵ������б���
                                IconNode friendNode = new IconNode(ColorConvertOp.getGrayPicture(face),
                                        friend.getNickname() + "  " + "(" + friend.getQq() + ")", friend.getSign());
                                root[groupNumber].add(friendNode);
                                friendNode.setQQ(Integer.toString(friend.getQq()));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            //��Ӻ��ѵ������б���
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
        //��ͼƬ������Ⱦ
        final IconNodeRenderer render = new IconNodeRenderer();

        render.setBackground(Color.pink);
        tree.setCellRenderer(render); //���õ�Ԫ������
        tree.setEditable(false); //�������Ƿ�ɱ༭
        tree.setRootVisible(false);//�������ĸ��ڵ��Ƿ����
        tree.setRowHeight(50);       //�����о�

        tree.setShowsRootHandles(true); //��ʾ�۵�/չ�� ͼ��

        tree.setToggleClickCount(1);//���õ�������չ�����ڵ�

        tree.setOpaque(false);
        tree.setUI(new MyTreeUI1() {
        });

        /**
         * �������
         */
        tree.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)//˫�����ѵ����Ի���
                {
                    TreePath path = tree.getSelectionPath();//��ȡѡ�нڵ�·��
                    Object node = path.getLastPathComponent();//ͨ��·����ָ��ָ��ýڵ�
                    if (((IconNode) node).isLeaf())//����ýڵ���Ҷ�ӽڵ�
                    {
                        System.out.println("Ҷ�ӽڵ������Ϊ��" + ((IconNode) node).getText() + "QQΪ��" + ((IconNode) node).getQQ());
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
                    } else//����Ҷ�ӽڵ�
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

                if (selRow != -1)//�ж��Ƿ�ѡ��
                {
                    final Object node = selPath.getLastPathComponent();//ͨ��·����ָ��ָ��ýڵ�
                    if (e.getModifiers() == InputEvent.BUTTON3_MASK && !((IconNode) node).isLeaf()) {

                        JPopupMenu popup = new JPopupMenu();
                        JMenuItem add = new JMenuItem("��ӷ���");
                        popup.add(add);
                        add.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                String result = JOptionPane.showInputDialog(MainFrm,
                                        "������Ҫ�����ķ�������", "��������",
                                        JOptionPane.INFORMATION_MESSAGE);
                                IconNode newGroup = new IconNode(result);//������ڵ�
                                Root.add(newGroup);
                                tree.updateUI();
                            }
                        });

                        JMenuItem reName = new JMenuItem("������");
                        popup.add(reName);
                        add.setForeground(Color.red);
                        add.setBackground(Color.green);

                        JMenuItem delete = new JMenuItem("ɾ������");
                        popup.add(delete);
                        delete.setForeground(Color.red);

                        popup.setForeground(Color.green);

                        popup.show(e.getComponent(), e.getX(), e.getY());

                    } else if (e.getModifiers() == InputEvent.BUTTON3_MASK && ((IconNode) node).isLeaf()) {//�Ҽ�����/Ҷ�ӽڵ�

                        JPopupMenu popup = new JPopupMenu();

                        JMenuItem check = new JMenuItem("�鿴����");
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

                        //TODO ������ʱ����д
                        JMenu chatRecord = new JMenu("��Ϣ��¼");
                        /******************************��Ϣ��¼�Ķ����Ӳ˵�begin****************************/
                        JMenuItem local = new JMenuItem("�鿴������Ϣ");
                        JMenuItem manyou = new JMenuItem("�鿴������Ϣ");
                        JMenuItem shangchuan = new JMenuItem("�鿴�ϴ���Ϣ");

                        chatRecord.add(local);
                        chatRecord.add(manyou);
                        chatRecord.add(shangchuan);

/******************************��Ϣ��¼�Ķ����Ӳ˵�end****************************/
                        popup.add(chatRecord);
                        popup.addSeparator();


                        JMenuItem reName = new JMenuItem("�޸ı�ע����");
                        popup.add(reName);
                        reName.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String result = JOptionPane.showInputDialog(MainFrm,
                                        "����Ҫ�޸ĵı�ע��", "�޸ı�ע",
                                        JOptionPane.INFORMATION_MESSAGE);
                                // TODO ���������ݿ�����޸�
                                System.out.println("���ѳɹ��޸ı�ע");
                            }
                        });

                        JMenu move = new JMenu("�ƶ���ϵ����");
/******************************�ƶ���ϵ�����Ķ����Ӳ˵�begin****************************/
                        JMenuItem friend = new JMenuItem("�ҵĺ���");
                        JMenuItem strager = new JMenuItem("İ����");
                        JMenuItem black = new JMenuItem("������");

/******************************�ƶ���ϵ�����Ķ����Ӳ˵�end****************************/
                        move.add(friend);
                        move.add(strager);
                        move.add(black);

                        popup.add(move);
                        move.setForeground(Color.red);

                        JMenuItem delete = new JMenuItem("ɾ������");
                        delete.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ClientToServer cts = new ClientToServer();
                                new Thread(cts).start();

                                TreePath path = tree.getSelectionPath();//��ȡѡ�нڵ�·��
                                Object node = path.getLastPathComponent();//ͨ��·����ָ��ָ��ýڵ�
                                String nodeQQ = "10";
                                if (((IconNode) node).isLeaf())//����ýڵ���Ҷ�ӽڵ�
                                {
                                    System.out.println("Ҷ�ӽڵ������Ϊ��" + ((IconNode) node).getText() + "QQΪ��" + ((IconNode) node).getQQ());
                                    nodeQQ = ((IconNode) node).getQQ();
                                }
                                if (nodeQQ.equals("10")) {
                                    JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ���ĺ���!");
                                }

                                tree.updateUI();


                                int qq = MainFrm.userInfo.getQq();
                                int fqq = Integer.parseInt(nodeQQ);
                                cts.deleteFriend(qq, fqq);
                                JOptionPane.showMessageDialog(null, "������ɾ��!");

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
        tree.setRootVisible(false); //������ʾ���ڵ�
        tree.putClientProperty("JTree.lineStyle", "None");

        UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);//��UIManager����ʹJTabbedPane���͸��
        JLabel Lfrind = new JLabel("����");
        Lfrind.setOpaque(false);
        JLabel Lgroup = new JLabel("Ⱥ��");
        Lgroup.setOpaque(false);
        JTabbedPane tabeld = new JTabbedPane();
//		�����б�

        JScrollPane JSPane;        //�������
        JSPane = new JScrollPane(tree);
        JSPane.setOpaque(false);
        JSPane.getViewport().setOpaque(false);
        JSPane.setBackground(new Color(1, 1, 1, 44));

        tabeld.addTab("", JSPane);
        tabeld.setIconAt(0, new ImageIcon(Pskin1));
        tabeld.addTab("Ⱥ��", Lgroup);
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
     * ˢ�º����б�ť�����
     */
    public void Update() {
        cts.Update();//JMainFrm�����ﱻ���ó�ʼ��
    }

    public void Update1(Hashtable friendInfoTable, int n) throws IOException {
        if (tabeld != null) {
            Root.removeAllChildren();
            this.friendInfoTable = friendInfoTable;
            System.out.println("���´��������б�");
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
                String ss = friend.getStatus() ? "����" : "����";
                //	JOptionPane.showMessageDialog(null, friend.getQq()+":"+ss);
                String strqq = "" + friend.getQq();
                if (qqStr.equals(strqq)) {
                    if (friend.getStatus()) {
                        face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";
                        nodechild.setIcon(new ImageIcon(face));
                        //JOptionPane.showMessageDialog(null, friend.getQq()+"������");
                    } else {
                        face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";
                        nodechild.setIcon(ColorConvertOp.getGrayPicture(face));
                    }

                }
            }
        }
    }


}
