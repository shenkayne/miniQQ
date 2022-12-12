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
 * 这个类是处理用户各种请求的功能类，他主要完成以下功能：
 * 1、用户登录
 * 2、注册新用户
 * 3、查找用户
 * 4、查找好友
 * 5、更改用户自己的信息
 * 6、添加、删除好友
 * 7、处理用户下线
 * 8、 找回密码
 */


public class Server implements Runnable {

    private Socket socket = null; //定义套接口
    private DefaultListModel listModel = null;
    private JLabel jLabel2 = null;
    private DataInputStream in = null; //定义输入流
    private DataOutputStream out = null; //定义输出流


    private Connection con = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;
    private UserInfoBean userInfo = null;
    private UserInfoBean friendInfo = null;
    private Boolean flag = true;//控制服务器线程的启动与停止
    private String IP = "";
    private int PORT = 0;
    private int qqnum;
    private String password;
    private int serverNo;

    public Server(Socket socket, DefaultListModel listModel, JLabel jLabel2, int serverNo) {
        this.socket = socket; //获得套接口
        this.listModel = listModel;
        this.jLabel2 = jLabel2;
        this.serverNo = serverNo;
        try {
            //创建输入流
            in = new DataInputStream(socket.getInputStream());
            //创建输出流
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            while (flag) {
                String str = in.readUTF();//读取客户端传来的请求，判断想要进行什么操作
                System.out.println("客户端传来的请求是："+str);
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

    //这个方法处理注册新用户
    public void registerNewUser() {
        //创建数据库连接对象
        con = ConnectionFactory.getConnnection();
        try {

            int newqq = 0;
            String sql = "SELECT MAX(QQ) FROM UserInfo ";
//            System.out.println(sql);

            pre = con.prepareStatement(sql);
            //执行查询命令，并获取返回的结果集
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

            //执行查询命令，并获取返回的结果集
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
     * 此方法用于处理用户登录
     */
    public void login() {
        //创建数据库连接对象
        con = ConnectionFactory.getConnnection();
        try {
            qqnum = Integer.parseInt(in.readUTF());             //R1.读取客户的id
            password = in.readUTF();                            //R2.读取客户的密码
            int port = Integer.parseInt(in.readUTF());          //R3.读取端口信息
            //JOptionPane.showMessageDialog(null, "server:"+port);
            String ClientIP = in.readUTF();                     //R4.读取本机地址

            IP = socket.getInetAddress().toString();
            PORT = socket.getPort();


            System.out.println("------------------------------------");
            System.out.println("客户端主机为："+socket.getInetAddress());
            System.out.println("客户端端口为："+socket.getPort());
            System.out.println("------------------------------------");
            System.out.println();

            //  int port = Integer.parseInt(in.readUTF());//读取客户的端口号
            String sql = "SELECT * FROM UserInfo WHERE QQ = " + qqnum + " AND PWD = '" + password + "'";
//            System.out.println(sql);
            pre = con.prepareStatement(sql);
            //执行查询命令，并获取返回的结果集
            rs = pre.executeQuery();

            boolean isSuccess = false;

            boolean isExist = false;
            while (rs.next()) {
                isExist = true;//如果查询到了用户输入的账号密码在数据库中存在就将isExist赋值为true
            }


            String sql1 = "SELECT * FROM Login WHERE lqq = " + qqnum + " and lstatus= 1";//查询用户输入的QQ号所对应的登录信息
//            System.out.println(sql1);
            pre = con.prepareStatement(sql1);
            //执行查询命令，并获取返回的结果集
            rs = pre.executeQuery();

            boolean isReLogin = false;
            while (rs.next()) {
                isReLogin = true;
            }

            if (!isReLogin && isExist) {
                isSuccess = true;
            }

            //如果登录成功,则执行如下操作
            if (isSuccess) {
                Port p = new Port();
                p.put(ClientIP, port);//存地址和端口
                p.put(qqnum, port);//存该QQ所对应端口
                p.put(qqnum, ClientIP);//存该QQ所对应地址

                //  JOptionPane.showMessageDialog(null, ClientIP);
                //   JOptionPane.showMessageDialog(null, "Server:"+ClientIP+",客户端分配的端口："+p.port.get(ClientIP));

                //  JOptionPane.showMessageDialog(null, "Server:loginSucess");
                out.writeUTF("loginSuccess");               //W0.登陆成功
                setOnline(qqnum);
                queryUser(qqnum);
                queryFriend(qqnum);
                //     noticeAll();
            } else {
                if (isReLogin) {
                    out.writeUTF("reLogin");                //W0.重复登陆
                } else {
                    out.writeUTF("loginFail");              //W0.登录失败
                }

                in.close();
                out.close();
                socket.close();
                flag = false;
            }

        } catch (Exception ex) {
            try {
                out.writeUTF("loginFail");                  //W0.登陆失败
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


    //此方法用于查找好友
    public void queryFriend(int qqnum) {
        //创建数据库连接对象
        con = ConnectionFactory.getConnnection();

        Vector friendNum = new Vector(); //此向量用于存储好友的QQ号码
        try {
            //检索好友QQ号码的SQL语句
            String sql1 = "SELECT fqq,fsno FROM Friends WHERE QQ = " + qqnum;
//            System.out.println(sql1);
            pre = con.prepareStatement(sql1);
            //执行查询命令，并获取返回的结果集
            rs = pre.executeQuery();
            int j = 0;
            int subgroupNo[] = new int[100];//分组
            //将好友的QQ号码依次存入向量中
            while (rs.next()) {
                int fqq = rs.getInt(1);
                int fsno = rs.getInt(2);
                friendNum.addElement(fqq);
                subgroupNo[j++] = fsno;
            }

            out.writeInt(friendNum.size());             //W11.发送给客户端通知好友数量

            friendInfo = new UserInfoBean();
            //依次取出好友的号码,并查找其信息
            for (int i = 0; i < friendNum.size(); i++) {
                int num = (Integer) friendNum.elementAt(i);//取出好友QQ号码
                String sql2 = "SELECT * FROM UserInfo WHERE QQ = " + num;
//                System.out.println(sql2);
                pre = con.prepareStatement(sql2);
                //执行查询命令，并获取返回的结果集
                rs = pre.executeQuery();

                rs.next();
                //向客户端发送好友信息
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
                //执行查询命令，并获取返回的结果集
                rs = pre.executeQuery();
                rs.next();

                String sname = rs.getString(1);//分组名
                out.writeUTF(sname);

                friendInfo.setSubGroupName(rs.getString(1));

                String sql4 = "SELECT lip,lport FROM Login WHERE lqq = " + num;
//                System.out.println(sql4);
                pre = con.prepareStatement(sql4);
                //执行查询命令，并获取返回的结果集
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

    //找回密码
    public void getPassword() {
        //创建数据库连接对象
        con = ConnectionFactory.getConnnection();

        try {
            qqnum = Integer.parseInt(in.readUTF()); //读取客户的QQ号码
            System.out.println("需要找回密码的QQ为："+qqnum);

            String sql = "select question,answer from GetPwdInfo where qq= " + qqnum;
//            System.out.println(sql);
            pre = con.prepareStatement(sql);
            //执行查询命令，并获取返回的结果集
            rs = pre.executeQuery();
            //将好友的QQ号码依次存入向量中
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

            System.out.println("该QQ的密码为："+rs.getString(1));
            out.writeUTF(rs.getString(1));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    //此方法用于查找用户
    public void queryUser(int qqnum) {
        //创建数据库连接对象
        con = ConnectionFactory.getConnnection();
        try {
            //int qqnum = in.readInt();
            String sql = "SELECT * FROM UserInfo WHERE qq = " + qqnum;

//            System.out.println(sql);

            pre = con.prepareStatement(sql);
            userInfo = new UserInfoBean();
            //执行查询命令，并获取返回的结果集
            rs = pre.executeQuery();
            while (rs.next()) {
                //向客户端发送用户信息
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

    //此方法用于查找用户
    public void queryUser1(int qqnum, String nickname) {
        //创建数据库连接对象
        con = ConnectionFactory.getConnnection();
        try {
            //int qqnum = in.readInt();
            String sql = "SELECT * FROM UserInfo WHERE QQ = " + qqnum + " or nickname = '" + nickname + "'";

            pre = con.prepareStatement(sql);
            userInfo = new UserInfoBean();
            //执行查询命令，并获取返回的结果集
            rs = pre.executeQuery();

            boolean isExist = false;

            while (rs.next()) {
                //向客户端发送用户信息
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

    //此方法处理用户双向添加好友，不打算实现好友验证功能
    public void addFriend() {
        //创建数据库连接对象
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

            pre.setInt(1, fqq);//被添加的好友的QQ号
            pre.setInt(2, fsno);//分组编号
            pre.setDate(3, time);//日期
            pre.setInt(4, 0);//好友在线与否
            pre.setInt(5, qq);//添加好友的当事人QQ号
            //执行查询命令，并获取返回的结果集
            pre.execute();

            //TODO 因为没有做添加好友的验证功能，因此默认添加好友是双向完成的
            String sql2 = "INSERT INTO Friends(fqq,fsno,fdate,fstatus,qq) VALUES(?,?,?,?,?)";
//            System.out.println(sql);
            Date utiltime = new Date();
            java.sql.Date sqltime = new java.sql.Date(utiltime.getTime());

            pre = con.prepareCall(sql2);
            pre.clearParameters();

            pre.setInt(1, qq);//被添加的好友的QQ号
            pre.setInt(2, fsno);//分组编号
            pre.setDate(3, sqltime);//日期
            pre.setInt(4, 0);//好友在线与否
            pre.setInt(5, fqq);//添加好友的当事人QQ号
            //执行查询命令，并获取返回的结果集
            pre.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    //此方法用于用户删除好友
    public void deleteFriend() {
        //创建数据库连接对象
        con = ConnectionFactory.getConnnection();
        try {
            int fqqnum = in.readInt();
            int qq = in.readInt();
            String sql = "DELETE FROM Friends WHERE QQ = " + qq + " and fqq=" + fqqnum;

            pre = con.prepareCall(sql);
            pre.clearParameters();

            //执行查询命令，并获取返回的结果集
            pre.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    //此方法用于用户更新自己的信息
    public void updateOwnInfo() {
        //创建数据库连接对象
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

            //执行查询命令，并获取返回的结果集
            pre.execute();


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    //此方法用于处理用户下线
    public void logout() {
        String nickname = userInfo.getNickname();
        int qq = userInfo.getQq();
        String strIndex = nickname + "(" + qq + ")";
        System.out.println("strIndex="+strIndex);
        int index = listModel.indexOf(strIndex);//搜索第一个出现的elem。
        listModel.remove(index);
        jLabel2.setText("" + listModel.getSize());
        //创建数据库连接对象
        con = ConnectionFactory.getConnnection();
        try {
            String sql = "DELETE FROM Login WHERE lqq = " + qqnum;
            pre = con.prepareStatement(sql);
            pre.clearParameters();
            //执行查询命令，并获取返回的结果集
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
    	 //创建数据库连接对象
        con = ConnectionFactory.getConnnection();
        try {
            int qqnum = Integer.parseInt(in.readUTF()); //读取客户的QQ号码
            String password = in.readUTF(); //读取客户的密码
          
            String sql = "DELETE FROM LOGIN WHERE QQ = "+qqnum;
           
            pre = con.prepareStatement(sql);	
            pre.clearParameters();
          
			//执行查询命令，并获取返回的结果集
			pre.executeQuery();
			
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
        */
    }

    /**
     * 插入在线的用户进入数据库
     * @param qq
     */
    public void setOnline(int qq) {
        //创建数据库连接对象
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
            pre.setInt(4, 1);//状态
            pre.setInt(5, qq);
            //执行查询命令，并获取返回的结果集
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
     * 在线通知
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
