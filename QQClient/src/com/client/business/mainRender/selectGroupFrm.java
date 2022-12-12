package com.client.business.mainRender;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.client.view.JMainFrm;

public class selectGroupFrm extends JFrame{
	 String GroupName="";
	 int Mwide=360,Mheight=200;//����ĳ���
		private int x,y;//�����λ�ã��϶�ʱ��λ��
		private int offsetX, offsetY;//�϶�����ƫ��λ��
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		static String editBk="Image/MainIcon/editBk.png";
		static String Background="Image/MainIcon/addFriendbk.png";
		static String touxiang="Image/MainIcon/qqicons\\DefaultFace.png";
		static String select="Image/MainIcon/qqicons\\select.png";
		static String Pclose="Image/MainIcon/close.png";//
		static String Pclose1="Image/MainIcon/close1.png";
		static String Pmin="Image/MainIcon/min.png";
		static String Pmin1="Image/MainIcon/min1.png";
		static String Pskin="Image/MainIcon/skin.png";
		static String Pskin1="Image/MainIcon/skin1.png";
		static String Ptask="Image/MainIcon/task.png";//
		
		static String Pfind="Image/MainIcon/MainTopToolBar\\find.png";

		public selectGroupFrm(final JMainFrm JMain, final findFriendFrm MainFrm){
			this.setLocation((int)(screenSize.getWidth()-Mwide)/2,
					(int)(screenSize.getHeight()-Mheight)/2);
			this.setSize(Mwide,Mheight);
			this.setResizable(false);
			this.setUndecorated(true);//����Ҫ��������װ��
			this.addMouseMotionListener(new MyMouseAdapter());
			this.addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e) {
					offsetX = e.getX();
					offsetY = e.getY();
				}
			});
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		

			JPanel abc=new JPanel();
			abc.setBounds(this.getBounds());
			abc.setLocation(0, 0);
			abc.setOpaque(true);
			abc.setLayout(null);
			
			final JButton B_close=new JButton("�ر�");
			B_close.setBounds(0,0,30,20);
			B_close.setLocation(this.getBounds().width-20, 0);
			B_close.setIcon(new ImageIcon(Pclose1));
			B_close.setOpaque(false);
			B_close.setBorder(null);
			B_close.setBackground(Color.white);
			B_close.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					setVisible(false);
				}
				public void mouseEntered(MouseEvent e) {	
					B_close.setIcon(new ImageIcon(Pclose));
				}
				public void mouseExited(MouseEvent e) {	
					B_close.setIcon(new ImageIcon(Pclose1));
				}}	);
			abc.add(B_close);
			
			JLabel addTo=new JLabel("�ƶ���ϵ������");
			addTo.setBounds(0, 0,100, 40);
			addTo.setLocation(60, 40);
			abc.add(addTo);
			
////////////��ʾ������Ϣ
			
			final JComboBox icb=new JComboBox();//�����׼��Ͽ�
			  icb.setMaximumRowCount(7);//���������ʾ��
			for(int i=1;i<=JMain.groupNumber;i++)
			{
				String name="";
				name=JMain.groupName[i];
			  icb.addItem(name);//���ѡ��
			 
			}
			icb.addItem("�ҵĺ���");//���ѡ��
			
			icb.setBounds(0, 0, 120,20);
			icb.setLocation(160, 52);
			icb.setBorder(null);
			
			abc.add(icb);
			
			final JLabel beizhu=new JLabel("��  ע  ��  �֣�");
			beizhu.setBounds(0, 0,100, 40);
			beizhu.setLocation(60, 90);
			abc.add(beizhu);
			
			final JTextField Tbeizhu=new JTextField("");
			Tbeizhu.setBounds(0, 0, 120, 20);
			Tbeizhu.setLocation(160, 100);
			abc.add(Tbeizhu);
			
			JButton ok=new JButton("ȷ��");
			ok.setBounds(0, 0, 60, 30);
			ok.setContentAreaFilled(false);
			ok.setLocation(60, 140);
			ok.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					String name="";
					if(Tbeizhu.getText().equals("")){
						name=MainFrm.getName();//���պ��ѵ�����
					}
					else{
						name=Tbeizhu.getText();
					}
					String fenzu=(String) icb.getSelectedItem();
					System.out.println("�õ���Ͽ��ϵ�ֵΪ��"+fenzu+"  ����������ǣ�"+name);
//					д������������
					try {
						JMain.UpdateFriendList();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null,"��ˢ�º����б��ˣ�tree");
					
				}});
			abc.add(ok);
			
			JButton cancel=new JButton("ȡ��");
			cancel.setBounds(0, 0, 60, 30);
			cancel.setContentAreaFilled(false);
			cancel.setLocation(220, 140);
			cancel.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setVisible(false);
				}});
			abc.add(cancel);
			//////////////
			
			JLabel bk=new JLabel();
			bk.setBounds(this.getBounds());
			bk.setIcon(new ImageIcon(Background));
			bk.setLocation(0, 0);
			abc.add(bk);
			this.add(abc);
			this.setVisible(true);
			
		}
	class MyMouseAdapter extends MouseAdapter {
			
			
			public void mouseDragged(MouseEvent e) {
				x=e.getXOnScreen();
				y= e.getYOnScreen();
					
					if(y<=0){y=0;
						
					}
					setLocation(x-offsetX, y-offsetY);
			}
		}
	class BoxListener implements MouseListener{
		final LineBorder myboder=new LineBorder(Color.gray);
		final LineBorder myboder1=new LineBorder(Color.red);
		JComponent com;
		BoxListener(){
			
		}
		BoxListener(JComponent com){
			this.com=com;
		}
		public void mouseClicked(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
			com.setBorder(myboder1);
		}
		public void mouseExited(MouseEvent e) {
			com.setBorder(myboder);
		}
		public void mousePressed(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		
	}
	public String getGroupName(){
		return GroupName;
	}
	
}
