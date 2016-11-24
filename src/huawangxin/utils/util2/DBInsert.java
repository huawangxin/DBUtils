package huawangxin.utils.util2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import huawangxin.entity.Database;
import huawangxin.utils.util.JDBCUtil;

public class DBInsert {
	public static void main(String[] args) throws SQLException {
		final Database database = new Database("oracle.jdbc.driver.OracleDriver",
				"jdbc:oracle:thin:@127.0.0.1:1521:test", "root", "123456");
PreparedStatement pstmtInsert = null;

		Connection con = database.getConnection();

		String insertSql = "insert into emp (ID, ORGUID)values (?, ?)";
		
		pstmtInsert = con.prepareStatement(insertSql);

		pstmtInsert.setObject(1, "ttttttttt");
		pstmtInsert.setObject(2, "WJW201603240007");
		pstmtInsert.setObject(3, "xxxx'");
		pstmtInsert.setObject(4, "xxxxx。");
		pstmtInsert.setObject(5, "xxx路xxxx弄");
		pstmtInsert.setObject(6, 0.00);
		pstmtInsert.setObject(7, 0.00);
		pstmtInsert.setObject(8, "xxxxxx");
		pstmtInsert.setObject(9, "xxxxxxxxxxx");
		pstmtInsert.setObject(10, "history");
		pstmtInsert.setObject(11, "wg_sb");
		pstmtInsert.setObject(12, null);
		pstmtInsert.setObject(13, new Date(System.currentTimeMillis()));
		pstmtInsert.setObject(14, null);
		pstmtInsert.setObject(15, null);
		pstmtInsert.setObject(16, new Date(System.currentTimeMillis()));
		pstmtInsert.setObject(17, "problemZjDef");
		pstmtInsert.setObject(18, null);
		pstmtInsert.setObject(19, null);
		pstmtInsert.setObject(20, "0");
		pstmtInsert.setObject(21, null);
		pstmtInsert.setObject(22, null);
		pstmtInsert.setObject(23, "车棚私拉电线");
		pstmtInsert.setObject(24, null);
		pstmtInsert.setObject(25,  "0");
		pstmtInsert.setObject(26, new Date(System.currentTimeMillis()));
		pstmtInsert.setObject(27, 0);
		pstmtInsert.setObject(28, null);
		pstmtInsert.setObject(29, "0");
		pstmtInsert.setObject(30, null);
		pstmtInsert.setObject(31, "0");
		pstmtInsert.setObject(32, null);
		pstmtInsert.setObject(33, "sb");
		pstmtInsert.setObject(34, null);
		pstmtInsert.setObject(35, null);
		pstmtInsert.setObject(36, null);
		pstmtInsert.setObject(37, "insert");
		pstmtInsert.setObject(38, new Timestamp(System.currentTimeMillis()));
		pstmtInsert.setObject(39, "0");
		pstmtInsert.setObject(40, null);
		pstmtInsert.setObject(41, new Date(System.currentTimeMillis()));
		pstmtInsert.setObject(42, "WJ-2-9");
		pstmtInsert.setObject(43, new Date(System.currentTimeMillis()));
		pstmtInsert.setObject(44, "001003002004002009");
		                      
		pstmtInsert.executeUpdate();
                              
		JDBCUtil.releaseConnection(con, pstmtInsert, null);
		
		System.out.println("success");
	}                        
}                            
                             
                             
                             
                             
                             
                             
                             
                             
                              