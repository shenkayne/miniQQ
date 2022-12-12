package com.tools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Properties;

import com.client.view.JMainFrm;
import com.common.Port;
import com.common.ReceivePort;
import com.common.User;
import com.common.UserInfoBean;
import com.tools.ClientToServerThread;

import javax.swing.*;

/**
 * ʵ�������˵�ͨ�ţ���¼�ͶԺ��ѽ��й���ʱ���߳�����
 */
public class ClientToServer implements Runnable {

    //Socket����
    private Socket s;
    private DataInputStream in = null; //����������
    private DataOutputStream out = null; //���������

    private String IP = "";
    private int PORT = 0;
    private String QQNum;

    private String pwd;
    private boolean flag = false;
    private boolean isSuccessLogin = false;//�����ж��Ƿ�ɹ���¼

    private UserInfoBean friendInfo;
    private UserInfoBean userInfo;
    private String loginInfo;
    private boolean isRegister;
    JMainFrm mainFrm;

    public ClientToServer(boolean isRegister) {
        getPropertiesInfo();
        this.isRegister = isRegister;
    }

    public ClientToServer(JMainFrm mainFrm) {
        this.mainFrm = mainFrm;
        getPropertiesInfo();
    }

    public ClientToServer() {//ͨ�����캯����ȡ�����ļ��е�IP��PORT
        getPropertiesInfo();
    }

    public ClientToServer(String IP, int port) {
        this.IP = IP;
        this.PORT = port;
    }

