package huawangxin.utils.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JDBCUtil {
	private static Configuration cfg = Configuration.configure();
	
	public static Connection getConnection() {
		try {
			Class.forName(cfg.getDriver());
			Connection conn = DriverManager.getConnection(cfg.getUrl(), cfg.getUsername(),
					cfg.getPassword());
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
