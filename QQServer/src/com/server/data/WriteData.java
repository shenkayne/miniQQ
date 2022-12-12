package com.server.data;

import java.io.*;

public class WriteData {

	public WriteData() {
		// TODO Auto-generated constructor stub
	}

	public void write(int value){
		try{
			   BufferedWriter writer = new BufferedWriter(new FileWriter(new File("save.txt")));

			   writer.write(""+value);
			   
			   writer.close();

			}catch(Exception e){

	  }
	}
	
	public void writeData(int []data,int n){
		File file =  new File("TXT/save.txt");
		try{
			FileOutputStream fos = new FileOutputStream(file);
			DataOutputStream outData = new DataOutputStream(fos);
			
			int i = 0;
			while(i < n){
				outData.writeInt(data[i++]);
			}
			fos.close();
			outData.close();
		}
		catch(IOException e){
			System.out.print(e.toString());
		}
		
	}
	
	public void writeData(String []data,int n){
		File file =  new File("TXT/admin.txt");
		try{
			FileOutputStream fos = new FileOutputStream(file);
			DataOutputStream outData = new DataOutputStream(fos);
			
			int i = 0;
			while(i < n){
				outData.writeBytes(data[i++]+"\r\n");
			}
			fos.close();
			outData.close();
		}
		catch(IOException e){
			System.out.print(e.toString());
		}
		
	}
	public void writeFile(String[] arrs) throws IOException {
      try{
        //写入中文字符时解决中文乱码问题
        FileOutputStream fos=new FileOutputStream(new File("TXT/admin.txt"));
        OutputStreamWriter osw=new OutputStreamWriter(fos);
        BufferedWriter  bw=new BufferedWriter(osw);
        //简写如下：
        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
        //        new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt")), "UTF-8"));

        for(String arr:arrs){
            bw.write(arr+"\t\n");
        }
        
        //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
        bw.close();
        osw.close();
        fos.close();
      }catch(IOException e){
			System.out.print(e.toString());
		}
    }
	public void writeData(String name,int data){
		File file =  new File("TXT/"+name+".txt");
		try{
			FileOutputStream fos = new FileOutputStream(file);
			DataOutputStream outData = new DataOutputStream(fos);
			
			outData.writeInt(data);
			
			fos.close();
			outData.close();
		}
		catch(IOException e){
			System.out.print(e.toString());
		}
		
	}
	public void clearData(String name){
		try {
			File f5 = new File("TXT/"+name + ".txt");// 这里是你txt的路径，记得路径下一级用“\\"实现
			FileWriter fw5 = new FileWriter(f5);
			BufferedWriter bw1 = new BufferedWriter(fw5);
			bw1.write("");
			fw5.close();
			bw1.close();
		} catch (Exception e) {
		}
	}
}
