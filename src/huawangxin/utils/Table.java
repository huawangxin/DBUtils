package huawangxin.utils;

import huawangxin.entity.Database;

public class Table {
	private Class<?> bean;
	private String uid;
	private Database database;

	public Table() {
		super();
	}

	public Table(Class<?> bean, Database database) {
		super();
		this.bean = bean;
		this.database = database;
	}

	public Table(Class<?> bean, Database database, String uid) {
		super();
		this.bean = bean;
		this.database = database;
		this.uid = uid;
	}

	public static Table create(Class<?> bean, Database database) {
		return new Table(bean, database);
	}

	public static Table create(Class<?> bean, Database database, String uid) {
		return new Table(bean, database, uid);
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Class<?> getBean() {
		return bean;
	}

	public void setBean(Class<?> bean) {
		this.bean = bean;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
