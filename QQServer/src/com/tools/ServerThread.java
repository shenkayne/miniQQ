package com.tools;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 这个类启动ServerSocket并等待客户端的连接，
 * 它主要完成以下功能：
 * 1、从文件dbProperties.txt中读取预先设定好的端口号以创建ServerSocet
 * 2、在服务器控制面板中显示用户的请求信息
 */

public class ServerThread extends Thread {

    JTextArea area = new JTextArea();//将信息实时显示在界面上
    DefaultListModel listModel = new DefaultListModel();
    JLabel jLabel2 = new JLabel();
    Boolean flag = true;
    String line_separator = System.getProperty("line.separator");//换行

    public ServerThread(){}
    
    public ServerThread(DefaultListModel listModel,JLabel jLabel2,JTextArea area) {
    	this.listModel = listModel;
    	this.jLabel2 = jLabel2;
        this.area = area;
    }

    //读取端口号
    private int getPort() {
        int port = 9999;
        return port;
    }

    public void pauseThread(){
        this.flag = false;
    }

    public void reStartThread(){
        this.flag = true;
    }

    public void run() {
        try {
            ServerSocket s = new ServerSocket(getPort());
//            System.out.println(line_separator);
            area.append("服务器成功启动,正在等待客户的请求......" + line_separator);
            area.append(line_separator);
            while (flag) {
                System.out.println("服务器成功启动,等待客户端连接......");
                Socket socket = s.accept();

                area.append("************************" + line_separator);
                area.append("用户 "+socket + "已连接" + line_separator);
                //System.out.println("Connection accept:" + socket);
                Date time = new Date();
              
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                String timeInfo = format.format(time);
                area.append("处理时间：" + timeInfo + line_separator);
                area.append("************************" + line_separator);
                area.append(line_separator);
                area.append(line_separator);

                int serverNo = ManageClientThread.clientNum;
                Server server = new Server(socket,listModel,jLabel2,serverNo);
                ManageClientThread.addClientThread(server);
                
                new Thread(server).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
