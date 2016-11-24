package huawangxin.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import huawangxin.service.EmpService;
import huawangxin.utils.DriverManagerDBUtil;
/**
 * 实现类
 * SQL PreparedStatement
 * @author huawangxin
 *
 * 
 * @date 2016年11月23日 下午7:27:25
 */
public class EmpServiceImp implements EmpService {

	public void login(Scanner scanner) {
		/*
		 * 1:要求用输入用户名及密码
		 * 2:根据用户输入作为条件去表中查询
		 * 3:若查询出数据，说明输入正确 
		 */
		System.out.println("现在是登录操作");
		System.out.println("请输入用户名:");
		String user = scanner.nextLine().trim();
		System.out.println("请输入密码:");
		String pwd = scanner.nextLine().trim();
		try{
			Connection conn = DriverManagerDBUtil.getConnection();
			String sql = "SELECT * FROM user_fanchuanqi " + "WHERE name=? AND password=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, pwd);
			ResultSet rs  = ps.executeQuery();
			//根据用户输入的能否查到数据
			if(rs.next()){
				System.out.println("登录成功!");
			}else{
				System.out.println("用户名或密码错误!");
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
			System.out.println("现在是注册操作:");
			System.out.println("请输入用户名:");
			String user = scanner.nextLine().trim();
			Connection conn = DriverManagerDBUtil.getConnection();
			Statement state  = conn.createStatement();
			String idSql = "SELECT MAX(id) id FROM user_fanchuanqi";
			ResultSet rs  =state.executeQuery(idSql);
			//ResultSet功能详解：
			//http://soft-development.iteye.com/blog/1420323
			int id = -1;
			if(rs.next()){
				id = rs.getInt("id");
			}
			//统计出最大值后，对ID加1
			System.out.println(id++);
			rs.close();
			String sql  = " ";
			System.out.println(sql);
			if(state.executeUpdate(sql)>0){
				System.out.println("注册成功！欢迎你:"+user);
			}else{
				System.out.println("呵呵");
			}
			DriverManagerDBUtil.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void testPreparedStatement(Scanner scanner) {
		try{
			Connection conn = DriverManagerDBUtil.getConnection();
//			Statement state = conn.createStatement();//普通sql请求
			String sql 
				= "INSERT INTO user_wangxin " +
					"VALUES(?,?,'123456',?,?)";
			/*
			 * 根据给定的预编译SQL语句创建一个
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
			System.out.println("插入完毕");		
			long end = System.currentTimeMillis();
			System.out.println("耗时:"+(end-start));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DriverManagerDBUtil.closeConnection();
		}
	}

	public void getPage(Scanner scanner) {
		scanner=new Scanner(System.in);
		System.out.println("请输入要查看的表名：");
		String tableName=scanner.nextLine().trim();
		System.out.println("请输入排序的列名：");
		String colName=scanner.nextLine().trim();
		System.out.println("请输入一页显示的条数");
		int pageSize=Integer.parseInt(scanner.nextLine().trim());
		System.out.println("请输入查看的页数");
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
