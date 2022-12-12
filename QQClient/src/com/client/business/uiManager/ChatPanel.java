package com.client.business.uiManager;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.client.chat.Gain_Voice;
import com.client.chat.PicsJWindow;
import com.client.chat.Play_Voice;
import com.client.cut.Cut;
import com.tools.ClientToServer;
import com.client.data.LoadImages;
import com.client.qipao.BubbleModel;
import com.client.qipao.BubbleRenderer;
import com.client.qipao.IMMessage;
import com.client.transform.ServerTest;
import com.client.view.JChatFrm;
import com.client.view.MyButton;
import com.common.Message;
import com.common.Port;
import com.common.ReceivePort;
import com.common.UserInfoBean;
import com.tools.ClientToServerThread;

public class ChatPanel extends JPanel implements ActionListener, ItemListener {
    String image_ep_number = null;
    ImageIcon cut_image = null;
    int voice_flag = 0;

    // 气泡消息
    JTable table = new JTable();
    JScrollBar jsbar;
    BubbleModel mModel = new BubbleModel();
    String sSend = "1";
    int number = 0;
    public MyButton btn_qp;// 气泡
    public MyButton btn_wz;// 文字
    boolean qp_flag = false;// 当前不是气泡模式
    // 气泡消息
    private ImageIcon send_Image;// 发送表情
    private int image_number = 0;
    private boolean i_flag = false;
    private Point start;
    private Timer shakeTimer;
    private int i_num = 0;

    private PicsJWindow picWindow;

    Gain_Voice gainvoice;  // 获取音频数据
    Play_Voice playvoice;  // 播放语音
    Socket gainsocket;     // 音频接收的Socket

    MyButton icon_btn;
    JChatFrm chatfrm;
    String backgroud = "bk";
    MessageLoggingPanel messageloggingpanel = new MessageLoggingPanel();
    boolean b_ml = false; // 消息记录

    public JPanel font_panel; // 设置字体面板
    public JPanel expression_panel;// 表情面板
    public JComboBox cb_font; // 字体

    public JComboBox cb_size; // 字的大小
    public MyButton bt_bold; // 粗体
    public boolean b_bold = true;
    public MyButton bt_Italic; // 斜体
    public boolean b_Italic = false;
    public MyButton bt_Underline; // 下划线
    public boolean b_Underline = false;
    public MyButton bt_color; // 字的颜色
    public boolean b_color = false;

    public JPanel panel2;

    public MyButton btn_head_portrait; // 头像
    public JLabel lab_nickname; // 昵称
    public JLabel lab_autograph; // 签名
    public MyButton bt_video; // 开启器视频对话
    public MyButton bt_voice; // 开启语音对话
    public MyButton bt_voice_cancel;// 语音取消
    public MyButton bt_voice_accept;// 接受语音消息
    public MyButton bt_voice_deny;// 拒绝语音消息
    public MyButton bt_file; // 传送文件
    public MyButton bt_file_cancel;// 取消传送文件
    public MyButton bt_file_accept;// 接收文件
    public JLabel lb_file_name;// jieshouwenjianming
    public MyButton bt_file_save;
    public MyButton bt_file_deny;// 拒绝接收文件
    public MyButton bt_create_group; // 创建群组
    public MyButton bt_app; // 应用

    public ImageIcon icon;
    public LoadImages image;

    public MyTextPane tp_message = new MyTextPane();
    public JScrollPane spanel1;

    public MyButton bt_font; // 字体
    public boolean b_font = false;//
    public MyButton bt_erpression; // 表情
    public MyButton btn_shanke;// 振动
    public MyButton bt_voice_message; // 语音消息
    public MyButton bt_Screen;// 截屏
    public MyButton bt_image; // 发送图片
    public MyButton bt_message_logging;// 消息记录
    public JScrollPane spanel2;// 发送消息Panel滚动
    public MyTextPane tp_send_message = new MyTextPane();
    public MyButton bt_close; // 关闭
    public MyButton bt_send; // 发送消息
    public String send_font = "华文隶书"; // 发送消息的字体
    public boolean send_font_bold; // 发送消息的字形
    public int send_font_size = 20; // 发送消息的字的大小
    public Color send_color = Color.BLUE; // 发送消息的颜色；

    private String begin;
    private String info;
    // ///////////////////////////////////
    ClientToServerThread ctsT;
    UserInfoBean user;
    UserInfoBean friend;
    Message message;
    private int sendPORT;
    private int receivePort;

    private boolean isStop = false;
    ClientToServer cts = null;

