package huawangxin.service;

import java.util.Scanner;

/**
 * 接口扩展类，具体细节由其子类实现
 * @author huawangxin
 *
 * 
 * @date 2016年11月23日 下午7:25:55
 */
public interface EmpService {

	/**
	 * 登录方法
	 * @param scanner
	 */
	public void login(Scanner scanner);
	
	/**
	 * 注册方法
	 * @param scanner
	 */
	public void regUser(Scanner scanner);
	
	/**
	 * 预编译方法
	 * @param scanner
	 */
	public void testPreparedStatement(Scanner scanner);
	
	/**
	 * 分页方法
	 * @param scanner
	 */
	public void getPage(Scanner scanner);
}
