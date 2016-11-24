package huawangxin.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 使用JDBC连接oracle数据库
 * @author huawangxin
 *
 * 
 * @date 2016年11月23日 下午1:48:54
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
			//加载驱动
			/*
			 * 当出现java.lang.ClassNotFoundException:
			 *  Class.forName("具体数据库驱动");
			 *  		oracle.jdbc.driver.OracleDriver
			 *  		com.mysql.jdbc.Driver
			 *  这个异常时，说明数据库的驱动jar包没有导入到项目中。
			 *  若导入到jar包还报错，大部分原因是书写的驱动有错误。
			 */
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/*
			 * 第二步：
			 * 通过DriverManager获取数据库连接
			 * 注意：
			 * 导入的包都在java.sql.*
			 * 
			 * DriverManager连接ORACLE时的路径格式
			 * jdbc:oracle:thin:@<host>:<port>:<sid>
			 * 
			 * Mysql的路径  端口号通常是：3306
			 * jdbc:mysql://<host>:<port>/<dbname>
			 */
			con=DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:orcl","huawangxin","huawangxin");
			/*
			 * 通过Connection创建Statement用来执行sql语句
			 */
			stmt=con.createStatement();
			/*
			 * 通过Statement执行查询语句
			 * 查询emp表中的信息
			 * SELECT empno,ename,sal,deptno
			 * FROM emp
			 */
			String sql="SELECT empno,ename,sal,deptno  FROM emp_wangxin";
			/*
			 * 使用executeQuery来执行DQL语句
			 * 并且查询后得到一个查询结果集
			 */
			rs=stmt.executeQuery(sql);
			/*
			 * 需要注意的是，ResultSet表示的是查询结果，但实际上查询的
			 * 结果集在ORACLE数据库服务器上，并没有完全保存在本地，
			 * 所以我们通过ResultSet的next方法获取下一条记录时，
			 * ResultSet会发送请求至服务端获取数据，若连接已经关闭，
			 * 那么会抛出异常。
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
			System.out.println("驱动类无法找到!");
			throw new RuntimeException(e);
		} catch (SQLException e) {
			System.out.println("数据库访问异常!");
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
				System.out.println("关闭连接时发生异常");
			}
		}
	}
}
