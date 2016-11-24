package huawangxin.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import huawangxin.service.EmpService;
import huawangxin.utils.DriverManagerDBUtil;
/**
 * ʵ����
 * SQL PreparedStatement
 * @author huawangxin
 *
 * 
 * @date 2016��11��23�� ����7:27:25
 */
public class EmpServiceImp implements EmpService {

	public void login(Scanner scanner) {
		/*
		 * 1:Ҫ���������û���������
		 * 2:�����û�������Ϊ����ȥ���в�ѯ
		 * 3:����ѯ�����ݣ�˵��������ȷ 
		 */
		System.out.println("�����ǵ�¼����");
		System.out.println("�������û���:");
		String user = scanner.nextLine().trim();
		System.out.println("����������:");
		String pwd = scanner.nextLine().trim();
		try{
			Connection conn = DriverManagerDBUtil.getConnection();
			String sql = "SELECT * FROM user_fanchuanqi " + "WHERE name=? AND password=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, pwd);
			ResultSet rs  = ps.executeQuery();
			//�����û�������ܷ�鵽����
			if(rs.next()){
				System.out.println("��¼�ɹ�!");
			}else{
				System.out.println("�û������������!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DriverManagerDBUtil.closeConnection();
		}
	}

	public void regUser(Scanner scanner) {
		try{
			//1
			System.out.println("������ע�����:");
			System.out.println("�������û���:");
			String user = scanner.nextLine().trim();
			Connection conn = DriverManagerDBUtil.getConnection();
			Statement state  = conn.createStatement();
			String idSql = "SELECT MAX(id) id FROM user_fanchuanqi";
			ResultSet rs  =state.executeQuery(idSql);
			//ResultSet������⣺
			//http://soft-development.iteye.com/blog/1420323
			int id = -1;
			if(rs.next()){
				id = rs.getInt("id");
			}
			//ͳ�Ƴ����ֵ�󣬶�ID��1
			System.out.println(id++);
			rs.close();
			String sql  = " ";
			System.out.println(sql);
			if(state.executeUpdate(sql)>0){
				System.out.println("ע��ɹ�����ӭ��:"+user);
			}else{
				System.out.println("�Ǻ�");
			}
			DriverManagerDBUtil.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void testPreparedStatement(Scanner scanner) {
		try{
			Connection conn = DriverManagerDBUtil.getConnection();
//			Statement state = conn.createStatement();//��ͨsql����
			String sql 
				= "INSERT INTO user_wangxin " +
					"VALUES(?,?,'123456',?,?)";
			/*
			 * ���ݸ�����Ԥ����SQL��䴴��һ��
			 * PreparedStatement
			 */
			PreparedStatement ps =	conn.prepareStatement(sql);	
			long start = System.currentTimeMillis();
				for(int i=100;i<200;i++){
					ps.setInt(1,i);
					ps.setString(2, "test"+i);
					ps.setInt(3, 5000);
					ps.setString(4,"test"+i+"@qq.com");
					ps.executeUpdate();
				}
			System.out.println("�������");		
			long end = System.currentTimeMillis();
			System.out.println("��ʱ:"+(end-start));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DriverManagerDBUtil.closeConnection();
		}
	}

	public void getPage(Scanner scanner) {
		scanner=new Scanner(System.in);
		System.out.println("������Ҫ�鿴�ı�����");
		String tableName=scanner.nextLine().trim();
		System.out.println("�����������������");
		String colName=scanner.nextLine().trim();
		System.out.println("������һҳ��ʾ������");
		int pageSize=Integer.parseInt(scanner.nextLine().trim());
		System.out.println("������鿴��ҳ��");
		int page=Integer.parseInt(scanner.nextLine().trim());
		try {
			Connection conn=DriverManagerDBUtil.getConnection();
			String sql="SELECT * FROM ( " +
					"SELECT ROWNUM rn, t.* FROM ( " +
					"SELECT * FROM "+tableName+" ORDER BY "+colName+"  " +
					")  t " +
					")" +
					"WHERE rn BETWEEN  ?  AND ? ";
			PreparedStatement ps=conn.prepareStatement(sql);
			int start=(page-1)*pageSize+1;
			int end=page*pageSize;
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
//				int rw=rs.getInt(1);
				int id=rs.getInt(2);
				String name=rs.getString(3);
				System.out.println(id+":"+name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DriverManagerDBUtil.closeConnection();
		}
	}

	
}
