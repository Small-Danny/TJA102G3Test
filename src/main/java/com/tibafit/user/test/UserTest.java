package com.tibafit.user.test;

import com.google.gson.Gson;
import com.tibafit.user.dao.UserDaoImpl;
import com.tibafit.user.model.User;
import com.tibafit.util.HibernateUtil;

public class UserTest {
	public static void main(String[] args) {

		HibernateUtil.getSessionFactory();
		//新增使用者
		User user = new User();

		user.setPassword("test1234");
		user.setEmail("testuser1234@example.com");
		user.setAccountStatus(1);
		user.setName("testuser");
		user.setForgotPasswordURL("https://example.com/reset/testuser123");

		UserDaoImpl userDao = new UserDaoImpl();

		//呼叫 addUser 方法插入資料
		userDao.addUser(user);

		// 測試完成關閉 SessionFactory
		HibernateUtil.shutdown();

		System.out.println("測試完成！請檢查資料庫。");}}

		// 建立 UserDaoImpl 實例
//		UserDaoImpl userDao = new UserDaoImpl();
//
//		// 呼叫 findByEmail
//		User userFound = userDao.findByEmail("testuser123@example.com");
//		
//
//		// 檢查是否成功找到使用者
//		if (userFound != null) {
//			Gson gson = new Gson();
//			String jsonOutputbefore = gson.toJson(userFound);
//			System.out.println("成功找到使用者");
//			System.out.println("更新前使用者資料：" + jsonOutputbefore);
//			// 更新使用者資料,改名字
//			userFound.setName("噴桶");
//			userDao.updateUser(userFound);
//			// 因為我用merge,所以必須再次查詢，因為他改完不會更新物件本身
//			User userUpdated = userDao.findByEmail("testuser123@example.com");
//			String jsonOutputAfter = gson.toJson(userUpdated);
//			System.out.println("更新後使用者資料：" + jsonOutputAfter);
//		} else {
//			System.out.println("找不到使用者");
//		}
//		
//		
//		//呼叫getUserByid
//		User userById = userDao.getUserById(1);
//		// 檢查是否成功找到使用者
//		if (userById != null) {
//			Gson gson1 = new Gson();
//			String json = gson1.toJson(userById);
//			System.out.println("成功找到使用者");
//			System.out.println("使用者資料：" + json);
//		} else {
//			System.out.println("找不到使用者");
//		}
//		
//		HibernateUtil.shutdown();
//
//	}
//}
