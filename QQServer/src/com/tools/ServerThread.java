package com.tools;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ���������ServerSocket���ȴ��ͻ��˵����ӣ�
 * ����Ҫ������¹��ܣ�
 * 1�����ļ�dbProperties.txt�ж�ȡԤ���趨�õĶ˿ں��Դ���ServerSocet
 * 2���ڷ����������������ʾ�û���������Ϣ
 */

public class ServerThread extends Thread {

    JTextArea area = new JTextArea();//����Ϣʵʱ��ʾ�ڽ�����
    DefaultListModel listModel = new DefaultListModel();
    JLabel jLabel2 = new JLabel();
    Boolean flag = true;
    String line_separator = System.getProperty("line.separator");//����

    public ServerThread(){}
    
    public ServerThread(DefaultListModel listModel,JLabel jLabel2,JTextArea area) {
    	this.listModel = listModel;
    	this.jLabel2 = jLabel2;
        this.area = area;
    }

    //��ȡ�˿ں�
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
            area.append("�������ɹ�����,���ڵȴ��ͻ�������......" + line_separator);
            area.append(line_separator);
            while (flag) {
                System.out.println("�������ɹ�����,�ȴ��ͻ�������......");
                Socket socket = s.accept();

                area.append("************************" + line_separator);
                area.append("�û� "+socket + "������" + line_separator);
                //System.out.println("Connection accept:" + socket);
                Date time = new Date();
              
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                String timeInfo = format.format(time);
                area.append("����ʱ�䣺" + timeInfo + line_separator);
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
