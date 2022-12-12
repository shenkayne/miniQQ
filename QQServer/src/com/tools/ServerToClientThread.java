/**
 *
 */
package com.tools;

import com.common.*;
import com.server.business.uiManager.MyTextPane;
import com.server.data.Dml;
import com.server.data.Query;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 * ���ӿͻ��˵������߳�
 */
public class ServerToClientThread extends Thread implements Serializable {

    private String sendIP = "127.0.0.1";
    private int sendPORT = 6666;
    private int receivePORT = 8888;

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

    private boolean isClose = false;
    private boolean isStopThread = false;


    //��������Ĵ�С
    public static final int BUFFER_SIZE = 81920;

    private byte inBuf[] = null; //�������ݵĻ�������
    private byte outBuf[] = null; //�������ݵĻ�������

    MyTextPane tp_message;

    private boolean isStop = false;

    // ���캯����ʼ�������������ļ��е�IP��PORT
    public ServerToClientThread() {
        getPropertiesInfo();
    }

    public void run() {
        String receiveInfo = "";
        try {
            inBuf = new byte[BUFFER_SIZE];
            receivePacket = new DatagramPacket(inBuf, inBuf.length);//�������ܳ���Ϊlength��buf����(�����ݴ����ֽ�����buf��)
            receiveSocket = new DatagramSocket(receivePORT);//������8888�˿ڵĽ������ݵ��׽���
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (true) {
            if (receiveSocket == null) {
                break;
            } else {
                try {
					//������׽��ֽ���һ�����ݱ��������˷�������ʱ��DatagramPacket�Ļ������������յ���������䡣
					//�����ݰ����������ͷ���IP��ַ�ͷ��ͷ������ϵĶ˿ںš�
                    receiveSocket.receive(receivePacket);
                    if (isStop) {//�رմ˴���������
                        receiveSocket.close();
                        return;
                    }
					/*
					String message = new String(receivePacket.getData(),0,receivePacket.getLength());
					int sendIP_index = message.indexOf("*");
					int receiveIP_index = message.indexOf("/");
					int sendPort_index = message.indexOf("#");
					
					sendIP = message.substring(0,sendIP_index);
					String receiveIP = message.substring(sendIP_index+1,receiveIP_index);
					sendPORT = Integer.parseInt(message.substring(receiveIP_index+1,sendPort_index));
					String info = message.substring(sendPort_index+1);
					byte buffer[] = info.getBytes();
					
					*/
                    receiveSocket.receive(receivePacket);

                    Message message = (Message) ByteToObject(receivePacket.getData());//�����ݰ��е�����ת����Message����

                    int mixqq = message.getReceiveQq() + message.getSendQq();

                    //LinkPort.hash.put(mixqq,two);

                    if (message.getInfoType().equals("Record")) {
                        //		sendIP = message.getReceiveIP();
                        //		sendPORT = Port.port2.get(message.getSendQq());

                        sendIP = Port.port3.get(message.getSendQq());
                        //      JOptionPane.showMessageDialog(null, "p1:"+sendIP);
                        sendPORT = message.getReceivePort();
                        //	    JOptionPane.showMessageDialog(null, "p2"+sendPORT);
                        //  JOptionPane.showMessageDialog(null, "p"+sendPORT);
                        //JOptionPane.showMessageDialog(null, ""+sendPORT);
                        Query query = new Query();
                        Hashtable record = query.getRecord(message.getSendQq(), message.getReceiveQq());
                        sendData(message);//����Ϣ���͸��ͻ���

                        //	getRecord(record);
                        sendData(record);//����־��¼���͸��ͻ���
                        //	JOptionPane.showMessageDialog(null, "server");
                    } else if (message.getInfoType().equals("TXT")) {
                        common(mixqq);
                        if (LinkPort.isfirst.get(mixqq)) {//���������QQ��һ�β�����ϵ
                            sendIP = Port.port3.get(message.getReceiveQq());
                            //	sendIP = message.getSendIP();
                            sendPORT = Port.port2.get(message.getReceiveQq());
                            //JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
                            message.setInfoType("Notice");

                            UserInfoBean user = new UserInfoBean();
                            UserInfoBean friend = new UserInfoBean();

                            user.setQq(message.getReceiveQq());
                            user.setNickname(message.getReceiveNickname());
                            user.setPhotoID(message.getreceivePhotoID());
                            user.setSign(message.getreceiveSign());
                            message.setUserBean(user);

                            friend.setQq(message.getSendQq());
                            friend.setNickname(message.getSendNickname());
                            friend.setPhotoID(message.getsendPhotoID());
                            friend.setSign(message.getsendSign());
                            message.setFriendBean(friend);

                            sendData(message);//���û���Ϣ��������Ϣ���͸��ͻ���

                            receiveSocket.receive(receivePacket);

                            Message msg1 = (Message) ByteToObject(receivePacket.getData());

                            sendPORT = msg1.getReceivePort();

                            TwoPortMsg two = new TwoPortMsg();
                            two.receiveQQ = message.getReceiveQq();
                            two.receiveIP = message.getReceiveIP();
                            two.receivePort = message.getReceivePort();

                            two.sendQQ = message.getSendQq();
                            sendIP = Port.port3.get(message.getReceiveQq());
                            two.sendIP = sendIP;
                            two.sendPort = sendPORT;
                            LinkPort.hash.put(mixqq, two);

                        } else {
                            int mix = message.getSendQq() + message.getReceiveQq();
                            TwoPortMsg two = new TwoPortMsg();
                            two = LinkPort.hash.get(mix);
                            if (message.getReceiveQq() == two.receiveQQ) {
                                sendIP = two.sendIP;
                                sendPORT = two.sendPort;

                            } else {

                                sendIP = two.receiveIP;
                                sendPORT = two.receivePort;
                            }
                            //	JOptionPane.showMessageDialog(null,sendIP);
                        }
                        //	sendIP = message.getSendIP();
                        //	sendPORT = message.getSendPort();

                        Dml dml = new Dml();
                        dml.insert(message);

                        sendData(message);
                    } else if (message.getInfoType().equals("Shake")) {
                        //	sendIP = Port.port3.get(message.getReceiveQq());
                        //	sendPORT = Port.port2.get(message.getReceiveQq());
                        common(mixqq);

                        if (LinkPort.isfirst.get(mixqq)) {
                            sendIP = Port.port3.get(message.getReceiveQq());
                            //	sendIP = message.getSendIP();
                            sendPORT = Port.port2.get(message.getReceiveQq());
                            //JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
                            message.setInfoType("Notice");
                            UserInfoBean user = new UserInfoBean();
                            UserInfoBean friend = new UserInfoBean();

                            user.setQq(message.getReceiveQq());
                            user.setNickname(message.getReceiveNickname());
                            user.setPhotoID(message.getreceivePhotoID());
                            user.setSign(message.getreceiveSign());
                            message.setUserBean(user);

                            friend.setQq(message.getSendQq());
                            friend.setNickname(message.getSendNickname());
                            friend.setPhotoID(message.getsendPhotoID());
                            friend.setSign(message.getsendSign());
                            message.setFriendBean(friend);

                            sendData(message);

                            receiveSocket.receive(receivePacket);

                            Message msg1 = (Message) ByteToObject(receivePacket.getData());

                            sendPORT = msg1.getReceivePort();

                            TwoPortMsg two = new TwoPortMsg();
                            two.receiveQQ = message.getReceiveQq();
                            two.receiveIP = message.getReceiveIP();
                            two.receivePort = message.getReceivePort();

                            two.sendQQ = message.getSendQq();
                            sendIP = Port.port3.get(message.getReceiveQq());
                            two.sendIP = sendIP;
                            two.sendPort = sendPORT;
                            LinkPort.hash.put(mixqq, two);

                            String rqq = two.receiveQQ + "";
                            String rIP = two.receiveIP;

                            String sqq = two.sendQQ + "";
                            String sIP = two.sendIP;

                            //	JOptionPane.showMessageDialog(null,rqq+rIP);
                            //	JOptionPane.showMessageDialog(null,sqq+sIP);
                        } else {
                            int mix = message.getSendQq() + message.getReceiveQq();
                            TwoPortMsg two = new TwoPortMsg();
                            two = LinkPort.hash.get(mix);
                            if (message.getReceiveQq() == two.receiveQQ) {
                                sendIP = two.sendIP;
                                sendPORT = two.sendPort;

                            } else {

                                sendIP = two.receiveIP;
                                sendPORT = two.receivePort;
                            }
                            //JOptionPane.showMessageDialog(null,sendIP);
                        }
                        //this.sleep(50);
                        sendData(message);
                    } else if (message.getInfoType().equals("Icon")) {
                        common(mixqq);

                        if (LinkPort.isfirst.get(mixqq)) {
                            sendIP = Port.port3.get(message.getReceiveQq());
                            //	sendIP = message.getSendIP();
                            sendPORT = Port.port2.get(message.getReceiveQq());
                            //JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
                            message.setInfoType("Notice");
                            UserInfoBean user = new UserInfoBean();
                            UserInfoBean friend = new UserInfoBean();

                            user.setQq(message.getReceiveQq());
                            user.setNickname(message.getReceiveNickname());
                            user.setPhotoID(message.getreceivePhotoID());
                            user.setSign(message.getreceiveSign());
                            message.setUserBean(user);

                            friend.setQq(message.getSendQq());
                            friend.setNickname(message.getSendNickname());
                            friend.setPhotoID(message.getsendPhotoID());
                            friend.setSign(message.getsendSign());
                            message.setFriendBean(friend);

                            sendData(message);

                            receiveSocket.receive(receivePacket);

                            Message msg1 = (Message) ByteToObject(receivePacket.getData());

                            sendPORT = msg1.getReceivePort();

                            TwoPortMsg two = new TwoPortMsg();
                            two.receiveQQ = message.getReceiveQq();
                            two.receiveIP = message.getReceiveIP();
                            two.receivePort = message.getReceivePort();

                            two.sendQQ = message.getSendQq();
                            sendIP = Port.port3.get(message.getReceiveQq());
                            two.sendIP = sendIP;
                            two.sendPort = sendPORT;
                            LinkPort.hash.put(mixqq, two);

                        } else {
                            int mix = message.getSendQq() + message.getReceiveQq();
                            TwoPortMsg two = new TwoPortMsg();
                            two = LinkPort.hash.get(mix);
                            if (message.getReceiveQq() == two.receiveQQ) {
                                sendIP = two.sendIP;
                                sendPORT = two.sendPort;

                            } else {

                                sendIP = two.receiveIP;
                                sendPORT = two.receivePort;
                            }
                            //	JOptionPane.showMessageDialog(null,sendIP);
                        }

                        sendData(message);
                    } else if (message.getInfoType().equals("CutImage")) {
                        common(mixqq);
                        if (LinkPort.isfirst.get(mixqq)) {
                            sendIP = Port.port3.get(message.getReceiveQq());
                            //	sendIP = message.getSendIP();
                            sendPORT = Port.port2.get(message.getReceiveQq());
                            //JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
                            message.setInfoType("Notice");
                            UserInfoBean user = new UserInfoBean();
                            UserInfoBean friend = new UserInfoBean();

                            user.setQq(message.getReceiveQq());
                            user.setNickname(message.getReceiveNickname());
                            user.setPhotoID(message.getreceivePhotoID());
                            user.setSign(message.getreceiveSign());
                            message.setUserBean(user);

                            friend.setQq(message.getSendQq());
                            friend.setNickname(message.getSendNickname());
                            friend.setPhotoID(message.getsendPhotoID());
                            friend.setSign(message.getsendSign());
                            message.setFriendBean(friend);

                            sendData(message);

                            receiveSocket.receive(receivePacket);

                            Message msg1 = (Message) ByteToObject(receivePacket.getData());

                            sendPORT = msg1.getReceivePort();

                            TwoPortMsg two = new TwoPortMsg();
                            two.receiveQQ = message.getReceiveQq();
                            two.receiveIP = message.getReceiveIP();
                            two.receivePort = message.getReceivePort();

                            sendIP = Port.port3.get(message.getReceiveQq());
                            two.sendQQ = message.getSendQq();
                            two.sendIP = sendIP;
                            two.sendPort = sendPORT;
                            LinkPort.hash.put(mixqq, two);

                        } else {
                            int mix = message.getSendQq() + message.getReceiveQq();
                            TwoPortMsg two = new TwoPortMsg();
                            two = LinkPort.hash.get(mix);
                            if (message.getReceiveQq() == two.receiveQQ) {
                                sendIP = two.sendIP;
                                sendPORT = two.sendPort;

                            } else {

                                sendIP = two.receiveIP;
                                sendPORT = two.receivePort;
                            }
                            //	JOptionPane.showMessageDialog(null,sendIP);
                        }
                        sendData(message);
                    } else if (message.getInfoType().equals("Voice")) {
                        common(mixqq);

                        if (LinkPort.isfirst.get(mixqq)) {
                            sendIP = Port.port3.get(message.getReceiveQq());
                            //	sendIP = message.getSendIP();
                            sendPORT = Port.port2.get(message.getReceiveQq());
                            //JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
                            message.setInfoType("Notice");
                            UserInfoBean user = new UserInfoBean();
                            UserInfoBean friend = new UserInfoBean();

                            user.setQq(message.getReceiveQq());
                            user.setNickname(message.getReceiveNickname());
                            user.setPhotoID(message.getreceivePhotoID());
                            user.setSign(message.getreceiveSign());
                            message.setUserBean(user);

                            friend.setQq(message.getSendQq());
                            friend.setNickname(message.getSendNickname());
                            friend.setPhotoID(message.getsendPhotoID());
                            friend.setSign(message.getsendSign());
                            message.setFriendBean(friend);

                            sendData(message);

                            receiveSocket.receive(receivePacket);

                            Message msg1 = (Message) ByteToObject(receivePacket.getData());

                            sendPORT = msg1.getReceivePort();

                            TwoPortMsg two = new TwoPortMsg();
                            two.receiveQQ = message.getReceiveQq();
                            two.receiveIP = message.getReceiveIP();
                            two.receivePort = message.getReceivePort();

                            two.sendQQ = message.getSendQq();
                            sendIP = Port.port3.get(message.getReceiveQq());
                            two.sendIP = sendIP;
                            two.sendPort = sendPORT;
                            LinkPort.hash.put(mixqq, two);

                        } else {
                            int mix = message.getSendQq() + message.getReceiveQq();
                            TwoPortMsg two = new TwoPortMsg();
                            two = LinkPort.hash.get(mix);
                            if (message.getReceiveQq() == two.receiveQQ) {
                                sendIP = two.sendIP;
                                sendPORT = two.sendPort;
                            } else {
                                sendIP = two.receiveIP;
                                sendPORT = two.receivePort;
                            }
                        }

                        sendData(message);

                        receiveSocket.receive(receivePacket);

                        Message msg1 = (Message) ByteToObject(receivePacket.getData());

                    } else if (message.getInfoType().equals("Voice1")) {
                        common(mixqq);
                        if (LinkPort.isfirst.get(mixqq)) {
                            sendIP = Port.port3.get(message.getReceiveQq());
                            //	sendIP = message.getSendIP();
                            sendPORT = Port.port2.get(message.getReceiveQq());
                            //JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
                            message.setInfoType("Notice");
                            UserInfoBean user = new UserInfoBean();
                            UserInfoBean friend = new UserInfoBean();

                            user.setQq(message.getReceiveQq());
                            user.setNickname(message.getReceiveNickname());
                            user.setPhotoID(message.getreceivePhotoID());
                            user.setSign(message.getreceiveSign());
                            message.setUserBean(user);

                            friend.setQq(message.getSendQq());
                            friend.setNickname(message.getSendNickname());
                            friend.setPhotoID(message.getsendPhotoID());
                            friend.setSign(message.getsendSign());
                            message.setFriendBean(friend);

                            sendData(message);

                            receiveSocket.receive(receivePacket);

                            Message msg1 = (Message) ByteToObject(receivePacket.getData());

                            sendPORT = msg1.getReceivePort();

                            TwoPortMsg two = new TwoPortMsg();
                            two.receiveQQ = message.getReceiveQq();
                            two.receiveIP = message.getReceiveIP();
                            two.receivePort = message.getReceivePort();

                            two.sendQQ = message.getSendQq();
                            sendIP = Port.port3.get(message.getReceiveQq());
                            two.sendIP = sendIP;
                            two.sendPort = sendPORT;
                            LinkPort.hash.put(mixqq, two);

                        } else {
                            int mix = message.getSendQq() + message.getReceiveQq();
                            TwoPortMsg two = new TwoPortMsg();
                            two = LinkPort.hash.get(mix);
                            if (message.getReceiveQq() == two.receiveQQ) {

                                sendIP = two.receiveIP;
                                sendPORT = two.receivePort;
                            } else {

                                sendIP = two.sendIP;
                                sendPORT = two.sendPort;
                            }
                        }


                        //JOptionPane.showMessageDialog(null,"Server:");

                        sendData(message);
                    } else if (message.getInfoType().equals("File")) {
                        common(mixqq);


                        if (LinkPort.isfirst.get(mixqq)) {
                            sendIP = Port.port3.get(message.getReceiveQq());
                            //	sendIP = message.getSendIP();
                            sendPORT = Port.port2.get(message.getReceiveQq());
                            //JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
                            message.setInfoType("Notice");
                            UserInfoBean user = new UserInfoBean();
                            UserInfoBean friend = new UserInfoBean();

                            user.setQq(message.getReceiveQq());
                            user.setNickname(message.getReceiveNickname());
                            user.setPhotoID(message.getreceivePhotoID());
                            user.setSign(message.getreceiveSign());
                            message.setUserBean(user);

                            friend.setQq(message.getSendQq());
                            friend.setNickname(message.getSendNickname());
                            friend.setPhotoID(message.getsendPhotoID());
                            friend.setSign(message.getsendSign());
                            message.setFriendBean(friend);

                            sendData(message);

                            receiveSocket.receive(receivePacket);

                            Message msg1 = (Message) ByteToObject(receivePacket.getData());

                            sendPORT = msg1.getReceivePort();

                            TwoPortMsg two = new TwoPortMsg();
                            two.receiveQQ = message.getReceiveQq();
                            two.receiveIP = message.getReceiveIP();
                            two.receivePort = message.getReceivePort();

                            two.sendQQ = message.getSendQq();
                            sendIP = Port.port3.get(message.getReceiveQq());
                            two.sendIP = sendIP;
                            two.sendPort = sendPORT;
                            LinkPort.hash.put(mixqq, two);

                        } else {
                            int mix = message.getSendQq() + message.getReceiveQq();
                            TwoPortMsg two = new TwoPortMsg();
                            two = LinkPort.hash.get(mix);
                            if (message.getReceiveQq() == two.receiveQQ) {
                                sendIP = two.sendIP;
                                sendPORT = two.sendPort;

                            } else {
                                sendIP = two.receiveIP;
                                sendPORT = two.receivePort;

                            }
                        }


                        sendData(message);


                    } else if (message.getInfoType().equals("CloseVoice")) {
                        int mix = message.getSendQq() + message.getReceiveQq();
                        TwoPortMsg two = new TwoPortMsg();
                        two = LinkPort.hash.get(mix);
                        if (message.getReceiveQq() == two.receiveQQ) {
                            sendIP = two.sendIP;
                            sendPORT = two.sendPort;

                        } else {
                            sendIP = two.receiveIP;
                            sendPORT = two.receivePort;

                        }
                        sendData(message);
                    }


                    //	byte buffer[] = ObjectToByte(message);
                    //	sendData(buffer);

                    //	tp_message.setdo("�յ�����:"+message+"\n", new SimpleAttributeSet());
                    //tp_message.setDocs("�յ�����:"+message+"\n", Color.RED, "����", true, false, false, 18);
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }
            }
        }
    }

    /**
     * �������Ϣ��QQ�ͽ�����Ϣ��QQ��һ�β���
     * @param mixqq ����Ϣ��QQ�ͽ�����Ϣ��QQ��ֵ���
     */
    private void common(int mixqq) {

        //	TwoPortMsg two = new TwoPortMsg();
        int i = 0;
        while (i < LinkPort.count) {
            if (mixqq == LinkPort.mixstore[i]) {//������QQ�Ѿ���mixstore��i�������±���¼����ֱ�ӽ�����һ��
                break;
            }
            i++;
        }
        if (i < LinkPort.count) {
            LinkPort.isfirst.put(mixqq, false);//���û��QQ����isfirst
            //	JOptionPane.showMessageDialog(null, ""+mixqq+false);
        }
        if (i == LinkPort.count) {
            LinkPort.mixstore[i] = mixqq;
            LinkPort.count++;
            LinkPort.isfirst.put(mixqq, true);
        }
    }

    /**
     * �÷���������÷����������ļ��е�IP��PORT
     */
    private void getPropertiesInfo() {
        Properties prop = new Properties();
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ServerInfo.properties");
        try {
            //�����Ӧ�ļ�ֵ��
            prop.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //������Ӧ�ļ���ö�Ӧ��ֵ
        receivePORT = Integer.parseInt(prop.getProperty("serverudp.port"));

    }

    /**
     * �����ݷ��͸��ͻ���
     * @param buffer
     */
    public void sendData(byte buffer[]) {
        try {
            InetAddress address = InetAddress.getByName(sendIP);//��������������ȷ��������IP��ַ��
            //	outBuf = new byte[BUFFER_SIZE];
            sendPacket = new DatagramPacket(buffer, buffer.length, address, sendPORT);
            sendSocket = new DatagramSocket();
            sendSocket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���������͵�����ת�����ֽ����ͷ��͸��ͻ���
     * @param obj
     */
    public void sendData(Object obj) {
        byte buffer[] = ObjectToByte(obj);
        sendData(buffer);
    }

    /**
     * �ر���������
     */
    public void closeSocket() {
        isStop = true;
    }


    /**
     * ���ֽ�����ת���ɶ���
     * @param bytes
     * @return
     */
    private Object ByteToObject(byte[] bytes) {
        Object obj = null;
        try {
            //bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);//�ֽ�����������
            ObjectInputStream oi = new ObjectInputStream(bi);//����������
            obj = oi.readObject();

            bi.close();
            oi.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    /**
     * ��������ת���ֽ�����
     * @param obj
     * @return
     */
    public byte[] ObjectToByte(Object obj) {
        byte[] bytes = null;
        try {
            //object to bytearray
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
}
