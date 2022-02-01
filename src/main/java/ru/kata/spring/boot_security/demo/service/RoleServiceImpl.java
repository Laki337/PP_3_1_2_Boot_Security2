package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
	
	private final RoleRepository roleRepository;
	
	public RoleServiceImpl(RoleRepository roleDao) {
		this.roleRepository = roleDao;
	}
	
	@Override
	public void createRole(Role role) {
		roleRepository.save(role);
	}
	
	@Override
	public Role getRoleById(Long id) {
		return roleRepository.findById(id).get();
	}
	
	@Override
	public void updateRole(Role role) {
		roleRepository.save(role);
	}
	
	@Override
	public void deleteRole(Long id) {
		roleRepository.delete(roleRepository.getById(id));
	}
	
	@Override
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}
	
	@Override
	public Role findByName(String name) {
		return roleRepository.findUserByNameRole(name);
	}
}
