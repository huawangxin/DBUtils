package huawangxin.utils;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * ʹ�����ӳؼ����������ݿ�����
 * ��BasicDataSource��
 * @author huawangxin
 * 
 * @date 2016��11��23�� ����1:40:11
 */
public class BasicDataSourceDBUtil {
	//���ݿ����ӳ�
	private static BasicDataSource ds;
	//Ϊ��ͬ�̹߳�������
	private static ThreadLocal<Connection> tl;
	static{
		try{
			Properties prop=new Properties();
			InputStream is=BasicDataSourceDBUtil.class.getClassLoader().
			getResourceAsStream("huawangxin/entity/config.properties");
			prop.load(is);
			is.close();
			//��ʼ�����ӳ�
			ds=new BasicDataSource();
			//��������Class.forName
			ds.setDriverClassName(prop.getProperty("driver"));
			//����url
			ds.setUrl(prop.getProperty("url"));
			//�������ݿ��û���
			ds.setUsername(prop.getProperty("user"));
			//�������ݿ�����
			ds.setPassword(prop.getProperty("pwd"));
			//��ʼ����������
			ds.setInitialSize(Integer.parseInt(prop.getProperty("initsize")));
			//���ӳ���������������
			ds.setMaxActive(Integer.parseInt(prop.getProperty("maxactive")));
			//�������ȴ�ʱ��
			ds.setMaxWait(Integer.parseInt(prop.getProperty("maxwait")));
			//������С������
			ds.setMinIdle(Integer.parseInt(prop.getProperty("minidle")));
			//������������
			ds.setMaxIdle(Integer.parseInt(prop.getProperty("maxidle")));
			//��ʼ���̱߳���
			tl=new ThreadLocal<Connection>();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ���ݿ�����
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException{
		/*
		 * ͨ�����ӳػ�ȡһ����������
		 */
		Connection conn=ds.getConnection();
		tl.set(conn);
		return conn;
	}
	/*
	 * �ر����ݿ�����
	 */
	public static void closeConnection(){
		try{
			Connection conn=tl.get();
			if(conn!=null){
				/*
				 * ͨ�����ӳػ�ȡ��Connection��close����
				 * ������û�н����ӹرգ����ǽ������ӹ黹��
				 */
				conn.close();
				tl.remove();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
