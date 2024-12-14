package com.rohit.usermanagementsystem.service;

import com.rohit.usermanagementsystem.dto.User;
import com.rohit.usermanagementsystem.dto.UserNoPasswordDTO;

import java.util.List;

public interface UserService {
	
	public void registerUser(User user);
	public User loginUser(String email, String pass);
 	public void updateUser(Long id, UserNoPasswordDTO user);
	public void deleteUser(Long id);
	public List<User> getAllUsers();
	public User getUserById(Long id);
	public User getUserByEmail(String email);
	public User getUserByMobile(String mobile);
	public UserNoPasswordDTO convertToUpdateDTO(User user);
}