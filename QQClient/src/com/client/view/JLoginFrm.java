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
 * 登录界面
 */
public class JLoginFrm extends JFrame implements ActionListener,ItemListener{
	private static final long serialVersionUID = 12L;

	private  BackgroundPanel contentPanel = new BackgroundPanel();
	MyButton btn_Login;//登录按钮
	MyButton btn_Cancel;//取消按钮
	MyButton btn_Register;//注册按钮
	MyButton btn_ForgotPwd;//忘记密码
	MyButton btn_close;//关闭按钮
	MyButton btn_min;//最小化按钮
	JTextField name;//用户名
	JCheckBox cb_pwd;//记住密码复选框
	JPasswordField password;//密码
	
	int number=0;
	JMainFrm mainFrm = null;//主页界面
	Login login = null;//辅助登录验证

	public static void main(String[] args) {
		try {
			new JLoginFrm();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JLoginFrm() {
		setUndecorated(true);//禁用或启用此帧的装饰。 此方法只能在框架不可显示时调用。要装饰这个框架，它必须是不透明的，并具有默认形状，否则将抛
		setResizable(false);//设置不可改变面板大小
		setBounds(500, 250, 429, 330);

		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();//获得屏幕大小
		setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);//设置面板居中

		{//用户名输入框			
			name = new JTextField();
			name.setOpaque(false);//如果为true，组件将绘制其边界内的每个像素。否则，组件可能不会绘制它的部分或所有像素，从而允许底层像素显示出来。
			name.setBounds(133,195,194,30);
			name.addActionListener(this);
			name.addKeyListener(new KeyListener(){
				//添加指定的键侦听器以从此组件接收键事件。如果l为空，则不抛出异常，也不执行任何操作
				/**
				 * 对键盘进行监听
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
						e.consume(); //对输入的这个事件不处理，销毁事件
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
				//添加插入符侦听器，用于通知插入符的任何更改。 形参:
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

		{//密码输入框			
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

		{//记住密码
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
		
			cb_pwd.addActionListener(this); //添加事件处理
		}

		{//登录按钮
			btn_Login = new MyButton();
			btn_Login.setBounds(133, 286,195,33);
			btn_Login.setContentAreaFilled(false);
			contentPanel.add(btn_Login);
		
			btn_Login.addActionListener(this); //添加事件处理
		}
		{//注册按钮
			btn_Register = new MyButton();//注册
			btn_Register.setBounds(328,195,63,26);
			btn_Register.setContentAreaFilled(false);
			contentPanel.add(btn_Register);	
			btn_Register.addActionListener(this);//添加事件处理
		}
		{//找回密码
			btn_ForgotPwd=new MyButton();
			btn_ForgotPwd.setBounds(328,226,63,26);
			btn_ForgotPwd.setContentAreaFilled(false);
			contentPanel.add(btn_ForgotPwd);
			btn_ForgotPwd.addActionListener(this);//添加事件处理
		}
		{//取消按钮
		    btn_Cancel = new MyButton();
		   
		    btn_Cancel.setBounds(114, 252,159,34);
		    btn_Cancel.setContentAreaFilled(false);
		    btn_Cancel.setVisible(false);
		    contentPanel.add(btn_Cancel);
		
			btn_Cancel.addActionListener(this); //添加事件处理
		}
		{//最小化
			btn_min=new MyButton();
			btn_min.setBounds(370,2,28,28);
			btn_min.setContentAreaFilled(false);
			contentPanel.add(btn_min);	
			btn_min.addActionListener(this);//添加事件处理
			
		}
		{//关闭窗体
			btn_close=new MyButton();
			btn_close.setBounds(400,2,28,28);
			btn_close.setContentAreaFilled(false);
			contentPanel.add(btn_close);	
			btn_close.addActionListener(this);//添加事件处理
			
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
		
		
		//添加设置的背景面板到当前面板
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	// 按钮响应事件
	@SuppressWarnings("null")
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == btn_Login) { // 登录

			ClientToServer cts = new ClientToServer(mainFrm);//打开主页面
			new Thread(cts).start();

			Login login = new Login(cts);
			String userName = name.getText().trim();
			String pwd = new String(password.getPassword());
            User user = new User();
            user.setUserId(userName);
            user.setPasswd(pwd);
			try {
				if (userName.equals("")) { // QQ号是否为空
					 JOptionPane.showMessageDialog(null, "请输入QQ号!");
				} else if (pwd.equals("")) {// 密码是否为空
					 JOptionPane.showMessageDialog(null, "请输入密码!");
				} else if (login.checkUser(user)) { // 核对用户是否合法	
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

					if (cb_pwd.isSelected()) {//记住密码复选框被选中
						login.remember(userName, pwd);
					} else {
						login.remember("", "");
					}
			         this.dispose();
				} else {
					String str = cts.getLoginInfo();
					if(str.equals("loginFail")){
					JOptionPane.showMessageDialog(null, "您输入的用户名或密码错误!");
					}else if(str.equals("reLogin")){
						JOptionPane.showMessageDialog(null, "不能重复登录!");
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (source == btn_Register) { // 加载注册窗口
			this.setVisible(false);
			JRegisterFrm register = new JRegisterFrm(this);
			register.setVisible(true);
		} else if (source == btn_Cancel) { // 退出主窗口，并释放资源
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
		}else if(source == btn_min){//最小化
			setExtendedState(JFrame.ICONIFIED);
		}else if(source==btn_ForgotPwd){//忘记密码
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
			getbackpwd.label.setText("请回答下列问题？");
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
		public String knight ; //要加载的图片资源的文件名
		public BackgroundPanel(){
			knight= "login";
		}
		public void setbk()
		{
			
		}
		//重写绘制组件方法
		public void paintComponent(Graphics g)
		{
		int x = 0,y = 0;
		
		//调用资源管理类中的加载图片资源方法，来加载背景图片
		ResourceManager imageResource = new ResourceManager();
		ImageIcon icon = new ImageIcon();
		icon = imageResource.GetImage(knight);
		//绘制窗口
		g.drawImage(icon.getImage(),x,y,icon.getIconWidth(),icon.getIconHeight(),this);
		}
	}
}
