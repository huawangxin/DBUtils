package huawangxin.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import huawangxin.entity.Database;
import huawangxin.entity.Emp;
import huawangxin.service.Table2Table;
import huawangxin.service.impl.Table2TableTranSportFinal;
import huawangxin.utils.Condtion;
import huawangxin.utils.Convert;
import huawangxin.utils.Table;

/**
 * 控制器
 * @author huawangxin
 *
 * 
 * @date 2016年11月23日 下午7:32:34
 */
public class EmpControl2 {
	static Map<String,String> c_type = new HashMap<String,String>();
	static {
		c_type.put("1000000","语文");
		c_type.put("2000000","数学");
		c_type.put("3000000","其他");
	}
	public static void main(String[] args) {
		Table2Table table = new Table2TableTranSportFinal();
		final Database database = new Database("com.mysql.jdbc.Driver",
					"jdbc:mysql://127.0.0.1/test", "root", "123456");
		final Database databaseto = new Database("oracle.jdbc.driver.OracleDriver",
				"jdbc:oracle:thin:@127.0.0.1:1521:test", "root", "123456");
			final Connection con4Convert = database.getConnection();
			Convert c = new Convert() {
				@Override
				public Object execute(String columnName, Object obj) {
					if (columnName.endsWith("Time")) {
						if(obj==null)
							return null;
						long dateTime = (Long) obj;
						return new Date(dateTime);
					}
					if (columnName.endsWith("time")) {
						if(obj==null)
							return null;
						long dateTime = (Long) obj;
						return new Date(dateTime);
					}
					
					if (columnName.endsWith("TIME")) {
						if(obj==null)
							return null;
						long dateTime = (Long) obj;
						return new Date(dateTime);
					}
					
					if(columnName.equalsIgnoreCase("C_TYPE")){
						return c_type.get(obj);
					}
					
					if(columnName.equalsIgnoreCase("org_code")){
						StringBuilder orgAllName = new StringBuilder();
						if(obj!=null&&!obj.equals("")){
							String orgUid = (String) obj;
							if(orgUid.length()==18){
								String sql ="select orgcode FROM EMP where id = ? ";
								PreparedStatement pstmt = null;
								ResultSet rs = null; 
								try {
									pstmt = con4Convert.prepareStatement(sql);
									pstmt.setString(1, orgUid);
									rs = pstmt.executeQuery();
									if (rs.next()) {
										String jd = rs.getString(1);
										orgAllName.append(jd);
									}
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
						return orgAllName.toString();
						
					}
					
					if(columnName.equalsIgnoreCase("TABLENAME")){
						StringBuilder orgAllName = new StringBuilder();
						if(obj!=null&&!obj.equals("")){
							String orgUid = (String) obj;
							if(orgUid.length()==18){
								String sql ="select NAME FROM emp where id = ? ";
								PreparedStatement pstmt = null;
								ResultSet rs = null; 
								try {
									pstmt = con4Convert.prepareStatement(sql);
									pstmt.setString(1, orgUid.substring(0, orgUid.length()-9));
									rs = pstmt.executeQuery();
									if (rs.next()) {
										String jd = rs.getString(1);
										orgAllName.append(jd);
									}
									pstmt.setString(1, orgUid.substring(0, orgUid.length()-3));
									rs = pstmt.executeQuery();
									if (rs.next()) {
										String sq = rs.getString(1);
										orgAllName.append(sq);
									}
									pstmt.setString(1, orgUid.substring(0, orgUid.length()));
									rs = pstmt.executeQuery();
									if (rs.next()) {
										String wg = rs.getString(1);
										orgAllName.append(wg);
									}
									
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
						return orgAllName.toString();
						
					}
					
					if (columnName.equalsIgnoreCase("eventScaleType")) {
						if (obj != null) {
							String type = ((String) obj).trim();
							
							if (type.equals("个体性事件")) {
								return "01";
							}else if(type.equals("群体性事件")){
								return "02";
							}else{
								return "";
							}
						}
					}
					if (columnName.equalsIgnoreCase("involvePeoNum")) {
						if (obj != null&&obj.equals("")) {
							Long d = Long.valueOf((String) obj);
							return d;
						}else{
							return 0;
						}
					}
					if (columnName.equalsIgnoreCase("coordinatex")) {
						if (obj != null) {
							try {
							double d = Double.valueOf((String) obj);
							return d;
							} catch (Exception e) {
								return 0.0;
							}
						}else{
							return 0.0;
						}
					}
					if (columnName.equalsIgnoreCase("coordinatey")) {
						if (obj != null) {
							try {
								double d = Double.valueOf((String) obj);
								return d;
								} catch (Exception e) {
									return 0.0;
								}
						}else{
							return 0.0;
						}
					}
					return obj;
				}

				@Override
				public String mapperName(String columnName) {
					
					return columnName;
				}

				@Override
				public boolean isUsed(String columnName) {
					return true;
				}
			};

			table.transport(
					Table.create(Emp.class, database),
					Table.create(Emp.class, databaseto,"id"),
					Condtion.create(c).addWhere("C_TYPE like '3A04%'")
							//.addWhere("status='01'")
			 .addWhere("orgUid LIKE '001%'")
			);
	}
}
