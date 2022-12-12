/**
 * 
 */
package com.server.data;

import com.common.FriendsInfoBean;
import com.common.Message;
import com.common.UserInfoBean;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;


/**
 * 数据库查询工具类
 */
public class Query {

	Connection conn = null;
	PreparedStatement pre = null;
	ResultSet rs = null;
	
	public Query(){
		
	}
	public boolean isExistUser(String tableName,String id,String password){
		try{
			conn = ConnectionFactory.getConnnection();
			String sql = "select * from "+tableName+" where qq = "+Integer.parseInt(id)+ " and pwd = '"+password+"'";
			
			pre = conn.prepareStatement(sql);
			
			//执行查询命令，并获取返回的结果集
			rs = pre.executeQuery();
			while(rs.next()){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbClose.close(rs, pre, conn);
		}	
		return false;
	}


	/**
	 * 从数据库中查询用户的具体信息，将具体信息保存到对象中返回
	 * @param tableName
	 * @param id
	 * @param pwd
	 * @return
	 */
	public UserInfoBean getUserInfo(String tableName,String id,String pwd){
		UserInfoBean user = new UserInfoBean();
		try{
			conn = ConnectionFactory.getConnnection();
			String sql = "select * from "+tableName+" where qq = "+Integer.parseInt(id)+ " and pwd = '"+pwd+"'";
			
			pre = conn.prepareStatement(sql);
			
			//执行查询命令，并获取返回的结果集
			rs = pre.executeQuery();
			while(rs.next()){
				user.setQq(rs.getInt(1));
//				user.setPwd(rs.getString(2));
				user.setSign(rs.getString(3));
				user.setPhotoID(rs.getInt(4));
				user.setNickname(rs.getString(5));
				user.setSex(rs.getString(6));
//				user.setBirthday(rs.getString(7));
//
//				user.setTelephone(rs.getString(8));
//				user.setEmail(rs.getString(9));
//				user.setAddress(rs.getString(10));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbClose.close(rs, pre, conn);
		}	
		
		return user;
	}

	/**
	 * 得到朋友信息
	 * @param tableName
	 * @param id
	 */
	public void getFriendsInfo(String tableName,String id){
		FriendsInfoBean friend=new FriendsInfoBean();
		try{
			conn = ConnectionFactory.getConnnection();
			String sql = "select * from "+tableName+" where qq = "+Integer.parseInt(id);
			
			pre = conn.prepareStatement(sql);
			
			//执行查询命令，并获取返回的结果集
			rs = pre.executeQuery();

			while(rs.next()){
				friend.setFqq(rs.getInt(2));
//				friend.setSubGroupName();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbClose.close(rs, pre, conn);
		}			
	}
	public void getInfo(String user[]){
		String tableName = "Scott.emp";
		try{
			conn = ConnectionFactory.getConnnection();
			String sql = "select ENAME,JOB from "+tableName;
			pre = conn.prepareStatement(sql);			
			
			//执行查询命令，并获取返回的结果集
			rs = pre.executeQuery();
			
			while(rs.next()){
				System.out.println("雇员名："+rs.getString(1)+" 工作："+rs.getString(2));	
				user[0] = rs.getString(1);
				user[1] = rs.getString(2);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbClose.close(rs, pre, conn);
		}
	}

	/**
	 * 获取日志信息,聊天记录
	 * @param sendQQ
	 * @param receiveQQ
	 * @return
	 */
	public Hashtable getRecord(int sendQQ,int receiveQQ){
		Hashtable record = new Hashtable();
		try{
			conn = ConnectionFactory.getConnnection();
			String sql = "select c.csendqq,c.creceiveqq,c.cdate,t.tcontext,t.tfonttype,t.tfontsize,t.tfontcolor,t.isBold,t.isItatic,t.isUnderline "
					  +"from Text t,ChatInfo c "
                      +"where c.tno = t.tno and ((c.csendqq = "+sendQQ+" and c.creceiveqq = "+receiveQQ+") or ("
                      +"c.csendqq = "+receiveQQ+" and c.creceiveqq = "+sendQQ+"))"
                      +"order by c.tno DESC";
			pre = conn.prepareStatement(sql);			
			
			//执行查询命令，并获取返回的结果集
			rs = pre.executeQuery();
			int i = 0;
			while (rs.next()) {
				Message message = new Message();
				message.setSendQq(rs.getInt(1));
				message.setReceiveQq(rs.getInt(2));
				message.setDate(rs.getDate(3));
				message.setInfo(rs.getString(4));
				message.setFontType(rs.getString(5));
				message.setFontSize(rs.getInt(6));
				String color = rs.getString(7);
				int one = color.lastIndexOf("*");
				int blue = Integer.parseInt(color.substring(one+1));
				color = color.substring(0, one);
				int two = color.lastIndexOf("*");
				int green = Integer.parseInt(color.substring(two+1));
				color = color.substring(0,two);
				int red = Integer.parseInt(color.substring(0,two));
				
				message.setFontColor(new Color(red,green,blue));
				message.setBold(rs.getInt(8)==1?true:false);
				message.setItatic(rs.getInt(9)==1?true:false);
				message.setUnderline(rs.getInt(10)==1?true:false);
				
				record.put(i++, message);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbClose.close(rs, pre, conn);
		}
		return record;
	}
}
