package huawangxin.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 使用配置文件来使JDBC连接数据库 该类用来管理数据库的连接
 * 【DriverManager】
 * @author huawangxin
 * 
 * @date 2016年11月23日 下午12:58:56
 */
public class DriverManagerDBUtil {
	// 连接数据库的路径
	private static String url;
	// 连接数据库的用户名
	private static String user;
	// 连接数据库的密码
	//ThreadLocal 线程局部变量
	private static String pwd;
	private static ThreadLocal<Connection> tl= new ThreadLocal<Connection>();
	// 静态块
	static {
		try {
			// 读取配置文件
			Properties prop = new Properties();
			/*
			 * 这种写法是将来更加推荐的相对路径写法。
			 */
			InputStream is = DriverManagerDBUtil.class.getClassLoader().getResourceAsStream(
					"huawangxin/entity/config.properties");
			prop.load(is);
			is.close();
			// 获得驱动
			String driver = prop.getProperty("driver").trim();
			// 获得地址
			url = prop.getProperty("url").trim();
			// 获取用户名
			user = prop.getProperty("user").trim();
			// 获取密码
			pwd = prop.getProperty("pwd").trim();
			// 注册驱动
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 获取一个连接
	 * 
	 * @return
	 */
	public static Connection getConnection() throws Exception {
		try {
			/*
			 * 通过DriverManager创建一个数据库的连接并返回
			 */
			Connection conn=DriverManager.getConnection(url, user, pwd);
			/*
			 * ThreadLocal的set方法
			 * 会将当前线程作为key啊，并将给定的值作为value存入内部的map中保存。
			 */
			tl.set(conn);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			// 通知调用者，创建连接出错
			throw e;
		}
	}
	/**
	 * 关闭给定的连接
	 */
	public static void closeConnection(){
		try{
			Connection conn=tl.get();
			if(conn!=null){
				conn.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
