package com.client.chat;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
public class Sender {
	/*本机ip*/
	public static String localIP = "127.0.0.1";
	/*服务器ip*/
	public static String serverIP = null;
	/*错误信息*/
	public static String err_msg = "";
	 /*默认发送端口*/
	public static int SendPort = 5555;
	/*默认聊天端口*/
	public static int chatPort = 6666;  
	/*接收信息的socket*/
	/*private static DatagramSocket recDs = null;*/
	/*private static DatagramSocket chatSoc = null;*/
	static{
		/*rm = new Random();*/
		/*setLocalIP();*/
		/*chatPort = generateUDPPort();*/
	}
	public Sender(){
/*		try {
			if(recDs == null){
				rePort = generateUsefulPort();
				recDs = new DatagramSocket(rePort);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}*/
		//findServer();
	}
	/**
	 * @param msgType 消息类别
	 * @param uname
	 * @param friendIP
	 * @param friendPort
	 * @return 发送是否成功
	 */
	public static boolean sendUDPMsg(int msgType,String uname,String friendIP,int friendPort,String messae){
	    try
        {
            /*从命令行得到要发送的内容，使用UTF-8编码将消息转换为字节*/
            byte[] msg = (msgType+"*"+uname+"*"+messae).getBytes("UTF-8");
            /*得到主机的internet地址*/
            InetAddress address = InetAddress.getByName(friendIP);

            /*用数据和地址初始化一个数据报分组（数据包）*/
            DatagramPacket packet = new DatagramPacket(msg, msg.length, address,
            		friendPort);

            /*创建一个默认的套接字，通过此套接字发送数据包*/
            DatagramSocket dSocket = new DatagramSocket();
            dSocket.send(packet);

            /*发送完毕后关闭套接字*/
            dSocket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            err_msg = "系统运行异常！";
            return false;
        }
		return true;
	}
	/**
	 * 获取本机的IP
	 */
/*	private  static void setLocalIP(){
		try {
			InetAddress address = InetAddress.getLocalHost();
			localIP = address.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			err_msg = "出错:SendMessage.takeLocalIP()：Exception！";
		}
		System.out.println("localIP = " + localIP);
	}*/
	/*private static Random  rm;*/
/*	private static int generateUsefulPort(){
			int port  = 1025 + rm.nextInt(5000);
			try{   
		        bindPort("0.0.0.0", port);   
		        bindPort(InetAddress.getLocalHost().getHostAddress(),port);   
		    }catch(Exception e){   
		    	e.printStackTrace();
		        return generateUsefulPort();   
		    }  
		    System.out.println("generateUsefulPort=" + port);
		    return port;
	}*/
/*	public static int generateUDPPort(){
		rePort = 1024 + rm.nextInt(5000);
		try {
			DatagramSocket socket = new DatagramSocket(rePort);
			socket.close();
		} catch (IOException e) {
			return generateUDPPort();
		}
		return rePort;
}*/
	/**
	 * 检测tcp端口是否可用
	 * @param host
	 * @param port
	 * @throws Exception
	 */
/*	private static void bindPort(String host, int port) throws Exception{   
        Socket s = new Socket();   
        s.bind(new InetSocketAddress(host, port));   
        s.close();
    }   */
}
