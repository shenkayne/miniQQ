package com.client.business.uiManager;

import java.util.Hashtable;

import com.tools.ClientToServer;

public class Getpwd {

    ClientToServer cts =  null;

	public Getpwd(){
	}
	
	public String getPwdQA(int qq,int no[],Hashtable<Integer,String> hash){
		cts = new ClientToServer(true);
		new Thread(cts).start();
		
		return cts.getPwd(qq,no,hash);
	}

	public void close(){
		cts.closeConnect();
	}
}
