package com.example.metier;

import java.util.List;

import com.example.entities.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user,String profile);
	public List<User> getAllUsers();
}
