package huawangxin.utils.ec;

import java.sql.Connection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import huawangxin.utils.util.JDBCUtil;

public class DBpool {
	private Queue<Connection> queue = new ConcurrentLinkedQueue<Connection>();
	
	public DBpool(){
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
		queue.add(JDBCUtil.getConnection());
	}
	
	public synchronized Connection getConnection(){
		while(queue.isEmpty()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return queue.poll();
	}
	
	public void releaseConnection(Connection con){
		queue.add(con);
	}
}
