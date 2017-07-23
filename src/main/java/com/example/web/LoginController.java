package com.example.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.RoleRepository;
import com.example.entities.Role;
import com.example.entities.User;

import com.example.metier.UserService;


@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleRepository roleRepository;;

	@RequestMapping(value={ "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/admin/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		/*Role role= new Role();
		Set<Role> roles=new HashSet<Role>(2);
		user.setRoles(roles);*/
		modelAndView.addObject("user", user); 
	/*	modelAndView.addObject("roles1", roleRepository.findAll());*/
		modelAndView.setViewName("admin/registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user,Role role, BindingResult bindingResult,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) { 
			modelAndView.setViewName("registration"); 
		} else {
			 String radiobtnb24=request.getParameter("radiobutton");
			 
			userService.saveUser(user,radiobtnb24);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("admin/registration");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	 @RequestMapping(value="/access-denied")
	    public String Error403(){
	        return "/access-denied";
	    }
	 
	 
	 @RequestMapping(path="/users", method=RequestMethod.GET)
	 @ResponseBody
		public List<User> getAllUsers(){
			return userService.getAllUsers();
		}
}