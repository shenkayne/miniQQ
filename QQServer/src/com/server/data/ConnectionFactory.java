/**
 * 数据库连接工厂
 */
package com.server.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * 数据库连接类
 * 通过单例模式来保证每次只能建立一个数据库连接
 */
public class ConnectionFactory {

	
	//数据库驱动程序类名
	private static String DRIVER = "";
	//连接数据库的URL
	private static String URL = "";
	//连接数据库的用户名
	private static String USERNAME = "";
	//连接数据库的密码
	private static String PASSWORD = "";
		
	private ConnectionFactory(){
	}

	static{//在项目启动时就完成配置文件中数据的读取
		getPropertiesInfo();		
	}
	
	/**
	 * 该方法用来获得属性文件中的driver、url、username、password
	 */
	private static void getPropertiesInfo(){
		Properties prop = new Properties();
		InputStream inStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("jdbcCon.properties");
		try{
			//获得相应的键值对
			prop.load(inStream);
		}catch(IOException e){
			e.printStackTrace();
		}
		//根据相应的键获得对应的值
		DRIVER = prop.getProperty("driver");
		URL = prop.getProperty("url");
		USERNAME = prop.getProperty("username");
		PASSWORD = prop.getProperty("password");		
		      
	}
	
	/**
	 * 该方法用来加载驱动，并获得数据库的连接对象
	 *
	 * @return 数据库连接对象conn
	 */
	public static Connection getConnnection(){
		Connection conn = null;
		try{
			//加载数据库驱动程序
			Class.forName(DRIVER);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		try{
			//获得数据库连接对象
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("数据库连接成功!");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
}
