package com.subin.springmyworkspace.todo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// 
@RestController
public class TodoController {

	private TodoRepository repo;

	// @Autowired: ��ü�� Spring IoC �����̳ʿ��� �����Ͽ� ������
	// DI(����������, Dependency Injection): ��ü�� ����ϴ� ���� �ƴ� �ܺο��� ��ü�� �����Ͽ� �����Ͽ� �ִ� ��

	// TodoController ��ü�� �����ϴ� ������ TodoRepository �������̽��� �´� ��ü�� ���� �� ����
	// TodoController �ν��Ͻ��� Spring IoC �����̳ʿ��� ������

	// TodoRepository �������̽��� �´� SQL���� ���డ���� ��ü�� ������

	// Entity-Repository: JPA(Java Persistence API)
	// Persistence(����ȭ): �޸𸮿� �ִ� ��ü�� ��ũ �Ǵ� �����ͺ��̽� ���� ���ֹ߼� ��ġ�� ����
	// ���������δ� Hibernate �����ӿ�ũ ���

	@Autowired
	public TodoController(TodoRepository repo) {
		this.repo = repo;
	}

	// GET ��������: //�����ּ�:��Ʈ/todos
	@GetMapping(value = "/todos")
	public List<Todo> getTodoList() {
		return repo.findAll();
	}

	// 1�� �߰�
	// POST /todos {memo:<h1>"Spring�����ϱ�"<h1>�� ����}
	@PostMapping(value = "/todos")
	public Todo addTodo(@RequestBody Todo todo, HttpServletResponse res) {

		// �޸� �� ���̸�, 400���� ó��
		// �����͸� �������� ó���ϴ� ��Ŀ� �°� ������ �ʾ���
		if (todo.getMemo() == null || todo.getMemo().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// ������ ����(validation)�� �ҵ�(sanitization)
		// Ŭ���̾�Ʈ���� �Ѿ���� �����Ϳ� ���� ����
		todo.setCreatedTime(new Date().getTime());

		// repository.save(entity)
		// @Id: �ʵ� ���� ������ insert, ������ update
		return repo.save(todo);
	}

	// 1�� ��ȸ
	// Get /todos/1 -> todo��Ͽ��� id�� 1�� ���ڵ�
	// @GetMapping(value = "/todos/{id}")
	@RequestMapping(method = RequestMethod.GET, value = "todos/{id}")
	public Todo getTodo(@PathVariable int id, HttpServletResponse res) {

		// SELECT ... FROM todo WHERE id = ?
		// null-safe�� ������� ��ü�� ó���ϰ� ��
		Optional<Todo> todo = repo.findById(id);

		// ���ҽ��� ������ 404������ �����
		if (todo.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return todo.get();
		// return todo.orElse(null); // id�� ������ �����ͳ����� ������ �� ��.
	}

	// 1�� ����
	// DELETE /todos/1 -> todo��Ͽ��� id�� 1�� ���ڵ� ����
	@DeleteMapping(value = "todos/{id}")
	public boolean removeTodo(@PathVariable int id, HttpServletResponse res) {

		Optional<Todo> todo = repo.findById(id);

		// ���ҽ��� ������ 404������ �����
		if (todo.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

		// soft-delete: Ư�� �÷��� ������Ʈ�Ͽ� ��ȸ�� �� �Ⱥ��̰���
		// hard-delete: ������ DELELTE������ ���ڵ带 ����
		// JPA repository�� �⺻������ hard-delete ���
		repo.deleteById(id);
		return true;
	}

	// 1�� ����
	// PUT/ todos/1 {"memo":"JPA �����ϱ�"}
	@PutMapping(value = "/todos/{id}")
	public Todo modifyTodo(@PathVariable int id, @RequestBody Todo todo, HttpServletResponse res) {
		// Ư�� �ʵ常 ������Ʈ �ؾ���
		// ������ �Է��� �ý��� �ʵ�� �����ϸ� �ȵ�

		// ���������� ��ȸ �� ����� �����͸� ������ ������ save

		Optional<Todo> findedTodo = repo.findById(id);

		// ���ҽ��� ������ 404������ �����
		if (findedTodo.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// ��ȸ�� �����Ϳ��� ������ �ʵ常 ����
		Todo toUpdateTodo = findedTodo.get(); // ���� ������
		toUpdateTodo.setMemo(todo.getMemo()); // ������ �ʵ�

		// repository.save(entity)
		// id���� �����ϰ� ��ü �ʵ带 ������Ʈ��
		return repo.save(toUpdateTodo);
	}

}