    public ChatPanel(JChatFrm chatfrm, ClientToServerThread ctsT,
                     UserInfoBean user, UserInfoBean friend) {

        this.chatfrm = chatfrm;
        this.friend = friend;
        getPropertiesInfo();
        this.user = user;
        send_Image = null;

        message = new Message();
        message.setSendIP(friend.getIP());
        message.setReceiveIP(user.getIP());
        message.setSendPort(sendPORT);
        message.setReceivePort(receivePort);
        message.setsendSign(user.getSign());
        message.setsendPhotoID(user.getPhotoID());

        message.setSendQq(user.getQq());
        message.setSendNickName(user.getNickname());
        message.setReceiveQq(friend.getQq());
        message.setReceiveNickName(friend.getNickname());
        message.setreceiveSign(friend.getSign());
        message.setreceivePhotoID(friend.getPhotoID());

        lab_nickname = new JLabel(friend.getNickname());
        lab_autograph = new JLabel(friend.getSign());

        ctsT = new ClientToServerThread(tp_message, this, friend.getQq(), receivePort);
        ctsT.start();
        this.ctsT = ctsT;

        setLayout(null);// 设置null布局

        icon = new ImageIcon();
        image = new LoadImages();

        setFontPanel();// 设置字体panel
        setMessage();// 设置消息

        {
            btn_head_portrait = new MyButton();// 头像
            btn_head_portrait.setBounds(5, 2, 45, 43);
            String face = "Image/MainIcon/qqicons\\Catch0000" + friend.getPhotoID() + ".jpg";

            btn_head_portrait.setIcon(new ImageIcon(face));
            btn_head_portrait.setContentAreaFilled(false); // 按
            add(btn_head_portrait);

            lab_nickname.setFont(new Font("楷体", 1, 25));
            lab_nickname.setBounds(50, 8, 300, 20);
            add(lab_nickname);

            lab_autograph.setFont(new Font("楷体", Font.BOLD | Font.ITALIC, 15));
            lab_autograph.setForeground(Color.BLUE);
            lab_autograph.setBounds(85, 24, 800, 30);

            add(lab_autograph);
        }
        {
            bt_voice = new MyButton();
            bt_voice.setBounds(4, 48, 42, 32);
            bt_voice.setToolTipText("语音会话");
            bt_voice.addActionListener(this);
            add(bt_voice);

            bt_voice_cancel = new MyButton();
            bt_voice_cancel.setBounds(497, 273, 62, 27);
            bt_voice_cancel.addActionListener(this);
            bt_voice_cancel.setVisible(false);
            add(bt_voice_cancel);
            {
                bt_voice_accept = new MyButton();
                bt_voice_accept.setBounds(456, 280, 62, 27);
                bt_voice_accept.addActionListener(this);
                bt_voice_accept.setVisible(false);
                add(bt_voice_accept);

                bt_voice_deny = new MyButton();// 拒绝语音
                bt_voice_deny.setBounds(531, 280, 62, 27);
                bt_voice_deny.addActionListener(this);
                bt_voice_deny.setVisible(false);
                add(bt_voice_deny);
            }
            bt_video = new MyButton();// 视频会话
            bt_video.setBounds(55, 48, 42, 32);
            bt_video.setToolTipText("视频会话");
            add(bt_video);

            bt_file = new MyButton();//文件传输
            bt_file.setBounds(103, 48, 42, 32);
            bt_file.setToolTipText("文件传输");
            bt_file.addActionListener(this);
            add(bt_file);

            bt_file_accept = new MyButton();// 接收文件
            bt_file_accept.setBounds(586, 157, 38, 23);
            bt_file_accept.addActionListener(this);
            bt_file_accept.setVisible(false);
            add(bt_file_accept);

            bt_file_save = new MyButton();// 取消接收文件
            bt_file_save.setBounds(625, 157, 43, 23);
            bt_file_save.addActionListener(this);
            bt_file_save.setVisible(false);
            add(bt_file_save);

            bt_file_deny = new MyButton();// 取消接收文件
            bt_file_deny.setBounds(672, 157, 38, 23);
            bt_file_deny.addActionListener(this);
            bt_file_deny.setVisible(false);
            add(bt_file_deny);

            bt_file_cancel = new MyButton();// 取消发送文件
            bt_file_cancel.setBounds(692, 155, 36, 23);
            bt_file_cancel.addActionListener(this);
            bt_file_cancel.setVisible(false);
            add(bt_file_cancel);

            lb_file_name = new JLabel();//显示发送文件名
            lb_file_name.setFont(new Font("楷体", Font.PLAIN,

                    15));
            lb_file_name.setForeground(Color.black);
            lb_file_name.setBounds(512, 125, 4500, 20);
            lb_file_name.setText("");
            lb_file_name.setVisible(false);
            add(lb_file_name);

            bt_create_group = new MyButton();
            bt_create_group.setBounds(150, 48, 37, 32);
            bt_create_group.setToolTipText("创建群组");
            add(bt_create_group);

            bt_app = new MyButton();
            bt_app.setBounds(281, 48, 42, 32);
            bt_app.setToolTipText("应用");
            add(bt_app);
        }

        {

            bt_font = new MyButton();//字体设置
            bt_font.setBounds(1, 373, 27, 28);
            bt_font.setToolTipText("字体");
            bt_font.addActionListener(this);
            add(bt_font);

            bt_erpression = new MyButton();//表情
            bt_erpression.setBounds(28, 373, 27, 28);
            bt_erpression.setToolTipText("表情");
            bt_erpression.addActionListener(this);
            add(bt_erpression);

            btn_shanke = new MyButton();//抖动
            btn_shanke.setBounds(77, 373, 28, 28);
            btn_shanke.setToolTipText("振动");
            btn_shanke.addActionListener(this);
            add(btn_shanke);

            bt_voice_message = new MyButton();//语音消息
            bt_voice_message.setBounds(105, 373, 26, 28);
            bt_voice_message.setToolTipText("语音消息");
            bt_voice_message.addActionListener(this);
            add(bt_voice_message);

            bt_image = new MyButton();
            bt_image.setBounds(156, 373, 35, 28);
            bt_image.setToolTipText("发送图片");
            bt_image.addActionListener(this);
            add(bt_image);

            bt_Screen = new MyButton();
            bt_Screen.setBounds(220, 373, 35, 28);
            bt_Screen.setToolTipText("截屏");
            bt_Screen.addActionListener(this);
            add(bt_Screen);

            bt_message_logging = new MyButton();
            bt_message_logging.setBounds(356, 373, 83, 27);
            bt_message_logging.setToolTipText("消息记录");
            bt_message_logging.addActionListener(this);
            add(bt_message_logging);
        }

        setSendMessage();
        {
            bt_close = new MyButton();
            bt_close.setBounds(273, 476, 72, 28);
            bt_close.setContentAreaFilled(false);
            bt_close.setToolTipText("关闭");
            add(bt_close);

            bt_send = new MyButton();
            bt_send.setBounds(347, 476, 92, 28);
            bt_send.setContentAreaFilled(false);
            bt_send.addActionListener(this);
            bt_send.setToolTipText("发送");
            add(bt_send);
        }

        add(messageloggingpanel);
        this.repaint();
    }


