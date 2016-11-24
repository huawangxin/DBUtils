package huawangxin.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import huawangxin.service.Table2Table;
import huawangxin.utils.Condtion;
import huawangxin.utils.Convert;
import huawangxin.utils.EmpUtil;
import huawangxin.utils.JDBCUtil;
import huawangxin.utils.Table;
import huawangxin.utils.TableColumn;
import huawangxin.utils.TableRow;


/**
 * 优化效率执行
 * @author huawangxin
 *
 * 2016年11月24日 上午12:20:52
 */
public class Table2TableTranSportFinal implements Table2Table {

	public void transport(final Table from, final Table to,
			final Condtion condition) {
		new Thread() {
			@Override
			public void run() {
				boolean isOracle = false;
				if (to.getDatabase().getDriver().contains("oracle"))
					isOracle = true;

				Sync(from, to, condition, isOracle);

			}
		}.start();

	}

	/**
	 * 
	 * @author hueb 2016-05-12 20:54:18
	 * @param fromCon
	 * @param toCon
	 * @param fromTable
	 * @param where
	 * @param toTable
	 * @param convert
	 */
	public void Sync(final Table from, final Table to,
			final Condtion condition, boolean isOracle) {

		Connection fromCon = from.getDatabase().getConnection();
		Connection toCon =  to.getDatabase().getConnection();
		Class<?> fromBean = from.getBean();
		Class<?> toBean = to.getBean();
		String where = condition.getWhereSql();
		Convert convert = condition.getConvert();

		String insertSql = EmpUtil.getInsertSql(toBean);
		String updateSql = null;
		String uid = to.getUid();
		
		String sql = EmpUtil.getSelectSql(fromBean, where);
		String sqls = EmpUtil.getSelectSql(toBean, "where "+uid+" = ?");

		try {
			long start = System.currentTimeMillis();
			PreparedStatement pstmt = fromCon.prepareStatement(sql);
			pstmt.setFetchSize(1000);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("start Sync from " + fromBean.getSimpleName()
					+ " TO " + toBean.getSimpleName());
			long insertTotal = 0;
			long updateTotal = 0;
			PreparedStatement pstmt2 = toCon.prepareStatement(sqls);
			PreparedStatement pstmtInsert = toCon.prepareStatement(insertSql);
			PreparedStatement pstmtUpdate = toCon.prepareStatement(updateSql);
			while (rs.next()) {
				Object object = EmpUtil.getDbData(fromBean, rs);
				Object toObject = EmpUtil.copyLeft2Right(object, toBean,
						convert);

				try {

					
					Object id = EmpUtil.getFieldValue(toObject,uid);
					pstmt2.setObject(1, id);
					ResultSet rs2 = pstmt2.executeQuery();
					if (rs2.next()) {

						// pstmtUpdate.setObject(tcolumns.size(), uid);
						/*
						 * pstmtUpdate.addBatch(); //} if ((updateTotal + 1) %
						 * 10000 == 0) { pstmtUpdate.executeBatch();
						 * pstmtUpdate.clearBatch(); pstmtUpdate =
						 * toCon.prepareStatement(updateSql); }
						 */

					} else {

						insertTotal++;

						TableRow row = EmpUtil.get4Db(toObject);
						List<TableColumn> tcolumns = row.getList();
						for (int i = 0; i < tcolumns.size(); i++) {
							pstmtInsert.setObject(i + 1, tcolumns.get(i)
									.getValue());
						}
						pstmtInsert.addBatch();

						if ((insertTotal + 1) % 10000 == 0) {
							pstmtInsert.executeBatch();
							pstmtInsert.clearBatch();
							pstmtInsert = toCon.prepareStatement(insertSql);
						}

					}
				} catch (SQLException e) {
					e.printStackTrace();

				} finally {

				}
			}
			if (insertTotal % 10000 != 0) {
				pstmtInsert.executeBatch();
				pstmtInsert.clearBatch();
			}
			if (updateTotal % 10000 != 0) {
				pstmtUpdate.executeBatch();
				pstmtUpdate.clearBatch();
			}

			if (insertTotal == 0) {
				System.out.println("end  Sync from " + fromBean.getSimpleName()
						+ " TO " + toBean.getSimpleName()
						+ " Sync Successfully no record update");

			} else {
				System.out.println("end  Sync from " + fromBean.getSimpleName()
						+ " TO " + toBean.getSimpleName()
						+ " Sync Successfully record:" + insertTotal);
			}

			JDBCUtil.releaseConnection(fromCon, pstmt, rs);
			JDBCUtil.releaseConnection(toCon, pstmt2, rs);
			JDBCUtil.releaseConnection(null, pstmtInsert, null);
			JDBCUtil.releaseConnection(null, pstmtUpdate, null);

			long end = System.currentTimeMillis();
			long time = end - start;
			System.out.println("total time:" + time + " ms 约" + time / 1000
					+ "秒 ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
