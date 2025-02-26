package com.smarts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smarts.dao.UserRepository;
import com.smarts.entities.User;
import com.smarts.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/home")
	public String home()
	{
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title","About - Smart Contact Manager");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("tittle","Register- Smart Contact Hub");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	@RequestMapping(value = "/do_register",method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result,@RequestParam(value = "agreement",defaultValue = "false") boolean agreement,Model model,HttpSession session)
	{ 
	      try {
	    	   if(!agreement)
				{
					System.out.println("You have not agreed the terms and conditions");
					throw new Exception("You have not agreed the terms and conditions");
				}
	    	   if(result.hasErrors())
	    	   {
	    		   System.out.println("ERROR" +result.toString());
	    		   model.addAttribute("user",user);
	    		   return "signup";
	    	   }
	    	  
				user.setRole("ROLE_USER");
				user.setEnabled(true);
				user.setImageUrl("default.png");
				
				System.out.println("Agreement" +agreement);
				System.out.println("USER" +user);
				
				User  result1 = this.userRepository.save(user);
				model.addAttribute("user",new User());
				session.setAttribute("message", new Message("Successfully Registered !!","alert-success"));
				return "signup";
	       }catch(Exception e) {
				e.printStackTrace();
				model.addAttribute("user",user);
				session.setAttribute("message", new Message("Something went wrong !!" + e.getMessage(),"alert-danger"));
				return "signup";
	       }
	    }
	}
	
	
	


