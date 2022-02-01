package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name_role",nullable = false, unique = true)
	private String nameRole;
	
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;
	
	public Role() {
	}
	
	public Role(String nameRole) {
		this.nameRole = nameRole;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNameRole() {
		return nameRole;
	}
	
	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}
	
	public Set<User> getUsers() {
		return users;
	}
	
	public void setUsers(Set<User> userSet) {
		this.users = userSet;
	}
	
	@Override
	public String toString() {
		return nameRole;
	}
	
	@Override
	public String getAuthority() {
		return nameRole;
	}
}