package com.server.data;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadData {

	public ReadData() {
		
	}
	public String read(String name)               //写文件的名字，读出文件的内容
	{
		  int b;
		  String s=new String();
		  byte tom[]=new byte[1000];
		  try{
			 			  
			  File f=new File("TXT/"+name+".txt");
			  FileInputStream in=new FileInputStream(f);
			  while((b=in.read(tom,0,1000))!=-1)
			  {
				  s=new String(tom,0,b);
			  }
			 
			  in.close();
	         }
		  catch(IOException e)
		  {
			  System.out.println("File read Error"+e);
		  }
		return s;
	}
	public void readData(int[] a,int n){
		File file = new File("TXT/save.txt");
		try{
			FileInputStream fis = new FileInputStream(file);
			DataInputStream inData = new DataInputStream(fis);
			
			int i = 0;
			while(i < n){
				a[i++] = inData.readInt();
			}
			fis.close();
			inData.close();
		}
		catch(IOException e){
			
		}
	}
	public void readData(String[] a,int n){
		File file = new File("TXT/admin.txt");
		try{
			FileInputStream fis = new FileInputStream(file);
			DataInputStream inData = new DataInputStream(fis);
			
			int i = 0;
			while(i < n){
				a[i++] = inData.readLine();
			}
			fis.close();
			inData.close();
		}
		catch(IOException e){
			
		}
	}
	public int readData(String name){
		File file = new File("TXT/"+name+".txt");
		int a = 0;
		try{
			FileInputStream fis = new FileInputStream(file);
			DataInputStream inData = new DataInputStream(fis);
			
			int i = 0;
			a = inData.readInt();
			fis.close();
			inData.close();
		}
		catch(IOException e){
			
		}
		return a;
	}

}
