package com.itpass.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itpass.quiz.auth.AddService;
import com.itpass.quiz.entity.AddUser;
import com.itpass.quiz.form.AddUserForm;

@Controller
public class AddController {
	@Autowired
	AddService service;
	
	@RequestMapping("/loginAddUser")
	public String showAddUser(Model model) {
		model.addAttribute(new AddUserForm());
		return "loginAddUser";
	}
	
	@RequestMapping("/addUser")
	public String showAddResult(@ModelAttribute AddUserForm addUserForm) {
		AddUser user = new AddUser();
		user.setUserName(addUserForm.getUserName());
		user.setPassword(addUserForm.getPassword());
		service.addUser(user);
		return "loginAddResult";
	}
}
