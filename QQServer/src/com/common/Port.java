package com.common;

import java.util.Hashtable;

public class Port {

	public static Hashtable<String, Integer> port = new Hashtable<String, Integer>();//��ŵ�ַ�Ͷ˿�
	public static Hashtable<Integer, Integer> port2 = new Hashtable<Integer, Integer>();//�����淢����Ϣ��QQ�ţ�ֵ����˿�
	public static Hashtable<Integer, String> port3 = new Hashtable<Integer, String>();//�����淢����Ϣ��QQ�ţ�ֵ����IP
	
	public void put(String Ip,int Port){
		port.put(Ip, Port);
	}
	public void put(int qq,int Port){
		port2.put(qq, Port);
	}
	public void put(int qq,String Ip){
		port3.put(qq, Ip);
	}
}
