package com.subin.springmyworkspace.todo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

//Aggregation Root ��ü
//���ջѸ���ü

// ��) Order �ֹ�����
// ��) OrderDetail, OrderLineTime: �ֹ���, �ֹ� ��ǰ���
@Data
@Entity
public class Todo {

	// @Id -> ���̺��� PK(����/��ǥ �÷�)
	@Id
	// @GeneratedValue -> �ʵ� �� ���� ��� ����, IDENTITY�� �����ͺ��̽��� �ڵ��������� ���
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String memo;
	private Long createdTime;

//  @JsonIgnore: JSON���� ��ȯ�Ҷ�������������
	@OneToMany // OneToMany(Order-OrderDetail), OnetoOne(User-Profile),
	// EAGER:������ü ���� �� ���� ��ü ���� ����
	// LAZY: ������ü�� ����� �� ���� ��ü�� �о��, �⺻ ��ġ����
	// ManytoMany(JoinColumn����ȵ�)
	@JoinColumn(name = "todoId") // todoId(�ʵ��), todo_id(�÷���)
	private List<TodoComment> comments;

}
