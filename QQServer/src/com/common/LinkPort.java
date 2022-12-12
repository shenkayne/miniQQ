package com.common;

import java.util.Hashtable;

/**
 * 连接端口
 */
public class LinkPort {
	public static Hashtable<Integer,TwoPortMsg> hash = new Hashtable<Integer,TwoPortMsg>();
	public static Hashtable<Integer,Boolean> isfirst = new Hashtable<Integer,Boolean>();
	public static int mixstore[] = new int[20];//混合数组，用于存储混合QQ
	public static int count = 0;
}
