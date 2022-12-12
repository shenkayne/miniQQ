/**
 * 
 */
package com.tools;

import java.util.HashMap;
import java.util.Iterator;


/**
 * 管理客户端线程
 */
public class ManageClientThread {

	public static HashMap hm=new HashMap<String, Server>();
	public static int clientNum = 0;
	
	public ManageClientThread(){}
	
	//向hm中添加一个客户端通讯线程
	public static  void addClientThread(Server ct)
	{
		hm.put(clientNum++, ct);
	}
	
	public static Server getClientThread(String uid)
	{
		return (Server)hm.get(uid);
	}
	
	//返回当前在线的人的情况
	public static String getAllOnLineUserid()
	{
		//使用迭代器完成
		Iterator it=hm.keySet().iterator();
		String res="";
		while(it.hasNext())
		{
			res+=it.next().toString()+" ";
		}
		return res;
	}
}
