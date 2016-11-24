package huawangxin.utils.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JDBCUtil2 {
	private static Configuration cfg = Configuration.configure();
	private static Configuration cfg2 = Configuration.configure2();
	
	
	public static Connection getConnectionFrom() {
		try {
			Class.forName(cfg.getDriver());
			Connection conn = DriverManager.getConnection(cfg.getUrl(), cfg.getUsername(),
					cfg.getPassword());
			return conn;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Connection getConnectionTo() {
		try {
			Class.forName(cfg2.getDriver());
			Connection conn = DriverManager.getConnection(cfg2.getUrl(), cfg2.getUsername(),
					cfg2.getPassword());
			return conn;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void releaseConnection(Connection con,
			PreparedStatement pstmt, ResultSet rs) {
		try {
			if (con != null) {
				con.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
