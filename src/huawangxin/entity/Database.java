package huawangxin.entity;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	private String driver;
	private String url;
	private String username;
	private String password;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Database(String driver, String url, String username, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public Connection getConnection() {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username,
					password);
			return conn;
		} catch (Exception e) {
			return null;
		}
	}
}
