package com.client.business.uiManager;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class MessageLoggingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public MyTextPane  tp_messagelogging = new MyTextPane();
	public JScrollPane spanel;
	public MessageLoggingPanel(){
		this.setVisible(false);
		setBounds(446,146,362, 342);	
		this.setLayout(null);
		tp_messagelogging.setOpaque(false);   //透明
		tp_messagelogging.setEditable(false);  //不可编辑

		tp_messagelogging.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent mouseEvent){
				tp_messagelogging.setCursor(new Cursor(Cursor.TEXT_CURSOR)); //鼠标进入Text区后变为文本输入指针
			}
			public void mouseExited(MouseEvent mouseEvent){
				tp_messagelogging.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); //鼠标离开Text区后恢复默认形态
			}
		});
		spanel=new JScrollPane(tp_messagelogging);
		spanel.setOpaque(false);  //透明
		spanel.getViewport().setOpaque(false);//透明
		spanel.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);  
		spanel.setVisible(true);
		spanel.setBounds(0,0,365, 413);
		add(spanel);
	}

}
