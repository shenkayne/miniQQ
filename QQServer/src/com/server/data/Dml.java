/**
 * ���ݿ����DML
 */
package com.server.data;

import com.common.Message;

import java.sql.*;
/**
 * ���ݲ�������
 * ִ����ɾ�Ĳ���
 */
public class Dml {

    private Connection con = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

	private static int tno;//���ڵ���Text��ChatInfo֮�����������
    public Dml() {
    }


    public void insert(int id, String password, String nickName, String sex, Date birthday, String city) {
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnnection();
            String sql = "{call SP_INSERTUSRS(?,?,?,?,?,?)}";
            cs = conn.prepareCall(sql);
            cs.setInt(1, id);
            cs.setString(2, password);
            cs.setString(3, nickName);
            cs.setString(4, sex);
            cs.setDate(5, birthday);
            cs.setString(6, city);
            //ִ��SQL���
            cs.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbClose.close(rs, cs, conn);
        }
    }
	/**
	 * �����ݿ��в���������Ϣ������
	 */
    public void insert(Message message) {
        //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            String sql = "INSERT INTO Text(tcontext,tfonttype,tfontsize,tfontcolor,isBold,isItatic,isUnderline) VALUES(?,?,?,?,?,?,?)";
            pre = con.prepareCall(sql);
            pre.clearParameters();
			String tcontext=message.getInfo();
			String tfonttype= message.getFontType();
			int tfontsize= message.getFontSize();
			int isBold=message.getIsBold() ? 1 : 0;
			int isItatic=message.getIsItatic() ? 1 : 0;
			int isUnderline= message.getIsUnderline() ? 1 : 0;

            pre.setString(1, tcontext);
            pre.setString(2, tfonttype);
            pre.setInt(3, tfontsize);
            String str = "" + message.getFontColor().getRed() + "*" + message.getFontColor().getGreen() + "*" + message.getFontColor().getBlue();
            System.out.println("Color=" + str);
            pre.setString(4, str);
            pre.setInt(5, isBold);
            pre.setInt(6, isItatic);
            pre.setInt(7,isUnderline);
            System.out.println(sql);
            //ִ�в�ѯ�������ȡ���صĽ����
            pre.execute();


			String queryIdsql="select tno from Text where tcontext = "+ tcontext +
					" and tfonttype = " + "'" + tfonttype + "'" +
					" and tfontsize = " + tfontsize  +
					" and isBold = " + isBold +
					" and isItatic = " + isItatic +
					" and isUnderline =" + isUnderline;

            System.out.println("queryIdsql====\n"+queryIdsql);

			pre = con.prepareStatement(queryIdsql);
			//ִ�в�ѯ�������ȡ���صĽ����
			rs = pre.executeQuery();
			while (rs.next()){
                int tno=rs.getInt(1);//������˵Ӧ��ֻ����һ��tno
            }

            String sql1 = "INSERT INTO ChatInfo(csendqq,creceiveqq,cdate,tno) VALUES(?,?, Current_timestamp,?)";

            pre = con.prepareCall(sql1);
            pre.clearParameters();
            pre.setInt(1, message.getSendQq());
            pre.setInt(2, message.getReceiveQq());
			pre.setInt(3,tno);
            //ִ�в�ѯ�������ȡ���صĽ����
            pre.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

    public void update(int id, String password, String nickName, String sex, Date birthday, String city) {
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnnection();
            String sql = "{call SP_UPDATEUSRS(?,?,?,?,?,?)}";
            cs = conn.prepareCall(sql);
            cs.setInt(1, id);
            cs.setString(2, password);
            cs.setString(3, nickName);
            cs.setString(4, sex);
            cs.setDate(5, birthday);
            cs.setString(6, city);
            //ִ��SQL���
            cs.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbClose.close(rs, cs, conn);
        }
    }

    public void delete(int id) {
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnnection();
            String sql = "{call SP_DELETEUSERS(?)}";
            cs = conn.prepareCall(sql);
            cs.setInt(1, id);
            //ִ��SQL���
            cs.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbClose.close(rs, cs, conn);
        }
    }
}
