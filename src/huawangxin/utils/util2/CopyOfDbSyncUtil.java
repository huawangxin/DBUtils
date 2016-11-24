package huawangxin.utils.util2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CopyOfDbSyncUtil {
	
	//static final Queue<String> set = new ConcurrentLinkedQueue<String>();
	
	private String insertSql = null;
	
	public void SyncThread(final Connection con, final String fromTable,
			final String where, final String toTable, final Convert convert, final  String insertsql) {
		new Thread(){
			@Override
			public void run() {
				final String uid = UUID.randomUUID().toString();
				//set.add(uid);
				Sync(con, fromTable, where, toTable, convert,insertsql);
				//set.remove(uid);
			}
		}.start();
	}
	
	
	public void Sync(final Connection con, String fromTable,
			String where, final String toTable, Convert convert, final String insertsql) {
		String sql = "select * FROM "+fromTable+" " + where;
		
		
		try {
			long total = 0;
			
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			
			long start = System.currentTimeMillis();
			System.out.println("start Sync from "+fromTable + " TO " +toTable);
			
			while (rs.next()) {
			ResultSetMetaData m=null;
			m = rs.getMetaData();
			int columns=m.getColumnCount();
			
			
			String id = "";
			Object[] objects = new Object[columns];
			final StringBuffer builder = new StringBuffer();
			builder.append("insert into "+toTable+" ( ");
			for (int i = 1; i <= columns; i++) {
				String columnName =  m.getColumnName(i);
				@SuppressWarnings("unused")
				int columnType = m.getColumnType(i);
				builder.append(columnName);
				
				objects[i-1]=convert.execute(columnName, rs.getObject(i));
			
				
				if(columnName.equals("id")){
					id = rs.getString(i);
					total++;
				}
					
				
				
				if(i!=columns)
				builder.append(" , ");
				//Types
			}
			/*
			 * mp_pid  不填
			 * m_datastate  状态需要
			 * m_changetime  必填
			 * m_exstate
			 */
			builder.append(" ,");
			builder.append(" m_datastate");
			builder.append(" ,");
			builder.append(" m_changetime");
		final Object[]	objects2 = Arrays.copyOf(objects, objects.length+2);
		objects2[objects2.length-2] = "insert";
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			objects2[objects2.length-1] = timestamp;
			builder.append(" )  VALUES (");
			for (int i = 0; i < objects2.length; i++) {
					builder.append(" ?");
				if(i!=objects2.length-1)
					builder.append(" ,");
			}
			
			
			builder.append(")");
			System.out.println(builder.toString());
			
			final String uid = id;
			new Thread(){

				@Override
				public void run() {
					
					//final String uid = UUID.randomUUID().toString();
					//set.add(uid);
					PreparedStatement pstmtInsert = null;
					try {
						String sql = "select * FROM "+toTable+"  T WHERE T.id=?";
						PreparedStatement pstmt = con.prepareStatement(sql);
						pstmt.setString(1, uid);
						ResultSet rs = pstmt.executeQuery();
					
						
						if(rs.next()){
							
						}else{
							pstmtInsert = con.prepareStatement(insertsql);
							for (int i = 0; i < objects2.length; i++) {
								pstmtInsert.setObject(i+1, objects2[i]);
							}
							pstmtInsert.executeUpdate();
							
						}
						//JDBCUtil.releaseConnection(null, pstmt, null);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						//JDBCUtil.releaseConnection(null, pstmtInsert, null);
					}
					
				//	set.remove(uid);
				}
			}.start();;
			}
			
			//JDBCUtil.releaseConnection(null, pstmt, rs);
			System.out.println("end  Sync from "+fromTable + " TO " +toTable +" Sync Successfully record:"+ total);
			long end = System.currentTimeMillis();
			long time = end - start;
			System.out.println("total time:"+time+" ms 约"+ time/1000+"秒 ");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
