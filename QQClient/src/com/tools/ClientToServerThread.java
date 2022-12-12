package com.tools;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.SimpleAttributeSet;

import com.client.business.uiManager.ChatPanel;
import com.client.business.uiManager.MyTextPane;
import com.client.transform.ClientTest;
import com.client.view.JChatFrm;
import com.common.FriendsInfoBean;
import com.common.Message;
import com.common.Port;
import com.common.ReceivePort;
import com.common.User;
import com.common.UserInfoBean;

/**
 * �ͻ��������˵�   ����   �߳�
 **/
public class ClientToServerThread extends Thread {

    private String serverIP = "127.0.0.1";
    private int serverPORT = 8880;
    private int receivePORT = 6666;//���ն˿�
    //����������Ϣ�����ݱ��׽���
    private DatagramSocket sendSocket = null;
    //����������Ϣ�����ݰ�
    private DatagramPacket sendPacket = null;
    //����������Ϣ�����ݱ��׽���
    private DatagramSocket receiveSocket = null;
    //����������Ϣ�����ݱ�
    private DatagramPacket receivePacket = null;
    //�շ����ݵĶ˿�
    private int myPort = 0;
    //��������������IP��ַ
    private String friendIP = null;
    private int friendPort = 0;
    private int cont = 0;
    //��������Ĵ�С
    public static final int BUFFER_SIZE = 81920;

    private boolean isStop = false;
    private boolean isCommon = false;

    private byte inBuf[] = null; //�������ݵĻ�������
    private byte outBuf[] = null; //�������ݵĻ�������

    private int friendqq = 0;

    MyTextPane tp_message;
    ChatPanel chatPanel;

    // ���캯��
    public ClientToServerThread(MyTextPane tp_message, ChatPanel chatPanel, int friendqq, int receivePort) {
        this.tp_message = tp_message;
        this.chatPanel = chatPanel;
        this.friendqq = friendqq;
        this.receivePORT = receivePort;
        getPropertiesInfo();

    }

    public ClientToServerThread(boolean isCommon) {
        this.isCommon = isCommon;
        getPropertiesInfo();
    }

