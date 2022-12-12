package com.client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.client.business.uiManager.ChatPanel;
import com.client.chat.PicsJWindow;
import com.client.data.LoadImages;
import com.common.UserInfoBean;
import com.tools.ClientToServerThread;

public class JChatFrm extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    public ChatPanel chatpanel;
    ClientToServerThread ctsT = null;

    public MyButton btn_close;


    public PicsJWindow picWindow;

    public JChatFrm(UserInfoBean user, UserInfoBean friend) {
        setTitle("����Ự");
        setBounds(350, 150, 586, 508);//540,517
        setResizable(false);
        setUndecorated(true);
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();//�����Ļ��С
        setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);//����������

        chatpanel = new ChatPanel(this, ctsT, user, friend);
        add(chatpanel);
        setVisible(false);
        LoadImages loadimage = new LoadImages();

        Image image = loadimage.loadImage("icon");
        this.setIconImage(image);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        picWindow = new PicsJWindow(this);
        picWindow.setVisible(false);
        /**
         * ����رհ�ť�¼�
         */
        {//�رմ���
            btn_close = new MyButton();
            btn_close.setBounds(558, 1, 28, 26);
            btn_close.setContentAreaFilled(false);
            btn_close.setToolTipText("�ر�");
            chatpanel.add(btn_close);
            btn_close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    {
                        if (JOptionPane.showConfirmDialog(null, "<html><font size=3>ȷ���˳���</html>", "ϵͳ��ʾ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {

                            chatpanel.closeCtst();
                            closeWindow();
                        } else {
                            return;
                        }
                    }
                }

            }
            );//����¼�����

        }
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               if (JOptionPane.showConfirmDialog(null, "<html><font size=3>ȷ���˳���</html>", "ϵͳ��ʾ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {

                   chatpanel.closeCtst();
                   closeWindow();
               } else {
                   return;
               }
           }
       });
        chatpanel.addMouseMotionListener(new MouseAdapter() {
            private Point draggingAnchor = null;

            @Override
            public void mouseMoved(MouseEvent e) {
                draggingAnchor = new Point(e.getX() + chatpanel.getX(), e.getY() + chatpanel.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getLocationOnScreen().x - draggingAnchor.x, e.getLocationOnScreen().y - draggingAnchor.y);
            }
        });
    }


    public void closeWindow() {
        this.dispose();

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String sendIP = "127.0.0.1";
        String receiveIP = "127.0.0.1";
        UserInfoBean user = new UserInfoBean();
        UserInfoBean friend = new UserInfoBean();

        user.setIP(sendIP);
        user.setQq(10003);
        user.setNickname("�������ѽ");
        user.setSign("��־���ʤ��!");
        friend.setIP(receiveIP);
        friend.setQq(10005);
        friend.setNickname("��СС����ѽ");
        JChatFrm jChatFrm = new JChatFrm(user, friend);
        jChatFrm.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void Bt_erpression_true() {
        picWindow.setVisible(true);
    }

    public void Bt_erpression_false() {
        picWindow.setVisible(false);
    }

}
