package huawangxin.utils.util2;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Convert {
	public static Date long2Date(long time){
		Date date = new Date(time);
		return date;
	}
	
	public static Map<String, Integer> map = new HashMap<String, Integer>();
	
	
	
	
	
	
	
	
	public static int String2Code(String key){
		return map.get(key);
	}
	
	
	public Object execute(String columnName,Object obj){
		if(columnName.equals("birthdate")){
			long dateTime = (Long) obj;
			return new Date(dateTime);
		}
			
		return  obj;
	}
	

}
