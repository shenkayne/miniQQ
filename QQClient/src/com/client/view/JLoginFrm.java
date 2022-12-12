package com.client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import javax.swing.JFrame;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.client.business.resourceManager.ResourceManager;
import com.client.business.uiManager.Login;
import com.tools.ClientToServer;
import com.common.User;
/**
 * ��¼����
 */
public class JLoginFrm extends JFrame implements ActionListener,ItemListener{
	private static final long serialVersionUID = 12L;

	private  BackgroundPanel contentPanel = new BackgroundPanel();
	MyButton btn_Login;//��¼��ť
	MyButton btn_Cancel;//ȡ����ť
	MyButton btn_Register;//ע�ᰴť
	MyButton btn_ForgotPwd;//��������
	MyButton btn_close;//�رհ�ť
	MyButton btn_min;//��С����ť
	JTextField name;//�û���
	JCheckBox cb_pwd;//��ס���븴ѡ��
	JPasswordField password;//����
	
	int number=0;
	JMainFrm mainFrm = null;//��ҳ����
	Login login = null;//������¼��֤

	public static void main(String[] args) {
		try {
			new JLoginFrm();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JLoginFrm() {
		setUndecorated(true);//���û����ô�֡��װ�Ρ� �˷���ֻ���ڿ�ܲ�����ʾʱ���á�Ҫװ�������ܣ��������ǲ�͸���ģ�������Ĭ����״��������
		setResizable(false);//���ò��ɸı�����С
		setBounds(500, 250, 429, 330);

		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();//�����Ļ��С
		setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);//����������

		{//�û��������			
			name = new JTextField();
			name.setOpaque(false);//���Ϊtrue�������������߽��ڵ�ÿ�����ء�����������ܲ���������Ĳ��ֻ��������أ��Ӷ�����ײ�������ʾ������
			name.setBounds(133,195,194,30);
			name.addActionListener(this);
			name.addKeyListener(new KeyListener(){
				//���ָ���ļ��������ԴӴ�������ռ��¼������lΪ�գ����׳��쳣��Ҳ��ִ���κβ���
				/**
				 * �Լ��̽��м���
				 * @param e
				 */
				@Override
				public void keyTyped(KeyEvent e) {
					int keyChar=e.getKeyChar();
					if(name.getText().compareTo("")==0){
						number=0;
					}
					if (keyChar>=KeyEvent.VK_0 && keyChar<=KeyEvent.VK_9) {
						if(number==0&&keyChar==KeyEvent.VK_0){
							e.consume();
						}else{
						number++;
						}
					} else {
						e.consume(); //�����������¼������������¼�
					}
				}

				@Override
				public void keyPressed(KeyEvent e) {
					
				}

				@Override
				public void keyReleased(KeyEvent e) {
					
				}
				
			});
			name.addCaretListener(new CaretListener(){
				//��Ӳ����������������֪ͨ��������κθ��ġ� �β�:
				@Override
				public void caretUpdate(CaretEvent e) {
					name.setOpaque(true);
					if(password.getText().compareTo("")==0){
						contentPanel.repaint();
						password.setOpaque(false);
					}
				}
			});
			contentPanel.add(name);
		}

		{//���������			
			password = new JPasswordField();
			password.setBounds(133,223,194,30);
			password.setOpaque(false);
			
			password.addCaretListener(new CaretListener(){

				@Override
				public void caretUpdate(CaretEvent e) {
					password.setOpaque(true);
					if(name.getText().compareTo("")==0){
						contentPanel.repaint();
						name.setOpaque(false);
					}
				}
			});
			contentPanel.add(password);
		}

		{//��ס����
			cb_pwd = new JCheckBox();
			login = new Login();
			String []rem = new String[2];
			if(login.isRemember()){
				login.ReadUserInfo(rem);
				name.setText(rem[0]);
				password.setText(rem[1]);
				cb_pwd.setSelected(true);
				name.setOpaque(true);
				password.setOpaque(true);
			}
			cb_pwd.setBounds(129, 254,72,27);
			cb_pwd.setOpaque(false);
			contentPanel.add(cb_pwd);
		
			cb_pwd.addActionListener(this); //����¼�����
		}

		{//��¼��ť
			btn_Login = new MyButton();
			btn_Login.setBounds(133, 286,195,33);
			btn_Login.setContentAreaFilled(false);
			contentPanel.add(btn_Login);
		
			btn_Login.addActionListener(this); //����¼�����
		}
		{//ע�ᰴť
			btn_Register = new MyButton();//ע��
			btn_Register.setBounds(328,195,63,26);
			btn_Register.setContentAreaFilled(false);
			contentPanel.add(btn_Register);	
			btn_Register.addActionListener(this);//����¼�����
		}
		{//�һ�����
			btn_ForgotPwd=new MyButton();
			btn_ForgotPwd.setBounds(328,226,63,26);
			btn_ForgotPwd.setContentAreaFilled(false);
			contentPanel.add(btn_ForgotPwd);
			btn_ForgotPwd.addActionListener(this);//����¼�����
		}
		{//ȡ����ť
		    btn_Cancel = new MyButton();
		   
		    btn_Cancel.setBounds(114, 252,159,34);
		    btn_Cancel.setContentAreaFilled(false);
		    btn_Cancel.setVisible(false);
		    contentPanel.add(btn_Cancel);
		
			btn_Cancel.addActionListener(this); //����¼�����
		}
		{//��С��
			btn_min=new MyButton();
			btn_min.setBounds(370,2,28,28);
			btn_min.setContentAreaFilled(false);
			contentPanel.add(btn_min);	
			btn_min.addActionListener(this);//����¼�����
			
		}
		{//�رմ���
			btn_close=new MyButton();
			btn_close.setBounds(400,2,28,28);
			btn_close.setContentAreaFilled(false);
			contentPanel.add(btn_close);	
			btn_close.addActionListener(this);//����¼�����
			
		}
		contentPanel.setLayout(new BorderLayout());
		
		contentPanel.addMouseMotionListener(new MouseAdapter() {
			    private Point draggingAnchor = null;
	            @Override
	            public void mouseMoved(MouseEvent e) {
	            	draggingAnchor = new Point(e.getX() + contentPanel.getX(), e.getY() + contentPanel.getY());
	            }
	            
	            @Override
	            public void mouseDragged(MouseEvent e) {
	            	setLocation(e.getLocationOnScreen().x - draggingAnchor.x, e.getLocationOnScreen().y - draggingAnchor.y);
	            }
		});
		
		
		//������õı�����嵽��ǰ���
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	// ��ť��Ӧ�¼�
	@SuppressWarnings("null")
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == btn_Login) { // ��¼

			ClientToServer cts = new ClientToServer(mainFrm);//����ҳ��
			new Thread(cts).start();

			Login login = new Login(cts);
			String userName = name.getText().trim();
			String pwd = new String(password.getPassword());
            User user = new User();
            user.setUserId(userName);
            user.setPasswd(pwd);
			try {
				if (userName.equals("")) { // QQ���Ƿ�Ϊ��
					 JOptionPane.showMessageDialog(null, "������QQ��!");
				} else if (pwd.equals("")) {// �����Ƿ�Ϊ��
					 JOptionPane.showMessageDialog(null, "����������!");
				} else if (login.checkUser(user)) { // �˶��û��Ƿ�Ϸ�	
					 contentPanel.knight="login2";
			         contentPanel.repaint();
			         name.setVisible(false);
			         password.setVisible(false);
			         cb_pwd.setVisible(false);
			         btn_Login.setVisible(false);
			         btn_Register.setVisible(false);
			         btn_ForgotPwd.setVisible(false);
			         btn_Cancel.setVisible(true);
			         // ReceivePort receiveport = new ReceivePort();

					if (cb_pwd.isSelected()) {//��ס���븴ѡ��ѡ��
						login.remember(userName, pwd);
					} else {
						login.remember("", "");
					}
			         this.dispose();
				} else {
					String str = cts.getLoginInfo();
					if(str.equals("loginFail")){
					JOptionPane.showMessageDialog(null, "��������û������������!");
					}else if(str.equals("reLogin")){
						JOptionPane.showMessageDialog(null, "�����ظ���¼!");
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (source == btn_Register) { // ����ע�ᴰ��
			this.setVisible(false);
			JRegisterFrm register = new JRegisterFrm(this);
			register.setVisible(true);
		} else if (source == btn_Cancel) { // �˳������ڣ����ͷ���Դ
			//System.exit(0);
			//this.dispose();
		    contentPanel.knight="login";
			 contentPanel.repaint();
			 name.setVisible(true);
			 password.setVisible(true);
			 cb_pwd.setVisible(true);
			 btn_Login.setVisible(true);
			 btn_Register.setVisible(true);
			 btn_ForgotPwd.setVisible(true);
			 btn_Cancel.setVisible(false);
		}else if(source == btn_close){
			System.exit(0);
		}else if(source == btn_min){//��С��
			setExtendedState(JFrame.ICONIFIED);
		}else if(source==btn_ForgotPwd){//��������
//			JRegisterFrm register = new JRegisterFrm(this);
//
//			register.l_name.setVisible(false);
//			register.name.setVisible(false);
//			register.l_password.setVisible(false);
//			register.password.setVisible(false);
//			register.l_repassword.setVisible(false);
//			register.resure_password.setVisible(false);
//			register.l_xl.setVisible(false);
//			register.diploma.setVisible(false);
//			register.l_pho.setVisible(false);
//			register.telephone.setVisible(false);
//			register.l_email.setVisible(false);
//			register.email.setVisible(false);
//			register.l_adr.setVisible(false);
//			register.address.setVisible(false);
//			register.btn_OK.setVisible(false);
//			register.l_icon.setVisible(false);
//			register.comboBox_icon.setVisible(false);
//			register.l_bloodType.setVisible(false);
//			register.bloodType.setVisible(false);
//			register.setVisible(true);
//			//
//			register.contentPanel.knight="findpwd";
//			register.l_qq.setVisible(true);
//			register.qq.setVisible(true);
//			register.btn_findpwd.setVisible(true);

			int qq = Integer.parseInt(name.getText().trim());
			JGetBackPwdFrm getbackpwd=new JGetBackPwdFrm(qq,true);
			getbackpwd.label.setText("��ش��������⣿");
			getbackpwd.knight="findpwd";
			getbackpwd.contentPanel.repaint();
			getbackpwd.btn_find.setVisible(true);
		}
		
	}
	public void closeWidows(){
		this.dispose();
		
	}
	public void itemStateChanged(ItemEvent e){
		Object source = e.getSource();

		if (source == cb_pwd){
			if(cb_pwd.isSelected()){
				cb_pwd.setSelected(false);
				
			}else{
				cb_pwd.setSelected(true);
				
			}
			
		}
	}
	

	class BackgroundPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String knight ; //Ҫ���ص�ͼƬ��Դ���ļ���
		public BackgroundPanel(){
			knight= "login";
		}
		public void setbk()
		{
			
		}
		//��д�����������
		public void paintComponent(Graphics g)
		{
		int x = 0,y = 0;
		
		//������Դ�������еļ���ͼƬ��Դ�����������ر���ͼƬ
		ResourceManager imageResource = new ResourceManager();
		ImageIcon icon = new ImageIcon();
		icon = imageResource.GetImage(knight);
		//���ƴ���
		g.drawImage(icon.getImage(),x,y,icon.getIconWidth(),icon.getIconHeight(),this);
		}
	}
}
