package com.common;

import java.util.Hashtable;

public class Port {

	public static Hashtable<String, Integer> port = new Hashtable<String, Integer>();//存放地址和端口
	public static Hashtable<Integer, Integer> port2 = new Hashtable<Integer, Integer>();//键来存发送消息的QQ号，值来存端口
	public static Hashtable<Integer, String> port3 = new Hashtable<Integer, String>();//键来存发送消息的QQ号，值来存IP
	
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
