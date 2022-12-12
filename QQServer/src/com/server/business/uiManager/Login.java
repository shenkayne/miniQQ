/**
 * 
 */
package com.server.business.uiManager;

import com.server.data.ReadData;
import com.server.data.WriteData;

/**
 * 辅助登录相关验证功能
 */
public class Login {
	
	public Login(){
	}
	
	public boolean checkUser(String user,String pwd){
		
		ReadData rd = new ReadData();
		String []admin = new String[2];
		rd.readData(admin, 2);
		
		String storeUser = admin[0];  
		String storePwd = admin[1];
		
		if(storeUser.equals(user) && storePwd.equals(pwd))
			return true;
		else
			return false;
	}
	
	public void remember(String user,String pwd){
		WriteData wd = new WriteData();
		String admin[] = new String[2];
		admin[0] = user;
		admin[1] = pwd;
		
		wd.writeData(admin,2);
 	}
	
	public boolean isRemember(){
		ReadData rd = new ReadData();
		String []admin = new String[2];
		rd.readData(admin, 2);
		
		if(admin[0].equals("")){
			return false;
		}else{
			return true;
		}
	}

	public void ReadUserInfo(String user[]) {

		ReadData rd = new ReadData();
		rd.readData(user, 2);

	}
}
