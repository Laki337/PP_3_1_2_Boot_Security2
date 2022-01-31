package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
	
	private final RoleRepository roleDao;
	
	public RoleServiceImpl(RoleRepository roleDao) {
		this.roleDao = roleDao;
	}
	
	@Override
	public void createRole(Role role) {
		roleDao.save(role);
	}
	
	@Override
	public Role getRoleById(Long id) {
		return roleDao.getById(id);
	}
	
	@Override
	public void updateRole(Role role) {
		roleDao.save(role);
	}
	
	@Override
	public void deleteRole(Long id) {
		roleDao.delete(roleDao.getById(id));
	}
	
	@Override
	public List<Role> getRoles() {
		return roleDao.findAll();
	}
	
	@Override
	public Role findByName(String name) {
		return roleDao.findUserByNameRole(name);
	}
}
