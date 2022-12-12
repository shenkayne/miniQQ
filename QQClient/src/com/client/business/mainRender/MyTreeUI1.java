package com.client.business.mainRender;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTreeUI;

public class MyTreeUI1 extends BasicTreeUI {
	/**
	 * 重写BasicTreeUI 中的setLeftChildIndent和setRightChildIndent方法， 以达到对齐父子节点
	 */
	protected void paintVerticalLine(Graphics g, JComponent c, int x, int top,
			int bottom) {
	}

	protected void paintHorizontalLine(Graphics g, JComponent c, int y,
			int left, int right) {
	}

	public void setLeftChildIndent(int newAmount) {
		super.setLeftChildIndent(0);
	}

	public void setRightChildIndent(int newAmount) {
		super.setRightChildIndent(5);
	}
}