package huawangxin.utils.ec;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Test {
	public static void main(String[] args) {
		JDBCUtil jdbc = JDBCUtil.create("mssql");
		Connection con =jdbc.getConnection();
		String sql = "select * from emp";
		Map<Long,List<FileObject>> uid2FileObject = new HashMap<Long,List<FileObject>>();
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int count = 1;
			while (rs.next()) {
				FileOutputStream fos;
				try {
					if (rs.next()) {
						//long id = rs.getLong("ID");
						//long uid = rs.getLong("UID");
						String scho_code = rs.getString("scho_code");
						//String fileTitle = rs.getString("FILETITLE");
						String fileName =scho_code+".jpg";
					/*	if(!fileTitle.contains(".")){
							fileTitle = fileTitle + fileExt.toLowerCase();
						}*/
						FileObject fileObject = new FileObject();
					//	fileObject.setFiletitle(fileTitle);
					//	fileObject.setId(id);
					//	fileObject.setUid(uid);
						fileObject.setUploadPath("/u/cms/www/"+fileName);
					//	List<FileObject> fileObjects = uid2FileObject.get(uid);
					//	if(fileObjects==null){
					//		fileObjects = new ArrayList<FileObject>();
					//	}
					//	fileObjects.add(fileObject);
						fos = new FileOutputStream("D:\\upload\\"+fileName);
						InputStream is = rs.getBinaryStream("scho_image");
						byte[] b100 = new byte[100];
						while (is.read(b100) != -1) {
							fos.write(b100);
						}
						fos.close();
						count++;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			JDBCUtil.releaseConnection(con, pstmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Map<Long,List<FileObject>>  getFileObjectMap(){
		JDBCUtil jdbc = JDBCUtil.create("mssql");
		Connection con =jdbc.getConnection();
		String sql = "select * from emp";
		Map<Long,List<FileObject>> uid2FileObject = new HashMap<Long,List<FileObject>>();
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FileOutputStream fos;
				try {
					if (rs.next()) {
						long id = rs.getLong("ID");
						long uid = rs.getLong("UID");
						String fileExt = rs.getString("FILENAME");
						String fileTitle = rs.getString("FILETITLE");
						String fileName = id+fileExt;
						if(!fileTitle.contains(".")){
							fileTitle = fileTitle + fileExt.toLowerCase();
						}
						FileObject fileObject = new FileObject();
						fileObject.setFiletitle(fileTitle);
						fileObject.setId(id);
						fileObject.setUid(uid);
						fileObject.setUploadPath("/u/cms/www/"+fileName);
						List<FileObject> fileObjects = uid2FileObject.get(uid);
						if(fileObjects==null){
							fileObjects = new ArrayList<FileObject>();
						}
						fileObjects.add(fileObject);
						fos = new FileOutputStream("D:\\upload\\"+fileName);
						InputStream is = rs.getBinaryStream("UPFILE");
						byte[] b100 = new byte[100];
						while (is.read(b100) != -1) {
							fos.write(b100);
						}
						fos.close();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			JDBCUtil.releaseConnection(con, pstmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return	uid2FileObject;
	}
}