    /**
     * �÷���������÷����������ļ��е�IP��PORT
     */
    private void getPropertiesInfo() {
        Properties prop = new Properties();

        //��ServerInfo.properties�е�������������ʽ������instream��
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ServerInfo.properties");
        try {
            //�����Ӧ�ļ�ֵ��
            prop.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //������Ӧ�ļ���ö�Ӧ��ֵ
        IP = prop.getProperty("serverip");//�õ�������ip
        PORT = Integer.parseInt(prop.getProperty("servertcp.port"));//�õ�tcp�˿�
    }


    /**
     *  ��½ʱ���͵�һ������
     */
    public boolean sendLoginInfoToServer(User u) throws IOException {
        try {
            s = new Socket(IP, PORT);
            out = new DataOutputStream(s.getOutputStream());//д���ݵ������
            QQNum = u.getUserId();//��ȡ��QQ��
            pwd=u.getPasswd();
            //�Զ����ڻ����ķ�ʽʹ���޸ĵ�UTF-8���뽫�ַ���д��ײ������

            out.writeUTF("login");                  //W0.�ͻ�������
            out.writeUTF(QQNum);                        //W1.�û�id
            out.writeUTF(pwd);                //W2.����

            ReceivePort receiveport = new ReceivePort();
            Port por = new Port();
            por.commonPort = receiveport.getReceivePort();//�ͻ����������˿�
            int p = receiveport.getReceivePort();

            out.writeUTF("" + p);                   //W3.�����˷��Ͷ˿���Ϣ

            InetAddress localAddr = null;
            try {
                localAddr = InetAddress.getLocalHost();//��ȡ������ַ
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
            String localIP = localAddr.getHostAddress();//���ı���ʽ����IP��ַ�ַ���
            IP = localIP;
            String ClientIP = IP;
            //String ClientIP = "127.0.0.1";

            out.writeUTF(ClientIP);                     //W4.��������ַд�뵽�����

            in = new DataInputStream(s.getInputStream());//�������ڶ�ȡ����˷������ݵ�������

            loginInfo = in.readUTF();                   //R0.��ȡ������˷��ص��Ƿ��¼�ɹ�����Ϣ
            System.out.println(loginInfo);

            // ���������֤�û���¼�ĵط�
            if (loginInfo.equals("loginSuccess")) {
                //QQNum = Integer.parseInt(qq);
                userInfo = new UserInfoBean();

                userInfo.setQq(Integer.parseInt(in.readUTF()));         //R1
                userInfo.setPwd(in.readUTF());                          //R2
                userInfo.setSign(in.readUTF());                         //R3
                userInfo.setPhotoID(in.readInt());                      //R4
                userInfo.setNickname(in.readUTF());                     //R5
                userInfo.setSex(in.readUTF());                          //R6
                userInfo.setBirthday(in.readUTF());                     //R7
                userInfo.setTelephone(in.readUTF());                    //R8
                userInfo.setEmail(in.readUTF());                        //R9
                userInfo.setAddress(in.readUTF());                      //R10


                Hashtable friendInfoTable = new Hashtable();

                int friendnum = in.readInt();                           //R11

                getFriendInfo(friendInfoTable, friendnum);

                mainFrm = new JMainFrm(this, userInfo, friendInfoTable, friendnum);//��ʼ��

                isSuccessLogin = true;
                flag = true;

                ClientToServerThread ctst = new ClientToServerThread(true);
                ctst.start();
                Port.comm.put(1, ctst);
            } else {

                in.close();
                out.close();
                //�ر�Scoket
                s.close();
            }

        } catch (Exception e) {

            //in.close();
            out.close();
            //�ر�Scoket
            s.close();
            e.printStackTrace();
        } finally {

        }
        return isSuccessLogin;//���ص�¼ʧ�ܵ���Ϣ
    }


    public String getLoginInfo() {
        return loginInfo;
    }

    /**
     * �˳�
     */
    public void logout() {
        try {
            out.writeUTF("logout");
            String msg = in.readUTF();
            if (msg.equals("logout")) {
                in.close();
                out.close();
                // �ر�Scoket
                s.close();

                flag = false;
            }

        } catch (Exception e) {
            try {
                in.close();
                out.close();
                // �ر�Scoket
                s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * �ر���������
     */
    public void closeConnect() {
        try {
            out.writeUTF("end");
            in.close();
            out.close();
            // �ر�Scoket
            s.close();
            flag = false;
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!isRegister) {
//                    Thread.sleep(500);
//                    noticeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    /**
     * ��ȡ�����б���Ϣ
     * @param friendInfoTable
     * @param friendnum
     */
    private void getFriendInfo(Hashtable friendInfoTable, int friendnum) {
        try {
            for (int i = 0; i < friendnum; i++) {
                friendInfo = new UserInfoBean();
                friendInfo.setQq(Integer.parseInt(in.readUTF()));

                friendInfo.setSign(in.readUTF());
                friendInfo.setPhotoID(in.readInt());
                friendInfo.setNickname(in.readUTF());
                friendInfo.setSex(in.readUTF());
                friendInfo.setBirthday(in.readUTF());
                friendInfo.setTelephone(in.readUTF());
                friendInfo.setEmail(in.readUTF());
                friendInfo.setAddress(in.readUTF());

                friendInfo.setSubGroupName(in.readUTF());

                String isLogin = in.readUTF();
                if (isLogin.equals("onLine")) {
                    friendInfo.setIP(in.readUTF());
                    friendInfo.setPORT(in.readInt());
                    friendInfo.setStatus(true);
                }
                friendInfoTable.put(i, friendInfo);
                String str = friendInfo.getQq() + " "
                        + friendInfo.getNickname() + " "
                        + friendInfo.getSubGroupName() + "���߷�"
                        + friendInfo.getStatus();
                System.out.println(str);
//                JOptionPane.showMessageDialog(null, str);
            }
        } catch (Exception e) {
            try {
                in.close();
                out.close();
                // �ر�Scoket
                s.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * ע�����û��߼�
     * @param user
     * @param no
     * @param hash
     * @return
     */
    public int toRegister(UserInfoBean user, int no[], Hashtable<Integer, String> hash) {
        int qq = 0;
        try {
            s = new Socket(IP, PORT);
            out = new DataOutputStream(s.getOutputStream());

            out.writeUTF("registerNewUser");//���߷������Ҫע�����û�
            out.writeUTF(user.getPwd());
            out.writeUTF(user.getSign());
            out.writeInt(user.getPhotoID());
            out.writeUTF(user.getNickname());
            out.writeUTF(user.getSex());
            out.writeUTF(user.getBirthday());
            out.writeUTF(user.getTelephone());
            out.writeUTF(user.getEmail());
            out.writeUTF(user.getAddress());

            out.writeInt(no[0]);
            out.writeUTF(hash.get(no[0]));
            out.writeInt(no[1]);
            out.writeUTF(hash.get(no[1]));
            out.writeInt(no[2]);
            out.writeUTF(hash.get(no[2]));

            in = new DataInputStream(s.getInputStream());
            qq = in.readInt();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return qq;
    }

    /**
     * �һ�����
     * @param qq
     * @param no
     * @param hash
     * @return
     */
    public String getPwd(int qq, int no[], Hashtable<Integer, String> hash) {
        String pwd = "";
        try {
            s = new Socket(IP, PORT);
            out = new DataOutputStream(s.getOutputStream());

            out.writeUTF("getPwd");//���Կ��������û�н��յ�
            out.writeUTF("" + qq);

            in = new DataInputStream(s.getInputStream());//��ȡ����˴�������Ϣ
            System.out.println(in);
            no[0] = in.readInt();
            hash.put(no[0], in.readUTF());
            no[1] = in.readInt();
            hash.put(no[1], in.readUTF());
            no[2] = in.readInt();
            hash.put(no[2], in.readUTF());

            pwd = in.readUTF();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return pwd;
    }


    /**
     * ��ѯ�û�ʱ����Ϣ
     * @param qq
     * @param nickname
     * @return
     */
    public UserInfoBean getUserInfo(int qq, String nickname) {
        UserInfoBean user = null;
        String pwd = "";
        try {
            s = new Socket(IP, PORT);
            out = new DataOutputStream(s.getOutputStream());

            out.writeUTF("queryUser1");
            out.writeUTF("" + qq);
            out.writeUTF(nickname);

            in = new DataInputStream(s.getInputStream());//��ȡ����˴�������Ϣ

            String info = in.readUTF();

            if (info.equals("ExistUser"))//������ص����Ѵ��ڵ��û����ͽ�����˴�����QQ,Sign,PhotoID,NickName����Ϣ���浽user��������󷵻�
            {
                user = new UserInfoBean();
                user.setQq(Integer.parseInt(in.readUTF()));
                user.setSign(in.readUTF());
                user.setPhotoID(in.readInt());
                user.setNickname(in.readUTF());

            }
        } catch (Exception e1) {
            try {
                in.close();
                out.close();
                // �ر�Scoket
                s.close();
            } catch (Exception e11) {
                e11.printStackTrace();
            }
            e1.printStackTrace();
        }
        return user;
    }


	/**
	 * ��Ӻ���
	 * @param qq ����QQ
	 * @param fqq ����QQ
	 * @param subno ������
	 */
    public void addFriend(int qq, int fqq, int subno) {
        try {
            s = new Socket(IP, PORT);
            out = new DataOutputStream(s.getOutputStream());

            out.writeUTF("addFriend");//���߷����,��Ӻ���
            out.writeInt(fqq);
            out.writeInt(subno);//����
            out.writeInt(qq);
            System.out.println("��Ӻ�������");
            in = new DataInputStream(s.getInputStream());
            String str = in.readUTF();
            System.out.println("�ӷ���˶�������ϢΪ:"+str);
            boolean isFlush=true;
            if(str.equals("flushFriend")){
                noticeUpdate();
            }
        } catch (Exception e1) {
            try {
                //in.close();
                out.close();
                // �ر�Scoket
                s.close();
            } catch (Exception e11) {
                e11.printStackTrace();
            }
            e1.printStackTrace();
        }
    }


	/**
	 * ɾ������
	 * @param qq
	 * @param fqq
	 */
    public void deleteFriend(int qq, int fqq) {
        try {
            s = new Socket(IP, PORT);
            out = new DataOutputStream(s.getOutputStream());

            out.writeUTF("deleteFriend");
            out.writeInt(fqq);
            out.writeInt(qq);

        } catch (Exception e1) {
            try {
                //	in.close();
                out.close();
                // �ر�Scoket
                s.close();
            } catch (Exception e11) {
                e11.printStackTrace();
            }
            e1.printStackTrace();
        }
    }

	/**
	 * ��ѯ����
	 */
    private void noticeUpdate() {
        try {
            if (isSuccessLogin) {
				s = new Socket(IP, PORT);
				out = new DataOutputStream(s.getOutputStream());

                out.writeUTF("queryFriend");

                Hashtable friendInfoTable = new Hashtable();

                int friendnum = in.readInt();
                getFriendInfo(friendInfoTable, friendnum);
                mainFrm.UpdateFriendList(friendInfoTable, friendnum);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


    /**
     * ��JMainFrm��ת����ִ��ˢ�º����б�
     */
    public void Update() {
        try {
            if (isSuccessLogin) {
//				System.out.println(out);
                out.writeUTF("queryFriend");
                Hashtable friendInfoTable = new Hashtable();

                int n = in.readInt();

                getFriendInfo(friendInfoTable, n);
                mainFrm.Update1(friendInfoTable, n);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public void Update(boolean isFlush) {
        try {
            if (isFlush) {
                System.out.println(out);
                out.writeUTF("queryFriend");
                Hashtable friendInfoTable = new Hashtable();
                int n = in.readInt();
                getFriendInfo(friendInfoTable, n);
                mainFrm.Update1(friendInfoTable, n);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    public void close() {

        try {
            in.close();
            out.close();
            // �ر�Scoket
            s.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        this.close();
    }

    public Socket getSocket() {
        return s;
    }
}
