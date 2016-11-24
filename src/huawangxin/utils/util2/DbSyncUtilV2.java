package huawangxin.utils.util2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DbSyncUtilV2 {
	
	//static final Queue<String> set = new ConcurrentLinkedQueue<String>();
	
	public void SyncThread(final Connection fromCon,final Connection toCon, final String fromTable,
			final String where, final String toTable, final Convert convert,  final String insertSql) {
		new Thread(){
			@Override
			public void run() {
				final String uid = UUID.randomUUID().toString();
				//set.add(uid);
				Sync(fromCon,toCon, fromTable, where, toTable, convert,insertSql);
				//set.remove(uid);
			}
		}.start();
	}
	
	
	static class TableColumn{
		private String columnName;
		private Object value;
		private int type;
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
	}
	
	static class TableRow{
		private Map<String, TableColumn> row = new LinkedHashMap<String,TableColumn>();
		
		public List<TableColumn> getList(){
			return new ArrayList<TableColumn>(row.values());
		}
		
		public Map<String, TableColumn> getRow() {
			return row;
		}

		public void setRow(Map<String, TableColumn> row) {
			this.row = row;
		}
		
		public void addColumn (TableColumn column) {
			row.put(column.getColumnName(), column);
		}

		public TableColumn getTableColumn(String columnName) {
			 return row.get(columnName);
		}
	}
	
	
	
	public void Sync(final Connection fromCon, final Connection toCon, String fromTable,
			String where, final String toTable, Convert convert,final String insertSql) {
		String sql = new StringBuffer("select * FROM ").append(fromTable).append(" ").append(where).toString();
		
		
		try {
			long total = 0;
			long start = System.currentTimeMillis();
			PreparedStatement pstmt = fromCon.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			System.out.println("start Sync from "+fromTable + " TO " +toTable);
			
			while (rs.next()) {
			ResultSetMetaData m=null;
			m = rs.getMetaData();
			int columns=m.getColumnCount();
			

			final TableRow row = new TableRow();
			
			for (int i = 1; i <= columns; i++) {
				String columnName =  m.getColumnName(i);
				int columnType = m.getColumnType(i);
				Object value = convert.execute(columnName, rs.getObject(i));
				TableColumn tableColumn = new TableColumn();
				tableColumn.setColumnName(columnName);
				tableColumn.setType(columnType);
				tableColumn.setValue(value);
				row.addColumn(tableColumn);
				
				
				if(columnName.equals("id")){
					total++;
				}
			}
			/*
			 * mp_pid  不填
			 * m_datastate  状态需要
			 * m_changetime  必填
			 * m_exstate
			 */
			TableColumn column1 = new TableColumn();
			column1.setColumnName("m_datastate");
			column1.setType(Types.VARCHAR);
			column1.setValue("insert");
			TableColumn column2 = new TableColumn();
			column2.setColumnName("m_changetime");
			column2.setType(Types.TIMESTAMP);
			column2.setValue(new Timestamp(System.currentTimeMillis()));
			row.addColumn(column1);
			row.addColumn(column2);
		
			//final StringBuilder bsql = new StringBuilder();
			final List<TableColumn> tcolumns = row.getList();
		/*	bsql.append("insert into "+toTable+" ( ");
			for (int j = 0; j < tcolumns.size(); j++) {
				TableColumn tableColumn = tcolumns.get(j);
				bsql.append(tableColumn.getColumnName());
				if(j!=tcolumns.size()-1)
					bsql.append(" , ");
			}
			bsql.append(" )  VALUES (");
			for (int i = 0; i < tcolumns.size(); i++) {
				bsql.append(" ?");
			if(i!=tcolumns.size()-1)
				bsql.append(" ,");
		}
		bsql.append(")");*/
			
			final String uid = (String) row.getTableColumn("id").getValue();
					PreparedStatement pstmtInsert = null;
					try {
						String sqls =  new StringBuffer("select * FROM ").append(toTable).append("  T WHERE T.id=?").toString();
						PreparedStatement pstmt2 = toCon.prepareStatement(sqls);
						pstmt2.setString(1, uid);
						ResultSet rs2 = pstmt2.executeQuery();
						if(rs2.next()){
							
						}else{
							//pstmtInsert = toCon.prepareStatement(bsql.toString());
							pstmtInsert = toCon.prepareStatement(insertSql);
							for (int i = 0; i < tcolumns.size(); i++) {
								pstmtInsert.setObject(i+1,tcolumns.get(i).getValue());
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
			}
			
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
