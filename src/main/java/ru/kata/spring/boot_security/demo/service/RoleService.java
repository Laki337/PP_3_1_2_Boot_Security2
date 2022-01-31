package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
	void createRole(Role role);
	Role getRoleById(Long id);
	void updateRole(Role role);
	void deleteRole(Long id);
	List<Role> getRoles();
	Role findByName(String name);
}
