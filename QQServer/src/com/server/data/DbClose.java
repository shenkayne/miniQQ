/**
 * 关闭数据库连接
 */
package com.server.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库关闭类
 */
public class DbClose {

	/**
	 * 关闭数据库连接
	 * @param conn 连接类对象
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
	 * 关闭数据库语句
	 * @param pre 语句对象,可防止SQL注入
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
	 *关闭数据库结果集
	 * @param rs 结果集对象
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
		System.out.println("数据库已关闭！");
	}
}
