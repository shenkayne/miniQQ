package com.common;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * 确定好UDP接收端口的工具类
 */
public class ReceivePort {

	public static int receivePort;
	
	public ReceivePort() throws IOException{
		try {
			DatagramSocket socket = getRangePort();
			receivePort = socket.getLocalPort();//返回此套接字所绑定的本地主机上的端口号
			
		} catch (SocketException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 获取确定的端口6666
	 * @return
	 * @throws IOException
	 */
	public static int getPort() throws IOException{
	    int port = 6666;
		try {
			DatagramSocket socket = getRangePort();  
			port= socket.getLocalPort();
			
		} catch (SocketException e) {
					// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return port;
	}
    public static int getReceivePort(){
    	return receivePort;
    }


//	public static DatagramSocket getRandomPort() throws SocketException {
//		DatagramSocket s = new DatagramSocket(0);
//		return s;
//	}

	/**
	 * 在5000到9999之间随机挑选一个端口用于被UDP套接字绑定
	 * @return 一个绑定了5000到9999之间端口号的UDP套接字
	 * @throws IOException
	 */
	public static DatagramSocket getRangePort() throws IOException {
		
		int ports[] = new int[20];//给出20个备用端口
		for(int i = 0; i < 20; i++){//给端口数组随机赋予5000到9999之间的端口值
			Random random=new Random();
			int temp = 5000+random.nextInt(5000);
			while(temp < 5000 || temp >9999){
				temp = 5000+random.nextInt(5000);			
			}
			ports[i] = temp;
		}

		for (int port : ports) {//遍历端口
	        try {
	        	DatagramSocket socket = new DatagramSocket(port);//创建绑定好前面端口的UDP套接字

	        	if(socket.getLocalPort() == receivePort){//此代码的目的是为了防止端口冲突
					//当前套接字所绑定的本地主机上的端口号和我们接收端口相同则换一个端口
					continue;
				}
	            return socket;
	        } catch (IOException ex) {//如果这个端口出现异常也换下一个端口
	            continue; // try next port
	        }
	    }
		//如果程序到达这里，说明在范围内找不到端口
	    throw new IOException("在5000到9999范围内未找到空闲端口");
	}
}
