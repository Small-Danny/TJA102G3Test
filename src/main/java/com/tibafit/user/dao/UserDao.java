package com.tibafit.user.dao;

import com.tibafit.user.model.User;
import java.util.List;

public interface UserDao {

	/**
	 * 新增一個使用者
	 * 
	 * @param user 要新增的 User 物件
	 */
	void addUser(User user);

	/**
	 * 更新現有的使用者資料
	 * 
	 * @param user 包含更新後資料的 User 物件
	 */
	void updateUser(User user);

	/**
	 * 查詢所有使用者
	 * 
	 * @return 包含所有使用者的 List
	 */
	List<User> getAllUsers();

	/**
	 * 透過 ID 查詢單一使用者
	 * 
	 * @param id 使用者的 ID
	 * @return 找到的 User 物件，若找不到則回傳 null
	 */
	User getUserById(int id);
	
	
	/**
	 * 
	 * @param email是我們帳號
	 * @return 找到的 User 物件，若找不到則回傳 null
	 */
	public User findByEmail(String email);
	
}