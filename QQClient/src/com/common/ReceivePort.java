package com.common;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * ȷ����UDP���ն˿ڵĹ�����
 */
public class ReceivePort {

	public static int receivePort;
	
	public ReceivePort() throws IOException{
		try {
			DatagramSocket socket = getRangePort();
			receivePort = socket.getLocalPort();//���ش��׽������󶨵ı��������ϵĶ˿ں�
			
		} catch (SocketException e) {

			e.printStackTrace();
		}
	}

	/**
	 * ��ȡȷ���Ķ˿�6666
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
	 * ��5000��9999֮�������ѡһ���˿����ڱ�UDP�׽��ְ�
	 * @return һ������5000��9999֮��˿ںŵ�UDP�׽���
	 * @throws IOException
	 */
	public static DatagramSocket getRangePort() throws IOException {
		
		int ports[] = new int[20];//����20�����ö˿�
		for(int i = 0; i < 20; i++){//���˿������������5000��9999֮��Ķ˿�ֵ
			Random random=new Random();
			int temp = 5000+random.nextInt(5000);
			while(temp < 5000 || temp >9999){
				temp = 5000+random.nextInt(5000);			
			}
			ports[i] = temp;
		}

		for (int port : ports) {//�����˿�
	        try {
	        	DatagramSocket socket = new DatagramSocket(port);//�����󶨺�ǰ��˿ڵ�UDP�׽���

	        	if(socket.getLocalPort() == receivePort){//�˴����Ŀ����Ϊ�˷�ֹ�˿ڳ�ͻ
					//��ǰ�׽������󶨵ı��������ϵĶ˿ںź����ǽ��ն˿���ͬ��һ���˿�
					continue;
				}
	            return socket;
	        } catch (IOException ex) {//�������˿ڳ����쳣Ҳ����һ���˿�
	            continue; // try next port
	        }
	    }
		//������򵽴����˵���ڷ�Χ���Ҳ����˿�
	    throw new IOException("��5000��9999��Χ��δ�ҵ����ж˿�");
	}
}
