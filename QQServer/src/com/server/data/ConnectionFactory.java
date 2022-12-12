/**
 * ���ݿ����ӹ���
 */
package com.server.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * ���ݿ�������
 * ͨ������ģʽ����֤ÿ��ֻ�ܽ���һ�����ݿ�����
 */
public class ConnectionFactory {

	
	//���ݿ�������������
	private static String DRIVER = "";
	//�������ݿ��URL
	private static String URL = "";
	//�������ݿ���û���
	private static String USERNAME = "";
	//�������ݿ������
	private static String PASSWORD = "";
		
	private ConnectionFactory(){
	}

	static{//����Ŀ����ʱ����������ļ������ݵĶ�ȡ
		getPropertiesInfo();		
	}
	
	/**
	 * �÷���������������ļ��е�driver��url��username��password
	 */
	private static void getPropertiesInfo(){
		Properties prop = new Properties();
		InputStream inStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("jdbcCon.properties");
		try{
			//�����Ӧ�ļ�ֵ��
			prop.load(inStream);
		}catch(IOException e){
			e.printStackTrace();
		}
		//������Ӧ�ļ���ö�Ӧ��ֵ
		DRIVER = prop.getProperty("driver");
		URL = prop.getProperty("url");
		USERNAME = prop.getProperty("username");
		PASSWORD = prop.getProperty("password");		
		      
	}
	
	/**
	 * �÷�������������������������ݿ�����Ӷ���
	 *
	 * @return ���ݿ����Ӷ���conn
	 */
	public static Connection getConnnection(){
		Connection conn = null;
		try{
			//�������ݿ���������
			Class.forName(DRIVER);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		try{
			//������ݿ����Ӷ���
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("���ݿ����ӳɹ�!");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
}
