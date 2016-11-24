package huawangxin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.List;

import huawangxin.entity.Emp;

public class EmpUtil {

	public static void main(String[] args) {
		Emp emp=new Emp();
		getDbData(Emp.class, null);
		TableRow row = get4Db(emp);
		List<TableColumn> tableColumns = row.getList();
		for (TableColumn tableColumn : tableColumns) {
			System.out.println(tableColumn.getColumnName());
		}

		System.out.println(getInsertSql(Emp.class));
		System.out.println(getSelectSql(emp, ""));
	}
	/**
	 * 将数据库中查出来的结果集ResultSet 转换成 指定的实体对象集合
	 * @param classType
	 * @param rs
	 * @return
	 */
	public static <T> T getDbData(Class<T> classType, ResultSet rs) {
		T object = null;
		try {
			object = classType.newInstance();
			Field[] fields = classType.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				Class<?> filedType = fields[i].getType();
				String methodSetName = "set" + getMethodName(fieldName);
				Method method = classType.getMethod(methodSetName, filedType);
				if (rs.getObject(fieldName) != null)
					method.invoke(object, rs.getObject(fieldName));
			}
		} catch (NullPointerException e) {
			System.out.println("请确认结果集是否为空!");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("未知错误 ！");
			e.printStackTrace();
		}
		return object;
	}
	private static String getMethodName(String fildeName) {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
	
	/**
	 * ？？？
	 * @param obj
	 * @return
	 */
	public static TableRow get4Db(Object obj) {
		Class<?> classType = obj.getClass();
		Field[] fields = classType.getDeclaredFields();
		TableRow row = new TableRow();
		for (Field field : fields) {
			String fieldName = field.getName();
			Class<?> filedType = field.getType();

			String methodGetName = "get" + getMethodName(fieldName);
			String methodSetName = "set" + getMethodName(fieldName);

			try {
				Method method = classType.getMethod(methodGetName);

				Method method2 = classType.getMethod(methodSetName, filedType);

				Object value = method.invoke(obj);
				method2.invoke(obj, "222");
				TableColumn column = new TableColumn();
				column.setColumnName(fieldName);
				column.setValue(value);
				row.addColumn(column);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	
	/**
	 * ???
	 * @param classType
	 * @return
	 */
	public static String getInsertSql(Class<?> classType) {
		Field[] fields = classType.getDeclaredFields();
		String tableName = classType.getSimpleName().toLowerCase();
		final StringBuilder bsql = new StringBuilder();
		bsql.append("insert into " + tableName + " ( ");
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			bsql.append(fieldName);
			if (i != fields.length - 1)
				bsql.append(" , ");
		}
		bsql.append(" )  VALUES (");
		for (int i = 0; i < fields.length; i++) {
			bsql.append(" ?");
			if (i != fields.length - 1)
				bsql.append(" ,");
		}
		bsql.append(")");
		return bsql.toString();
	}
	
	/**
	 * ???
	 * @param classType
	 * @param getSupper
	 * @return
	 */
	public static String getInsertSql(Class<?> classType,boolean getSupper) {
		Field[] fields = classType.getDeclaredFields();
		Field[] fields2 = new Field[0];
		if(getSupper){
			 fields2 = classType.getSuperclass().getDeclaredFields();
		}
	
		String tableName = classType.getSimpleName().toLowerCase();
		final StringBuilder bsql = new StringBuilder();
		bsql.append("insert into " + tableName + " (");
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			bsql.append(" ");
			bsql.append(fieldName);
			bsql.append(" ,");
		}
	
		for (int i = 0; i < fields2.length; i++) {
			String fieldName = fields2[i].getName();
			bsql.append(" ");
			bsql.append(fieldName);
			bsql.append(" ,");
		}
		
		bsql.deleteCharAt(bsql.length()-1);
		
		bsql.append(" )  VALUES (");
		for (int i = 0; i < fields.length; i++) {
			bsql.append(" ");
			bsql.append("?");
			bsql.append(" ,");
		}
		for (int i = 0; i < fields2.length; i++) {
			bsql.append(" ");
			bsql.append("?");
			bsql.append(" ,");
		}
		bsql.deleteCharAt(bsql.length()-1);
		bsql.append(")");
		return bsql.toString();
	}
	
	/**
	 * ???
	 * @param temple
	 * @param getSupper
	 * @return
	 */
	public static Object[] getInsertSqlObjs(Object temple,boolean getSupper) {
		Class<?> classType = temple.getClass();
		Field[] fields = classType.getDeclaredFields();
		Object[] objects = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			String methodGetName = "get" + getMethodName(fieldName);
			try {
				Method method = classType.getMethod(methodGetName);
				Object value = method.invoke(temple);
				objects[i] = value;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(getSupper){
			Class<?> supperclassType = classType.getSuperclass();
			Field[] fields2 = supperclassType.getDeclaredFields();
			Object[] objects2 = new Object[fields.length+fields2.length];
			for (int i = 0; i < fields.length; i++) {
				objects2[i] = objects[i];
			}
			for (int i = 0; i < fields2.length; i++) {
				String fieldName = fields2[i].getName();
				String methodGetName = "get" + getMethodName(fieldName);
				try {
					Method method = supperclassType.getMethod(methodGetName);
					Object value = method.invoke(temple);
					objects2[fields.length+i] = value;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return objects2;
		}
		return objects;
	}
	
	/**
	 * ???
	 * @param temple
	 * @return
	 */
	public static Object[] getInsertSqlObjs(Object temple) {
		Class<?> classType = temple.getClass();
		Field[] fields = classType.getDeclaredFields();
		Object[] objects = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			String methodGetName = "get" + getMethodName(fieldName);
			try {
				Method method = classType.getMethod(methodGetName);
				Object value = method.invoke(temple);
				objects[i] = value;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return objects;
	}
	
	/**
	 * ???
	 * @param obj
	 * @param where
	 * @return
	 */
	public static String getSelectSql(Object obj, String where) {
		Class<?> classType = obj.getClass();
		Field[] fields = classType.getDeclaredFields();
		String tableName = classType.getSimpleName().toLowerCase();
		final StringBuilder bsql = new StringBuilder();
		bsql.append("select ");
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			bsql.append(fieldName);
			if (i != fields.length - 1)
				bsql.append(" , ");
		}
		bsql.append(" from " + tableName);
		bsql.append(" ").append(where);
		return bsql.toString();
	}
	
	/**
	 * ???
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Class<?> classType = obj.getClass();
		try {
			String methodSetName = "get" + getMethodName(fieldName);
			Method method = classType.getMethod(methodSetName);
			return method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ???
	 * @param obj1
	 * @param classType2
	 * @return
	 */
	public static Object copyLeft2Right(Object obj1, Class<?> classType2) {
		Object obj2 = null;
		try {
			obj2 = classType2.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		copyLeft2Right(obj1, obj2);
		return obj2;
	}

	/**
	 * ???
	 * @param obj1
	 * @param classType2
	 * @param convert
	 * @return
	 */
	public static Object copyLeft2Right(Object obj1, Class<?> classType2, Convert convert) {
		Object obj2 = null;
		try {
			obj2 = classType2.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		copyLeft2Right(obj1, obj2, convert);
		return obj2;
	}

	/**
	 * ???
	 * @param obj1
	 * @param obj2
	 * @param convert
	 */
	public static void copyLeft2Right(Object obj1, Object obj2, Convert convert) {
		Class<?> classType1 = obj1.getClass();
		Class<?> classType2 = obj2.getClass();
		Field[] fields = classType1.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			String methodGetName = "get" + getMethodName(fieldName);
			try {
				// Obj1读取
				Method method1 = classType1.getMethod(methodGetName);
				Object obj1Value = method1.invoke(obj1);
				// Obj2写入
				if (convert != null) {
					obj1Value = convert.execute(fieldName, obj1Value);
					fieldName = convert.mapperName(fieldName);
				}
				Class<?> filedType2 = classType2.getField(fieldName).getType();
				String methodSetName = "set" + getMethodName(fieldName);
				Method method2 = classType2
						.getMethod(methodSetName, filedType2);
				method2.invoke(obj2, obj1Value);
			} catch (Exception e) {

			}
		}
	}

	/**
	 * ???
	 * @param obj1
	 * @param obj2
	 */
	public static void copyLeft2Right(Object obj1, Object obj2) {
		Class<?> classType1 = obj1.getClass();
		Class<?> classType2 = obj2.getClass();
		Field[] fields = classType1.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			Class<?> filedType = fields[i].getType();
			String methodGetName = "get" + getMethodName(fieldName);
			String methodSetName = "set" + getMethodName(fieldName);
			try {
				// Obj1读取
				Method method1 = classType1.getMethod(methodGetName);
				Object obj1Value = method1.invoke(obj1);
				// Obj2写入
				Method method2 = classType2.getMethod(methodSetName, filedType);
				method2.invoke(obj2, obj1Value);
			} catch (Exception e) {

			}
		}
	}
	
	
}
