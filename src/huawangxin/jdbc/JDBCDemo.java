package huawangxin.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ʹ��JDBC����oracle���ݿ�
 * @author huawangxin
 *
 * 
 * @date 2016��11��23�� ����1:48:54
 */
public class JDBCDemo {
	public static void main(String[] args) {
		JDBCDemo jdbcDemo=new JDBCDemo();
		jdbcDemo.find();
	}
	public void find(){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			//��������
			/*
			 * ������java.lang.ClassNotFoundException:
			 *  Class.forName("�������ݿ�����");
			 *  		oracle.jdbc.driver.OracleDriver
			 *  		com.mysql.jdbc.Driver
			 *  ����쳣ʱ��˵�����ݿ������jar��û�е��뵽��Ŀ�С�
			 *  �����뵽jar���������󲿷�ԭ������д�������д���
			 */
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/*
			 * �ڶ�����
			 * ͨ��DriverManager��ȡ���ݿ�����
			 * ע�⣺
			 * ����İ�����java.sql.*
			 * 
			 * DriverManager����ORACLEʱ��·����ʽ
			 * jdbc:oracle:thin:@<host>:<port>:<sid>
			 * 
			 * Mysql��·��  �˿ں�ͨ���ǣ�3306
			 * jdbc:mysql://<host>:<port>/<dbname>
			 */
			con=DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:orcl","huawangxin","huawangxin");
			/*
			 * ͨ��Connection����Statement����ִ��sql���
			 */
			stmt=con.createStatement();
			/*
			 * ͨ��Statementִ�в�ѯ���
			 * ��ѯemp���е���Ϣ
			 * SELECT empno,ename,sal,deptno
			 * FROM emp
			 */
			String sql="SELECT empno,ename,sal,deptno  FROM emp_wangxin";
			/*
			 * ʹ��executeQuery��ִ��DQL���
			 * ���Ҳ�ѯ��õ�һ����ѯ�����
			 */
			rs=stmt.executeQuery(sql);
			/*
			 * ��Ҫע����ǣ�ResultSet��ʾ���ǲ�ѯ�������ʵ���ϲ�ѯ��
			 * �������ORACLE���ݿ�������ϣ���û����ȫ�����ڱ��أ�
			 * ��������ͨ��ResultSet��next������ȡ��һ����¼ʱ��
			 * ResultSet�ᷢ������������˻�ȡ���ݣ��������Ѿ��رգ�
			 * ��ô���׳��쳣��
			 */
			while(rs.next()){
				int empno=rs.getInt("empno");
				String ename=rs.getString("ename");
				int sal=rs.getInt("sal");
				int deptno=rs.getInt("deptno");
				System.out.println(empno+","+ename+","
						+sal+","+deptno);
			}
		}  catch (ClassNotFoundException e) {
			System.out.println("�������޷��ҵ�!");
			throw new RuntimeException(e);
		} catch (SQLException e) {
			System.out.println("���ݿ�����쳣!");
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();		
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("�ر�����ʱ�����쳣");
			}
		}
	}
}
