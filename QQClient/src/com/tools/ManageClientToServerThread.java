/**
 * 
 */
package com.tools;

import java.util.HashMap;


public class ManageClientToServerThread {

private static HashMap hm=new HashMap<String, ClientToServerThread>();
	
	//把创建好的ClientConServerThread放入到hm
	public static void addClientConServerThread(String qqId,ClientToServerThread ccst)
	{
		hm.put(qqId, ccst);
	}
	
	//可以通过qqId取得该线程 
	public static ClientToServerThread getClientConServerThread(String qqId)
	{
		return (ClientToServerThread)hm.get(qqId);
	}
}
