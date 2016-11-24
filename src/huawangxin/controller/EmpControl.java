package huawangxin.controller;

import java.util.Scanner;

import huawangxin.service.EmpService;
import huawangxin.service.impl.EmpServiceImp;

/**
 * 控制器
 * @author huawangxin
 *
 * 
 * @date 2016年11月23日 下午7:32:34
 */
public class EmpControl {
	static EmpServiceImp empServiceImp=new EmpServiceImp();

	public static void main(String[] args) {
		/*
		 * 程序启动后:
		 * 选择1,2,3,4等操作
		 * 1:注册新用户  用户ID从1开始
		 * 2:更改用户信息
		 * 3:删除用户信息
		 * 4:查询用户信息
		 */
		System.out.println("请输入选项:");
		System.out.print("1:注册  ");
		System.out.print("2:登录  ");
		System.out.print("3:修改  ");
		System.out.print("4:删除  ");
		System.out.println("5:查询  ");
		Scanner scanner 
					= new Scanner(System.in);
		int option 
			= Integer.parseInt(
					scanner.nextLine().trim());
		
		switch(option){
		case 1:
			//注册
			empServiceImp.regUser(scanner);
			break;
		case 2:
			//登录
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
