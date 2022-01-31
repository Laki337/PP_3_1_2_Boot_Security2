package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final UserService userService;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;

	public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	
	@GetMapping
	public String listUser(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("listUser", userService.getAllUsers());
		model.addAttribute("listRoles", roleService.getRoles());
		model.addAttribute("user",user);
		return "adminPage";
	}
	
	@PostMapping(value = "/edit/{id}")
	public String editUser(@ModelAttribute User user) {

		userService.update(user);
		return "redirect:/admin";
	}
	
	@PostMapping(value = "/newUser")
	public String newUser(@ModelAttribute User user) {
		userService.save(user);
		return "redirect:/admin";
	}
	@PostMapping(value = "/delete/{id}")
	public String deleteUser(@PathVariable("id") long id) {
		userService.delete(userService.getById(id));
		return "redirect:/admin";
	}
}
