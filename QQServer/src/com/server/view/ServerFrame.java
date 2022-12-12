package com.server.view;
/**
 *�������Ŀ��ƽ��棬��ʵ�����¹��ܣ�
 * 1���������ߵ��û�
 * 2����ʾ�û���¼��ʱ��
 * 3�����Ʒ�������������ֹͣ
 */

import com.common.UserInfoBean;
import com.server.data.ConnectionFactory;
import com.tools.ServerThread;
import com.tools.ServerToClientThread;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

public class ServerFrame extends JFrame implements ActionListener{
	
    JPanel contentPane;
    JPanel leftPane = new JPanel();
    JPanel rightPane = new JPanel();
    JLabel timeLabel = new JLabel();
    Border border1 = BorderFactory.createLineBorder(UIManager.getColor("ProgressBar.selectionBackground"), 1);//�����Զ�������׼�߽����Ĺ�����

    Border border2 = new TitledBorder(border1, "�����û��б�");
    JPanel jPanel2 = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    DefaultListModel listModel = new DefaultListModel();
    JList userList = new JList(listModel);
    JLabel jLabel1 = new JLabel();


    JButton lookInfoButton = new JButton();//�鿴�û���Ϣ
    JButton jButton2 = new JButton();//�߳�


    JLabel jLabel2 = new JLabel();
    JLabel userNum = new JLabel();
    JLabel jLabel3 = new JLabel();
    JScrollPane jScrollPane2 = new JScrollPane();
    JTextArea serverInfo = new JTextArea();
    JPanel jPanel1 = new JPanel();
    JButton pauseButton = new JButton();//�������
    JButton exitButton = new JButton();//�˳�
    JButton getServerIP = new JButton();//��ȡ������IP
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    FlowLayout flowLayout1 = new FlowLayout();


    private Hashtable userTable= new Hashtable();

    private Connection con = null; //���ݿ����Ӷ���
    ServerThread serverThread = null;
    ServerToClientThread ctst = null;
    
