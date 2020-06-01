package com.boraji.tutorial.spring.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.boraji.tutorial.model.User;
import com.boraji.tutorial.service.UserService;

@Controller
public class HelloWorldController {

	@Autowired
	UserService service;
	
		 @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/" }, method = RequestMethod.GET)
    public String listAllUsers(ModelMap model,final Principal principal) {
 

    	List<User> users = service.findAllUsers();
        model.addAttribute("users", users);
        return "allusers";
    }
	
    
   @PreAuthorize("hasRole('ROLE_USER')")
	   @RequestMapping(value = {"/list" }, method = RequestMethod.GET)
	    public String listAllUsers2(ModelMap model,final Principal principal) {
	 
	        List<User> users = service.findAllUsers();
	        model.addAttribute("users", users);
	        return "allusers";
	    }
	   
	   
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = { "/edit-user-{id}" }, method = RequestMethod.GET)
    public String editUser(@PathVariable int id, ModelMap model) {
        User user  = service.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return "registration";
    }
    
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = { "/edit-user-{id}" }, method = RequestMethod.POST)
    public String updateUser(User user, ModelMap model, @PathVariable int id) {
        service.updateUser(user);
        model.addAttribute("success", "User " + user.getFirstName()  + " updated successfully");
        return "success";
    }
    
   // @PreAuthorize("hasRole('ROLE_DBA')")
    @RequestMapping(value = { "/delete-user-{id}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable int id) {
        service.deleteUser(id);
        return "redirect:/list";
    }
    
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "accessDenied";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}