package com.subin.springmyworkspace.fitness;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Fitness {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Long createdTime;

	private String height;
	private String weight;
	private String smm;
	private String fat;

}
