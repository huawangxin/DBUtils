package huawangxin.utils.util2;

import java.sql.Connection;

import huawangxin.utils.util.JDBCUtil;


public class SyncAll {
	public static void main(String[] args) {
		
		Convert c = new Convert();
		Convert2 c2 = new Convert2();
		
		Connection con1 = JDBCUtil.getConnection();
		Connection con2 = JDBCUtil.getConnection();
		Connection con3 = JDBCUtil.getConnection();
		
		Connection con4 = JDBCUtil.getConnection();
		Connection con5 = JDBCUtil.getConnection();
		Connection con6 = JDBCUtil.getConnection();
		Connection con7 = JDBCUtil.getConnection();
		Connection con8 = JDBCUtil.getConnection();
		Connection con9 = JDBCUtil.getConnection();
		Connection con10 = JDBCUtil.getConnection();
		Connection con11 = JDBCUtil.getConnection();
		Connection con12 = JDBCUtil.getConnection();
		Connection con13 = JDBCUtil.getConnection();
		
		DbSyncUtil db = new DbSyncUtil();
		String registered_population = "insert into emp ( id , name )  VALUES ( ? , ?)";
		db.SyncThread(con1, "emp", "where peopleType='2A010100' AND status='01'", "registered_population", c,registered_population);
		
		
	}

}