    boolean isStartThread = false;
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		new ServerFrame();
	}
	
    public ServerFrame() {
        try {
            //�������ݿ�����
            con = ConnectionFactory.getConnnection();
      
        	ctst = new ServerToClientThread();
        	ctst.start();//�����Ϳͻ���ͨ�ŵ��߳�
        	
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
            serverThread = new ServerThread(listModel,userNum,serverInfo);
 //           serverThread.start();
            //�����ڱ�ǩ�ж�̬��ʾʱ��
            java.util.Timer myTimer1 = new java.util.Timer();

            java.util.Timer myTimer2 = new java.util.Timer();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    protected void processWindowEvent(WindowEvent e){
        if(e.getID() == WindowEvent.WINDOW_CLOSING){
            int option = JOptionPane.showConfirmDialog(this,"��ȷ��Ҫ�˳�ô��");
            if(option == JOptionPane.YES_OPTION){
  //              DBcon.closeConnection();//�ر����ݿ����Ӷ���
  //              Server.DBcon.closeConnection();//�رշ������̵߳����ݿ�����
            	ctst.closeSocket();

                System.exit(0);
            }
        }
    }

    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(gridBagLayout1);
        setBounds(0,0,850,675);
     //   setSize(new Dimension(640, 475));
        setTitle("�������˿��ƽ���");
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();//�����Ļ��С
        setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);//����������
        setVisible(true);
       
        leftPane.setBorder(BorderFactory.createEtchedBorder());
        leftPane.setPreferredSize(new Dimension(202, 150));

        leftPane.setLayout(borderLayout1);
        rightPane.setLayout(borderLayout2);

        timeLabel.setBorder(null);
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timeLabel.setText("jLabel1");
        jPanel2.setLayout(gridBagLayout2);
        jLabel1.setMaximumSize(new Dimension(82, 50));
        jLabel1.setPreferredSize(new Dimension(82, 25));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("�����û��б�");
        lookInfoButton.setText("�鿴��Ϣ");
        lookInfoButton.addActionListener(this);
        jButton2.setText("�߳�");
        jButton2.addActionListener(this);
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);        
        jLabel2.setText("��������:");
        jLabel3.setBorder(null);
        jLabel3.setMaximumSize(new Dimension(100, 50));
        jLabel3.setMinimumSize(new Dimension(100, 25));
        jLabel3.setPreferredSize(new Dimension(100, 25));
        jLabel3.setText("    ��������־��");
        serverInfo.setEditable(false);
        pauseButton.setText("��������");
        pauseButton.addActionListener(this);
        exitButton.setText("�˳�");
        exitButton.addActionListener(this);
        getServerIP.setText("������IP");
        getServerIP.addActionListener(this);
        jPanel2.setBorder(null);
        jPanel1.setBorder(null);
        jPanel1.setLayout(flowLayout1);
        rightPane.setBorder(BorderFactory.createEtchedBorder());
        flowLayout1.setHgap(30);
        jScrollPane1.getViewport().add(userList);
        jScrollPane2.getViewport().add(serverInfo);
        jPanel1.add(pauseButton);
        jPanel1.add(exitButton);
        jPanel1.add(getServerIP);
        leftPane.add(jLabel1, BorderLayout.NORTH);
        leftPane.add(jScrollPane1, BorderLayout.CENTER);
        leftPane.add(jPanel2, BorderLayout.SOUTH);
        rightPane.add(jLabel3, BorderLayout.NORTH);
        rightPane.add(jScrollPane2, BorderLayout.CENTER);
        rightPane.add(jPanel1, BorderLayout.SOUTH);
        jPanel2.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(9, 12, 0, 0), 20, 12));
        jPanel2.add(userNum, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(10, 23, 0, 22), 19, 9));
        jPanel2.add(lookInfoButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(13, 9, 0, 0), 0, 0));
        jPanel2.add(jButton2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(13, 18, 0, 7), 24, 0));
        contentPane.add(rightPane, new GridBagConstraints(1, 0, 1, 1, 0.7, 0.9
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 300, 334));
        contentPane.add(leftPane, new GridBagConstraints(0, 0, 1, 1, 0.3, 0.9
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 10, 186));
        contentPane.add(timeLabel, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.1
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 570, 9));
    }

    /**
     * ɾ���û�
     * @param QQNUM
     */
    public void removeUser(int QQNUM){
        String sql = "UPDATE Login SET lstatus = 0 WHERE lqq = "+ QQNUM;
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * ���ݺ��ѵ�QQ�Ų��Һ��ѵ���Ϣ��
     * @param e
     */
    public void lookInfoButton_actionPerformed(ActionEvent e) {
        String selectedUser = null;
        Integer QQNUM = null;
        selectedUser = (String)userList.getSelectedValue();
        if(selectedUser == null){
            JOptionPane.showMessageDialog(this,"�뵥�����ѡ��һ���û���");
        }else{
            System.out.println(selectedUser);
            QQNUM = new Integer(selectedUser.substring(selectedUser.indexOf("(") + 1, selectedUser.indexOf(")")));
            //���ݺ��ѵ�QQ�Ų��Һ��ѵ���Ϣ��
            UserInfoBean user = (UserInfoBean)userTable.get(QQNUM);
            UserInfo userInfo = new UserInfo(this,"�û��Ļ�����Ϣ",user);
            userInfo.setVisible(true);
        }
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        int index = userList.getSelectedIndex();
        Integer QQNUM = null;
        if(index == -1){
            JOptionPane.showMessageDialog(this,"�뵥�����ѡ��һ���û���");
        }else{
            String userInfo = (String)listModel.getElementAt(index);
            System.out.println(userInfo);
            QQNUM = new Integer(userInfo.substring(userInfo.indexOf("(") + 1, userInfo.indexOf(")")));
            System.out.println(QQNUM);
            removeUser(QQNUM);
            listModel.remove(index);
            int num = Integer.parseInt(userNum.getText())-1;
            userNum.setText(new Integer(num).toString());
        }
    }

    public void pauseButton_actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("��������")){
        	isStartThread = true;
            serverThread.start();
            pauseButton.setText("��ͣ����");
        }else if(command.equals("��ͣ����")){
            serverThread.pauseThread();
            pauseButton.setText("�ָ�����");
        }else if(command.equals("�ָ�����")){
            serverThread.reStartThread();
            pauseButton.setText("��ͣ����");
        }
    }

    public void exitButton_actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(this,"��ȷ��Ҫ�˳�ô��");
        if(option == JOptionPane.YES_OPTION){
        	ctst.closeSocket();
            System.exit(0);
            }
    }
    
    public void getServerIPButton_actionPerformed(ActionEvent e) {
    	InetAddress localAddr = null;
		try {
			localAddr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		String localIP = localAddr.getHostAddress();

		JOptionPane.showMessageDialog(null, localIP);
    }
    public void actionPerformed(ActionEvent e){
       if (e.getSource() == pauseButton)//��������
          pauseButton_actionPerformed(e);
       else if (e.getSource() == exitButton)//�˳�
          exitButton_actionPerformed(e);
       else if (e.getSource() == jButton2)//�߳��û�
          jButton2_actionPerformed(e);
       else if (e.getSource() == lookInfoButton)//��ѯ�û�
          lookInfoButton_actionPerformed(e);
       else if (e.getSource() == getServerIP)
    	   getServerIPButton_actionPerformed(e);
    }
}
