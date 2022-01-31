package ru.kata.spring.boot_security.demo.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class OnApplicationStartUp implements ApplicationRunner {
	
	private UserService userService;
	private RoleService roleService;
	
	@Autowired
	public OnApplicationStartUp(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Role roleAdmin = new Role("ADMIN");
		Role roleUser = new Role("USER");
		roleService.createRole(roleAdmin);
		roleService.createRole(roleUser);
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(roleUser);
		userService.save(new User("user1", "user1", 20, "user1", "user1", "user1", roleSet));
		userService.save(new User("user2", "user2", 20, "user2", "user2", "user2", roleSet));
		userService.save(new User("user3", "user3", 20, "user3", "user3", "user3", roleSet));
		roleSet.add(roleAdmin);
		userService.save(new User("admin", "admin", 20, "admin", "admin", "admin", roleSet));
	}
}
