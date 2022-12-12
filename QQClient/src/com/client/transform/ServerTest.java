package com.client.transform;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.client.business.uiManager.ChatPanel;
import com.common.Message;
import com.tools.ClientToServerThread;

public class ServerTest extends JFrame{
	
	public ChatPanel cp;
	
	JButton transformButton;
	JFrame mainframe;
	private JFileChooser fc;
	int flag;
//	String filePath = "D:\\lib.rar";
	String filePath;
	boolean isReceive = false;
	
	int port = 8821;
	ClientToServerThread ctsT = null;
	Message msg = null;
	
    public ServerTest(ChatPanel cp,Message msg,ClientToServerThread ctsT){
    	this.cp = cp;
    	this.msg = msg;
    	this.ctsT = ctsT;
    	
	fc=new JFileChooser();
	mainframe=this;
	//初始化窗体
	this.setBounds(300, 200, 400, 300);
	
	this.setLayout(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//初始化按钮
	transformButton=new JButton("传输文件");
	transformButton.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			chuanshu_do();
		}});
	transformButton.setBounds(0, 0, 160, 40);
	transformButton.setLocation(this.getWidth()/2, this.getHeight()/2);
	
	transformButton.setVisible(true);
	this.add(transformButton);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//	this.setVisible(true);
	chuanshu_do();
	this.setVisible(false);
}
public void chuanshu(){
	fc.setDialogTitle("请选择要传输的文件");       
    //这里显示打开文件的对话框    
	try{     
         flag=fc.showOpenDialog(mainframe);     
      }    
	catch(HeadlessException head){     
       System.out.println("Open File Dialog ERROR!");    
      }         
    //如果按下确定按钮，则获得该文件。    
    if(flag==JFileChooser.APPROVE_OPTION)    
      {    
           //获得该文件    
    	// cp.bt_File_Send();
             File f = fc.getSelectedFile();    
             System.out.println("open file----"+f.getName()+"  filePath:"+f.getPath()); 
             filePath=f.getPath();
            
             start();		//开始传输
            
       }  
}
	void start() {
		Socket s = null;
		try {
			ServerSocket ss = new ServerSocket(port);
			while (true) {
				// 选择进行传输的文件
				
				File fi = new File(filePath);

				if(!isReceive){
				ctsT.sendData(msg);
				ctsT.sendData(msg);				
				isReceive = true;
				
				}
				
				System.out.println("文件长度:" + (int) fi.length());

				// public Socket accept() throws
				// IOException侦听并接受到此套接字的连接。此方法在进行连接之前一直阻塞。

				s = ss.accept();
				System.out.println("建立socket链接");
				DataInputStream dis = new DataInputStream(
						new BufferedInputStream(s.getInputStream()));
				dis.readByte();

				
				
				DataInputStream fis = new DataInputStream(
						new BufferedInputStream(new FileInputStream(filePath)));
				DataOutputStream ps = new DataOutputStream(s.getOutputStream());
				// 将文件名及长度传给客户端。这里要真正适用所有平台，例如中文名的处理，还需要加工，具体可以参见Think In Java
				// 4th里有现成的代码。
				ps.writeUTF(fi.getName());
				ps.flush();
				ps.writeLong((long) fi.length());
				ps.flush();

				int bufferSize = 8192;
				byte[] buf = new byte[bufferSize];

				while (true) {
					int read = 0;
					if (fis != null) {
						read = fis.read(buf);
					}

					if (read == -1) {
						break;
					}
					ps.write(buf, 0, read);
				}
				ps.flush();
				// 注意关闭socket链接哦，不然客户端会等待server的数据过来，
				// 直到socket超时，导致数据不完整。
				fis.close();
				
				ps.close();
				dis.close();
				s.close();
				System.out.println("文件传输完成");
				
				//System.exit(1);
				this.dispose();
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void chuanshu_do(){
		new send().run();
		
		
		
	}
	class send implements Runnable
	{
		public send()
		{
		}
		public void run()
		{
			if(!isReceive)
			   chuanshu();
			else{
				return;
			}
		}
	}
	public static void main(String arg[]) {
	//	new ServerTest();
	}
	
}
