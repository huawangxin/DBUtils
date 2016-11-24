package huawangxin.utils.ec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


public class JDBCUtil {
	private static Configuration cfg = null;
	private static Configuration cfg1 = null;
	private static Configuration cfg2 = null;

	private static Vector<Connection> pool = new Vector<Connection>(10);

	private static Vector<Connection> outpool = new Vector<Connection>(10);

	public static JDBCUtil create(String xml) {
		JDBCUtil jdbc = new JDBCUtil();
			cfg = Configuration.xmglConfigure(xml);
		try {
			Class.forName(cfg.getDriver());
			for (int i = 0; i < cfg.getPoolSize(); i++) {
				Connection conn = DriverManager.getConnection(cfg.getUrl(),
						cfg.getUsername(), cfg.getPassword());
				pool.add(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jdbc;
	}
	
	public JDBCUtil() {
		
	}
	
	public JDBCUtil(String xml) {
		if ("xmgl".equals(xml))
			cfg = cfg2;
		else if ("jira".equals(xml))
			cfg = cfg1;
		try {
			Class.forName(cfg.getDriver());
			for (int i = 0; i < cfg.getPoolSize(); i++) {
				Connection conn = DriverManager.getConnection(cfg.getUrl(),
						cfg.getUsername(), cfg.getPassword());
				pool.add(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized Connection getConnection() {
		while(pool.isEmpty()){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Connection conn = pool.remove(pool.size()-1);
		outpool.add(conn);
		return conn;
	}

	public static void releaseConnection(Connection con,
			PreparedStatement pstmt, ResultSet rs) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				outpool.remove(con);
				pool.add(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void releaseConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
