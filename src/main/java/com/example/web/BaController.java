package com.example.web;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.RoleRepository;
import com.example.entities.Compte;
import com.example.entities.Operation;
import com.example.entities.User;
import com.example.metier.Imetier;
import com.example.metier.UserService;

@Controller
public class BaController {
	@Autowired
	private Imetier imetier;
	@Autowired
	UserService userService;
	
	@RequestMapping("/index")
	public ModelAndView index(ModelAndView  modelAndView)
	{    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	User user = userService.findUserByEmail(auth.getName());
	modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName()) ;
	
	modelAndView.setViewName("index");
	return modelAndView;
		
		
		
	}
	
	@RequestMapping("/consulterCompte")
	public String consulterCompte(Model model ,String codeCompte)
	
	{     model.addAttribute("codeCompte",codeCompte);

		try
		{
			Compte compte=imetier.consulterCompte(codeCompte);
			Page<Operation> operations=imetier.listOperation(codeCompte, 0, 4);
			model.addAttribute("compte",compte);
			model.addAttribute("listeOperations",operations.getContent());
		}catch(Exception e)
		{model.addAttribute("exception",e);}
			
		return "index";	
			
		}
	
	@RequestMapping(value="/saveOperation",method=RequestMethod.POST)
	public String saveOperation(Model model ,String typeOperation,String codeCompte,double montant,String codeCompte2)
	
	{  
		try
		{if(typeOperation.equals("VERS"))
		imetier.verser(codeCompte, montant);
	else if(typeOperation.equals("RETR"))
		imetier.retirer(codeCompte, montant);
	else
		imetier.virement(codeCompte, codeCompte2, montant);
		
		}catch (Exception e)
		{
			model.addAttribute("error",e);
			
		}
		return "redirect:/consulterCompte?codeCompte="+codeCompte;	
			
		}
		
	}


