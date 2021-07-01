package com.subin.springmyworkspace.contact;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ContactComment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String content;
	private Long createdTime; // Long-> nullable, long -> non-nullable(0, zero-base)
	private int contactId;
}
