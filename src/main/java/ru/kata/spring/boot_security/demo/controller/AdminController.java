package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final UserService userService;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	
	public AdminController( UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	@GetMapping
	public String listUser(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("listUser", userService.getAllUsers());
		model.addAttribute("listRoles", roleService.getRoles());
		model.addAttribute("user", user);
		return "adminPage";
	}
	
	@GetMapping(value = "/newUser")
	public String newUser(Model model) {
		System.err.println("RAZ");
		model.addAttribute("user", new User());
		model.addAttribute("roles", roleService.getRoles());
		return "adminPage";
	}

	@GetMapping(value = "/edit/{id}")
	public String editUser(@PathVariable("id") long id, Model model) {
		model.addAttribute("user", userService.getById(id));
		model.addAttribute("roles", roleService.getRoles());
		return "adminPage";
	}
	@PostMapping(value = "/edit/{id}")
	public String editUser(@ModelAttribute User user, @RequestParam(value = "rolez") Long[] roles) {
	
		Set<Role> roleSet = new HashSet<>();
		for(Long role : roles) {
			System.err.println(roleService.getRoleById(role));
			roleSet.add(roleService.getRoleById(role));
		}
		user.setRoles(roleSet);
		userService.update(user);
		return "redirect:/admin";
	}
	

	@PutMapping(value = "/newUser")
	public String newUser(@ModelAttribute User user, @RequestParam(value = "rolez") Long[] roles) {
		Set<Role> roleSet = new HashSet<>();
		for(Long role : roles) {
			roleSet.add(roleService.getRoleById(role));
		}
		user.setRoles(roleSet);
		userService.save(user);
		return "createNew";
	}

	
	@DeleteMapping(value = "/delete/{id}")
	public String deleteUser(@PathVariable("id") long id) {
		userService.delete(userService.getById(id));
		return "redirect:/admin";
	}
}
