package com.common;

import java.util.Hashtable;

import com.tools.ClientToServerThread;


/**
 * 端口类
 */
public class Port {
	public static int commonPort;
	public static Hashtable<Integer,Integer> hash = new Hashtable<Integer,Integer>();
	public static Hashtable<Integer,ClientToServerThread> comm = new Hashtable<Integer,ClientToServerThread>();//管理用户向服务器连接的线程
	
}
