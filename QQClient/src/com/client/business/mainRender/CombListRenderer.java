package com.client.business.mainRender;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

public class CombListRenderer extends JLabel implements ListCellRenderer{

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	/**
	 * 重写Comblist的Render
	 */
	public Component getListCellRendererComponent(JList list,Object obj,
			int row,boolean sel,boolean hasFocus)
	 {
		// TODO Auto-generated method stub
		Object[] cell=(Object[])obj;
		  setIcon((Icon)cell[0]);//设置图片
		  setText((cell[1].toString()));//设置文本
		//  setToolTipText(cell[2].toString());//设置提示文本
		//  setBorder(new LineBorder(Color.gray));//绘制边框
		  
		  
		  if(sel)//如果选中
		  {
		   setForeground(Color.green);//设前景色为蓝色
		   setBackground(Color.blue);
		   repaint();
		   
		  }
		  else//没选中
		  {
		   setForeground(list.getForeground());//设前景色为默认
		   setBackground(list.getBackground());
		  }
		  return this; 
		 }
	

}
