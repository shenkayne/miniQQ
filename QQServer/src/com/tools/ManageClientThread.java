/**
 * 
 */
package com.tools;

import java.util.HashMap;
import java.util.Iterator;


/**
 * ����ͻ����߳�
 */
public class ManageClientThread {

	public static HashMap hm=new HashMap<String, Server>();
	public static int clientNum = 0;
	
	public ManageClientThread(){}
	
	//��hm�����һ���ͻ���ͨѶ�߳�
	public static  void addClientThread(Server ct)
	{
		hm.put(clientNum++, ct);
	}
	
	public static Server getClientThread(String uid)
	{
		return (Server)hm.get(uid);
	}
	
	//���ص�ǰ���ߵ��˵����
	public static String getAllOnLineUserid()
	{
		//ʹ�õ��������
		Iterator it=hm.keySet().iterator();
		String res="";
		while(it.hasNext())
		{
			res+=it.next().toString()+" ";
		}
		return res;
	}
}
