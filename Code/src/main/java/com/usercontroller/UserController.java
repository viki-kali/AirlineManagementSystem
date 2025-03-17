package com.usercontroller;

import java.util.List;

import com.userdao.UserDao;
import com.model.User;

public class UserController {
	UserDao userDao = new UserDao();
	public List<User> getAllUsers(){
		return userDao.getUsers();
	}
	public User getUser(int userId) {
		return userDao.getUser(userId);
	}
	public int insertUser(User user) {
		return userDao.insertUser(user);
	}
	public int getUserId() {
		return userDao.getUserId();
	}
	public User getUsername(int userId,String password) {
		return userDao.getUsername(userId, password);
	}
	public boolean deleteUser(int userId) {
		return userDao.deleteUser(userId);
	}
	public User updateUser(User user) {
		return userDao.updateUser(user);
	}
//	public User confirmUserCredentials(int userId,String password) {
//		return userDao.confirmUserCredentials(userId, password);
//	}
}