    public void paintComponent(Graphics g) {
        drawBtnVideo(g);

    }

    /**
     * 绘制语音按钮
     * @param g
     */
    public void drawBtnVideo(Graphics g) {
        LoadImages image = new LoadImages();
        ImageIcon icon = new ImageIcon();

        if (user.getQq() / 2 == 0) {
            backgroud += "0";
        }
        icon = image.LoadImageIcon(backgroud);
        g.drawImage(icon.getImage(), -1, -1, getSize().width + 3,
                getSize().height + 3, this);

    }

    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bt_send) { // 发送消息
            sendMessage();
        } else if (e.getSource() == bt_font) { // 字体设置
            if (b_font == false) {
                font_panel.setVisible(true);
                icon = image.LoadImageIcon("font1");
                this.backgroud = "bk2";
                if (qp_flag) {

                    btn_qp.setVisible(false);
                    btn_wz.setVisible(true);
                } else {
                    btn_qp.setVisible(true);
                    btn_wz.setVisible(false);
                }
                spanel1.setBounds(0, 83, 447, 260);
                b_font = true;
            } else {
                btn_qp.setVisible(true);
                btn_wz.setVisible(true);
                font_panel.setVisible(false);
                this.backgroud = "bk";
                icon = image.LoadImageIcon("font0");
                spanel1.setBounds(0, 83, 447, 290);
                b_font = false;
            }
            bt_font.setIcon(icon);
        } else if (e.getSource() == bt_bold) {//字体加粗
            if (b_bold == false) {
                icon = image.LoadImageIcon("bold");
                b_bold = true;
            } else {

                icon = null;
                b_bold = false;
            }
            bt_bold.setIcon(icon);
        } else if (e.getSource() == bt_Italic) {//字体斜体
            if (b_Italic == false) {
                icon = image.LoadImageIcon("Italic");
                b_Italic = true;
            } else {

                icon = null;
                b_Italic = false;
            }
            bt_Italic.setIcon(icon);
        } else if (e.getSource() == bt_Underline) {//字体下划线
            if (b_Underline == false) {
                icon = image.LoadImageIcon("Underline");
                b_Underline = true;
            } else {
                icon = null;
                b_Underline = false;
            }
            bt_Underline.setIcon(icon);

        } else if (e.getSource() == bt_color) {//字体颜色
            Color newColor = JColorChooser.showDialog(this, "字体颜色", send_color);
            send_color = newColor;
        } else if (e.getSource() == bt_message_logging) {
            // TODO:
            if (b_ml == false) {

                message.setInfoType("Record");
                ctsT.sendData(message);
                ctsT.sendData(message);

                // JOptionPane.showMessageDialog(null, "send ");
                chatfrm.setSize(809, 508);
                chatfrm.btn_close.setBounds(781, 1, 28, 26);
                this.backgroud = "bk1";
                messageloggingpanel.setOpaque(false);
                messageloggingpanel.setVisible(true);

                this.repaint();
                b_ml = true;
            } else {

                chatfrm.setSize(586, 508);
                chatfrm.btn_close.setBounds(558, 1, 28, 26);
                this.backgroud = "bk";
                this.repaint();
                messageloggingpanel.setVisible(false);
                b_ml = false;
            }

        } else if (e.getSource() == bt_image) {//发送图片
            tp_send_message.insertImage(sendImage());
        } else if (e.getSource() == bt_file) {//发送文件
            System.out.println("fule");
//			JFileChooser chooser = new JFileChooser();
//			File file = null;
//			FileNameExtensionFilter filter = new FileNameExtensionFilter(
//					"文本文件", "txt", "doc");
//			chooser.setFileFilter(filter);
//			int option = chooser.showOpenDialog(this);
//			if (option == JFileChooser.APPROVE_OPTION) {
//				file = chooser.getSelectedFile();
//				try {
//					ImageIcon image = new ImageIcon(file.toURI().toURL());
//
//				} catch (MalformedURLException e1) {
//					e1.printStackTrace();
//				}
//			}

            message.setInfoType("File");
            message.setSendIP(getLocalIP());
            //	ctsT.sendData(message);
            //	ctsT.sendData(message);
            new ServerTest(this, message, ctsT);

            // bt_File_Accept();
        } else if (e.getSource() == bt_file_accept) {//文件接收
            lb_file_name.setVisible(true);
            this.backgroud = "file_send";
            chatfrm.setSize(730, 508);
            chatfrm.btn_close.setBounds(702, 1, 25, 26);
            bt_message_logging.setEnabled(false);
            bt_file_cancel.setVisible(true);
            bt_file_save.setVisible(false);
            bt_file_accept.setVisible(false);
            bt_file_deny.setVisible(false);
            lb_file_name.setText("wenjianming");//显示发送的文件名
            lb_file_name.setVisible(true);
            this.repaint();

        } else if (e.getSource() == bt_file_save) {// 文件接收另存为

        } else if (e.getSource() == bt_file_deny) {//文件接收取消

            this.backgroud = "bk";
            chatfrm.setSize(586, 508);
            chatfrm.btn_close.setBounds(558, 1, 28, 26);

            lb_file_name.setVisible(false);
            bt_message_logging.setEnabled(true);
            bt_file_accept.setVisible(false);
            bt_file_save.setVisible(false);
            bt_file_deny.setVisible(false);

            lb_file_name.setVisible(false);
            this.repaint();
        } else if (e.getSource() == bt_file_cancel) {//文件取消
            this.backgroud = "bk";
            chatfrm.setSize(586, 508);
            chatfrm.btn_close.setBounds(558, 1, 28, 26);

            lb_file_name.setVisible(false);
            bt_message_logging.setEnabled(true);
            bt_file_cancel.setVisible(false);
            bt_file_deny.setVisible(false);

            lb_file_name.setVisible(false);
            this.repaint();
        } else if (e.getSource() == btn_shanke) {//发送抖动
            message.setInfoType("Shake");
            ctsT.sendData(message);
            ctsT.sendData(message);
            startShake();// 振动消息
        } else if (e.getSource() == bt_erpression) {//发送表情
            System.out.println("bioqing");
            if (i_flag == false) {
                chatfrm.Bt_erpression_true();
                i_flag = true;
            } else {
                chatfrm.Bt_erpression_false();
                i_flag = false;
            }
        } else if (e.getSource() == btn_qp) {//气泡模式
            qp_flag = true;
            this.backgroud = "bk3";
            font_panel.setVisible(false);
            spanel1.setViewportView(table);
            btn_qp.setVisible(false);
            btn_wz.setVisible(true);
        } else if (e.getSource() == btn_wz) {//文字模式
            this.backgroud = "bk2";
            qp_flag = false;
            font_panel.setVisible(true);
            spanel1.setViewportView(tp_message);
            btn_qp.setVisible(true);
            btn_wz.setVisible(false);
        } else if (e.getSource() == bt_Screen) {
            new Cut(getChatPanel());//
        } else if (e.getSource() == bt_voice)//点击开始通话
        {
            voice_flag++;
            Message message = new Message();
            message.setReceiveQq(friend.getQq());
            message.setsendPhotoID(user.getPhotoID());
            message.setSendQq(user.getQq());
            message.setsendSign(user.getSign());
            message.setInfoType("Voice");
            message.setSendIP(getLocalIP());
            ctsT.sendData(message);
            ctsT.sendData(message);

            //String Ip = "192.168.191.6";
            playvoice = new Play_Voice();
            playvoice.start();

            if (voice_flag == 2) {
                bt_Voice();
                voice_flag = 0;
            }

        } else if (e.getSource() == bt_voice_accept)// 接受语音通话
        {


        } else if (e.getSource() == bt_voice_deny)// 拒绝语音通话
        {
            this.backgroud = "bk";
            chatfrm.setSize(586, 508);
            bt_voice.setEnabled(true);
            bt_video.setEnabled(true);
            chatfrm.btn_close.setBounds(558, 1, 28, 26);
            bt_voice_cancel.setVisible(false);
            bt_message_logging.setEnabled(true);
            bt_voice_accept.setVisible(false);
            bt_voice_deny.setVisible(false);
            this.repaint();
        } else if (e.getSource() == bt_voice_cancel) {


            voice_cancel();
            message.setInfoType("CloseVoice");
            ctsT.sendData(message);
            ctsT.sendData(message);

        }

        tp_send_message.removeAll();
        String str1 = tp_send_message.getText().trim();
        tp_send_message.setText(" ");
        if (str1.length() == 0) {
            str1 = " ";
        }
        tp_send_message.setDocs(str1, send_color, send_font, b_bold, b_Italic,
                b_Underline, send_font_size);
    }

    //录音
    public void startYuyin(String Ip) {


        //	show_voice_accept();
        // bt_Voice();

        // 语音聊天

        gainvoice = new Gain_Voice(Ip); // 初始化一个对象


        gainvoice.start();
        //gainvoice.run(); // 获取音频设备的数据

        this.backgroud = "voiceing";
        bt_voice_accept.setVisible(false);
        bt_voice_deny.setVisible(false);
        bt_voice_cancel.setBounds(497, 296, 62, 27);
        bt_voice_cancel.setVisible(true);
        this.repaint();

        //	playvoice.run();

    }

    public void acceptYuyin(String Ip) {
		/*this.backgroud = "voiceing";
		bt_voice_accept.setVisible(false);
		bt_voice_deny.setVisible(false);
		bt_voice_cancel.setBounds(497, 296, 62, 27);
		bt_voice_cancel.setVisible(true);
		this.repaint();
		*/

        //	show_voice_accept();.

        //	Socket s = playvoice.getSocket();
        //	gainvoice = new Gain_Voice(s); // 初始化一个对象
        playvoice = new Play_Voice();
        playvoice.start();

        gainvoice = new Gain_Voice(Ip);
        //	gainvoice.run(); // 获取音频设备的数据
        gainvoice.start();

        this.backgroud = "voiceing";
        bt_voice_accept.setVisible(false);
        bt_voice_deny.setVisible(false);
        bt_voice_cancel.setBounds(497, 296, 62, 27);
        bt_voice_cancel.setVisible(true);
        this.repaint();

    }

    public String getLocalIP() {
        InetAddress localAddr = null;
        try {
            localAddr = InetAddress.getLocalHost();
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return localAddr.getHostAddress();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == cb_font) {
            send_font = "";
            send_font = cb_font.getSelectedItem().toString();
        } else {

            send_font_size = cb_size.getSelectedIndex() + 14;
        }
        String str1 = tp_send_message.getText().trim();
        if (str1.length() == 0) {
            str1 = " ";
        }
        tp_send_message.setDocs(str1, send_color, send_font, b_bold, b_Italic,
                b_Underline, send_font_size);

    }

    public void sendMessage() {
        Date time = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        String timeInfo = format.format(time);
        String str1 = user.getNickname() + "  " + timeInfo + "\n";
        String str = tp_send_message.getText().trim();

        tp_message.setDocs(str1, Color.BLUE, "楷体", false, false, true, 14);

        if (str.compareTo("") != 0) {
            str += "\n";

            tp_message.setDocs(str, send_color, send_font, b_bold, b_Italic, b_Underline, send_font_size);

            info = tp_send_message.getText().trim();

            message.setFontColor(send_color);
            message.setFontSize(send_font_size);
            message.setFontType(send_font);
            message.setBold(b_bold);
            message.setItatic(b_Italic);
            message.setUnderline(b_Underline);
            message.setInfo(info);
            message.setInfoType("TXT");
            // byte buffer[] = ctsT.ObjectToByte(message);
            // ctsT.sendData(buffer);
            ctsT.sendData(message);
            ctsT.sendData(message);


            // ///气泡消息
            if (sSend.equals("1") == true) {
                sSend = "2";
            } else {
                sSend = "1";
            }

            // /气泡消息
            IMMessage imMsg = new IMMessage();
            imMsg.setSender(sSend);
            imMsg.setTime(timeInfo);
            imMsg.setMsg(str);
            mModel.addRow(imMsg);

        }

        if (send_Image != null) {
            //JOptionPane.showMessageDialog(null, "Length");
            //tp_message.setDocs(str1, Color.BLUE, "楷体", false, false, true, 14);

            tp_message.insertImage(send_Image);

            image_number = 0;
            tp_message.setDocs("\n", Color.BLUE, "楷体", false, false, true, 14);

            //TODO:
            String biaoqin = image_ep_number;

            message.setInfoType("Icon");
            message.setBiaoqin(biaoqin);

            ctsT.sendData(message);
            ctsT.sendData(message);
            send_Image = null;
        }
        if (cut_image != null) {

            tp_message.insertImage(cut_image);
            tp_message.setDocs("\n", Color.BLUE, "楷体", false, false, true, 14);
            message.setInfoType("CutImage");
            message.setCutImage(cut_image);

            ctsT.sendData(message);
            ctsT.sendData(message);
            cut_image = null;
        }

        /*

         */

        image_number = 0;
        i_flag = false;
        tp_send_message.setText(" ");


        int height = 10;
        Point p = new Point();
        p.setLocation(0, tp_send_message.getHeight() + tp_message.getHeight());
        spanel1.getViewport().setViewPosition(p);

    }

    public ImageIcon sendImage() {

        JFileChooser chooser = new JFileChooser();
        ImageIcon image = null;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("图片文件",
                "jpg", "gif", "png", "jpeg");
        chooser.setFileFilter(filter);
        int option = chooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try {
                image = new ImageIcon(file.toURI().toURL());

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }
        return image;

    }

    public void sendFile() {

    }

    public void startShake() {
        File file = new File("Image/shake.wav");
        AudioClip clip;
        try {
            clip = Applet.newAudioClip(file.toURL());
            clip.play();
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
        }

        int R = 10;// 画圆半径
        double posx = chatfrm.getLocation().x;
        double posy = chatfrm.getLocation().y;
        int n = 50;
        double alpha = 2 * Math.PI / 10;
        double theta = 0;
        while (n > 0) {
            n--;
            theta = theta - alpha;
            double x = posx + Math.cos(theta) * R;
            double y = posy + Math.sin(theta) * R;

            chatfrm.setLocation((int) x, (int) y);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        chatfrm.setLocation((int) posx, (int) posy);
        // shakeTimer.start();//振动开始
    }

    public void getRecord(Hashtable record) {
        int size = record.size();
        messageloggingpanel.tp_messagelogging.removeAll();
        Enumeration it = record.elements();
        for (int i = 0; i < size; i++) {
            Message message = (Message) it.nextElement();
            String str1 = message.getSendQq() + "  "
                    + message.getDate().toString() + "\n";
            messageloggingpanel.tp_messagelogging.setDocs(str1, Color.BLUE,
                    "楷体", false, false, true, 14);
            // tp_message.setDocs(str1,Color.BLUE,"楷体",false,false,true,14);
            String str = message.getInfo() + "\n";
            Color col = message.getFontColor();
            String font = message.getFontType();
            boolean bold = message.getIsBold();
            boolean Italic = message.getIsItatic();
            boolean Underline = message.getIsUnderline();
            int fontSize = message.getFontSize();
            messageloggingpanel.tp_messagelogging.setDocs(str, col, font, bold,
                    Italic, Underline, fontSize);
        }
    }

    public void stopShanke() {

        chatfrm.setLocation(start);
        chatfrm.repaint();
    }

    public void setFontPanel() {
        // 气泡
        btn_qp = new MyButton();
        btn_wz = new MyButton();
        btn_qp.addActionListener(this);
        btn_wz.addActionListener(this);
        btn_qp.setBounds(2, 344, 58, 25);
        btn_wz.setBounds(58, 344, 60, 25);
        btn_qp.setVisible(false);
        btn_wz.setVisible(false);
        add(btn_qp);
        add(btn_wz);
        //

        font_panel = new JPanel();
        cb_font = new JComboBox();
        cb_size = new JComboBox();
        bt_bold = new MyButton(); // 粗体
        bt_Italic = new MyButton();// 斜体
        bt_Underline = new MyButton();// 下划线
        bt_color = new MyButton(); // 颜色

        bt_bold.setToolTipText("cuti");//粗体
        bt_Italic.setToolTipText("xieti");//斜体
        bt_Underline.setToolTipText("xiahuaxian");//下划线
        bt_color.setToolTipText("yanse");//颜色

        cb_font.addItemListener(this);
        cb_size.addItemListener(this);
        bt_bold.addActionListener(this);
        bt_Italic.addActionListener(this);
        bt_Underline.addActionListener(this);
        bt_color.addActionListener(this);

        cb_font.addItem("楷体");
        cb_font.addItem("宋体");
        cb_font.addItem("黑体");
        cb_font.addItem("华文隶书");
        cb_font.addItemListener(this);
        for (int i = 14; i < 36; i++) {
            String s = "" + i;
            cb_size.addItem(s);
        }
        cb_size.setSelectedItem(send_font_size);
        font_panel.setBounds(235, 345, 208, 25);
        font_panel.setLayout(null);

        cb_font.setOpaque(false);
        cb_size.setOpaque(false);
        bt_color.setContentAreaFilled(false);

        cb_font.setBounds(0, 0, 75, 25);
        cb_size.setBounds(78, 0, 40, 25);
        bt_bold.setBounds(118, 0, 22, 25);
        icon = image.LoadImageIcon("bold");
        bt_bold.setIcon(icon);
        bt_Italic.setBounds(140, 0, 22, 25);
        bt_Underline.setBounds(162, 0, 22, 25);
        bt_color.setBounds(184, 0, 23, 25);

        font_panel.add(cb_font);
        font_panel.add(cb_size);
        font_panel.add(bt_bold);
        font_panel.add(bt_Italic);
        font_panel.add(bt_Underline);
        font_panel.add(bt_color);

        font_panel.setOpaque(false);
        font_panel.setVisible(false);
        add(font_panel);

    }

    public void setMessage() {// 设置显示当前会话面板
        tp_message.setOpaque(false); // 透明
        tp_message.setEditable(false); // 不可编辑
        // tp_message.setLineWrap(true); //自动换行

        tp_message.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent mouseEvent) {
                tp_message.setCursor(new Cursor(Cursor.TEXT_CURSOR)); // 鼠标进入Text区后变为文本输入指针
            }

            public void mouseExited(MouseEvent mouseEvent) {
                tp_message.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 鼠标离开Text区后恢复默认形态
            }
        });

        spanel1 = new JScrollPane(tp_message);
        spanel1.setOpaque(false); // 透明
        spanel1.getViewport().setOpaque(false);// 透明
        spanel1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        spanel1.setVisible(true);
        spanel1.setBounds(0, 83, 447, 290);

        Bt_Qipao();
        spanel1.setViewportView(tp_message);
        add(spanel1);
        jsbar = spanel1.getHorizontalScrollBar();
        if (jsbar == null) {
            jsbar.setValue(jsbar.getHeight());
        }
    }

    public void setSendMessage() {// 设置发送消息panel
        tp_send_message.setDocs("", send_color, send_font, b_bold, b_Italic,
                b_Underline, send_font_size);
        tp_send_message.setOpaque(false); // 透明
        tp_send_message.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (keyChar == KeyEvent.VK_ENTER) {// 回车发送消息
                    {
                        sendMessage();

                    }

                } else {

                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });

        tp_send_message.setForeground(new Color(1, 0, 1));
        tp_send_message.setCaretColor(new Color(0, 0, 1));
        spanel2 = new JScrollPane(tp_send_message);
        spanel2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        spanel2.setOpaque(false); // 透明
        spanel2.getViewport().setOpaque(false);// 透明
        spanel2.setVisible(true);
        spanel2.setBounds(0, 402, 447, 70);
        add(spanel2);

    }

    public void insertSendPic(ImageIcon imgIc) {// 插入发送图片
        send_Image = imgIc;
        tp_send_message.insertImage(imgIc); // 插入图片
        i_flag = false;
        System.out.print(imgIc.toString());
        // insert(new FontAttrib()); // 这样做可以换行
    }

    public JButton getPicBtn() {
        return bt_erpression;
    }

    public void Bt_Qipao() {// 气泡模式按钮响应
        // 气泡消息
        table.setTableHeader(null);
        table.setModel(mModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(0)
                .setCellRenderer(new BubbleRenderer());
        spanel1.setViewportView(table);
        table.setOpaque(false);
        table.setShowHorizontalLines(false);
        // 气泡消息
    }

    public void insertCutImage(ImageIcon im) {// 插入截图
        cut_image = new ImageIcon();
        cut_image = im;
        tp_send_message.insertImage(cut_image);
    }

    public void insertUpCutImage(ImageIcon im, String friend) {
        Date time = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        String timeInfo = format.format(time);
        String str1 = friend + "  " + timeInfo + "\n";
        tp_message.setDocs(str1, Color.BLUE, "楷体", false, false, true, 14);
        tp_message.insertImage(im);
        tp_message.setDocs("\n", Color.BLUE, "楷体", false, false, true, 14);

    }

    public void insertImage(String biaoqin) {
        //TODO:分割表情字符串，显示到会话面板
        LoadImages li = new LoadImages();
        ImageIcon ic = new ImageIcon(li.loadEpImage(biaoqin));
        tp_message.insertImage(ic);
    }

    public void insertImage1(String biaoqin) {
        Date time = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        String timeInfo = format.format(time);
        String str1 = friend.getNickname() + "  " + timeInfo + "\n";
        tp_message.setDocs(str1, Color.BLUE, "楷体", false, false, true, 14);
        LoadImages li = new LoadImages();
        ImageIcon ic = new ImageIcon(li.loadEpImage(biaoqin));
        tp_message.insertImage(ic);
    }

    public ChatPanel getChatPanel() {// 得到当前面板指针
        return this;
    }

    public void show_voice_accept() {// 显示语音接受面板
        this.backgroud = "bk5";
        chatfrm.setSize(599, 508);
        bt_voice.setEnabled(false);
        bt_video.setEnabled(false);
        chatfrm.btn_close.setBounds(573, 1, 25, 26);
        bt_voice_cancel.setVisible(false);
        bt_voice_accept.setVisible(true);
        bt_voice_deny.setVisible(true);
        bt_message_logging.setEnabled(false);
        this.repaint();
    }

    public void bt_Voice() {// 发送语音
        this.backgroud = "bk4";
        chatfrm.setSize(599, 508);
        bt_voice.setEnabled(false);
        bt_video.setEnabled(false);
        chatfrm.btn_close.setBounds(573, 1, 25, 26);
        bt_voice_cancel.setVisible(true);
        bt_message_logging.setEnabled(false);
        this.repaint();
    }

    public void bt_File_Send() {// 发送文件
        lb_file_name.setVisible(true);
        this.backgroud = "file_send";
        chatfrm.setSize(730, 508);
        chatfrm.btn_close.setBounds(702, 1, 25, 26);
        bt_message_logging.setEnabled(false);
        bt_file_cancel.setVisible(true);
        lb_file_name.setText("wenjianming");
        lb_file_name.setVisible(true);
        this.repaint();
    }

    public void bt_File_Accept() {// 接收文件
        lb_file_name.setVisible(true);
        this.backgroud = "file_accept";
        chatfrm.setSize(730, 508);
        chatfrm.btn_close.setBounds(702, 1, 25, 26);
        bt_message_logging.setEnabled(false);
        bt_file_accept.setVisible(true);
        bt_file_save.setVisible(true);
        bt_file_deny.setVisible(true);
        lb_file_name.setText("wenjianming");
        lb_file_name.setVisible(true);
        this.repaint();
    }

    public void closeCtst() {
        this.ctsT.closeSocket();
    }

    /**
     * 该方法用来获得服务器属性文件中的IP、PORT
     */
    private void getPropertiesInfo() {
        Properties prop = new Properties();
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ServerInfo.properties");
        try {
            //获得相应的键值对
            prop.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //根据相应的键获得对应的值

        try {
            receivePort = ReceivePort.getPort();
            System.out.println("pp" + receivePort);
            //	JOptionPane.showMessageDialog(null, "person:"+receivePort);
            Port.hash.put(friend.getQq(), receivePort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //sendPORT = Integer.parseInt(prop.getProperty("sendudp.port"));

    }

    public static DatagramSocket getRandomPort() throws SocketException {
        DatagramSocket s = new DatagramSocket(0);
        return s;
    }

    public DatagramSocket getRangePort(int[] ports) throws IOException {
        for (int port : ports) {
            try {
                return new DatagramSocket(port);
            } catch (IOException ex) {
                continue; // try next port
            }
        }

        // if the program gets here, no port in the range was found
        throw new IOException("no free port found");
    }

    public void set_ep_number(int number) {
        image_ep_number = "" + number;
        System.out.println("表情：" + image_ep_number);
    }

    public void voice_cancel() {

        //	gainvoice.stop();
        //	playvoice.stop();
        this.backgroud = "bk";
        chatfrm.setSize(586, 508);
        bt_voice.setEnabled(true);
        bt_video.setEnabled(true);
        chatfrm.btn_close.setBounds(558, 1, 28, 26);
        bt_voice_cancel.setVisible(false);
        bt_message_logging.setEnabled(true);
        this.repaint();
    }
}
