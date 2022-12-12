package com.client.chat;

import java.awt.Event;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class voice_chat {
	Socket client;
    InputStream in_Stream;   // 输入流
    OutputStream out_Stream; // 输出流
    BufferedReader read_in; 
    BufferedWriter write_out;
    boolean flag;         //  数据读取是否

    public  voice_chat(){
    	// Socket 参数1 String host 
    	//        参数2 int port      
    	try {
			client = new Socket("127.0.0.1", 5555);
			in_Stream = client.getInputStream();   // 获得音频流
			read_in = new BufferedReader(new InputStreamReader(in_Stream));
			out_Stream=client.getOutputStream();   // 输出流
            write_out=new BufferedWriter(new OutputStreamWriter(out_Stream));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    
		// 获得到数据
		while(true){
	       try{
	        
	    	 byte[] buf=new byte[200];
//	    	 if(){
	         in_Stream.read(buf); // 输入流获得数据	
//	    	 }
//	    	 else
	    		 
	       }
	       catch(IOException e){
            
	    	   System.out.print(e.getMessage());	           
	       }
	     
		}
   }
    // 判断按键事件
    public boolean action(Event evt,Object arg){		
		if(evt.target.equals("语音")){	        	        
	       try{	           
	        //  Socket cli=new Socket("127.0.0.1",6000);  ////////////端口号     
	          String ip = "127.0.0.1";
	  //        Gain_Voice cap=new Gain_Voice(ip);
	 //        cap.start();
	          }
	       catch(Exception e){
	    	   e.printStackTrace();
	            }
	        }
	        return true;
	    }

   
}
