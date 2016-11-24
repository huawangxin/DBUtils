package huawangxin.utils;

import java.util.Date;

public abstract class Convert {
	public static Date long2Date(long time){
		Date date = new Date(time);
		return date;
	}
	
	public abstract Object execute(String columnName,Object obj);
	
	public abstract String mapperName(String columnName);
	
	public abstract boolean isUsed(String columnName);
}
