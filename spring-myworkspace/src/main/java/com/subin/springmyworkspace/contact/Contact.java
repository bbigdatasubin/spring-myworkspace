package com.subin.springmyworkspace.contact;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Contact {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	private Long createdTime;
	private String name;
	private String num;
	private String mail;

}
