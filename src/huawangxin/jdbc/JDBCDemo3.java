package huawangxin.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import huawangxin.utils.DriverManagerDBUtil;
/**
 * ͨ��DBUtil��ȡ���ӣ���ִ��sql
 * @author huawangxin
 *
 * 
 * @date 2016��11��23�� ����2:47:16
 */
public class JDBCDemo3 {
	public static void main(String[] args) {
		try {
			Connection conn=DriverManagerDBUtil.getConnection();
			System.out.println("���ݿ�������");
			Statement state=conn.createStatement();
			String sql="SELECT *FROM emp_wangxin";
			//ִ��sql���õ������
			ResultSet rs=state.executeQuery(sql);
			while(rs.next()){
				int  empno=rs.getInt("empno");
				String ename=rs.getString("ename");
				int sal=rs.getInt("sal");
				int deptno=rs.getInt("deptno");
				System.out.println(empno+","+ename+","+sal+","+deptno);
			}
			/*
			 * �����ʹ����Ϻ��Ӧ���رգ��ͷ���Դ��
			 * ������Statement�رպ��ˣ���ôrs���Զ��رա�
			 */
			rs.close();
			/*
			 * ������ͨ��Statementִ������sqlʱ������Ӧ����ʱ�ر�Statement 
			 * ���ͷ�JDBC�����ݿ����Դռ�á�
			 */
			state.close();
			
			//ʹ�ú�ر�����
			DriverManagerDBUtil.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
