package huawangxin.service;

import java.util.Scanner;

/**
 * �ӿ���չ�࣬����ϸ����������ʵ��
 * @author huawangxin
 *
 * 
 * @date 2016��11��23�� ����7:25:55
 */
public interface EmpService {

	/**
	 * ��¼����
	 * @param scanner
	 */
	public void login(Scanner scanner);
	
	/**
	 * ע�᷽��
	 * @param scanner
	 */
	public void regUser(Scanner scanner);
	
	/**
	 * Ԥ���뷽��
	 * @param scanner
	 */
	public void testPreparedStatement(Scanner scanner);
	
	/**
	 * ��ҳ����
	 * @param scanner
	 */
	public void getPage(Scanner scanner);
}
