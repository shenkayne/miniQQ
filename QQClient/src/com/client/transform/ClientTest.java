package com.client.transform;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ClientTest extends JFrame{	
	JFrame mainframe=null;
	private JFileChooser fc;
	int flag;
	private ClientSocket cs = null;
	private String ip = "192.16.137.3"; // 设置成服务器IP
	private int port = 8821;
	private String sendMessage = "Windwos";	
//	String savePath = "E:\\";
	String savePath;			//保存接收文件的路径
    JButton transformButton;
    public rece rec = null;
    public boolean isStop = false;
    
    
	public ClientTest(String ip) {
		this.ip = ip;	
	
		//final ClientTest client=new ClientTest(ip);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//初始化窗体
		this.setBounds(300, 200, 400, 300);		
		this.setLayout(null);	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//初始化按钮
		transformButton=new JButton("接收文件");
		transformButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jieshou_do();
			}});
		transformButton.setBounds(0, 0, 160, 40);
		transformButton.setLocation(this.getWidth()/2, this.getHeight()/2);
		
		transformButton.setVisible(true);
		this.add(transformButton);
		
		this.setVisible(true);
		
		fc=new JFileChooser();
		fc.setDialogTitle("选择接收文件保存的路径");    
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择目录

	}
	public void jieshou(){
		 //这里显示打开文件的对话框    
		 try{     
          flag=fc.showOpenDialog(mainframe);     
          }    
		 catch(HeadlessException head){     
           System.out.println("Open File Dialog ERROR!");    
          }   
		//获得该文件    
        File f = fc.getSelectedFile();   
        
        System.out.println("open file----"+f.getName()+"  filePath:"+f.getPath());  
        savePath=f.getPath();
      //开始接收消息  
        try {
			if (createConnection()) {
				sendMessage();
				getMessage();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	private boolean createConnection() {
		cs = new ClientSocket(ip, port);
		try {
			cs.CreateConnection();
			System.out.print("连接服务器成功!" + "\n");
			return true;
		} catch (Exception e) {
			System.out.print("连接服务器失败!" + "\n");
			return false;
		}

	}

	private void sendMessage() {
		if (cs == null)
			return;
		try {
			cs.sendMessage(sendMessage);
		} catch (Exception e) {
			System.out.print("发送消息失败!" + "\n");
		}
	}

	private void getMessage() {
		if (cs == null)
			return;
		DataInputStream inputStream = null;
		try {
			inputStream = cs.getMessageStream();
		} catch (Exception e) {
			System.out.print("接收消息缓存错误\n");
			return;
		}

		try {
			// 本地保存路径，文件名会自动从服务器端继承而来。
			
			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			int passedlen = 0;
			long len = 0;

			savePath += inputStream.readUTF();
			DataOutputStream fileOut = new DataOutputStream(
					new BufferedOutputStream(new BufferedOutputStream(
							new FileOutputStream(savePath))));
			len = inputStream.readLong();

			System.out.println("文件的长度为:" + len + "\n");
			System.out.println("开始接收文件!" + "\n");

			while (true) {
				int read = 0;
				if (inputStream != null) {
					read = inputStream.read(buf);
				}
				passedlen += read;
				if (read == -1) {
					break;
				}
				// 下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
				System.out.println("文件接收了" + (passedlen * 100 / len) + "%\n");
				fileOut.write(buf, 0, read);
			}
			System.out.println("接收完成，文件存为" + savePath + "\n");

			fileOut.close();
			this.dispose();
			
		} catch (Exception e) {
			System.out.println("接收消息错误" + "\n");
			return;
		}
		//this.dispose();
		isStop = true;
		return;
	}
	public void jieshou_do(){
		rec = new rece();
		rec.run();
	}
	public class rece implements Runnable
	{
		public rece()
		{
		}
		public void run()
		{
			if(!isStop)
			    jieshou();
			else
				return;
		}
	}
	public static void main(String arg[]) {
		JFrame frame=new JFrame();
		JButton transformButton;
		String ip = "192.16.137.2";
		final ClientTest client=new ClientTest(ip);
		//初始化窗体
		frame.setBounds(300, 200, 400, 300);		
		frame.setLayout(null);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//初始化按钮
		transformButton=new JButton("接收文件");
		transformButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				client.jieshou_do();
			}});
		transformButton.setBounds(0, 0, 160, 40);
		transformButton.setLocation(frame.getWidth()/2, frame.getHeight()/2);
		
		transformButton.setVisible(true);
		frame.add(transformButton);
		
		frame.setVisible(true);
		//String ip = "192.16.137.3";
		new ClientTest(ip);
	}
}
