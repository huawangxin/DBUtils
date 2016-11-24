package huawangxin.jdbc;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * ���������ļ��Ķ�ȡ config.properties
 * @author huawangxin
 *
 * 
 * @date 2016��11��23�� ����2:46:45
 */
public class JDBCDemo2 {
	public static void main(String[] args) {
		try {
			// java.util.Properties
			/*
			 * Properties�����ڶ�ȡproperties�ļ�ʹ�ø������������Map����ʽ��ȡ�����ļ��е�����
			 * properties�ļ��е����ݸ�ʽ���ƣ�user=openlab��ô�Ⱥ���߾���key���Ⱥ��������value
			 */
			Properties prop = new Properties();
			/*
			 * ʹ��Propertiesȥ��ȡ�����ļ�
			 */
			FileInputStream fis = new FileInputStream("./src/day01/config.properties");
			/*
			 * ��ͨ��Properties��ȡ�ļ�����ô�������Ȼ���ִ�״̬������Ӧ�����йرա�
			 */
			prop.load(fis);
			System.out.println("�ɹ������ļ����");
			/*
			 * ��������Ϻ󣬾Ϳ��Ը����ı��ļ��еȺ���ߵ����ݣ�key������ȡ�Ⱥ���������ݣ�value��
			 * ���Ա���İ�Properties������һ��Map
			 */
			String driver = prop.getProperty("driver").trim();
			String url = prop.getProperty("url").trim();
			String user = prop.getProperty("user").trim();
			String pwd = prop.getProperty("pwd").trim();
			System.out.println("driver:" + driver);
			System.out.println("url:" + url);
			System.out.println("user:" + user);
			System.out.println("pwd:" + pwd);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
