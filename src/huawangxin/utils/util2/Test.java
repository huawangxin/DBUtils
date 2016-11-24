package huawangxin.utils.util2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:orcl", "test1", "oracle");
			return conn;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {

		Connection con = getConnection();

		String sql = "select * from DRUG_PEOPLE t";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);


			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("cellphone"));
				System.out.println(rs.getString(1)+"--"+rs.getString(2));
			}
		

			// JDBCUtil.releaseConnection(con, pstmt, rs);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
