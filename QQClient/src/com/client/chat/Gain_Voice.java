package com.client.chat;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;


public class Gain_Voice implements Runnable {

       TargetDataLine line; //从音频设备获取其数据的数据行
       Thread thread; 
       Socket s; 
       String ip;//////
       BufferedOutputStream captrueOutputStream;

       public Gain_Voice(String ip){  //构造器 取得socket以获得网络输出流 
       
    	   this.ip=ip; 
         
       }

       public void start() {

           thread = new Thread(this); 
           thread.setName("Gain_Voice"); 
           thread.start(); 
       }

       public void stop() { 
           thread = null; 
       }

       public void run() {
    	      
    	   
           try { 
        	 //建立输出流 此处可以加套压缩流用来压缩数据 
        	 // ServerSocket s1 = new ServerSocket(6000);
           //	  s = s1.accept();
           	  
        	 s= new Socket(ip, 6000);
             captrueOutputStream=new BufferedOutputStream(s.getOutputStream());
           } 
           catch (IOException ex) { 
              
        	   
        	   return; 
           }          
           // AudioFormat(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian） 
           // sampleRate 每秒的样本数 
           // sampleSizeInBits 每个样本中的位数 
           // channels 声道数（单声道1个,立体声2个,等等）
           // signed 指示数据是有符号的，还是无符号的
           // bigEndian 指示是否以 big-endian 字节顺序存储单个样本中的数据（false 意味着 little-endian）。      
           AudioFormat format =new AudioFormat(8000,16,2,true,true);
           //捕获音频
           DataLine.Info info = new DataLine.Info(TargetDataLine.class,format);
           try { 
               line = (TargetDataLine) AudioSystem.getLine(info); 
               line.open(format, line.getBufferSize());// 打开具有指定格式和请求缓冲区大小的行                                                        
           } catch (Exception ex) { //获得将适合数据行的内部缓冲区的最大数据字节数
               return;              //缓冲区的大小 getBufferSize()
           }

           byte[] data = new byte[1024];//此处的1024可以情况进行调整，应跟下面的1024应保持一致 
           int numBytesRead=0; 
           line.start();//允许某一数据行执行数据I/O

           while (thread != null) { 
               numBytesRead = line.read(data,0,128);//取数据（1024）的大小直接关系到传输的速度，一般越小越快， 
               try { 
                 captrueOutputStream.write(data,0,numBytesRead);//写入数据流 
               } 
               catch (Exception ex) { 
                   break; 
               } 
           }

           line.stop(); //停止的行应该停止I/O活动
           line.close();//关闭行,指示可以释放的该行使用的所有系统资源 
           line = null;

           try { 
               captrueOutputStream.flush(); 
               captrueOutputStream.close(); 
           } catch (IOException ex) { 
               ex.printStackTrace(); 
           } 
       } 
     
}