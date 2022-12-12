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
 * 实现与服务端的通信，登录和对好友进行管理时该线程启动
 */
public class ClientToServer implements Runnable {

    //Socket对象
    private Socket s;
    private DataInputStream in = null; //定义输入流
    private DataOutputStream out = null; //定义输出流

    private String IP = "";
    private int PORT = 0;
    private String QQNum;

    private String pwd;
    private boolean flag = false;
    private boolean isSuccessLogin = false;//用于判断是否成功登录

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

    public ClientToServer() {//通过构造函数获取属性文件中的IP、PORT
        getPropertiesInfo();
    }

    public ClientToServer(String IP, int port) {
        this.IP = IP;
        this.PORT = port;
    }

    /**
     * 该方法用来获得服务器属性文件中的IP、PORT
     */
    private void getPropertiesInfo() {
        Properties prop = new Properties();

        //将ServerInfo.properties中的数据以流的形式保存在instream中
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ServerInfo.properties");
        try {
            //获得相应的键值对
            prop.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //根据相应的键获得对应的值
        IP = prop.getProperty("serverip");//得到服务器ip
        PORT = Integer.parseInt(prop.getProperty("servertcp.port"));//得到tcp端口
    }


    /**
     *  登陆时发送第一次请求
     */
    public boolean sendLoginInfoToServer(User u) throws IOException {
        try {
            s = new Socket(IP, PORT);
            out = new DataOutputStream(s.getOutputStream());//写数据的输出流
            QQNum = u.getUserId();//获取到QQ号
            pwd=u.getPasswd();
            //以独立于机器的方式使用修改的UTF-8编码将字符串写入底层输出流

            out.writeUTF("login");                  //W0.客户端请求
            out.writeUTF(QQNum);                        //W1.用户id
            out.writeUTF(pwd);                //W2.密码

            ReceivePort receiveport = new ReceivePort();
            Port por = new Port();
            por.commonPort = receiveport.getReceivePort();//客户端随机分配端口
            int p = receiveport.getReceivePort();

            out.writeUTF("" + p);                   //W3.向服务端发送端口信息

            InetAddress localAddr = null;
            try {
                localAddr = InetAddress.getLocalHost();//获取本机地址
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
            String localIP = localAddr.getHostAddress();//以文本形式返回IP地址字符串
            IP = localIP;
            String ClientIP = IP;
            //String ClientIP = "127.0.0.1";

            out.writeUTF(ClientIP);                     //W4.将本机地址写入到输出流

            in = new DataInputStream(s.getInputStream());//创建用于读取服务端返回数据的输入流

            loginInfo = in.readUTF();                   //R0.读取到服务端返回的是否登录成功的信息
            System.out.println(loginInfo);

            // 这里就是验证用户登录的地方
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

                mainFrm = new JMainFrm(this, userInfo, friendInfoTable, friendnum);//初始化

                isSuccessLogin = true;
                flag = true;

                ClientToServerThread ctst = new ClientToServerThread(true);
                ctst.start();
                Port.comm.put(1, ctst);
            } else {

                in.close();
                out.close();
                //关闭Scoket
                s.close();
            }

        } catch (Exception e) {

            //in.close();
            out.close();
            //关闭Scoket
            s.close();
            e.printStackTrace();
        } finally {

        }
        return isSuccessLogin;//返回登录失败的消息
    }


    public String getLoginInfo() {
        return loginInfo;
    }

    /**
     * 退出
     */
    public void logout() {
        try {
            out.writeUTF("logout");
            String msg = in.readUTF();
            if (msg.equals("logout")) {
                in.close();
                out.close();
                // 关闭Scoket
                s.close();

                flag = false;
            }

        } catch (Exception e) {
            try {
                in.close();
                out.close();
                // 关闭Scoket
                s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 关闭网络连接
     */
    public void closeConnect() {
        try {
            out.writeUTF("end");
            in.close();
            out.close();
            // 关闭Scoket
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
     * 获取朋友列表信息
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
                        + friendInfo.getSubGroupName() + "在线否："
                        + friendInfo.getStatus();
                System.out.println(str);
//                JOptionPane.showMessageDialog(null, str);
            }
        } catch (Exception e) {
            try {
                in.close();
                out.close();
                // 关闭Scoket
                s.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 注册新用户逻辑
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

            out.writeUTF("registerNewUser");//告诉服务端想要注册新用户
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
     * 找回密码
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

            out.writeUTF("getPwd");//测试看服务端有没有接收到
            out.writeUTF("" + qq);

            in = new DataInputStream(s.getInputStream());//获取服务端传来的消息
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
     * 查询用户时的信息
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

            in = new DataInputStream(s.getInputStream());//获取服务端传来的信息

            String info = in.readUTF();

            if (info.equals("ExistUser"))//如果返回的是已存在的用户，就将服务端传来的QQ,Sign,PhotoID,NickName等信息保存到user对象中最后返回
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
                // 关闭Scoket
                s.close();
            } catch (Exception e11) {
                e11.printStackTrace();
            }
            e1.printStackTrace();
        }
        return user;
    }


	/**
	 * 添加好友
	 * @param qq 本人QQ
	 * @param fqq 好友QQ
	 * @param subno 分组编号
	 */
    public void addFriend(int qq, int fqq, int subno) {
        try {
            s = new Socket(IP, PORT);
            out = new DataOutputStream(s.getOutputStream());

            out.writeUTF("addFriend");//告诉服务端,添加好友
            out.writeInt(fqq);
            out.writeInt(subno);//分组
            out.writeInt(qq);
            System.out.println("添加好友了吗");
            in = new DataInputStream(s.getInputStream());
            String str = in.readUTF();
            System.out.println("从服务端读到的消息为:"+str);
            boolean isFlush=true;
            if(str.equals("flushFriend")){
                noticeUpdate();
            }
        } catch (Exception e1) {
            try {
                //in.close();
                out.close();
                // 关闭Scoket
                s.close();
            } catch (Exception e11) {
                e11.printStackTrace();
            }
            e1.printStackTrace();
        }
    }


	/**
	 * 删除好友
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
                // 关闭Scoket
                s.close();
            } catch (Exception e11) {
                e11.printStackTrace();
            }
            e1.printStackTrace();
        }
    }

	/**
	 * 查询好友
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
     * 从JMainFrm跳转过来执行刷新好友列表
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
            // 关闭Scoket
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
