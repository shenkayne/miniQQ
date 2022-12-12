package com.server.business.uiManager;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;


/**
 *
 */
public class MyTextPane extends JTextPane{

	private static final long serialVersionUID = 1L;
	public MyTextPane(){
		
	}
	public void insert(String str,AttributeSet attrSet)   {   
		Document doc = getDocument();
	//	if(doc.getLength()!=0){
		//str =str+"\n";   
		//}
		try{   
			doc.insertString(doc.getLength(),str,attrSet);   
		}catch(BadLocationException e){   
			System.out.println("BadLocationException:"+e);   
		}   
	}   

	public void setDocs(String str,Color col,String font,boolean bold,boolean Italic,boolean Underline,int fontSize)   {   
		SimpleAttributeSet attrSet = new SimpleAttributeSet();   
		StyleConstants.setForeground(attrSet,col);   //��ɫ   
		if(bold==true){   
			StyleConstants.setBold(attrSet,true); //����   
		}
		if(Italic==true){
		StyleConstants.setItalic(attrSet,true); //б��
		}
		if(Underline==true){
		StyleConstants.setUnderline(attrSet,true); //�»���
		}
		StyleConstants.setFontFamily(attrSet,font);  //����
		StyleConstants.setFontSize(attrSet,fontSize);   //�����С   
		insert(str,attrSet);
	}   
	public void gui(){   
		//textPane.insertIcon(image);   
		//setDocs("��һ�е�����",Color.red,false,20);  
	//	textPane.setFont(new Font("����",0,25));
		//textPane.setText("�����ƺ��������");
		///setDocs("�ڶ��е�����",Color.BLACK,true,25); 
		//setDocs("�����е�����",Color.BLACK,false,25); 
		//setDocs("�����е�����",Color.BLUE,false,20);   
	/*	frame.getContentPane().add(textPane,   BorderLayout.CENTER);   
		frame.addWindowListener(new WindowAdapter(){   
			public   void   windowClosing(WindowEvent e)   {   
				System.exit(0);   
			}});   
		frame.setSize(200,300);   
		frame.setVisible(true);
		*/   
	}

}