    @Override
    public void run() {
        String receiveInfo = "";
        try {
            inBuf = new byte[BUFFER_SIZE];
            receivePacket = new DatagramPacket(inBuf, inBuf.length);
            //JOptionPane.showMessageDialog(null, "dd:"+receivePORT);
            this.sleep(3000);
            receiveSocket = new DatagramSocket(receivePORT);//����һ�����ݱ��׽��֣�������󶨵����������ϵ�ָ���˿ڡ��׽��ֽ����󶨵�ͨ�����ַ�������ں�ѡ���һ��IP��ַ��
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            if (receiveSocket == null) {
                break;
            } else {
                try {
                    receiveSocket.receive(receivePacket);
                    if (isStop) {
                        receiveSocket.close();
                        return;
                    }
                    Message message = (Message) ByteToObject(receivePacket.getData());
                    UserInfoBean userInfo = null;
                    UserInfoBean friendInfo = null;
                    //	String message1 = new String(receivePacket.getData(),0,receivePacket.getLength());
                    //	tp_message.insert("�յ�����:"+message+"\n", new SimpleAttributeSet());
                    if (message.getInfoType().equals("Record")) {
                        //JOptionPane.showMessageDialog(null, "receive");
                        receiveSocket.receive(receivePacket);
                        Hashtable record = (Hashtable) ByteToObject(receivePacket.getData());
                        //	getRecord(record);
                        chatPanel.getRecord(record);

                    } else if (message.getInfoType().equals("TXT")) {
                        String message1 = message.getInfo();

                        Date time = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
                        String timeInfo = format.format(time);

                        String str1 = message.getSendNickname() + "  " + timeInfo + "\n";
                        tp_message.setDocs(str1, Color.BLUE, "����", false,
                                false, true, 14);
                        tp_message.setDocs("" + message1 + "\n",
                                        message.getFontColor(),
                                        message.getFontType(),
                                        message.getIsBold(),
                                        message.getIsItatic(),
                                        message.getIsUnderline(),
                                        message.getFontSize());
                    } else if (message.getInfoType().equals("Shake")) {
                        chatPanel.startShake();
                    } else if (message.getInfoType().equals("Icon")) {
                        String biaoqin = message.getBiaoqin();
                        chatPanel.insertImage1(biaoqin);
                    } else if (message.getInfoType().equals("CutImage")) {

                        ImageIcon im = message.getCutImage();
                        String friend = message.getSendNickname();
                        chatPanel.insertUpCutImage(im, friend);

                    } else if (message.getInfoType().equals("Voice")) {
                        if (cont != 0) {
                            chatPanel.show_voice_accept();
                            JOptionPane.showMessageDialog(null, "�Ƿ���������");
                        }
                        String sendip = message.getSendIP();
                        //		JOptionPane.showMessageDialog(null,"fa:"+sendip);
                        if (cont != 0) {
                            JOptionPane.showMessageDialog(null, "�����������߳�");
                            //chatPanel.startYuyin(sendip);
                            chatPanel.acceptYuyin(sendip);
                        }
                        //
                        String ip = getLocalIP();
                        //message.setReceiveQq(receiveQq);
                        message.setReceiveIP(ip);
                        message.setInfoType("Voice1");
                        cont++;
                        sendData(message);

                        if (cont % 2 == 0) {
                            sendData(message);
                            sendData(message);
                        } else {
                            sendData(message);
                        }

                    } else if (message.getInfoType().equals("Voice1")) {
                        String ip = message.getReceiveIP();
                        //	JOptionPane.showMessageDialog(null,"�Է���"+ip);
                        JOptionPane.showMessageDialog(null, "�����������߳�");
                        chatPanel.startYuyin(ip);
                    } else if (message.getInfoType().equals("Notice")) {

                        userInfo = message.getUserBean();
                        friendInfo = message.getFriendBean();

                        JChatFrm jcf = new JChatFrm(userInfo, friendInfo);
                        jcf.setVisible(false);
                        Message msg = new Message();
                        msg.setReceivePort(Port.hash.get(friendInfo.getQq()));
                        //this.sleep(500);
                        sendData(msg);
                        //sendData(msg);

                        JOptionPane.showMessageDialog(null, "����Ϣ��");
                        jcf.setVisible(true);
                    } else if (message.getInfoType().equals("File")) {
                        JOptionPane.showMessageDialog(null, "�Ƿ�����ļ�?");
                        String ip = message.getSendIP();
                        new ClientTest(ip);
                    } else if (message.getInfoType().equals("CloseVoice")) {
                        chatPanel.voice_cancel();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }
            }
        }
    }

    /**
     * �÷���������÷����������ļ��е�IP��PORT
     */
    private void getPropertiesInfo() {
        Properties prop = new Properties();
        InputStream inStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("ServerInfo.properties");
        try {
            //�����Ӧ�ļ�ֵ��
            prop.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //������Ӧ�ļ���ö�Ӧ��ֵ
        serverIP = prop.getProperty("serverip");
        serverPORT = Integer.parseInt(prop.getProperty("serverudp.port"));

        if (isCommon) {
            receivePORT = ReceivePort.getReceivePort();
            //	JOptionPane.showMessageDialog(null, "comm:"+receivePORT);
        } else {

            Port p = new Port();
            receivePORT = p.hash.get(friendqq);
            //	  JOptionPane.showMessageDialog(null, "person:"+receivePORT);
        }

        //	receivePORT = Integer.parseInt(prop.getProperty("receiveudp.port"));


    }

    public int getReceivePort() {
        return receivePORT;
    }

    public void sendData(byte buffer[]) {
        try {
            InetAddress address = InetAddress.getByName(serverIP);
            // outBuf = new byte[BUFFER_SIZE];
            sendPacket = new DatagramPacket(buffer, buffer.length, address,
                    serverPORT);
            sendSocket = new DatagramSocket();
            sendSocket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void sendData(Object obj) {
        byte buffer[] = ObjectToByte(obj);
        sendData(buffer);
    }

    public void closeSocket() {
        isStop = true;

    }

    private Object ByteToObject(byte[] bytes) {
        Object obj = null;
        try {
            // bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();

            bi.close();
            oi.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public byte[] ObjectToByte(Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (bytes);

    }

    public void getRecord(Hashtable record) {
        int size = record.size();

        Enumeration it = record.elements();
        for (int i = 0; i < size; i++) {
            Message message = (Message) it.nextElement();
            String str1 = message.getSendQq() + "  " + message.getDate().toString() + "\n";
            //messageloggingpanel.tp_messagelogging.setDocs(str1,Color.BLUE,"����",false,false,true,14);
            //tp_message.setDocs(str1,Color.BLUE,"����",false,false,true,14);
            String str = message.getInfo() + "\n";
            Color col = message.getFontColor();
            String font = message.getFontType();
            boolean bold = message.getIsBold();
            boolean Italic = message.getIsItatic();
            boolean Underline = message.getIsUnderline();
            int fontSize = message.getFontSize();

            JOptionPane.showMessageDialog(null, str1 + str);
            //messageloggingpanel.tp_messagelogging.setDocs(str, col, font, bold, Italic, Underline, fontSize);
            //tp_message.setDocs(str, col, font, bold, Italic, Underline, fontSize);
        }
    }

    public String getLocalIP() {
        InetAddress localAddr = null;
        try {
            localAddr = InetAddress.getLocalHost();
        } catch (UnknownHostException e1) {

            e1.printStackTrace();
        }
        return localAddr.getHostAddress();
    }

}
