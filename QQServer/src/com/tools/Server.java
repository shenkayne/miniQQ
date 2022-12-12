package com.tools;

import com.common.Port;
import com.common.UserInfoBean;
import com.server.data.ConnectionFactory;
import com.server.data.DbClose;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * ������Ǵ����û���������Ĺ����࣬����Ҫ������¹��ܣ�
 * 1���û���¼
 * 2��ע�����û�
 * 3�������û�
 * 4�����Һ���
 * 5�������û��Լ�����Ϣ
 * 6����ӡ�ɾ������
 * 7�������û�����
 * 8�� �һ�����
 */


public class Server implements Runnable {

    private Socket socket = null; //�����׽ӿ�
    private DefaultListModel listModel = null;
    private JLabel jLabel2 = null;
    private DataInputStream in = null; //����������
    private DataOutputStream out = null; //���������


    private Connection con = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;
    private UserInfoBean userInfo = null;
    private UserInfoBean friendInfo = null;
    private Boolean flag = true;//���Ʒ������̵߳�������ֹͣ
    private String IP = "";
    private int PORT = 0;
    private int qqnum;
    private String password;
    private int serverNo;

    public Server(Socket socket, DefaultListModel listModel, JLabel jLabel2, int serverNo) {
        this.socket = socket; //����׽ӿ�
        this.listModel = listModel;
        this.jLabel2 = jLabel2;
        this.serverNo = serverNo;
        try {
            //����������
            in = new DataInputStream(socket.getInputStream());
            //���������
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            while (flag) {
                String str = in.readUTF();//��ȡ�ͻ��˴����������ж���Ҫ����ʲô����
                System.out.println("�ͻ��˴����������ǣ�"+str);
                if (str.equals("end")) {
                    break;
                } else if (str.equals("registerNewUser")) {
                    registerNewUser();
                } else if (str.equals("login")) {
                    login();
                } else if (str.equals("queryUser")) {
                    int qqnum = Integer.parseInt(in.readUTF());
                    queryUser(qqnum);
                } else if (str.equals("addFriend")) {
                    addFriend();
                    out.writeUTF("flushFriend");
                } else if (str.equals("deleteFriend")) {
                    deleteFriend();
                } else if (str.equals("updateOwnInfo")) {
                    updateOwnInfo();
                } else if (str.equals("logout")) {
                    logout();
                    break;
                } else if (str.equals("queryFriend")) {
                    queryFriend(qqnum);
                } else if (str.equals("getPwd")) {
                    getPassword();
                } else if (str.equals("queryUser1")) {
                    int qqnum = Integer.parseInt(in.readUTF());
                    String nickname = in.readUTF();
                    queryUser1(qqnum, nickname);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //�����������ע�����û�
    public void registerNewUser() {
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {

            int newqq = 0;
            String sql = "SELECT MAX(QQ) FROM UserInfo ";
//            System.out.println(sql);

            pre = con.prepareStatement(sql);
            //ִ�в�ѯ�������ȡ���صĽ����
            rs = pre.executeQuery();
            while (rs.next()) {
                newqq = rs.getInt(1);
            }
            newqq++;
            String pwd = in.readUTF();
            String sign = in.readUTF();
            int photoID = in.readInt();
            String nickname = in.readUTF();
            String sex = in.readUTF();
            String birthday = in.readUTF();
            String telephone = in.readUTF();
            String email = in.readUTF();
            String address = in.readUTF();

            int question1No = in.readInt();
            String answer1 = in.readUTF();
            int question2No = in.readInt();
            String answer2 = in.readUTF();
            int question3No = in.readInt();
            String answer3 = in.readUTF();


            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(birthday);
            java.sql.Date time = new java.sql.Date(date.getTime());

            String sql1 = "INSERT INTO UserInfo VALUES(?,?,?,?,?,?,?,?,?,?)";
//            System.out.println(sql1);
            pre = con.prepareCall(sql1);
            pre.clearParameters();

            pre.setInt(1, newqq);
            pre.setString(2, pwd);
            pre.setString(3, sign);
            pre.setInt(4, photoID);
            pre.setString(5, nickname);
            pre.setString(6, sex);
            pre.setDate(7, time);
            pre.setString(8, telephone);
            pre.setString(9, email);
            pre.setString(10, address);

            //ִ�в�ѯ�������ȡ���صĽ����
            pre.execute();

            String sql2 = "insert into GetPwdInfo(question,answer,qq) values(?,?," + newqq + ")";
//            System.out.println(sql2);
            pre = con.prepareCall(sql2);
            pre.clearParameters();

            pre.setInt(1, question1No);
            pre.setString(2, answer1);
            pre.execute();

            String sql3 = "insert into GetPwdInfo(question,answer,qq) values(?,?," + newqq + ")";
            pre = con.prepareCall(sql3);
            pre.clearParameters();

            pre.setInt(1, question2No);
            pre.setString(2, answer2);
            pre.execute();

            String sql4 = "insert into GetPwdInfo(question,answer,qq) values(?,?," + newqq + ")";
            pre = con.prepareCall(sql4);
            pre.clearParameters();

            pre.setInt(1, question3No);
            pre.setString(2, answer3);
            pre.execute();

            out.writeInt(newqq);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    /**
     * �˷������ڴ����û���¼
     */
    public void login() {
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            qqnum = Integer.parseInt(in.readUTF());             //R1.��ȡ�ͻ���id
            password = in.readUTF();                            //R2.��ȡ�ͻ�������
            int port = Integer.parseInt(in.readUTF());          //R3.��ȡ�˿���Ϣ
            //JOptionPane.showMessageDialog(null, "server:"+port);
            String ClientIP = in.readUTF();                     //R4.��ȡ������ַ

            IP = socket.getInetAddress().toString();
            PORT = socket.getPort();


            System.out.println("------------------------------------");
            System.out.println("�ͻ�������Ϊ��"+socket.getInetAddress());
            System.out.println("�ͻ��˶˿�Ϊ��"+socket.getPort());
            System.out.println("------------------------------------");
            System.out.println();

            //  int port = Integer.parseInt(in.readUTF());//��ȡ�ͻ��Ķ˿ں�
            String sql = "SELECT * FROM UserInfo WHERE QQ = " + qqnum + " AND PWD = '" + password + "'";
//            System.out.println(sql);
            pre = con.prepareStatement(sql);
            //ִ�в�ѯ�������ȡ���صĽ����
            rs = pre.executeQuery();

            boolean isSuccess = false;

            boolean isExist = false;
            while (rs.next()) {
                isExist = true;//�����ѯ�����û�������˺����������ݿ��д��ھͽ�isExist��ֵΪtrue
            }


            String sql1 = "SELECT * FROM Login WHERE lqq = " + qqnum + " and lstatus= 1";//��ѯ�û������QQ������Ӧ�ĵ�¼��Ϣ
//            System.out.println(sql1);
            pre = con.prepareStatement(sql1);
            //ִ�в�ѯ�������ȡ���صĽ����
            rs = pre.executeQuery();

            boolean isReLogin = false;
            while (rs.next()) {
                isReLogin = true;
            }

            if (!isReLogin && isExist) {
                isSuccess = true;
            }

            //�����¼�ɹ�,��ִ�����²���
            if (isSuccess) {
                Port p = new Port();
                p.put(ClientIP, port);//���ַ�Ͷ˿�
                p.put(qqnum, port);//���QQ����Ӧ�˿�
                p.put(qqnum, ClientIP);//���QQ����Ӧ��ַ

                //  JOptionPane.showMessageDialog(null, ClientIP);
                //   JOptionPane.showMessageDialog(null, "Server:"+ClientIP+",�ͻ��˷���Ķ˿ڣ�"+p.port.get(ClientIP));

                //  JOptionPane.showMessageDialog(null, "Server:loginSucess");
                out.writeUTF("loginSuccess");               //W0.��½�ɹ�
                setOnline(qqnum);
                queryUser(qqnum);
                queryFriend(qqnum);
                //     noticeAll();
            } else {
                if (isReLogin) {
                    out.writeUTF("reLogin");                //W0.�ظ���½
                } else {
                    out.writeUTF("loginFail");              //W0.��¼ʧ��
                }

                in.close();
                out.close();
                socket.close();
                flag = false;
            }

        } catch (Exception ex) {
            try {
                out.writeUTF("loginFail");                  //W0.��½ʧ��
                in.close();
                out.close();
                socket.close();
                flag = false;
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }


    //�˷������ڲ��Һ���
    public void queryFriend(int qqnum) {
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();

        Vector friendNum = new Vector(); //���������ڴ洢���ѵ�QQ����
        try {
            //��������QQ�����SQL���
            String sql1 = "SELECT fqq,fsno FROM Friends WHERE QQ = " + qqnum;
//            System.out.println(sql1);
            pre = con.prepareStatement(sql1);
            //ִ�в�ѯ�������ȡ���صĽ����
            rs = pre.executeQuery();
            int j = 0;
            int subgroupNo[] = new int[100];//����
            //�����ѵ�QQ�������δ���������
            while (rs.next()) {
                int fqq = rs.getInt(1);
                int fsno = rs.getInt(2);
                friendNum.addElement(fqq);
                subgroupNo[j++] = fsno;
            }

            out.writeInt(friendNum.size());             //W11.���͸��ͻ���֪ͨ��������

            friendInfo = new UserInfoBean();
            //����ȡ�����ѵĺ���,����������Ϣ
            for (int i = 0; i < friendNum.size(); i++) {
                int num = (Integer) friendNum.elementAt(i);//ȡ������QQ����
                String sql2 = "SELECT * FROM UserInfo WHERE QQ = " + num;
//                System.out.println(sql2);
                pre = con.prepareStatement(sql2);
                //ִ�в�ѯ�������ȡ���صĽ����
                rs = pre.executeQuery();

                rs.next();
                //��ͻ��˷��ͺ�����Ϣ
                int fqq = rs.getInt(1);
                String sign = rs.getString(3);
                int photoId = rs.getInt(4);
                String nickname = rs.getString(5);
                String sex = rs.getString(6);
                String date = rs.getDate(7).toString();
                String telephone = rs.getString(8);
                String email = rs.getString(9);
                String address = rs.getString(10);
                out.writeUTF("" + fqq);
                out.writeUTF(sign);
                out.writeInt(photoId);
                out.writeUTF(nickname);
                out.writeUTF(sex);
                out.writeUTF(date);
                out.writeUTF(telephone);
                out.writeUTF(email);
                out.writeUTF(address);

                friendInfo.setQq(fqq);
                friendInfo.setSign(sign);
                friendInfo.setPhotoID(photoId);
                friendInfo.setNickname(nickname);
                friendInfo.setSex(sex);
                friendInfo.setBirthday("" + date);
                friendInfo.setTelephone(telephone);
                friendInfo.setEmail(email);
                friendInfo.setAddress(address);

                String sql3 = "SELECT sname FROM SubGroup WHERE sno = " + subgroupNo[i];
//                System.out.println(sql3);
                pre = con.prepareStatement(sql3);
                //ִ�в�ѯ�������ȡ���صĽ����
                rs = pre.executeQuery();
                rs.next();

                String sname = rs.getString(1);//������
                out.writeUTF(sname);

                friendInfo.setSubGroupName(rs.getString(1));

                String sql4 = "SELECT lip,lport FROM Login WHERE lqq = " + num;
//                System.out.println(sql4);
                pre = con.prepareStatement(sql4);
                //ִ�в�ѯ�������ȡ���صĽ����
                rs = pre.executeQuery();

                boolean isLogin = false;
                if (rs.next()) {
                    isLogin = true;
                }
                if (isLogin) {

                    out.writeUTF("onLine");
                    String lip = rs.getString(1);
                    int lport = rs.getInt(2);
                    out.writeUTF(lip);
                    out.writeInt(lport);

                    friendInfo.setIP(lip);
                    friendInfo.setPORT(lport);
                    friendInfo.setStatus(true);
                } else {
                    out.writeUTF("NotonLine");
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    //�һ�����
    public void getPassword() {
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();

        try {
            qqnum = Integer.parseInt(in.readUTF()); //��ȡ�ͻ���QQ����
            System.out.println("��Ҫ�һ������QQΪ��"+qqnum);

            String sql = "select question,answer from GetPwdInfo where qq= " + qqnum;
//            System.out.println(sql);
            pre = con.prepareStatement(sql);
            //ִ�в�ѯ�������ȡ���صĽ����
            rs = pre.executeQuery();
            //�����ѵ�QQ�������δ���������
            while (rs.next()) {
                out.writeInt(rs.getInt(1));
                out.writeUTF(rs.getString(2));
//                System.out.println(rs.getInt(1));
//                System.out.println(rs.getString(2));
            }

            String sql2 = "select pwd from UserInfo where qq= " + qqnum;
//            System.out.println(sql2);
            pre = con.prepareStatement(sql2);
            rs = pre.executeQuery();
            rs.next();

            System.out.println("��QQ������Ϊ��"+rs.getString(1));
            out.writeUTF(rs.getString(1));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    //�˷������ڲ����û�
    public void queryUser(int qqnum) {
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            //int qqnum = in.readInt();
            String sql = "SELECT * FROM UserInfo WHERE qq = " + qqnum;

//            System.out.println(sql);

            pre = con.prepareStatement(sql);
            userInfo = new UserInfoBean();
            //ִ�в�ѯ�������ȡ���صĽ����
            rs = pre.executeQuery();
            while (rs.next()) {
                //��ͻ��˷����û���Ϣ
                int qq = rs.getInt(1);
                String pwd=rs.getString(2);
                String sign = rs.getString(3);
                int photoId = rs.getInt(4);
                String nickname = rs.getString(5);
                String sex = rs.getString(6);
                String date = rs.getDate(7).toString();
                String telephone = rs.getString(8);
                String email = rs.getString(9);
                String address = rs.getString(10);
                out.writeUTF("" + qq);
                out.writeUTF(pwd);
                out.writeUTF(sign);
                out.writeInt(photoId);
                out.writeUTF(nickname);
                out.writeUTF(sex);
                out.writeUTF(date);
                out.writeUTF(telephone);
                out.writeUTF(email);
                out.writeUTF(address);

                userInfo.setQq(qq);
                userInfo.setPwd(pwd);
                userInfo.setSign(sign);
                userInfo.setPhotoID(photoId);
                userInfo.setNickname(nickname);
                userInfo.setSex(sex);
                userInfo.setBirthday("" + date);
                userInfo.setTelephone(telephone);
                userInfo.setEmail(email);
                userInfo.setAddress(address);

                listModel.addElement(userInfo.getNickname() + "(" + userInfo.getQq() + ")");

                jLabel2.setText("" + listModel.getSize());
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    //�˷������ڲ����û�
    public void queryUser1(int qqnum, String nickname) {
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            //int qqnum = in.readInt();
            String sql = "SELECT * FROM UserInfo WHERE QQ = " + qqnum + " or nickname = '" + nickname + "'";

            pre = con.prepareStatement(sql);
            userInfo = new UserInfoBean();
            //ִ�в�ѯ�������ȡ���صĽ����
            rs = pre.executeQuery();

            boolean isExist = false;

            while (rs.next()) {
                //��ͻ��˷����û���Ϣ
                isExist = true;
                out.writeUTF("ExistUser");

                int qq = rs.getInt(1);
                int pwd = rs.getInt(2);
                String sign = rs.getString(3);
                int photoId = rs.getInt(4);
                String nickName = rs.getString(5);
                String sex = rs.getString(6);
                java.sql.Date birthday = rs.getDate(7);
                String telephone = rs.getString(8);
                String email = rs.getString(9);
                String address = rs.getString(10);

                out.writeUTF("" + qq);
                out.writeUTF(sign);
                out.writeInt(photoId);
                out.writeUTF(nickName);
            }
            if (!isExist) {
                out.writeUTF("NotExistUser");
            }
        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    //�˷��������û�˫����Ӻ��ѣ�������ʵ�ֺ�����֤����
    public void addFriend() {
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            int fqq = in.readInt();
            int fsno = in.readInt();
            int qq = in.readInt();

            String sql = "INSERT INTO Friends(fqq,fsno,fdate,fstatus,qq) VALUES(?,?,?,?,?)";
//            System.out.println(sql);
            Date time1 = new Date();
            java.sql.Date time = new java.sql.Date(time1.getTime());

            pre = con.prepareCall(sql);
            pre.clearParameters();

            pre.setInt(1, fqq);//����ӵĺ��ѵ�QQ��
            pre.setInt(2, fsno);//������
            pre.setDate(3, time);//����
            pre.setInt(4, 0);//�����������
            pre.setInt(5, qq);//��Ӻ��ѵĵ�����QQ��
            //ִ�в�ѯ�������ȡ���صĽ����
            pre.execute();

            //TODO ��Ϊû������Ӻ��ѵ���֤���ܣ����Ĭ����Ӻ�����˫����ɵ�
            String sql2 = "INSERT INTO Friends(fqq,fsno,fdate,fstatus,qq) VALUES(?,?,?,?,?)";
//            System.out.println(sql);
            Date utiltime = new Date();
            java.sql.Date sqltime = new java.sql.Date(utiltime.getTime());

            pre = con.prepareCall(sql2);
            pre.clearParameters();

            pre.setInt(1, qq);//����ӵĺ��ѵ�QQ��
            pre.setInt(2, fsno);//������
            pre.setDate(3, sqltime);//����
            pre.setInt(4, 0);//�����������
            pre.setInt(5, fqq);//��Ӻ��ѵĵ�����QQ��
            //ִ�в�ѯ�������ȡ���صĽ����
            pre.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    //�˷��������û�ɾ������
    public void deleteFriend() {
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            int fqqnum = in.readInt();
            int qq = in.readInt();
            String sql = "DELETE FROM Friends WHERE QQ = " + qq + " and fqq=" + fqqnum;

            pre = con.prepareCall(sql);
            pre.clearParameters();

            //ִ�в�ѯ�������ȡ���صĽ����
            pre.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    //�˷��������û������Լ�����Ϣ
    public void updateOwnInfo() {
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {

            String sql1 = "UPDATE UserInfo SET PWD = ?,SIGN = ?,PHOTOID = ?,"
                    + "nickName = ?,SEX = ?,BIRTHDAY = ?,CONSTELLATION = ?,"
                    + "BOOLDTYPE = ?,DIPLOMA = ?,TELEPHONE = ?,EMAIL = ?,ADDRESS = ?";

            pre.setString(1, in.readUTF());
            pre.setString(2, in.readUTF());
            pre.setInt(3, in.readInt());
            pre.setString(4, in.readUTF());
            pre.setString(5, in.readUTF());
            pre.setString(6, in.readUTF());
            pre.setString(7, in.readUTF());
            pre.setString(8, in.readUTF());
            pre.setString(9, in.readUTF());
            pre.setString(10, in.readUTF());
            pre.setString(11, in.readUTF());
            pre.setString(12, in.readUTF());

            //ִ�в�ѯ�������ȡ���صĽ����
            pre.execute();


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    //�˷������ڴ����û�����
    public void logout() {
        String nickname = userInfo.getNickname();
        int qq = userInfo.getQq();
        String strIndex = nickname + "(" + qq + ")";
        System.out.println("strIndex="+strIndex);
        int index = listModel.indexOf(strIndex);//������һ�����ֵ�elem��
        listModel.remove(index);
        jLabel2.setText("" + listModel.getSize());
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            String sql = "DELETE FROM Login WHERE lqq = " + qqnum;
            pre = con.prepareStatement(sql);
            pre.clearParameters();
            //ִ�в�ѯ�������ȡ���صĽ����
            pre.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
        try {
            out.writeUTF("logout");
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	/*
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            int qqnum = Integer.parseInt(in.readUTF()); //��ȡ�ͻ���QQ����
            String password = in.readUTF(); //��ȡ�ͻ�������
          
            String sql = "DELETE FROM LOGIN WHERE QQ = "+qqnum;
           
            pre = con.prepareStatement(sql);	
            pre.clearParameters();
          
			//ִ�в�ѯ�������ȡ���صĽ����
			pre.executeQuery();
			
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
        */
    }

    /**
     * �������ߵ��û��������ݿ�
     * @param qq
     */
    public void setOnline(int qq) {
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {

            String sql = "INSERT INTO Login(lip,lport,ldate,lstatus,lqq) VALUES(?,?,?,?,?)";
            Date time1 = new Date();
            java.sql.Date time = new java.sql.Date(time1.getTime());

            pre = con.prepareCall(sql);
            pre.clearParameters();

            pre.setString(1, IP);
            pre.setInt(2, PORT);
            pre.setDate(3, time);
            pre.setInt(4, 1);//״̬
            pre.setInt(5, qq);
            //ִ�в�ѯ�������ȡ���صĽ����
            pre.execute();


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    public int getServerNo() {
        return serverNo;
    }

    private void noticeAll() {
        int n = ManageClientThread.clientNum;
        for (int i = 0; i <= n; i++) {
            Server server = ManageClientThread.getClientThread(i + "");
            if (this.serverNo != server.getServerNo()) {
                server.noticeOnline(qqnum, IP, PORT);
            }
        }
    }

    /**
     * ����֪ͨ
     * @param qq
     * @param ip
     * @param port
     */
    public void noticeOnline(int qq, String ip, int port) {

        try {
            out.writeUTF("notice");
            out.writeInt(qq);
            out.writeUTF(ip);
            out.writeInt(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
