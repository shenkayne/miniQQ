/**
 * �ر����ݿ�����
 */
package com.server.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ���ݿ�ر���
 */
public class DbClose {

	/**
	 * �ر����ݿ�����
	 * @param conn ���������
	 */
	public static void close(Connection conn){
		if(null != conn){
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * �ر����ݿ����
	 * @param pre ������,�ɷ�ֹSQLע��
	 */
	public static void close(PreparedStatement pre){
		if(null != pre){
			try{
				pre.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *�ر����ݿ�����
	 * @param rs ���������
	 */
	public static void close(ResultSet rs){
		if(null != rs){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet rs,PreparedStatement pre,Connection conn){
		close(rs);
		close(pre,conn);
	}
	public static void close(PreparedStatement pre,Connection conn){
		close(pre);
		close(conn);
		System.out.println("���ݿ��ѹرգ�");
	}
}
