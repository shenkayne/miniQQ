package com.client.business.mainRender;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * 重写JTree
 */
public class IconNodeRenderer extends DefaultTreeCellRenderer// 继承该类
{
	// 重写该方法
	IconNodeRenderer	main;
	private String text1 = "";
	private String text2 = "";
	private String text3 = "";
	private Icon touxiang = null;
	private boolean isSelect;
	private boolean isleaf;
	String beijing = "C:\\Users\\Administrator\\Desktop\\beijing.png";
	Image Ibeijing = Toolkit.getDefaultToolkit().getImage(beijing);
	public IconNodeRenderer(){
		main=this;
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				text1="fuck";
				main.repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
			}
		});
	}
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus); // 调用父类的该方法
		Icon icon = ((IconNode) value).getIcon();// 从节点读取图片
		this.touxiang = icon;
		String[] txt = ((IconNode) value).getText(); // 从节点读取文本
		setIcon(icon);// 设置图片
		setText(text1);// 设置文本
		// this.text1=txt;
		this.text1 = txt[0];
		this.text2 = txt[1];
		this.text3 = txt[2];
		this.setPreferredSize(new Dimension(280, 55));
		isSelect = sel;
		isleaf = leaf;
		return this;
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (isleaf) {
			if (isSelect) {
				g.setColor(Color.LIGHT_GRAY);
				g.fill3DRect(0, 0, 290, 50, true);
				if (touxiang != null)
					touxiang.paintIcon(this, g, 2, 2);
				// g.drawImage( touxiang., 2, 2, this);
				g.setColor(Color.black);
				g.setFont(new Font("宋体", 20, 20));
				g.drawString(this.text1, 50, 20);

				g.setColor(Color.blue);
				g.setFont(new Font("宋体", 17, 15));
				g.drawString(this.text2, 50, 35);

				g.setColor(Color.BLACK);
				g.setFont(new Font("宋体", 12, 11));
				g.drawString(this.text3, 50, 50);
			}
			else {
				// g.drawImage(Ibeijing, 0, 0, null);
				g.setColor(this.getBackground());
				
				g.fill3DRect(0, 0, 290, 50, true);
				if(touxiang!=null)
				touxiang.paintIcon(this, g, 2, 2);
				// g.drawImage( touxiang., 2, 2, this);
				g.setColor(Color.black);
				g.setFont(new Font("宋体", 20, 20));
				g.drawString(this.text1, 50, 20);

				g.setColor(Color.blue);
				g.setFont(new Font("宋体", 17, 15));
				g.drawString(this.text2, 50, 35);

				g.setColor(Color.BLACK);
				g.setFont(new Font("宋体", 12, 11));
				g.drawString(this.text3, 50, 50);
			}
		} else {
			 {
				// g.drawImage(Ibeijing, 0, 0, null);
				g.setColor(Color.pink);
				g.fill3DRect(0, 0, 290, 50, true);
				g.setColor(Color.red);
				g.setFont(new Font("宋体", 20, 25));
				g.drawString(this.text1, 50, 35);
				
				
			}

		}

	}
}



