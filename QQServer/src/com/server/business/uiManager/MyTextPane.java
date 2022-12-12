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
		StyleConstants.setForeground(attrSet,col);   //颜色   
		if(bold==true){   
			StyleConstants.setBold(attrSet,true); //字形   
		}
		if(Italic==true){
		StyleConstants.setItalic(attrSet,true); //斜体
		}
		if(Underline==true){
		StyleConstants.setUnderline(attrSet,true); //下划线
		}
		StyleConstants.setFontFamily(attrSet,font);  //字体
		StyleConstants.setFontSize(attrSet,fontSize);   //字体大小   
		insert(str,attrSet);
	}   
	public void gui(){   
		//textPane.insertIcon(image);   
		//setDocs("第一行的文字",Color.red,false,20);  
	//	textPane.setFont(new Font("宋体",0,25));
		//textPane.setText("电算会计函数大会上");
		///setDocs("第二行的文字",Color.BLACK,true,25); 
		//setDocs("第三行的文字",Color.BLACK,false,25); 
		//setDocs("第四行的文字",Color.BLUE,false,20);   
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
