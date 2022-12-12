package com.client.cut;


import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;


/**
 * 重写JFrame实现放大效果
 *
 */
public class Magnifier extends JFrame {
	private Container container = getContentPane(); // 主容器
	private int setCoordinateX; // 放大镜x坐标
	private int setCoordinateY;// 放大镜y坐标
	private int absoluteX;// 鼠标绝对x坐标
	private int absoluteY;// 鼠标绝对Y坐标
	private int relativeX;// 鼠标按下时的相对x坐标
	private int relativeY;// 鼠标按下时的相对x坐标
	private boolean mousePressedNow;// 标记鼠标是否按下。如果按下则为true，否则为false
	private int magnifierSize = 100;// 放大镜尺寸
	public JMagnifierPanel magnifierPanel = new JMagnifierPanel(magnifierSize);// 放大镜内容面板

	public Magnifier() {// 构造函数，创建一个放大镜窗体
		setUndecorated(true); // 窗体边缘
	
		//this.setBackground(Color.red);
		setResizable(false);
		container.add(magnifierPanel);
		addMouseListener(new MouseFunctions());
		addMouseMotionListener(new MouseMotionFunctions());
		updateSize(magnifierSize);
		this.setVisible(true);
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// Repaint();
				// TODO Auto-generated method stub
				setLocation((int)e.getLocationOnScreen().getX() + 20,(int) e.getLocationOnScreen().getY() + 20);
			magnifierPanel.setMagnifierLocation(e.getX(), e.getY());

				setVisible(true);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		this.setLayout(null);
		this.setBackground(Color.red);
		this.setForeground(Color.black);
		this.setBounds(0, 0, 200, 200);
		this.setVisible(false);
	}



	/**
	 * 更新窗体 放大镜尺寸
	 */
	public void updateSize(int magnifierSize) {

		magnifierPanel.setMagnifierSize(magnifierSize + 100);
		setSize(magnifierSize + 100, magnifierSize + 100);
		validate(); // 更新所有子控件
	}

	private class MouseFunctions extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			if (e.getClickCount() == 1) {// 如果鼠标左键点了一下，说明按住了窗体
				mousePressedNow = true;
				relativeX = e.getX();
				relativeY = e.getY();
			}
		}

		public void mouseReleased(MouseEvent e) {

			mousePressedNow = false;
		}
	}

	private class MouseMotionFunctions extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			if (mousePressedNow == true) {// 如果此时鼠标按下了，说明在拖拽窗体

				absoluteX = Magnifier.this.getLocationOnScreen().x + e.getX();
				absoluteY = Magnifier.this.getLocationOnScreen().y + e.getY();
				setCoordinateX = absoluteX - relativeX;
				setCoordinateY = absoluteY - relativeY;
				magnifierPanel.setMagnifierLocation(setCoordinateX,
						setCoordinateY);
				setLocation(setCoordinateX, setCoordinateY);

			}

		}

	}

//	/**
//	 * 程序入口点 启动参数，这里为空
//	 */
//	public static void main(String arg[]) {
//
//		Magnifier magnifier = new Magnifier();
//		magnifier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
}
