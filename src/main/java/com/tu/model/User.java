package com.tu.model;

import javax.persistence.*;
import java.util.Set;

/**
 * An entity class which contains the information of a single user. Representing
 * users table in the database.
 * 
 * @author ivan
 */
@Entity
@Table(name = "users")
public class User {

	/**
	 * Unique identifier.
	 */
	private Long id;

	/**
	 * Name of the user.
	 */
	private String username;

	/**
	 * Password of the user.
	 */
	private String password;

	/*
	 * Password confirmation.
	 */
	private String passwordConfirm;

	/*
	 * Set of the roles that the user can play.
	 */
	private Set<Role> roles;

	/*
	 * Retrieves id of the user.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	/**
	 * Set id of the user.
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retrieves name of the user.
	 * @return username 
	 */
	public String getUsername() {
		return username;
	}

	/*
	 * Set name of the user.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retrieves password of the user.
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password of the 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
