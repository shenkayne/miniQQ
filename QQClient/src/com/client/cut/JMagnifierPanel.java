package com.client.cut;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import javax.swing.JPanel;

/**
 * 重写面板实现放大镜效果
 */
public class JMagnifierPanel extends JPanel {
    private Image screenImage;//屏幕
    private int magnifierSize;//放大镜的尺寸
    private int locationX;
    private int locationY;
    private Robot robot;//该类用于为测试自动化、自运行演示和其他需要控制鼠标和键盘的应用程序生成本机系统输入事件。Robot的主要目的是促进Java平台实现的自动化测试

    //放大尺寸
    public JMagnifierPanel(int magnifierSize) {
        try {
            robot = new Robot();

        } catch (AWTException e) {
            e.printStackTrace();
        }
        // 截屏幕
        screenImage = robot.createScreenCapture(new Rectangle(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height));
        this.magnifierSize = magnifierSize;

    }

    //设置放大镜的位置 x坐标  y坐标
    public void setMagnifierLocation(int locationX, int locationY) {

        this.locationX = locationX;
        this.locationY = locationY;
        repaint();        // 注意重画控件
    }

    //设置放大镜的尺寸 magnifierSize 放大镜尺寸
    public void setMagnifierSize(int magnifierSize) {

        this.magnifierSize = magnifierSize;
    }

    public void paintComponent(Graphics g) {

        super.paintComponent((Graphics2D) g);
        // 关键处理代码
        g.drawImage(
                screenImage,                 // 要画的图片
                0,                    // 目标矩形的第一个角的x坐标
                0,                    // 目标矩形的第一个角的y坐标
                magnifierSize,                 // 目标矩形的第二个角的x坐标
                magnifierSize,                 // 目标矩形的第二个角的y坐标
                locationX + (magnifierSize / 4),     // 源矩形的第一个角的x坐标
                locationY + (magnifierSize / 4),    // 源矩形的第一个角的y坐标
                locationX + (magnifierSize / 4 * 3),     // 源矩形的第二个角的x坐标
                locationY + (magnifierSize / 4 * 3),     // 源矩形的第二个角的y坐标
                this);
    }

}