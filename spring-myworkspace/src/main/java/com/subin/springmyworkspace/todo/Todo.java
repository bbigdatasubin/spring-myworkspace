package com.subin.springmyworkspace.todo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

// @Entitiy = �����ͺ��̽��� ���̺�� ������(mapping)
// ORM: ��ü�� ���̺��� �����Ѵ�.

// class�� ���̺��� pascal-case -> snake-case�� ����
// Todo class -> todo table
// StudentInfo class -> student_info table

// �ʵ�� �÷��� camel-case -> snake-case�� ����
// createdTime field -> created_time column

// �ڵ� ���迡 ���� �����ͺ��̽� ������ ��������� ���
// Auto-migration

@Data
@Entity
public class Todo {

	// @Id -> ���̺��� PK
	@Id
	// @GeneratedValue -> �ʵ尪 ���� ��� ����, IDENTITY�� �����ͺ��̽��� �ڵ��������� ���
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String memo;
	private Long createdTime;

}
