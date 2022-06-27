package com.isaiah.sketchframe.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
//	Creates the potential role that can be assigned to a registered user
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	Very simple structure. Each role just has a name
	private String name;
	
	public Role() {
		
	}
	public Role(String name) {
		super();
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
