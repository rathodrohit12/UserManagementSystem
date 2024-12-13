package com.rohit.usermanagementsystem.service;

import com.rohit.usermanagementsystem.entity.UserEntity;
import com.rohit.usermanagementsystem.model.User;

import java.util.List;

public interface UserService {
	
	public void registerUser(User user);
	public UserEntity loginUser(String email, String pass);
 	public void updateUser(int id, User user);
	public void deleteUser(int id);
	public List<User> getAllUsers();
	public UserEntity getUserById(int id);
}