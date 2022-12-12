package com.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;

import com.client.qipao.BubbleDemo;
import com.client.qipao.BubbleModel;
import com.client.qipao.BubbleRenderer;
import com.client.qipao.IMMessage;

public class Test extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	BubbleModel mModel = new BubbleModel();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		BubbleDemo frame = new BubbleDemo();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		setTitle("ÅÝÅÝÁÄÌì");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 550);
		setLocationRelativeTo(null);
		JPanel meggage_panel = new JPanel();
		
		meggage_panel.setLayout(new BorderLayout(0, 0));
		setContentPane(meggage_panel);
		meggage_panel.setOpaque(false);
		JScrollPane scrollPane = new JScrollPane();
		meggage_panel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setOpaque(false);
		
		JButton btnSend = new JButton("·¢ËÍ");
		//
		JTable table = new JTable();
		table.setTableHeader(null);
		table.setModel(mModel);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(260);
		table.getColumnModel().getColumn(0).setCellRenderer(new BubbleRenderer());
		scrollPane.setViewportView(table);
		table.setBackground(Color.white);
		table.setOpaque(true);
		table.setShowHorizontalLines(false);
		//
		scrollPane.getViewport().setBackground(Color.WHITE);
		
		
		//final JComboBox cmb = new JComboBox(new String[]{"ÃÆÉ§ÄÐ", "ÃÈÃÃ×Ó"});
		final JTextPane txtPnl = new JTextPane();
		JPanel pnlSend = new JPanel(new MigLayout("ins 4"));
		//pnlSend.add(cmb, "hmax pref");
		pnlSend.add(new JScrollPane(txtPnl),"hmin 50px,growx,pushx");
		pnlSend.add(btnSend, "growy,pushy");
		meggage_panel.add(pnlSend, BorderLayout.SOUTH);
		
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sMsg = txtPnl.getText().trim();
				if ("".equals(sMsg)) {
					System.err.println("Require not blank.");
					return ;
				}
				//String sSend = (String)cmb.getSelectedItem();
				String sTime =  getTime();
				///////////////////
				IMMessage imMsg = new IMMessage();
				//imMsg.setSender(sSend);
				imMsg.setTime(sTime);
				imMsg.setMsg(sMsg);
				mModel.addRow(imMsg);
				/////////////////
				
				//clear
				txtPnl.setText("");
			}
		});
		
	}
	static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(new Date());
	}
	
}
