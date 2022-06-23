package com.isaiah.sketchframe.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "first_name", nullable = false)
	private String firstname;
	@Column(name = "last_name", nullable = false)
	private String lastname;
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	//	Creates relationship between users and their role(s)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "user_id",referencedColumnName = "id"
					),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"
					)
			)
	private Collection<Role> roles;
	//	Creates relationship between users and their artwork (yet to be implemented as a feature)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_artwork",
			joinColumns = @JoinColumn(
					name = "user_id",referencedColumnName = "id"
			),
			inverseJoinColumns = @JoinColumn(
					name = "art_id", referencedColumnName = "id"
			)
	)
	private Collection<Artwork> artwork;
	public User() {

	}

	public User(String firstname, String lastname, String email, String password, String username,
			Collection<Role> roles) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.username = username;
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

}