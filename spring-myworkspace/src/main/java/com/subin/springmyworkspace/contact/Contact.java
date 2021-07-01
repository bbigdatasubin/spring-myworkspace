package com.subin.springmyworkspace.contact;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

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

	@OneToMany // OneToMany(Order-OrderDetail), OnetoOne(User-Profile),
	@JoinColumn(name = "contactId") // contactId(�ʵ��), contacto_id(�÷���)
	private List<ContactComment> comments;

}
