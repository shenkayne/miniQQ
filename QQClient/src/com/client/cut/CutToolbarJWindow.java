package com.client.cut;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.omg.CORBA.SystemException;

import com.client.data.LoadImages;
import com.client.view.MyButton;

/**
 * ������İ���
 */
public class CutToolbarJWindow extends JWindow implements ActionListener {
	private static final long serialVersionUID = 1L;

	Cut cut;
	int x, y;
	MyButton btn_close; // �رհ�ť
	MyButton btn_ok;    // ��ɰ�ť
	MyButton btn_save;  // ���水ť


	JPanel panel;

	public CutToolbarJWindow(Cut cut, int x, int y) {
		super(cut);

		this.x = x;
		this.y = y;
		this.cut = cut;
		btn_close = new MyButton();
		btn_close.setBounds(272, 1, 40, 26);
		btn_close.setToolTipText("�ر�");
		btn_close.addActionListener(this);// ����¼�����

		btn_ok = new MyButton();
		btn_ok.setBounds(310, 1, 55, 26);
		btn_ok.setToolTipText("ok");
		btn_ok.addActionListener(this);// ����¼�����
		// ///////////////////////////////
		// ���水ť
		btn_save = new MyButton();
		btn_save.setBounds(192, 1, 26, 26);
		btn_save.setToolTipText("����");
		btn_save.addActionListener(this); // ����¼�����

		try {
			init();
			this.setAlwaysOnTop(true);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		// this.setBounds(0, 0, 367, 28);
	}

	private void init() throws Exception {
		LoadImages loadimage = new LoadImages();

		final Image image = loadimage.loadImage("cuttoolbar");
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {

				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(image, 0, 0, image.getWidth(this), image
						.getHeight(this), this);

				super.paintComponent(g);
			}
		};

		panel.setOpaque(false);
		panel.setBounds(0, 0, 367, 28);
		panel.setLayout(null);
		// panel.setBackground(Color.red);
		// �����Ӧ��ť
		panel.add(btn_close);
		panel.add(btn_ok);
		panel.add(btn_save);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				// getObj().dispose();
			}

		});
		add(panel);
	}

	@Override
	public void setVisible(boolean show) {
		if (show) {
			determineAndSetLocation();
		}
		super.setVisible(show);
	}

	private void determineAndSetLocation() {

		System.out.println(x + "--" + y);
		setBounds(x - 367, y, 367, 28);
		this.setBackground(Color.blue);
	}

	private JWindow getObj() {
		return this;
	}

	public void closeWindow() {
		this.dispose();
		cut.dispose();
	}

	// ��ť��Ӧ
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btn_close) {
			cut.mf.dispose();
			closeWindow();
			cut.cp.setVisible(true);
			
		} else if (e.getSource() == btn_ok) {
			closeWindow();
			cut.cp.setVisible(true);
			cut.insertCutImage();
			
			
		} else if (e.getSource() == btn_save) {
			// ���水ť����Ӧ
		    cut.Save();
		    cut.cp.setVisible(true);
		}
		}

}