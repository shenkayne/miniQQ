package com.client.business.uiManager;

import com.tools.ClientToServer;
import com.common.User;
import com.client.data.ReadData;
import com.client.data.WriteData;

/**
 * 解决用户登录界面中相关的功能，包括登录验证，退出，记住密码等
 */
public class Login {
	ClientToServer cts;
	
	public Login(){}
	
	public Login(ClientToServer cts){
		this.cts = cts;
	}
	public boolean checkUser(User u) throws Exception{
		return cts.sendLoginInfoToServer(u);
	}
	public void userLogout() throws Exception{
		cts.logout();
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
