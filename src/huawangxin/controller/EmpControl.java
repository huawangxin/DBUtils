package huawangxin.controller;

import java.util.Scanner;

import huawangxin.service.EmpService;
import huawangxin.service.impl.EmpServiceImp;

/**
 * ������
 * @author huawangxin
 *
 * 
 * @date 2016��11��23�� ����7:32:34
 */
public class EmpControl {
	static EmpServiceImp empServiceImp=new EmpServiceImp();

	public static void main(String[] args) {
		/*
		 * ����������:
		 * ѡ��1,2,3,4�Ȳ���
		 * 1:ע�����û�  �û�ID��1��ʼ
		 * 2:�����û���Ϣ
		 * 3:ɾ���û���Ϣ
		 * 4:��ѯ�û���Ϣ
		 */
		System.out.println("������ѡ��:");
		System.out.print("1:ע��  ");
		System.out.print("2:��¼  ");
		System.out.print("3:�޸�  ");
		System.out.print("4:ɾ��  ");
		System.out.println("5:��ѯ  ");
		Scanner scanner 
					= new Scanner(System.in);
		int option 
			= Integer.parseInt(
					scanner.nextLine().trim());
		
		switch(option){
		case 1:
			//ע��
			empServiceImp.regUser(scanner);
			break;
		case 2:
			//��¼
			empServiceImp.login(scanner);
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		}
	}
}
