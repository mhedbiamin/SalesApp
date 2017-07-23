package com.example.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.dao.RoleRepository;
import com.example.dao.UserRepository;
import com.example.entities.Role;
import com.example.entities.User;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user,String profile) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole=null;
        if (profile.equals("1"))
        
        
       userRole = roleRepository.findByRole("ADMIN");
        
        else
        	userRole = roleRepository.findByRole("USER");
        	
        
        Set<Role> roles=new HashSet<Role>();
        roles.add(userRole);
        userRole = roleRepository.findByRole("USER");
        roles.add(userRole);
        
        user.setRoles(roles);
		userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
