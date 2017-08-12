package com.tu.model;

import javax.persistence.*;
import java.util.Set;

/**
 * An entity class which contains the information of a user role. Representing
 * role table in the database.
 * 
 * @author ivan
 */
@Entity
@Table(name = "role")
public class Role {

	/**
	 * Unique identifier.
	 */
	private Long id;

	/**
	 * The name of the role.
	 */
	private String name;

	/**
	 * Set of {@link:User} user that play that role.
	 */
	private Set<User> users;

	/**
	 * Retrieves role id.
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retrieves name of the role.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name of the role.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves Set of users that play that role.
	 * 
	 * @return users
	 */
	@ManyToMany(mappedBy = "roles")
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * Set user of the role.
	 * 
	 * @param users
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
