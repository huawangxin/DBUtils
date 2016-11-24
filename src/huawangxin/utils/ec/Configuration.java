package huawangxin.utils.ec;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class Configuration {
	private String url;
	private String driver;
	private String username;
	private String password;
	private String hostName;
	private int smtpPort;
	private String charset;
	private String subject;
	private boolean sslOnConnect;
	private String from;
	private String addcontent;

	private int poolSize;

	public Configuration() {
	}

	public Configuration(String username, String password, String hostName,
			int smtpPort, String charset, String subject, boolean sslOnConnect,
			String from, String addcontent) {
		super();
		this.username = username;
		this.password = password;
		this.hostName = hostName;
		this.smtpPort = smtpPort;
		this.charset = charset;
		this.subject = subject;
		this.sslOnConnect = sslOnConnect;
		this.from = from;
		this.addcontent = addcontent;
	}

	public Configuration(String url, String driver, String username,
			String password) {
		super();
		this.url = url;
		this.driver = driver;
		this.username = username;
		this.password = password;
	}

	public Configuration(String url, String driver, String username,
			String password, String poolSize) {
		this.url = url;
		this.driver = driver;
		this.username = username;
		this.password = password;
		this.poolSize = Integer.valueOf(poolSize);
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isSslOnConnect() {
		return sslOnConnect;
	}

	public void setSslOnConnect(boolean sslOnConnect) {
		this.sslOnConnect = sslOnConnect;
	}

	public String getAddcontent() {
		return addcontent;
	}

	public void setAddcontent(String addcontent) {
		this.addcontent = addcontent;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	
	public static Configuration jiraConfigure() {
		try {
			InputStream in = Configuration.class
					.getResourceAsStream("jira.xml");
			Configuration cfg = load(in);
			return cfg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;// 无法加载配置信息，返回Null
		}
	}

	
	public static Configuration xmglConfigure() {
		try {
			InputStream in = Configuration.class
					.getResourceAsStream("xmgl.xml");
			Configuration cfg = load(in);
			return cfg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;// 无法加载配置信息，返回Null
		}
	}
	
	
	public static Configuration xmglConfigure(String name) {
		try {
			InputStream in = Configuration.class
					.getResourceAsStream(name+".xml");
			Configuration cfg = load(in);
			return cfg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;// 无法加载配置信息，返回Null
		}
	}


	public static Configuration configureemail() {
		try {
			InputStream in = Configuration.class
					.getResourceAsStream("email.xml");
			Configuration cfg = load2(in);
			return cfg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;// 无法加载配置信息，返回Null
		}
	}

	private static Configuration load2(InputStream in) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(in);
		Element email = doc.getRootElement();
		String hostName = email.element("HostName").getText();
		int smtpPort = Integer.valueOf(email.element("SmtpPort").getText());
		String username = email.element("username").getText();
		String password = email.element("password").getText();
		String charset = email.element("Charset").getText();
		String subject = email.element("Subject").getText();
		boolean sslOnConnect = false;
		if ("true".equals(email.element("Subject").getText())) {
			sslOnConnect = true;
		}
		String from = email.element("From").getText();
		String addcontent = email.element("addcontent").getText();
		Configuration cfg = new Configuration(username, password, hostName,
				smtpPort, charset, subject, sslOnConnect, from, addcontent);
		return cfg;
	}

	
	public static Configuration configure(File configFile) {
		try {
			InputStream in = new FileInputStream(configFile);
			Configuration cfg = load(in);
			return cfg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Configuration load(InputStream in) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(in);
		Element jdbc = doc.getRootElement();
		String url = jdbc.element("url").getText();
		String driver = jdbc.element("driver").getText();
		String username = jdbc.element("username").getText();
		String password = jdbc.element("password").getText();
		String poolSize = jdbc.element("poolSize").getText();
		Configuration cfg = new Configuration(url, driver, username, password,
				poolSize);
		return cfg;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(String poolSize) {
		this.poolSize = Integer.valueOf(poolSize);
	}

	// public static void main(String[] args) {
	// Configuration cfg = Configuration.configure(new
	// File("src/tarena/jdbc/mysql.xml"));
	// System.out.println(cfg.getUrl());
	// System.out.println(cfg.getDriver());
	// System.out.println(cfg.getUsername());
	// System.out.println(cfg.getPassword());
	// }
}