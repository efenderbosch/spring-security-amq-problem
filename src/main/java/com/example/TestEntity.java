package com.example;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestEntity {

	@Id
	private Long id;
	private String attribute;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

}
